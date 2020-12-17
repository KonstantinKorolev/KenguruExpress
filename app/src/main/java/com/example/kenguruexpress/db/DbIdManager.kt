package com.example.kenguruexpress.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DbIdManager(context: Context) {
    val myDbHelper = DbIdHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb() {
        db = myDbHelper.writableDatabase
    }

    fun insertToDb(email: String, id: Int) {
        val values = ContentValues().apply {
            put(DbIdClass.COLUMN_NAME_EMAIL, email)
            put(DbIdClass.COLUMN_NAME_ID, id)

        }
        db?.insert(DbIdClass.TABLE_NAME, null, values)
    }
    fun readDbData(selection: String?, selectionArgs : Array<String>?) : ArrayList<DbList> {
        val dataList = ArrayList<DbList>()
        val cursor = db?.query(DbIdClass.TABLE_NAME, null, selection, selectionArgs,
            null, null, null)

            while (cursor?.moveToNext()!!) {
                val dataEmail = cursor.getString(cursor.getColumnIndex(DbIdClass.COLUMN_NAME_EMAIL))
                val dataId = cursor.getInt(cursor.getColumnIndex(DbIdClass.COLUMN_NAME_ID))
                val item = DbList()
                item.email = dataEmail
                item.id = dataId
                dataList.add(item)
            }

        cursor.close()
        return dataList
        }

    fun closeDb() {
        myDbHelper.close()
    }

}
