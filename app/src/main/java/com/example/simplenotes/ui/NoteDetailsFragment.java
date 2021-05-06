package com.example.simplenotes.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simplenotes.R;
import com.example.simplenotes.domain.Note;

public class NoteDetailsFragment extends Fragment implements Observer {

    private static final String ARG_NOTE = "ARG_NOTE";
    private Publisher publisher;
    private ImageView imageView;
    private TextView title;


    public static NoteDetailsFragment newInstance(Note note) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PublisherHolder) {
            publisher = ((PublisherHolder) context).getPublisher();
            publisher.addObserver(this);
        }
    }

    @Override
    public void onDetach() {
        if (publisher != null) {
            publisher.removeObserver(this);
        }
        super.onDetach();
    }

    public NoteDetailsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.note_name);

        Note note = getArguments().getParcelable(ARG_NOTE);
        if (note != null) {
            //imageView = view.findViewById(R.id.image);
            //title = view.findViewById(R.id.note_name);

            imageView.setImageResource(note.getDrawableRes());
            title.setText(note.getTitleRes());
        }
    }

    @Override
    public void updateNote(Note note) {
        imageView.setImageResource(note.getDrawableRes());
        title.setText(note.getTitleRes());
    }
}