package com.android.projectpbp

import  android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.android.projectpbp.Model.Menu
import java.io.ByteArrayOutputStream

class DatabaseHelper(context:Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "Penjualan.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "user"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"

        private const val TABLE_MENU = "menu"
        private const val COLUMN_MENU_ID = "id"
        private const val COLUMN_MENU_NAMA ="namamenu"
        private const val COLUMN_MENU_KATEGORI = "kategori"
        private const val COLUMN_MENU_HARGA = "harga"
        private const val COLUMN_MENU_GAMBAR = "menugambar"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USERNAME TEXT," +
                "$COLUMN_PASSWORD TEXT)")
        db?.execSQL(createTableQuery)

        val createMenuQuery = ("CREATE TABLE $TABLE_MENU (" +
                "$COLUMN_MENU_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "$COLUMN_MENU_NAMA TEXT,"+
                "$COLUMN_MENU_HARGA INTEGER)")
        db?.execSQL(createMenuQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)

        val dropMenuQuery = "DROP TABLE IF EXISTS $TABLE_MENU"
        db?.execSQL(dropMenuQuery)
        onCreate(db)
    }

    fun insertUser(username: String, password: String) : Long {
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
        }
        val db = writableDatabase
        return db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun insertMenu(menu : Menu): Long {
        val values = ContentValues().apply {
            put(COLUMN_MENU_NAMA, menu.namamenu)
            put(COLUMN_MENU_KATEGORI, menu.kategori)
            put(COLUMN_MENU_HARGA, menu.harga)
//            put(COLUMN_MENU_GAMBAR, bitmapToByteArray(menu.gambar))
        }
        val db = writableDatabase
        return db.insert(TABLE_MENU, null, values)
        db.close()
    }


        fun getAllMenu(): List<Menu>{
        val menuList = mutableListOf<Menu>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_MENU"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val idmenu = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MENU_ID))
            val namamenu = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_NAMA))
            val kategori = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_KATEGORI))
            val harga = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MENU_HARGA))

            val menu = Menu(idmenu, namamenu, kategori, harga)
            menuList.add(menu)
        }
        cursor.close()
        db.close()
        return menuList
    }

//    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
//        val stream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//        return stream.toByteArray()
//    }
//
//    private fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
//        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
//    }

    fun readUser(username: String, password: String): Boolean{
        val db = readableDatabase
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)

        val userExists = cursor.count > 0
        cursor.close()
        return userExists
    }


    fun updateMenu(menu: Menu){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_MENU_NAMA, menu.namamenu)
            put(COLUMN_MENU_KATEGORI, menu.kategori)
            put(COLUMN_MENU_HARGA, menu.harga)
        }
        val whereClause = "$COLUMN_MENU_ID = ?"
        val whereArgs = arrayOf(menu.idmenu.toString())
        db.update(TABLE_MENU, values, whereClause, whereArgs)
        db.close()
    }

    fun getMenuByID(menuId: Int) : Menu{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_MENU WHERE $COLUMN_MENU_ID = $menuId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MENU_ID))
        val namaMenu = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_NAMA))
        val kategori = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MENU_KATEGORI))
        val hargaMenu = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MENU_HARGA))

        cursor.close()
        db.close()
        return Menu(id, namaMenu, kategori, hargaMenu)
    }

    fun deleteMenu(menuId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(menuId.toString())
        db.delete(TABLE_MENU, whereClause, whereArgs)
        db.close()
    }
}