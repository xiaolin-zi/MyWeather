package com.example.android.myweather.city_manager

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewParent
import android.widget.*
import com.example.android.myweather.MainActivity
import com.example.android.myweather.R
import com.example.android.myweather.base.BaseActivity
import com.example.android.myweather.bean.CityBean
import com.google.gson.Gson
import java.text.FieldPosition

class SearchCityActivity : BaseActivity(),View.OnClickListener {
    lateinit var searchEt: EditText
    lateinit var submitIv: ImageView
    lateinit var searchGv: GridView
    var hotCitys = arrayOf(
        "北京", "上海", "广州", "深圳", "珠海", "佛山", "南京", "苏州", "厦门", "长沙", "成都", "福州",
        "杭州", "武汉", "青岛", "西安", "太原", "沈阳", "重庆", "天津", "南宁"
    )
    var url1 = "https://geoapi.qweather.com/v2/city/lookup?location="
    var url2 = "&key=adbf52e687d245d8acd5ba260e707de0"

    lateinit var city:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_city)
        searchEt = findViewById(R.id.search_et)
        submitIv = findViewById(R.id.search_iv_submit)
        searchGv = findViewById(R.id.search_gv)

        submitIv.setOnClickListener(this);

        //设置适配器
        val adapter = ArrayAdapter<String>(this, R.layout.item_hotcity, hotCitys)
        searchGv.adapter = adapter

        setListener()
    }

    /*设置热门城市点击监听事件*/
    private fun setListener() {
        searchGv.setOnItemClickListener(object : AdapterView.OnItemClickListener{

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                city=hotCitys[position]
                var url = url1+city+url2
                loadData(url)
            }

        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.search_iv_submit -> {
                city = searchEt.getText().toString();
                if (!TextUtils.isEmpty(city)) {
                    //判断是否能够找到这个城市
                    var url = url1+city+url2
                    loadData(url)
                } else {
                    Toast.makeText(this, "输入内容不能为空！", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    override fun onSuccess(result: String?) {
        var cityBean:CityBean = Gson().fromJson(result, CityBean::class.java)
        if(cityBean.code.equals("200")&&cityBean.location.get(0).country.equals("中国")){
            //城市存在
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            city = cityBean.location.get(0).name
            intent.putExtra("city", city)
            startActivity(intent)
        }else{
            //城市不存在
            Toast.makeText(this,"${city}不存在或暂无收录该城市天气！",Toast.LENGTH_SHORT).show()
        }
    }
}