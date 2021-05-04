package com.example.simplenotes;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simplenotes.domain.Note;
import com.example.simplenotes.domain.NotesRepository;

import java.util.List;

public class NotesFragment extends Fragment {

    public interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    private OnNoteClicked onNoteClicked;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnNoteClicked) {
            onNoteClicked = (OnNoteClicked) context;
        }
    }

    @Override
    public void onDetach() {
        onNoteClicked = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Note> notes = new NotesRepository().getNotes();

        LinearLayout notesList = (LinearLayout) view.findViewById(R.id.notes_list);

        for (Note note : notes) {

            View noteView = LayoutInflater.from(requireContext()).inflate(R.layout.item_note, notesList, false);

            noteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openNoteDetails(note);
                }
            });

            ImageView image = noteView.findViewById(R.id.image);
            image.setImageResource(note.getDrawableRes());

            TextView title = noteView.findViewById(R.id.note_name);
            title.setText(note.getTitleRes());

            notesList.addView(noteView);
        }

    }

    private void openNoteDetails(Note note) {

        if (getActivity() instanceof PublisherHolder) {
            PublisherHolder holder = (PublisherHolder) getActivity();
            holder.getPublisher().notify(note);
        }

        if (onNoteClicked != null) {
            onNoteClicked.onNoteClicked(note);
        }
    }
}