package com.example.android.myweather

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class CityFragmentPageAdapter( fm : FragmentManager,fragmentList: List<Fragment>) : FragmentStatePagerAdapter(fm){

    var fragmentList:List<Fragment> = fragmentList

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }


    /*在删除操作结束后，因为page已经都创建好了，它没有监听到数据已经发生改变，
    * 而回到主界面时会创建Fragment，发起一条请求，根据fragment的city请求到的数据，而此时在数据库中查找不到，
    * 就会重新添加进数据库
    * */


    var childCount = 0 //表示ViewPager包含的页数

    //当ViewPager的页数发生改变后，必须重写两个函数
    override fun notifyDataSetChanged() {
        this.childCount = count
        super.notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        if(childCount>0){
            childCount--
            return POSITION_NONE
        }
        return super.getItemPosition(`object`)
    }

}