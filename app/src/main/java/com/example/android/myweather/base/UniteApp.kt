package com.example.android.myweather.base

import android.app.Application
import com.example.android.myweather.database.DBHelper
import com.example.android.myweather.database.DBManager
import org.xutils.x

//自定义application，用于加载数据
class UniteApp : Application(){
    override fun onCreate() {
        super.onCreate()

        x.Ext.init(this)
        DBManager.initDB(this)
    }
}