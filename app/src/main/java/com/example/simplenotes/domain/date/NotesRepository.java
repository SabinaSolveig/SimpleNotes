package com.example.simplenotes.domain.date;

import com.example.simplenotes.domain.model.Note;

import java.util.List;

public interface NotesRepository {

    void getNotes(Callback<List<Note>> callback);

    void insert(Note note, Callback<Note> callback);

    void update(Note note, Callback<Object> callback);

    void delete(Note note, Callback<Object> callback);
}
