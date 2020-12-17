package com.example.kenguruexpress.db

import android.provider.BaseColumns

object DbIdClass {
    const val TABLE_NAME = "IdTable"
    const val COLUMN_NAME_EMAIL = "email"
    const val COLUMN_NAME_ID = "id"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "UserIdDB.db"

    const val CREAT_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_NAME_EMAIL TEXT, $COLUMN_NAME_ID INTEGER)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}