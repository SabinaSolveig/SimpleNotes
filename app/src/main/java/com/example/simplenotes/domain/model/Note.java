package com.example.simplenotes.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Note implements Parcelable {

    private String id;
    public String text;
    public long timestamp;
    public boolean done;

    public Note() {
    }

    public Note(String id, Note note) {
        this.id = id;
        this.text = note.text;
        this.timestamp = note.timestamp;
        this.done = note.done;
    }

    public Note(String id, String text, long timestamp, boolean done) {
        this.id = id;
        this.text = text;
        this.timestamp = timestamp;
        this.done = done;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (!id.equals(note.id)) return false;
        if (timestamp != note.timestamp) return false;
        if (done != note.done) return false;
        return Objects.equals(text, note.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, timestamp, done);
    }

    protected Note(Parcel in) {
        id = in.readString();
        text = in.readString();
        timestamp = in.readLong();
        done = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(text);
        dest.writeLong(timestamp);
        dest.writeByte((byte) (done ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
