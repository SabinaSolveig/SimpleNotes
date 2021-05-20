package com.example.simplenotes.ui.screens.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.simplenotes.R;
import com.example.simplenotes.domain.model.Note;
import com.example.simplenotes.domain.router.AppRouter;
import com.example.simplenotes.domain.router.RouterHolder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NotesFragment extends Fragment {

    private NotesListViewModel viewModel;
    private NotesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(NotesListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new NotesAdapter(this);

        adapter.setClickListener(new NotesAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                AppRouter router = ((RouterHolder) getActivity()).getRouter();
                router.editNote(note);
            }
        });

        viewModel.getNoteLiveData().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setItems(notes);
            }
        });

        if (savedInstanceState == null) {
            viewModel.requestNotes();
        }

        RecyclerView list = view.findViewById(R.id.list);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        list.setLayoutManager(lm);
        list.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        list.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppRouter router = ((RouterHolder) getActivity()).getRouter();
                router.editNote(null);
            }
        });
    }

    public void updateNote(Note note) {
        viewModel.updateNote(note);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        requireActivity().getMenuInflater().inflate(R.menu.menu_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_edit) {

            if (getActivity() instanceof RouterHolder) {
                Note note = adapter.getItem(adapter.getLongClickedPosition());
                AppRouter router = ((RouterHolder) getActivity()).getRouter();
                router.editNote(note);
                return true;
            }
        }

        if (item.getItemId() == R.id.action_delete) {
            showAlertDialog();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.alert_dialog_title)
                .setMessage(R.string.alert_dialog_message)
                .setCancelable(false)
                .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Note note = adapter.getItem(adapter.getLongClickedPosition());
                        viewModel.deleteNote(note);
                    }
                })
                .setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
