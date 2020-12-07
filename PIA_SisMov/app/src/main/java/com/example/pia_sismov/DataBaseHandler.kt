package com.example.pia_sismov

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.widget.Toast
import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.entities.User
import com.example.pia_sismov.presentation.account.model.LoginData

val DATABASENAME = "DATABASE"
val USESRS_TABLENAME = "Users"
val COL_EMAIL = "email"
val COL_PWS = "password"
val COL_ID = "id"

val POSTS_TABLENAME = "POSTS"
val COL_TITLE = "title"
val COL_DESCR = "description"



class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableuSERS = "CREATE TABLE " + USESRS_TABLENAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_EMAIL + " VARCHAR(256)," + COL_PWS + "  VARCHAR(256))"

        val createTablepOSTS = "CREATE TABLE " + POSTS_TABLENAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COL_TITLE + " VARCHAR(256)," + COL_DESCR + "  VARCHAR(256))"

        db?.execSQL(createTableuSERS)
        db?.execSQL(createTablepOSTS)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //onCreate(db);
    }

    fun existLoginData(): Boolean {
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $USESRS_TABLENAME"
        val cursor = db.rawQuery(selectQuery, null)
        var exists = false;
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.moveToNext()) {
                exists = true
            }
        }
        cursor.close()
        return exists
    }

    fun insertlOGINData(user: LoginData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_EMAIL, user.email)
        contentValues.put(COL_PWS, user.password)
        val result = database.insert(USESRS_TABLENAME, null, contentValues)
        database.close()
    }

    fun udpateLogin(user: LoginData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_EMAIL, user.email)
        contentValues.put(COL_PWS, user.password)
        val result = database.update(USESRS_TABLENAME,contentValues,"ID <> 0 ",null)
        database.close()
    }

    fun readLoginData(): MutableList<LoginData> {
        val list: MutableList<LoginData> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $USESRS_TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            //do {
                val user = LoginData("","")
                user.email = result.getString(result.getColumnIndex(COL_EMAIL))
                user.password = result.getString(result.getColumnIndex(COL_PWS))
                list.add(user)
             //   break
            //}
           // while (result.moveToNext())
        }
        result.close()
        return list
    }


    fun insertPost(post: Post) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TITLE, post.title)
        contentValues.put(COL_DESCR, post.description)
        val result = database.insert(POSTS_TABLENAME, null, contentValues)
        database.close()
    }

    fun udpatePost(post: Post) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TITLE, post.title)
        contentValues.put(COL_DESCR, post.description)
        val result = database.update(USESRS_TABLENAME,contentValues,"ID = $post.uid ",null)
        database.close()
    }

    fun readpOSTData(): MutableList<Post> {
        val list: MutableList<Post> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $POSTS_TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do{
            val post = Post()
                post.uid = result.getString(result.getColumnIndex(COL_ID))
                post.title = result.getString(result.getColumnIndex(COL_TITLE))
                post.description = result.getString(result.getColumnIndex(COL_DESCR))
            list.add(post)
            }while (result.moveToNext())
        }
        result.close()
        return list
    }
}