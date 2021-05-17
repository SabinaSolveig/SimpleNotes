package com.example.simplenotes.domain.router;

import androidx.fragment.app.FragmentManager;

import com.example.simplenotes.R;
import com.example.simplenotes.domain.model.Note;
import com.example.simplenotes.ui.screens.details.NoteDetailsFragment;
import com.example.simplenotes.ui.screens.main.NotesFragment;

public class AppRouter {

    private final FragmentManager fragmentManager;

    public AppRouter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showNotesList() {
        fragmentManager.beginTransaction()
                .replace(R.id.container, new NotesFragment())
                .commit();
    }

    public void editNote(Note note) {
        fragmentManager.beginTransaction()
                .replace(R.id.container, NoteDetailsFragment.newInstance(note))
                .addToBackStack(null)
                .commit();
    }
}
