package com.example.android.myweather

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.android.myweather.database.DBManager

class MoreActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var bgTv:TextView
    lateinit var cacheTv:TextView
    lateinit var versionTv:TextView
    lateinit var shareTv:TextView
    lateinit var exbgRg:RadioGroup
    lateinit var backIv:ImageView
    lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)
        bgTv = findViewById(R.id.more_tv_exchangebg);
        cacheTv = findViewById(R.id.more_tv_cache);
        versionTv = findViewById(R.id.more_tv_version);
        shareTv = findViewById(R.id.more_tv_share);
        backIv = findViewById(R.id.more_iv_back);
        exbgRg = findViewById(R.id.more_rg);
        bgTv.setOnClickListener(this);
        cacheTv.setOnClickListener(this);
        shareTv.setOnClickListener(this);
        backIv.setOnClickListener(this);
        pref = getSharedPreferences("bg_pref", MODE_PRIVATE)
        //设置版本信息
        var versionName: String? = getVersionName()
        versionTv.setText("当前版本： v"+versionName)

        setRGListener()
    }

    private fun setRGListener() {
        //设置改变背景图片的单选按钮监听
        exbgRg.setOnCheckedChangeListener(object :RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkId: Int) {
                //获取目前的默认壁纸
                val bg = pref.getInt("bg", 0)
                var editor:SharedPreferences.Editor = pref.edit()
                val intent = Intent(this@MoreActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

                when(checkId){
                    R.id.more_rb_kabi->{
                        if(bg==0){
                            Toast.makeText(this@MoreActivity,"您正在使用该壁纸！",Toast.LENGTH_SHORT).show()
                            return
                        }
                        editor.putInt("bg",0)
                        editor.commit()
                    }
                    R.id.more_rb_riluo->{
                        if(bg==1){
                            Toast.makeText(this@MoreActivity,"您正在使用该壁纸！",Toast.LENGTH_SHORT).show()
                            return
                        }
                        editor.putInt("bg",1)
                        editor.commit()
                    }
                    R.id.more_rb_wanfeng->{
                        if(bg==2){
                            Toast.makeText(this@MoreActivity,"您正在使用该壁纸！",Toast.LENGTH_SHORT).show()
                            return
                        }
                        editor.putInt("bg",2)
                        editor.commit()
                    }
                    R.id.more_rb_bangwan->{
                        if(bg==3){
                            Toast.makeText(this@MoreActivity,"您正在使用该壁纸！",Toast.LENGTH_SHORT).show()
                            return
                        }
                        editor.putInt("bg",3)
                        editor.commit()
                    }
                    R.id.more_rb_yinghua->{
                        if(bg==4){
                            Toast.makeText(this@MoreActivity,"您正在使用该壁纸！",Toast.LENGTH_SHORT).show()
                            return
                        }
                        editor.putInt("bg",4)
                        editor.commit()
                    }
                    R.id.more_rb_tianye->{
                        if(bg==5){
                            Toast.makeText(this@MoreActivity,"您正在使用该壁纸！",Toast.LENGTH_SHORT).show()
                            return
                        }
                        editor.putInt("bg",5)
                        editor.commit()
                    }
                }

                startActivity(intent)
            }

        })

    }

    private fun getVersionName(): String? {
        /*获取应用版本号*/
        val manager = packageManager
        var versionName: String? = null
        try {
            val packageInfo = manager.getPackageInfo(packageName, 0)
            versionName= packageInfo.versionName
        }catch(e:PackageManager.NameNotFoundException){
            e.printStackTrace()
        }

        return versionName
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.more_tv_cache->{
                clearCache()
            }
            R.id.more_tv_exchangebg->{
                if(exbgRg.visibility==View.VISIBLE){
                    exbgRg.visibility=View.GONE
                }else{
                    exbgRg.visibility=View.VISIBLE
                }
            }
            R.id.more_tv_share->{
                //分享
                shareSoftwareMsg("MyWeather是一款集简约和自定义一体的天气预报软件，能够获取所需的天气信息，支持更换壁纸！快来下载！")
            }
            R.id.more_iv_back->{
                //返回
                finish()
            }
        }
    }

    private fun shareSoftwareMsg(s: String) {
        /*分享软件信息*/
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT,s)
        startActivity(Intent.createChooser(intent,"MyWeather"))

    }

    private fun clearCache() {
        /*清除缓存，其实就是把数据库信息清空*/
        val builder = AlertDialog.Builder(this)
        builder.setTitle("提示信息").setMessage("确定要清除缓存吗?")
        builder.setPositiveButton("确定",object :DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                DBManager.deleteAllInfo()
                Toast.makeText(this@MoreActivity,"清除缓存成功！",Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MoreActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }).setNegativeButton("取消",null)
        builder.create().show()
    }
}