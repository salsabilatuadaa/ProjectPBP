package com.android.projectpbp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.android.projectpbp.Activity.UpdateMenu
import com.android.projectpbp.Model.Menu
import com.android.projectpbp.Model.Warung
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

        private const val TABLE_WARUNG = "warung"
        private const val COLUMN_WARUNG_ID = "idwarung"
        private const val COLUMN_WARUNG_NAMA = "namawarung"
        private const val COLUMN_WARUNG_MEJA = "nomormeja"
        private const val COLUMN_WARUNG_GAMBAR = "gambarwarung"
        private const val COLUMN_WARUNG_LOGO = "logowarung"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USERNAME TEXT," +
                "$COLUMN_PASSWORD TEXT)")
        db?.execSQL(createTableQuery)

        val createMenuQuery = ("CREATE TABLE $TABLE_MENU (" +
                "$COLUMN_MENU_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "$COLUMN_MENU_NAMA TEXT," +
                "$COLUMN_MENU_KATEGORI TEXT," +
                "$COLUMN_MENU_HARGA INTEGER,"+
                "$COLUMN_MENU_GAMBAR BLOB)")
        db?.execSQL(createMenuQuery)

        val createWarungQuery = ("CREATE TABLE $TABLE_WARUNG (" +
                "$COLUMN_WARUNG_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "$COLUMN_WARUNG_NAMA TEXT," +
                "$COLUMN_WARUNG_MEJA TEXT," +
                "$COLUMN_WARUNG_GAMBAR BLOB," +
                "$COLUMN_WARUNG_LOGO BLOB)")
        db?.execSQL(createWarungQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)

        val dropMenuQuery = "DROP TABLE IF EXISTS $TABLE_MENU"
        db?.execSQL(dropMenuQuery)

        val dropWarungQuery = "DROP TABLE IF EXISTS $TABLE_WARUNG"
        db?.execSQL(dropWarungQuery)

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
            menu.gambar?.let {
                put(COLUMN_MENU_GAMBAR, bitmapToByteArray(it))
            }
        }
        val db = writableDatabase
        return db.insert(TABLE_MENU, null, values)
        db.close()
    }

    fun insertWarung(warung: Warung) : Long{
        val values = ContentValues().apply {
            put(COLUMN_WARUNG_NAMA, warung.namawarung)
            put(COLUMN_WARUNG_MEJA, warung.nomormeja)
            warung.gambarwarung?.let {
                put(COLUMN_WARUNG_GAMBAR, bitmapToByteArray(it))
            }
            warung.logowarung?.let {
                put(COLUMN_WARUNG_LOGO, bitmapToByteArray(it))
            }
        }
        val db = writableDatabase
        return db.insert(TABLE_WARUNG, null, values)
        db.close()
    }


    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }



    fun readUser(username: String, password: String): Boolean{
        val db = readableDatabase
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)

        val userExists = cursor.count > 0
        cursor.close()
        return userExists
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
            val gambarByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_MENU_GAMBAR))
            val harga = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MENU_HARGA))

            val gambarBitmap = BitmapFactory.decodeByteArray(gambarByteArray, 0, gambarByteArray.size)

            val menu = Menu(idmenu, namamenu, kategori, harga, gambarBitmap)
            menuList.add(menu)
        }
        cursor.close()
        db.close()
        return menuList
    }

    fun getAllWarung(): List<Warung>{
        val warungList = mutableListOf<Warung>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_WARUNG"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val idwarung = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WARUNG_ID))
            val namawarung = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WARUNG_NAMA))
            val nomormeja = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WARUNG_MEJA))
            val gambarByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_WARUNG_GAMBAR))
            val logoByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_WARUNG_LOGO))

            if (gambarByteArray != null && logoByteArray != null) {
                val gambarBitmap = BitmapFactory.decodeByteArray(gambarByteArray, 0, gambarByteArray.size)
                val logoBitmap = BitmapFactory.decodeByteArray(logoByteArray, 0, logoByteArray.size)

                val warung = Warung(idwarung, namawarung, nomormeja, gambarBitmap, logoBitmap)
                warungList.add(warung)
            }
//
        }
        cursor.close()
        db.close()
        return warungList
    }


    fun UpdateMenu(menu: Menu){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_MENU_NAMA, menu.namamenu)
            put(COLUMN_MENU_KATEGORI, menu.kategori)
            put(COLUMN_MENU_HARGA, menu.harga)
            menu.gambar?.let {
                put(COLUMN_MENU_GAMBAR, bitmapToByteArray(it))
            }
        }
        val whereClause = "$COLUMN_MENU_ID = ?"
        val whereArgs = arrayOf(menu.idmenu.toString())
        db.update(TABLE_MENU, values, whereClause, whereArgs)
        db.close()
    }

    fun UpdateWarung(warung: Warung){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_WARUNG_NAMA, warung.namawarung)
            put(COLUMN_WARUNG_MEJA, warung.nomormeja)
            warung.gambarwarung?.let {
                put(COLUMN_WARUNG_GAMBAR, bitmapToByteArray(it))
            }
            warung.logowarung?.let {
                put(COLUMN_WARUNG_LOGO, bitmapToByteArray(it))
            }
        }
        val whereClause = "$COLUMN_WARUNG_ID = ?"
        val whereArgs = arrayOf(warung.idwarung.toString())
        db.update(TABLE_WARUNG, values, whereClause, whereArgs)
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
        val gambarByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_MENU_GAMBAR))
        val hargaMenu = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MENU_HARGA))

        val gambarBitmap = BitmapFactory.decodeByteArray(gambarByteArray, 0, gambarByteArray.size)

        cursor.close()
        db.close()
        return Menu(id, namaMenu, kategori, hargaMenu, gambarBitmap)
    }

    fun getWarungByID(warungId: Int) : Warung{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_WARUNG WHERE $COLUMN_WARUNG_ID = $warungId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WARUNG_ID))
        val namaWarung = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WARUNG_NAMA))
        val nomorMeja = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WARUNG_MEJA))
        val gambarByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_WARUNG_GAMBAR))
        val logoByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_WARUNG_LOGO))

        val gambarBitmap = BitmapFactory.decodeByteArray(gambarByteArray, 0, gambarByteArray.size)
        val logoBitmap = BitmapFactory.decodeByteArray(logoByteArray, 0, logoByteArray.size)

        cursor.close()
        db.close()
        return Warung(id, namaWarung, nomorMeja, gambarBitmap, logoBitmap)
    }

    fun deleteMenu(menuId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_MENU_ID = ?"
        val whereArgs = arrayOf(menuId.toString())
        db.delete(TABLE_MENU, whereClause, whereArgs)
        db.close()
    }

    fun deleteWarung(warungId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_WARUNG_ID = ?"
        val whereArgs = arrayOf(warungId.toString())
        db.delete(TABLE_WARUNG, whereClause, whereArgs)
        db.close()
    }


}