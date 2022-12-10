package com.example.android.myweather.database


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.android.myweather.bean.Data

class DBManager {
    companion object{
        lateinit var database:SQLiteDatabase
        /*初始化数据库信息*/
        fun initDB(context: Context){
            val dbHelper = DBHelper(context)
            database = dbHelper.writableDatabase

        }
        /*查找数据库当中城市列表*/
        @SuppressLint("Range")
        fun  queryAllCityName():ArrayList<String>{

            //表名,需要哪些列（全部为null），筛选条件(相当于where），条件字句，参数数组，分组列，分组条件，排序列
            var cursor:Cursor = database.query("info",null,null,null,null,null,null)
            var cityList: ArrayList<String> = ArrayList<String>()
            while (cursor.moveToNext()){
                var city:String = cursor.getString(cursor.getColumnIndex("city"))
                cityList.add(city)
            }
            return cityList
        }

        /*根据城市名称替换信息内容*/
        fun updateInfoByCity(city:String,content:String):Int{
            val values = ContentValues()
            values.put("content",content)
            val update:Int = database.update("info", values, "city=?", arrayOf(city))
            return update
        }

        /*新增城市记录*/
        fun addCityInfo(city:String,content: String):Long{
            val values = ContentValues()
            values.put("city",city)
            values.put("content",content)
            return database.insert("info",null,values)
        }

        /*根据城市名，查询数据库中的内容*/
        @SuppressLint("Range")
        fun queryInfoByCity(city: String): String? {
            var cursor:Cursor = database.query("info",null,"city=?", arrayOf(city),null,null,null)
            if(cursor.count>0){
                cursor.moveToFirst()
                val content:String = cursor.getString(cursor.getColumnIndex("content"))
                return content
            }
            return null
        }

        /*存储城市天气要求最多存储五个城市信息，一旦超过5个城市就不能存储了，获取目前已经存储的数量*/
        fun getCityCount():Int{
            var cursor:Cursor = database.query("info",null,null,null,null,null,null)
            var count = cursor.count
            return count
        }

        /*查询数据库的全部信息*/
        @SuppressLint("Range")
        fun queryAllInfo():ArrayList<DatabaseBean>{
            var cursor:Cursor = database.query("info",null,null,null,null,null,null)
            var list:ArrayList<DatabaseBean> = ArrayList<DatabaseBean>()
            while(cursor.moveToNext()){
                var id:Int = cursor.getInt(cursor.getColumnIndex("_id"))
                var city = cursor.getString(cursor.getColumnIndex("city"))
                val content = cursor.getString(cursor.getColumnIndex("content"))
                val bean:DatabaseBean = DatabaseBean(id, city, content)
                list.add(bean)
            }
            return list
        }

        /*根据城市名称删除数据库中的数据*/
        fun deleteInfo(city:String):Int{
            return database.delete("info","city=?", arrayOf(city))
        }

        /*清空表中的信息*/
        fun deleteAllInfo(){
            var sql = "delete from info"
            database.execSQL(sql)
        }
    }
}