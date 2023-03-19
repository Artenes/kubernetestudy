package io.github.artenes.anotes;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface NotesRepository extends MongoRepository<NoteDocument, UUID> {



}
