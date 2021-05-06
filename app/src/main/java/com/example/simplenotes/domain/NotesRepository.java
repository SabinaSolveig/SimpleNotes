package com.example.simplenotes.domain;

import com.example.simplenotes.R;

import java.util.ArrayList;
import java.util.List;

public class NotesRepository {

    public List<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note(R.drawable.cat_blue, R.string.markiz));
        notes.add(new Note(R.drawable.cat_red, R.string.barsik));
        notes.add(new Note(R.drawable.cat_white, R.string.mursik));
        return notes;
    }
}
