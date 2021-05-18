package com.example.simplenotes.ui.screens.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simplenotes.domain.date.Callback;
import com.example.simplenotes.domain.date.FirestoreNotesRepository;
import com.example.simplenotes.domain.date.NotesRepository;
import com.example.simplenotes.domain.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesListViewModel extends ViewModel {

    private final MutableLiveData<List<Note>> noteLiveData = new MutableLiveData<>();

    private final NotesRepository repository = new FirestoreNotesRepository();

    public LiveData<List<Note>> getNoteLiveData() {
        return noteLiveData;
    }

    public void requestNotes() {
        repository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> value) {
                noteLiveData.setValue(value);
            }

            @Override
            public void onError(Throwable error) {
            }
        });
    }

    public void deleteNote(Note note) {
        repository.delete(note, new Callback<Object>() {
            @Override
            public void onSuccess(Object value) {

                if (noteLiveData.getValue() != null) {

                    ArrayList<Note> notes = new ArrayList<>(noteLiveData.getValue());

                    notes.remove(note);

                    noteLiveData.setValue(notes);
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void insertNote(Note note) {
        repository.insert(note, new Callback<Note>() {
            @Override
            public void onSuccess(Note value) {
                if (noteLiveData.getValue() != null) {

                    ArrayList<Note> notes = new ArrayList<>(noteLiveData.getValue());

                    notes.add(value);

                    noteLiveData.setValue(notes);
                } else {
                    ArrayList<Note> notes = new ArrayList<>();
                    notes.add(value);

                    noteLiveData.setValue(notes);
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void updateNote(Note note) {
        repository.update(note, new Callback<Object>() {
            @Override
            public void onSuccess(Object value) {

            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void saveNote(Note note, String text, boolean add) {
        if (text.length() > 0) {
            note.text = text;
            note.done = false;
            note.timestamp = System.currentTimeMillis();

            if (add) {
                insertNote(note);
            } else {
                updateNote(note);
            }
        }
    }
}
