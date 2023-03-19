package io.github.artenes.anotes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("anote")
public class ANoteProperties {

    private String uploadDir = "/tmp/uploads/";

    private MinioConfig minio = new MinioConfig();

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MinioConfig {

        private String host;
        private String bucket = "image-storage";
        private String accessKey;
        private String secretKey;
        private boolean useSSL = false;
        private boolean reconnectEnabled = true;

    }

}
