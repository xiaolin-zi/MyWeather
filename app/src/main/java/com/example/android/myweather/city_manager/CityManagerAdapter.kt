package com.example.android.myweather.city_manager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.android.myweather.R
import com.example.android.myweather.bean.Data
import com.example.android.myweather.bean.WeatherBean
import com.example.android.myweather.database.DatabaseBean
import com.google.gson.Gson

class CityManagerAdapter(context: Context,mDatas:ArrayList<DatabaseBean>) : BaseAdapter() {
    var context:Context = context
    var mDatas:ArrayList<DatabaseBean> =mDatas
    override fun getCount(): Int {
        return mDatas.size
    }

    override fun getItem(position: Int): Any {
        return mDatas.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var holder: ViewHolder? = null
        var convertView = convertView
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city_manager, null)
            holder = ViewHolder(convertView)
            convertView.setTag(holder)
        }else{

            holder= convertView.getTag() as ViewHolder?
        }
        val bean = mDatas.get(position)
        holder?.cityTv?.setText(bean.city)
        var weatherBean:WeatherBean = Gson().fromJson(bean.content,WeatherBean::class.java)
        //获取今日天气情况
        var today:Data = weatherBean.data.get(0)
        holder?.conditionTv?.setText("天气:"+today.wea)
        holder?.tempTv?.setText(today.tem)
        holder?.windTv?.setText(today.win.get(0))
        holder?.tempRangeTv?.setText(today.tem2+"~"+today.tem1)

        return convertView

    }

    class ViewHolder(itemView: View?){
        var tempTv : TextView? = itemView?.findViewById(R.id.item_center_tv_temp)
        var cityTv : TextView? = itemView?.findViewById(R.id.item_city_tv_city)
        var conditionTv : TextView? = itemView?.findViewById(R.id.item_center_tv_con)
        var windTv : TextView? = itemView?.findViewById(R.id.item_city_wind)
        var tempRangeTv : TextView? = itemView?.findViewById(R.id.item_city_temprange)
    }
}