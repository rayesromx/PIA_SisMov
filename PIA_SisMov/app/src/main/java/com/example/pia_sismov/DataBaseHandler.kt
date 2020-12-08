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
val COL_NAME = "name"
val COL_LASTNAME = "lastName"
val COL_PHONE = "phone"
val COL_UID = "uid"
val COL_ID = "id"

val POSTS_TABLENAME = "POSTS"
val COL_TITLE = "title"
val COL_DESCR = "description"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableuSERS = "CREATE TABLE " + USESRS_TABLENAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " TEXT,"+
                COL_LASTNAME + " TEXT,"+
                COL_EMAIL + " TEXT," +
                COL_PHONE + " TEXT," +
                COL_UID + " TEXT," +
                COL_PWS + "  TEXT)"

        val createTablepOSTS = "CREATE TABLE " + POSTS_TABLENAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_UID + " TEXT," +
                COL_TITLE + " TEXT," +
                COL_DESCR + " TEXT)"

        db?.execSQL(createTableuSERS)
        db?.execSQL(createTablepOSTS)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onCreate(db);
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
    fun insertlOGINData(login: LoginData, user:User) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_EMAIL, user.email)
        contentValues.put(COL_PWS, login.password)
        contentValues.put(COL_NAME, user.name)
        contentValues.put(COL_LASTNAME, user.lastName)
        contentValues.put(COL_PHONE, user.phone)
        contentValues.put(COL_UID, user.uid)
        val result = database.insert(USESRS_TABLENAME, null, contentValues)
        database.close()
    }
    fun udpateLogin(login: LoginData, user:User) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_EMAIL, user.email)
        contentValues.put(COL_PWS, login.password)
        contentValues.put(COL_NAME, user.name)
        contentValues.put(COL_LASTNAME, user.lastName)
        contentValues.put(COL_PHONE, user.phone)
        contentValues.put(COL_UID, user.uid)
        val result = database.update(USESRS_TABLENAME,contentValues,"ID <> 0 ",null)
        database.close()
    }
    fun readLoginData(): MutableList<LoginData> {
        val list: MutableList<LoginData> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $USESRS_TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            val user = LoginData("","")
            user.email = result.getString(result.getColumnIndex(COL_EMAIL))
            user.password = result.getString(result.getColumnIndex(COL_PWS))
            list.add(user)
        }
        result.close()
        return list
    }
    fun readUserData(): MutableList<User> {
        val list: MutableList<User> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $USESRS_TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            val user = User()
            user.email = result.getString(result.getColumnIndex(COL_EMAIL))
            user.name = result.getString(result.getColumnIndex(COL_NAME))
            user.lastName = result.getString(result.getColumnIndex(COL_LASTNAME))
            user.phone = result.getString(result.getColumnIndex(COL_PHONE))
            user.uid = result.getString(result.getColumnIndex(COL_UID))
            list.add(user)
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
        contentValues.put(COL_UID, post.uid)
        contentValues.put(COL_TITLE, post.title)
        contentValues.put(COL_DESCR, post.description)
        val result = database.update(USESRS_TABLENAME,contentValues,COL_UID+ " == $post.uid ",null)
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

    fun deletePost(post:Post): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(POSTS_TABLENAME, COL_UID + "=?", arrayOf(post.uid.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }
}