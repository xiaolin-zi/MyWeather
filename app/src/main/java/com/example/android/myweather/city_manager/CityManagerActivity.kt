package com.example.android.myweather.city_manager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import com.example.android.myweather.R
import com.example.android.myweather.database.DBManager
import com.example.android.myweather.database.DatabaseBean

class CityManagerActivity : AppCompatActivity(),View.OnClickListener{
    lateinit var addIv:ImageView
    lateinit var backIv:ImageView
    lateinit var deleteTv:ImageView
    lateinit var cityLv:ListView
    lateinit var mDatas:ArrayList<DatabaseBean> //显示列表数据源
    lateinit var adapter:CityManagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_manager)
        addIv = findViewById(R.id.city_iv_add)
        backIv =findViewById(R.id.city_iv_back)
        deleteTv =findViewById(R.id.city_iv_delete)
        cityLv = findViewById(R.id.city_lv)
        mDatas = ArrayList<DatabaseBean>()
        //添加点击事件
        addIv.setOnClickListener(this)
        deleteTv.setOnClickListener(this)
        backIv.setOnClickListener(this)
        //设置适配器
        adapter = CityManagerAdapter(this, mDatas)
        cityLv.adapter = adapter
    }

    /*获取数据库中的真实数据源，添加到原有数据源当中，提示适配器更新*/
    override fun onResume() {
        super.onResume()
        val list = DBManager.queryAllInfo()
        mDatas.clear()
        mDatas.addAll(list)
        adapter.notifyDataSetChanged()
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.city_iv_add ->{
                var cityCount:Int = DBManager.getCityCount()
                if(cityCount<5){
                    val intent = Intent(this, SearchCityActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"存储城市数量已达上限，请删除后再添加！",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.city_iv_back ->{
                finish()
            }
            R.id.city_iv_delete ->{
                val intent1 = Intent(this, DeleteCityActivity::class.java)
                startActivity(intent1)
            }
        }
    }
}