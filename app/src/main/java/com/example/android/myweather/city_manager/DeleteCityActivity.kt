package com.example.android.myweather.city_manager

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.example.android.myweather.R
import com.example.android.myweather.database.DBManager

class DeleteCityActivity : AppCompatActivity() ,View.OnClickListener{
    lateinit var errorIv:ImageView
    lateinit var rightIv:ImageView
    lateinit var deleteLv:ListView
    lateinit var mDatas:ArrayList<String> //城市类别信息
    lateinit var deleteCitys:ArrayList<String> //存储了待删除城市的信息
    lateinit var adapter:DeleteCityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_city)
        errorIv = findViewById(R.id.delete_iv_error)
        rightIv = findViewById(R.id.delete_iv_right)
        deleteLv = findViewById(R.id.delete_lv)
        mDatas = ArrayList<String>()
        deleteCitys = ArrayList<String>()
        //设置点击监听事件
        errorIv.setOnClickListener(this)
        rightIv.setOnClickListener(this)

        //适配器设置
        adapter = DeleteCityAdapter(this, mDatas, deleteCitys)
        deleteLv.adapter = adapter
    }


    override fun onResume() {
        super.onResume()
        val cityList:ArrayList<String> = DBManager.queryAllCityName()
        mDatas.addAll(cityList)
        adapter.notifyDataSetChanged()
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.delete_iv_error->{

                //如果删除了数据，退出时提示是否取消操作
                if(deleteCitys.size>0){
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("提示信息").setMessage("您确定取消更改吗？")
                        .setPositiveButton("确定",object :DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                finish() //关闭当前的activity
                            }

                        })
                    builder.setNegativeButton("取消",null)
                    builder.create().show()
                }else{
                    //没有删除数据，自由进出
                    finish()
                }
            }
            R.id.delete_iv_right->{
                //完成操作（将待删除的城市信息，统一进行删除）
                for (i in 0 until deleteCitys.size){
                    val city = deleteCitys.get(i)
                    //调用删除方法
                    DBManager.deleteInfo(city)
                }
                //删除完毕，返回上一层界面
                finish()
            }
        }
    }
}