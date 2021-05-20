package com.example.simplenotes.domain.date;

import com.example.simplenotes.domain.model.Note;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirestoreNotesRepository implements NotesRepository{

    private static final String NOTES = "notes";
    private static final String TEXT = "text";
    private static final String TIMESTAMP = "timestamp";
    private static final String DONE = "done";

    private final FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    @Override
    public void getNotes(Callback<List<Note>> callback) {

        fireStore.collection(NOTES)
                .orderBy(TIMESTAMP)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        ArrayList<Note> tmp = new ArrayList<>();

                        List<DocumentSnapshot> docs = task.getResult().getDocuments();

                        for (DocumentSnapshot doc : docs) {
                            String id = doc.getId();
                            String text = doc.getString(TEXT);
                            long timestamp = doc.getLong(TIMESTAMP);
                            boolean done = doc.getBoolean(DONE);

                            tmp.add(new Note(id, text, timestamp, done));
                        }
                        callback.onSuccess(tmp);

                    } else {
                        callback.onError(task.getException());
                    }
                });
    }

    @Override
    public void insert(Note note, Callback<Note> callback) {
        HashMap<String, Object> data = new HashMap<>();

        data.put(TEXT, note.text);
        data.put(TIMESTAMP, note.timestamp);
        data.put(DONE, note.done);

        fireStore.collection(NOTES)
                .add(data)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        callback.onSuccess(new Note(task.getResult().getId(), note));
                    } else {
                        callback.onError(task.getException());
                    }
                });
    }

    @Override
    public void update(Note note, Callback<Object> callback) {
        HashMap<String, Object> data = new HashMap<>();

        data.put(TEXT, note.text);
        data.put(TIMESTAMP, note.timestamp);
        data.put(DONE, note.done);

        fireStore.collection(NOTES)
                .document(note.getId())
                .update(data)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        callback.onSuccess(new Object());
                    } else {
                        callback.onError(task.getException());
                    }
                });
    }

    @Override
    public void delete(Note note, Callback<Object> callback) {

        fireStore.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        callback.onSuccess(new Object());
                    } else {
                        callback.onError(task.getException());
                    }
                });
    }
}
