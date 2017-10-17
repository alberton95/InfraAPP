package br.com.appinfra.appinfra.persistence.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.com.appinfra.appinfra.models.Usuario
import br.com.appinfra.appinfra.persistence.DBConfig

class UsuarioDAO(var context: Context){

    var config: DBConfig = DBConfig(context, DBConfig.NOME_DB, null, DBConfig.DB_VER)
    var sliteWrite: SQLiteDatabase = config.writableDatabase
    var sliteRead: SQLiteDatabase = config.readableDatabase

    fun insertData(usuario: Usuario): Boolean{

        var cv: ContentValues = ContentValues()
        cv.put(DBConfig.COL_USUARIOS_NOME, usuario.nome)
        cv.put(DBConfig.COL_USUARIOS_USER, usuario.user)
        cv.put(DBConfig.COL_USUARIOS_SENHA, usuario.senha)

        return sliteWrite.insert(DBConfig.TABELA_NOME, null, cv) > 0

    }

    fun readAll(){

    }

    fun closeConn(){
        sliteWrite.close()
        sliteRead.close()
        config.close()
    }

}