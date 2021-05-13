package com.example.simplenotes.ui.screens.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.simplenotes.App;
import com.example.simplenotes.domain.model.Note;

import java.util.List;

public class NotesListViewModel extends ViewModel {

    private final LiveData<List<Note>> noteLiveData =
            App.getInstance().getNoteDao().getAllLiveData();

    public LiveData<List<Note>> getNoteLiveData() {
        return noteLiveData;
    }

    public void deleteNote(Note note) {
        App.getInstance().getNoteDao().delete(note);
    }

    public void saveNote(Note note, String text, boolean add) {
        if (text.length() > 0) {
            note.text = text;
            note.done = false;
            note.timestamp = System.currentTimeMillis();

            if (add) {
                App.getInstance().getNoteDao().insert(note);
            } else {
                App.getInstance().getNoteDao().update(note);
            }
        }
    }
}
