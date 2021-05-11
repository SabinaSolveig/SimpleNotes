package com.example.simplenotes.ui.screens.details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.simplenotes.App;
import com.example.simplenotes.R;
import com.example.simplenotes.domain.model.Note;
import com.example.simplenotes.domain.router.AppRouter;
import com.example.simplenotes.domain.router.RouterHolder;

public class NoteDetailsFragment extends Fragment {

    private static final String EXTRA_NOTE = "NoteDetailsFragment.EXTRA_NOTE";
    private Note note;
    private EditText editText;
    private NoteDetailsViewModel viewModel;
    private Bundle bundle;


    public static NoteDetailsFragment newInstance(Note note) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle bundle = new Bundle();
        if (note != null) {
            bundle.putParcelable(EXTRA_NOTE, note);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        editText = view.findViewById(R.id.text);

        bundle = getArguments();

        if (bundle != null) {
            note = bundle.getParcelable(EXTRA_NOTE);
            editText.setText(note.text);
        } else {
            note = new Note();
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_save) {
                    if (editText.getText().length() > 0) {
                        note.text = editText.getText().toString();
                        note.done = false;
                        note.timestamp = System.currentTimeMillis();

                        if (bundle == null) {
                            App.getInstance().getNoteDao().insert(note);
                        } else {
                            App.getInstance().getNoteDao().update(note);
                        }
                    }

                    AppRouter router = ((RouterHolder) getActivity()).getRouter();
                    router.showNotesList();
                    return true;
                }
                return false;
            }
        });
    }
}