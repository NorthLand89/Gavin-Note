package com.example.eventeverytime.bean;

import android.database.Cursor;

public interface CursorCreateable {
    public Object getObjectByCursor(Cursor cursor);
}