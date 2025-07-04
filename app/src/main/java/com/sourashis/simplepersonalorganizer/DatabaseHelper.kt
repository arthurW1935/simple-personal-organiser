/*
    Name: Sourashis Sarkar
    Rollno: 2023EBCS656
*/

package com.sourashis.simplepersonalorganizer

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "personal_organizer.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_USERS = "Users"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_USERS (
              $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
              $COL_NAME TEXT,
              $COL_EMAIL TEXT
            );
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun insertUser(name: String, email: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NAME, name)
            put(COL_EMAIL, email)
        }
        return db.insert(TABLE_USERS, null, values)
    }

    fun getAdminUsers(): List<UserListActivity.User> {
        val users = mutableListOf<UserListActivity.User>()
        val db = readableDatabase

        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COL_ID, COL_NAME, COL_EMAIL),
            "$COL_NAME = ?",
            arrayOf("admin"),
            null,
            null,
            "$COL_ID ASC"
        )

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow(COL_ID))
                val name = it.getString(it.getColumnIndexOrThrow(COL_NAME))
                val email = it.getString(it.getColumnIndexOrThrow(COL_EMAIL))
                users.add(UserListActivity.User(id, name, email))
            }
        }

        return users
    }
}