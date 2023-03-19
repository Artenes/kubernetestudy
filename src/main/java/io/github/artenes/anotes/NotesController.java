package io.github.artenes.anotes;

import io.minio.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class NotesController {

    private final NotesRepository repository;
    private final Parser parser;
    private final HtmlRenderer htmlRenderer;
    private final ANoteProperties properties;
    private MinioClient minioClient;

    private static final Logger log = LoggerFactory.getLogger(NotesController.class);

    @PostConstruct
    public void init() {
        initMinio();
    }

    @GetMapping("/")
    public String index(Model model) {

        log.info("Received index request 2");
        var notes = repository.findAll();
        Collections.reverse(notes);
        model.addAttribute("notes", notes);
        return "index";

    }

    @GetMapping(value = "/uploads/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody
    byte[] getImageByName(@PathVariable String name) throws Exception {

        var stream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(properties.getMinio().getBucket())
                .object(name)
                .build());

        return IOUtils.toByteArray(stream);

    }

    @PostMapping("/note")
    public String saveNote(
            @RequestParam("image") MultipartFile file,
            @RequestParam String description,
            @RequestParam(required = false) String publish,
            @RequestParam(required = false) String upload,
            Model model) throws Exception {

        if (publish != null && publish.equals("Publish")) {

            if (description != null && !description.isBlank()) {
                var document = parser.parse(description.trim());
                var html = htmlRenderer.render(document);
                repository.save(new NoteDocument(null, html));
                model.addAttribute("description", "");
            }

            return "redirect:/";

        }

        if (upload != null && upload.equals("Upload")) {

            if (file != null && file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank()) {

                String fileId = UUID.randomUUID() + "." + file.getOriginalFilename().split("\\.")[1];

                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(properties.getMinio().getBucket())
                        .object(fileId)
                        .stream(file.getInputStream(), file.getSize(), 6000000)
                        .contentType(file.getContentType())
                        .build());

                model.addAttribute("description", description + "![](/uploads/" + fileId + ")");
                var notes = repository.findAll();
                Collections.reverse(notes);
                model.addAttribute("notes", notes);
                return "index";

            }

        }

        return "redirect:/";

    }

    private void initMinio() {

        var success = false;
        while (!success) {

            try {

                log.info("Config for MinIO: {}, {}", properties.getMinio().getAccessKey(), properties.getMinio().getSecretKey());

                minioClient = MinioClient.builder()
                        .endpoint(properties.getMinio().getHost(), 9000, false)
                        .credentials(properties.getMinio().getAccessKey(), properties.getMinio().getSecretKey())
                        .build();

                var exists = minioClient.bucketExists(BucketExistsArgs.builder()
                        .bucket(properties.getMinio().getBucket())
                        .build());

                if (!exists) {
                    minioClient.makeBucket(MakeBucketArgs.builder()
                            .bucket(properties.getMinio().getBucket())
                            .build());
                }

                success = true;

            } catch (Exception exception) {

                exception.printStackTrace();
                if (properties.getMinio().isReconnectEnabled()) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException exception1) {
                        exception1.printStackTrace();
                    }
                } else {
                    success = true;
                }

            }

        }

    }

}
