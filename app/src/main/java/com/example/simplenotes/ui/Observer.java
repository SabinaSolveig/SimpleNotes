package com.example.simplenotes.ui;

import com.example.simplenotes.domain.Note;

public interface Observer {

    void updateNote(Note note);
}
