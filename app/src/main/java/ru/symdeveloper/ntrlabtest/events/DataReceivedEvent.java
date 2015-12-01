package ru.symdeveloper.ntrlabtest.events;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.VolleyError;

import ru.symdeveloper.ntrlabtest.model.Entity;

/**
 * Created by Alexander Fokin on 01.12.2015.
 */
public class DataReceivedEvent {
    private Entity entity;
    private VolleyError error;

    public DataReceivedEvent(@NonNull Entity entity) {
        this.entity = entity;
    }

    public DataReceivedEvent(@NonNull VolleyError error) {
        this.error = error;
    }

    public Entity getEntity() {
        return entity;
    }

    public VolleyError getError() {
        return error;
    }

    public boolean hasData() { return entity != null; }
    public boolean isError() { return error != null; }
}
