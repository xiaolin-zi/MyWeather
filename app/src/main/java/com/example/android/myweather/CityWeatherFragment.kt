package com.example.android.myweather

import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.android.myweather.base.BaseFragment
import com.example.android.myweather.bean.Data
import com.example.android.myweather.bean.Index
import com.example.android.myweather.bean.Sky
import com.example.android.myweather.bean.WeatherBean
import com.example.android.myweather.database.DBManager
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CityWeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CityWeatherFragment : BaseFragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var tempTv : TextView
    lateinit var cityTv : TextView
    lateinit var conditionTv : TextView
    lateinit var windTv : TextView
    lateinit var tempRangeTv : TextView
    lateinit var dateTv : TextView
    lateinit var clothIndexTv : TextView
    lateinit var carIndexTv : TextView
    lateinit var airIndexTv : TextView
    lateinit var coldIndexTv : TextView
    lateinit var sportIndexTv : TextView
    lateinit var raysIndexTv : TextView
    lateinit var dayIv : ImageView
    lateinit var futureLayout: LinearLayout

    lateinit var outLayout: ScrollView

    lateinit var indexList:List<Index>


    lateinit var city:String

    lateinit var pref: SharedPreferences
    var bgNum:Int = 0

    var url1 = "https://www.tianqiapi.com/api?version=v1&city="
    var url2 = "&appid=49863887&appsecret=QmDyGqp7"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view : View = inflater.inflate(R.layout.fragment_city_weather, container, false)
        initView(view)

        exchangeBg()
        //可以通过activity传值获取到当前fragment加载的是那个地方的天气情况
        var bundle : Bundle? = arguments
        city = bundle?.getString("city").toString()
        var url = url1+city+url2;
        loadData(url)
        return view
    }

    override fun onSuccess(result: String?) {
        println("成功了")
        //解析并展示数据
        parseShowData(result)
        //更新数据
        if (result != null) {
            var i:Int = DBManager.updateInfoByCity(city, result)
            if (i<=0){
                //更新数据失败，说明没有这个城市信息，需要增加这个城市记录
                DBManager.addCityInfo(city,result)
            }
        };
    }

    override fun onError(ex: Throwable?, isOnCallback: Boolean) {
        println("失败了")
        //数据库当中查找上一次信息显示在Fragment中
        val s = DBManager.queryInfoByCity(city)
        if(!TextUtils.isEmpty(s)){
            parseShowData(s)
        }
    }

    private fun parseShowData(result: String?) {
        //使用gson解析数据
        var weatherBean:WeatherBean = Gson().fromJson(result, WeatherBean::class.java)
        val daily:ArrayList<Data> = weatherBean.data as ArrayList<Data>
        //获取指数信息集合列表
        indexList = daily.get(0).index

        //设置今日日期
        dateTv.setText(daily.get(0).date)

        cityTv.setText(weatherBean.city)
        //获取今日天气情况
        var todyWeather = daily.get(0)

        //风向
        windTv.setText(todyWeather.win.get(0))

        //气温
        tempRangeTv.setText(todyWeather.tem2+"~"+todyWeather.tem1)
        //实时气温
        tempTv.setText(todyWeather.tem)
        //天气状况
        conditionTv.setText(todyWeather.wea)

        //设置显示的天气情况图片
        var sky = Sky()
        sky.setImage(todyWeather.wea_img,activity,dayIv)


        //获取未来六天天气情况，加载到layout中
        daily.removeAt(0)
        for(i in 0 until  daily.size){
            val itemView:View = LayoutInflater.from(activity).inflate(R.layout.item_main_center, null)
            itemView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            futureLayout.addView(itemView)
            var idateTv:TextView = itemView.findViewById(R.id.item_center_tv_date)
            var iconTv:TextView= itemView.findViewById(R.id.item_center_tv_con)
            var itemrangeTv:TextView= itemView.findViewById(R.id.item_center_tv_temp)
            var iTv:ImageView = itemView.findViewById(R.id.item_center_iv)
            //获取对应日期的天气情况
            var dataBean:Data = daily.get(i)
            idateTv.setText(dataBean.date)
            iconTv.setText(dataBean.wea)
            itemrangeTv.setText(dataBean.tem2+"~"+dataBean.tem1)
            sky.setImage(dataBean.wea_img,activity,iTv)
        }

    }



    fun exchangeBg(){
        pref = activity?.getSharedPreferences("bg_pref", MODE_PRIVATE)!!
        bgNum = pref.getInt("bg", 0)
        when(bgNum){
            0->{
                outLayout.setBackgroundResource(R.drawable.kabi)
            }
            1->{
                outLayout.setBackgroundResource(R.drawable.riluo)
            }
            2->{
                outLayout.setBackgroundResource(R.drawable.wanfeng)
            }
            3->{
                outLayout.setBackgroundResource(R.drawable.bangwan)
            }
            4->{
                outLayout.setBackgroundResource(R.drawable.yinghua)
            }
            5->{
                outLayout.setBackgroundResource(R.drawable.tianye)
            }
        }
    }





    private fun initView(view: View) {
        //用于初始化控件操作
        tempTv = view.findViewById(R.id.frag_tv_currenttemp)
        cityTv = view.findViewById(R.id.frag_tv_city)
        conditionTv = view.findViewById(R.id.frag_tv_condition)
        windTv = view.findViewById(R.id.frag_tv_wind)
        tempRangeTv = view.findViewById(R.id.frag_tv_temprange)
        dateTv = view.findViewById(R.id.frag_tv_date)
        clothIndexTv = view.findViewById(R.id.frag_index_tv_dress)
        carIndexTv = view.findViewById(R.id.frag_index_tv_washcar)
        coldIndexTv = view.findViewById(R.id.frag_index_tv_cold)
        sportIndexTv = view.findViewById(R.id.frag_index_tv_sport)
        raysIndexTv = view.findViewById(R.id.frag_index_tv_rays)
        airIndexTv = view.findViewById(R.id.frag_index_tv_air)
        dayIv = view.findViewById(R.id.frag_iv_today)
        futureLayout = view.findViewById(R.id.frag_center_layout)
        outLayout = view.findViewById(R.id.out_layout)


        //设置点击事件的监听
        clothIndexTv.setOnClickListener(this)
        carIndexTv.setOnClickListener(this)
        coldIndexTv.setOnClickListener(this)
        sportIndexTv.setOnClickListener(this)
        raysIndexTv.setOnClickListener(this)
        airIndexTv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        when(v?.id){
            R.id.frag_index_tv_dress -> {
                builder.setTitle("穿衣指数")
                var indexBean:Index = indexList.get(3)
                var msg:String = indexBean.level+"\n"+indexBean.desc
                builder.setMessage(msg)
                builder.setPositiveButton("确定",null)
            }

            R.id.frag_index_tv_washcar -> {
                builder.setTitle("洗车指数")
                var indexBean:Index = indexList.get(4)
                var msg:String = indexBean.level+"\n"+indexBean.desc
                builder.setMessage(msg)
                builder.setPositiveButton("确定",null)
            }

            R.id.frag_index_tv_cold -> {
                builder.setTitle("血糖指数")
                var indexBean:Index = indexList.get(2)
                var msg:String = indexBean.level+"\n"+indexBean.desc
                builder.setMessage(msg)
                builder.setPositiveButton("确定",null)
            }

            R.id.frag_index_tv_sport -> {
                builder.setTitle("运动指数")
                var indexBean:Index = indexList.get(1)
                var msg:String = indexBean.level+"\n"+indexBean.desc
                builder.setMessage(msg)
                builder.setPositiveButton("确定",null)
            }

            R.id.frag_index_tv_rays -> {
                builder.setTitle("紫外线指数")
                var indexBean:Index = indexList.get(0)
                var msg:String = indexBean.level+"\n"+indexBean.desc
                builder.setMessage(msg)
                builder.setPositiveButton("确定",null)
            }

            R.id.frag_index_tv_air -> {
                builder.setTitle("空气污染指数")
                var indexBean:Index = indexList.get(5)
                var msg:String = indexBean.level+"\n"+indexBean.desc
                builder.setMessage(msg)
                builder.setPositiveButton("确定",null)
            }
        }
        builder.create().show()
    }

     companion object {
         // TODO: Rename and change types and number of parameters
         @JvmStatic
         fun newInstance(param1: String, param2: String) =
             CityWeatherFragment().apply {
                 arguments = Bundle().apply {
                     putString(ARG_PARAM1, param1)
                     putString(ARG_PARAM2, param2)
                 }
             }
     }
}