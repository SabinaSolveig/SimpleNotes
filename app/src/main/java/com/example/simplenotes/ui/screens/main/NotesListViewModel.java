package com.example.simplenotes.ui.screens.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.simplenotes.App;
import com.example.simplenotes.domain.model.Note;

import java.util.List;

public class NotesListViewModel extends ViewModel {

    private LiveData<List<Note>> noteLiveData =
            App.getInstance().getNoteDao().getAllLiveData();

    public LiveData<List<Note>> getNoteLiveData() {
        return noteLiveData;
    }
}
