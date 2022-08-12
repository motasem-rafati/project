package com.example.project

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.text.TextUtils
import java.lang.IllegalArgumentException
import java.util.HashMap

class BEST_APP():ContentProvider() {
    companion object {

        val PROVIDER_NAME = "com.example.project.BEST_APP"

        val URL = "content://" + PROVIDER_NAME + "/apps"

        val CONTENT_URI = Uri.parse(URL)

        val _ID = "_id"

        val NAME = "name"

        val TOP = "top"



        private val APPS_PROJECTION_MAP: HashMap<String, String>? = null

        const val APPS = 1

        var APP_ID = 2

        val uriMatcher: UriMatcher? = null

        val DATABASE_NAME = "APP_Store"

        val APPS_TABLE_NAME = "APPS"

        val DATABASE_VERSION = 1

        val CREATE_DB_TABLE = " CREATE TABLE " + APPS_TABLE_NAME +        " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + " name TEXT NOT NULL, " +

                " top TEXT NOT NULL);"

    }

    private var sUriMatcher = UriMatcher(UriMatcher.NO_MATCH);
    init
    {
        sUriMatcher.addURI(PROVIDER_NAME, "APPS", APPS);
        sUriMatcher.addURI(PROVIDER_NAME, "APPS/#",APP_ID);
    }




    private var db: SQLiteDatabase? = null
    /**
     * Helper class that actually creates and manages * the provider's underlying data repository.
     */
    private class DatabaseHelper internal constructor(context: Context?) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(CREATE_DB_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $APPS_TABLE_NAME")
            onCreate(db)
        }
    }
    override fun onCreate(): Boolean {
        val context = context
        val dbHelper = DatabaseHelper(context)
        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.  */
        db = dbHelper.writableDatabase
        return if (db == null) false else true
    }
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        /**
         * Add a new student record
         */
        val rowID = db!!.insert(APPS_TABLE_NAME, "", values)
        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            val _uri = ContentUris.withAppendedId(CONTENT_URI, rowID)
            context!!.contentResolver.notifyChange(_uri, null)
            return _uri
        }
        throw SQLException("Failed to add a record into $uri")
    }
    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        var sortOrder = sortOrder
        val qb = SQLiteQueryBuilder()
        qb.tables = APPS_TABLE_NAME
        /* when (uriMatcher!!.match(uri)) {
            APP_ID -> qb.appendWhere(_ID + "=" + uri.pathSegments[1])
             else -> { null
             }
         }*/
        if (sortOrder == null || sortOrder === "") {
            /*** By default sort on student names*/
            sortOrder = NAME
        }
        val c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder)
        /**
         * register to watch a content URI for changes  */
        c.setNotificationUri(context!!.contentResolver, uri)
        return c
    }
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var count = 0
        when (uriMatcher!!.match(uri)) {
            APPS -> count = db!!.delete(
                APPS_TABLE_NAME, selection,
                selectionArgs
            )
           APP_ID -> {
                val id = uri.pathSegments[1]
                count = db!!.delete(
                    APPS_TABLE_NAME,
                    _ID + " = " + id +
                            if (!TextUtils.isEmpty(selection)) " AND ($selection)" else "",
                    selectionArgs
                )
            }
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        var count = 0
        when (uriMatcher!!.match(uri)) {
            APPS -> count = db!!.update(
                APPS_TABLE_NAME, values, selection,
                selectionArgs
            )
           APP_ID -> count = db!!.update(
                APPS_TABLE_NAME,
                values,
                _ID + " = " + uri.pathSegments[1] + (if (!TextUtils.isEmpty(selection)) " AND ($selection)" else ""),
                selectionArgs
            )
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }
    override fun getType(uri: Uri): String? {
        when (uriMatcher!!.match(uri)) {
            APPS -> return "vnd.android.cursor.dir/vnd.example.APPS"
           APP_ID -> return "vnd.android.cursor.item/vnd.example.APPS"
            else -> throw IllegalArgumentException("Unsupported URI: $uri")
        }
    }
   }