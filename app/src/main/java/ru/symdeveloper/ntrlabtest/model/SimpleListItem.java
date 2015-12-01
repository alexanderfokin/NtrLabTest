package ru.symdeveloper.ntrlabtest.model;

import android.support.annotation.NonNull;

/**
 * Created by Alexander Fokin on 01.12.2015.
 */
public class SimpleListItem {
    private @NonNull String field;
    private @NonNull String value;

    public SimpleListItem(@NonNull String field, @NonNull String value) {
        this.field = field;
        this.value = value;
    }

    @NonNull
    public String getField() {
        return field;
    }

    @NonNull
    public String getValue() {
        return value;
    }
}
