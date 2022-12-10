package com.example.android.myweather.bean

import android.app.Activity
import android.media.Image
import android.widget.ImageView
import com.example.android.myweather.R
import com.squareup.picasso.Picasso

class Sky {

    fun setImage(wea:String?,activity: Activity?,imageView: ImageView){
        when(wea){
            "qing" -> Picasso.with(activity).load(R.drawable.qing).into(imageView)
            "yun" -> Picasso.with(activity).load(R.drawable.yun).into(imageView)
            "bingbao" -> Picasso.with(activity).load(R.drawable.bingbao).into(imageView)
            "lei" -> Picasso.with(activity).load(R.drawable.lei).into(imageView)
            "shachen" -> Picasso.with(activity).load(R.drawable.shachen).into(imageView)
            "wu" -> Picasso.with(activity).load(R.drawable.wu).into(imageView)
            "xue" -> Picasso.with(activity).load(R.drawable.xue).into(imageView)
            "yin" -> Picasso.with(activity).load(R.drawable.yin).into(imageView)
            "yu" -> Picasso.with(activity).load(R.drawable.yu).into(imageView)
            "dayu" -> Picasso.with(activity).load(R.drawable.dayu).into(imageView)
            "leizhenyu" -> Picasso.with(activity).load(R.drawable.leizhenyu).into(imageView)
            "mai" -> Picasso.with(activity).load(R.drawable.mai).into(imageView)
            "xiaoyu" -> Picasso.with(activity).load(R.drawable.xiaoyu).into(imageView)
            "yujiaxue" -> Picasso.with(activity).load(R.drawable.yujiaxue).into(imageView)
            "zhongyu" -> Picasso.with(activity).load(R.drawable.zhongyu).into(imageView)
        }
    }
}