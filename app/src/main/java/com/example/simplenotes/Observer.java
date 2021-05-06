package com.example.simplenotes;

import com.example.simplenotes.domain.Note;

public interface Observer {

    void updateNote(Note note);
}
