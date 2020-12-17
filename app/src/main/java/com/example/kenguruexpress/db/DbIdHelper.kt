package com.example.kenguruexpress.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbIdHelper(context: Context) : SQLiteOpenHelper(context, DbIdClass.DATABASE_NAME,
    null, DbIdClass.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DbIdClass.CREAT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DbIdClass.SQL_DELETE_TABLE)
        onCreate(db)
    }
}