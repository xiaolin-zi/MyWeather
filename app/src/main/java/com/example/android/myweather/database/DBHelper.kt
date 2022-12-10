package com.example.android.myweather.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context):SQLiteOpenHelper(context,"myWeather.db",null,1) {


    //创建数据库时执行
    override fun onCreate(db: SQLiteDatabase?) {
        //创建表的操作
        var sql = "create table info(_id integer primary key autoincrement,city varchar(20) unique not null,content text not null)"
        db?.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }


}