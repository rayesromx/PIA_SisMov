package com.example.pia_sismov

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.pia_sismov.domain.entities.Post
import com.example.pia_sismov.domain.entities.User
import com.example.pia_sismov.presentation.account.model.LoginData
import com.example.pia_sismov.presentation.posts.model.DtoDocument
import java.io.IOException

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


val IMG_TABLENAME = "IMGS"
val COL_URI = "uri"
val COL_BLOBBMP = "blobbmp"
val COL_POSTID = "postid"
val COL_TYPE = "type"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(
    context, DATABASENAME, null,
    1
) {
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

        val createTableImgs = "CREATE TABLE " + IMG_TABLENAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_URI + " TEXT," +
                COL_TYPE + " TEXT," +
                COL_BLOBBMP + " BLOB," +
                COL_POSTID + " TEXT)"

        db?.execSQL(createTableImgs)
        db?.execSQL(createTableuSERS)
        db?.execSQL(createTablepOSTS)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onCreate(db);
    }

    fun existLoginData(user:User): Boolean {
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $USESRS_TABLENAME WHERE " + COL_UID + " =?"
        val cursor = db.rawQuery(selectQuery, arrayOf(user.uid))
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

    fun getUserByLoginData(loginData:LoginData): User {
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $USESRS_TABLENAME WHERE " + COL_EMAIL + " =?"
        val cursor = db.rawQuery(selectQuery, arrayOf(loginData.email))
        val user = User()

        if (cursor != null) {
            cursor.moveToFirst()
            user.email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
            user.name = cursor.getString(cursor.getColumnIndex(COL_NAME))
            user.lastName = cursor.getString(cursor.getColumnIndex(COL_LASTNAME))
            user.phone = cursor.getString(cursor.getColumnIndex(COL_PHONE))
            user.uid = cursor.getString(cursor.getColumnIndex(COL_UID))
        }
        cursor.close()
        return user
    }

    fun insertlOGINData(login: LoginData, user: User) {
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
    fun udpateLogin(login: LoginData, user: User) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_EMAIL, user.email)
        contentValues.put(COL_PWS, login.password)
        contentValues.put(COL_NAME, user.name)
        contentValues.put(COL_LASTNAME, user.lastName)
        contentValues.put(COL_PHONE, user.phone)
        contentValues.put(COL_UID, user.uid)
        try{
            val _success = database.update(USESRS_TABLENAME,contentValues, COL_UID + "=?", arrayOf(user.uid)).toLong()
        }catch (ex:Exception){
            val msg = ex.message
        }
        //val result = database.update(USESRS_TABLENAME,contentValues, COL_UID + "=? ",arrayOf(user.uid))
        database.close()
        //insertlOGINData(login, user)
    }
    fun readLoginData(): MutableList<LoginData> {
        val list: MutableList<LoginData> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $USESRS_TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do{
                val user = LoginData("", "")
                user.email = result.getString(result.getColumnIndex(COL_EMAIL))
                user.password = result.getString(result.getColumnIndex(COL_PWS))
                list.add(user)
            }while (result.moveToNext())

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

    fun getUserById(userId:String): User {
        val user = User()
        val db = this.readableDatabase
        val query = "Select * from $USESRS_TABLENAME WHERE " + COL_UID + " =?"
        val result = db.rawQuery(query, arrayOf(userId))
        if (result.moveToFirst()) {
            user.email = result.getString(result.getColumnIndex(COL_EMAIL))
            user.name = result.getString(result.getColumnIndex(COL_NAME))
            user.lastName = result.getString(result.getColumnIndex(COL_LASTNAME))
            user.phone = result.getString(result.getColumnIndex(COL_PHONE))
            user.uid = result.getString(result.getColumnIndex(COL_UID))
        }
        result.close()
        return user
    }


    fun insertPost(post: Post):Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TITLE, post.title)
        contentValues.put(COL_DESCR, post.description)
        val result = database.insert(POSTS_TABLENAME, null, contentValues)
        database.close()
        return result.toInt()
    }




    fun udpatePost(post: Post) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_UID, post.uid)
        contentValues.put(COL_TITLE, post.title)
        contentValues.put(COL_DESCR, post.description)
        val result = database.update(
            USESRS_TABLENAME,
            contentValues,
            COL_UID + " == $post.uid ",
            null
        )
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

    fun deletePost(post: Post): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(POSTS_TABLENAME, COL_ID + "=?", arrayOf(post.uid)).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    @Throws(IOException::class)
    private fun readBytes(context: Context, uri: Uri): ByteArray? =
        context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }

    fun insertImage(img: DtoDocument) {
       /* var fil = File(img.uri.toString())
        val fis = FileInputStream(fil)
        val bytmig = ByteArray(fis.available())
        fis.read(bytmig)*/

        if(img.uri.toString().isBlank())
            return

        val bytmig =readBytes(context,img.uri)

        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_URI, img.uri.path)
        contentValues.put(COL_POSTID, img.postId)
        contentValues.put(COL_TYPE, img.type)
        contentValues.put(COL_BLOBBMP, bytmig)
        val result = database.insert(IMG_TABLENAME, null, contentValues)
        database.close()
    }

    fun readImageData(post: Post): MutableList<DtoDocument> {
        val list: MutableList<DtoDocument> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $IMG_TABLENAME "
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do{
                val postid = result.getString(result.getColumnIndex(COL_POSTID))
                if(postid == post.uid) {
                    val type = result.getString(result.getColumnIndex(COL_TYPE))
                    val img = DtoDocument(type, Uri.EMPTY)
                    img.postId = postid

                    val byte = result.getBlob(result.getColumnIndex(COL_BLOBBMP))
                    img.bmpImage = BitmapFactory.decodeByteArray(byte, 0, byte.size)

                    list.add(img)
                }
            }while (result.moveToNext())
        }
        result.close()
        return list
    }

    fun deleteDocs(post: Post): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(IMG_TABLENAME, COL_POSTID + "=?", arrayOf(post.uid)).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }
}