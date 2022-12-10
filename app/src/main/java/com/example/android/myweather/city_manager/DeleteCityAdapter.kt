package com.example.android.myweather.city_manager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.android.myweather.R
import com.example.android.myweather.bean.Data
import com.example.android.myweather.bean.WeatherBean
import com.google.gson.Gson

class DeleteCityAdapter(context: Context,mDatas:ArrayList<String>,deleteCitys:ArrayList<String>):BaseAdapter() {

    var context = context
    var mDatas = mDatas
    var deleteCitys = deleteCitys
    override fun getCount(): Int {
        return mDatas.size
    }

    override fun getItem(p0: Int): Any {
        return mDatas.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var holder:ViewHolder? = null
        var convertView = convertView
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_deletecity, null)
            holder = ViewHolder(convertView)
            convertView.setTag(holder)
        }else{
            holder= convertView.getTag() as ViewHolder?
        }
        val city = mDatas.get(position)
        holder?.tv?.setText(city)
        holder?.iv?.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                mDatas.remove(city)
                deleteCitys.add(city)
                notifyDataSetChanged() //删除完毕，提示适配器更新
            }

        })

        return convertView
    }

    class ViewHolder(itemView: View){
        var tv:TextView? = itemView.findViewById(R.id.item_delete_tv)
        var iv:ImageView? = itemView.findViewById(R.id.item_delete_iv)
    }
}