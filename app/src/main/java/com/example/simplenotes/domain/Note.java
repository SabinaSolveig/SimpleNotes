package com.example.simplenotes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class Note implements Parcelable {
    @DrawableRes
    private final int drawableRes;

    @StringRes
    private final int titleRes;

    public Note(int drawableRes, int titleRes) {
        this.drawableRes = drawableRes;
        this.titleRes = titleRes;
    }

    protected Note(Parcel in) {
        drawableRes = in.readInt();
        titleRes = in.readInt();
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

    public int getDrawableRes() {
        return drawableRes;
    }

    public int getTitleRes() {
        return titleRes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(drawableRes);
        dest.writeInt(titleRes);
    }
}


