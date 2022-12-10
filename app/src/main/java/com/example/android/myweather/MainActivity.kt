package com.example.android.myweather

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.android.myweather.city_manager.CityManagerActivity
import com.example.android.myweather.database.DBManager

class MainActivity : AppCompatActivity() ,View.OnClickListener {
    //lateinit后期初始化
    lateinit var addCityIv : ImageView
    lateinit var  moreIv : ImageView
    lateinit var pointLayout: LinearLayout
    lateinit var mainVp : ViewPager
    //ViewPager的数据源
    lateinit var fragmentList:ArrayList<Fragment>
    //表示需要显示的城市的集合
    lateinit var cityList: ArrayList<String>
    //表示ViewPager的页数指数器显示的集合
    lateinit var imgList: ArrayList<ImageView>
    lateinit var adapter:CityFragmentPageAdapter
    lateinit var pref: SharedPreferences
    var bgNum:Int = 0
    lateinit var outLayout:RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        addCityIv = findViewById(R.id.main_iv_add)
        moreIv = findViewById(R.id.main_iv_more)
        pointLayout = findViewById(R.id.main_layout_point)
        mainVp = findViewById(R.id.main_vp)
        //添加点击事件
        addCityIv.setOnClickListener(this)
        moreIv.setOnClickListener(this)

        outLayout=findViewById(R.id.main_out_layout)
        exchangeBg()

        fragmentList = ArrayList<Fragment>()

        //从数据库获取城市名
        cityList = DBManager.queryAllCityName()

        imgList = ArrayList<ImageView>()

        //用户第一次使用
        if(cityList.size==0){
            cityList.add("广州")
           /* cityList.add("北京")
            cityList.add("揭阳")*/
        }

        //搜索城市后，成功会跳转到此界面，获取一下传过来的city
        var intent = intent
        var city = intent.getStringExtra("city")

        if(!cityList.contains(city)){
            if (city != null) {
                cityList.add(city)
            }
        }

        //初始化ViewPager页面
        initPager()


        adapter = CityFragmentPageAdapter(supportFragmentManager, fragmentList)
        mainVp.adapter = adapter

        //创建小圆点指示器
        initPoint()

        //设置最后一个城市信息
        mainVp.setCurrentItem(fragmentList.size-1)

        //设置ViewPager页面监听
        setPagerListener()


    }

    fun exchangeBg(){
        pref = getSharedPreferences("bg_pref", MODE_PRIVATE)
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

    private fun setPagerListener() {
        /*设置监听*/
        mainVp.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                //页面被选中状态
                for(i in 0 until  imgList.size){
                    imgList.get(i).setImageResource(R.mipmap.a1)
                }
                imgList.get(position).setImageResource(R.mipmap.a2)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    private fun initPoint() {
        //创建底部小圆点
        for (i in 0 until fragmentList.size){
            val pIv:ImageView = ImageView(this)
            pIv.setImageResource(R.mipmap.a1)
            pIv.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            var lp:LinearLayout.LayoutParams = pIv.layoutParams as LinearLayout.LayoutParams
            lp.setMargins(0,0,20,0)
            imgList.add(pIv)
            pointLayout.addView(pIv)
        }
        imgList.get(imgList.size-1).setImageResource(R.mipmap.a2)
    }

    private fun initPager() {
        /*创建Fragment对象，添加到ViewPager数据源当中*/
        for(i in 0 until cityList.size){
            val cwFrag = CityWeatherFragment()
            var bundle = Bundle()
            bundle.putString("city",cityList.get(i))
            cwFrag.arguments = bundle
            fragmentList.add(cwFrag)
        }
    }

    override fun onClick(v: View?) {
        val intent = Intent()
        when(v?.id){
             R.id.main_iv_add->{
                 intent.setClass(this, CityManagerActivity::class.java)
             }
            R.id.main_iv_more-> {
                intent.setClass(this, MoreActivity::class.java)
            }
        }
        startActivity(intent)
    }


    /*当页面重新加载时调用，这个函数在页面获取焦点时进行调用，在这里完成ViewPager页数的更新*/
    override fun onRestart() {
        super.onRestart()
        //获取数据库中剩下的城市集合
        val newCityList = DBManager.queryAllCityName()
        //若在删除操作中已经全部删除
        if(newCityList.size==0){
            //添加一条北京记录
            newCityList.add("北京")
        }

        //清空原来的数据
        cityList.clear()
        //重新添加新数据
        cityList.addAll(newCityList)
        //剩余的城市也要创建对应的fragment数据
        fragmentList.clear()
        initPager()

        //提示adapter更新数据
        adapter.notifyDataSetChanged()

        //指示器数量，即底部的小点点也要进行改变
        imgList.clear()
        pointLayout.removeAllViews() //将布局中的所有元素移出

        //重新初始化小点点
        initPoint()

        //设置显示最后一个城市信息
        mainVp.setCurrentItem(fragmentList.size-1)

    }

}