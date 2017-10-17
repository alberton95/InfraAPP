package br.com.appinfra.appinfra.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBConfig (var context: Context,
                var name: String,
                var cursor: SQLiteDatabase.CursorFactory?,
                var version: Int): SQLiteOpenHelper(context, name, cursor, version){

    companion object {
        val DB_VER = 1
        val NOME_DB = "InfraApp"
        val TABELA_NOME = "Usuarios"
        val COL_USUARIOS_ID = "Id"
        val COL_USUARIOS_USER = "User"
        val COL_USUARIOS_NOME = "Nome"
        val COL_USUARIOS_SENHA = "123"
    }


    private val CREATE_TABLE_USUARIOS =
            " CREATE TABLE " + TABELA_NOME + " ( " +
                    COL_USUARIOS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
                    COL_USUARIOS_USER + " TEXT," +
                    COL_USUARIOS_NOME + " TEXT," +
                    COL_USUARIOS_SENHA + " TEXT ) ";



    override fun onCreate(db: SQLiteDatabase) {

       db.execSQL(CREATE_TABLE_USUARIOS)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_NOME + ";");
        onCreate(db)
    }

    // GRAPH QL


}