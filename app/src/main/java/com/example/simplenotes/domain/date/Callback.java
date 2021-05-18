package com.example.simplenotes.domain.date;

public interface Callback<T> {

    void onSuccess(T value);

    void onError(Throwable error);
}
