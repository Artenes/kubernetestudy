package io.github.artenes.anotes;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "notes")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoteDocument {

    @Id
    private String id;

    private String description;

}
