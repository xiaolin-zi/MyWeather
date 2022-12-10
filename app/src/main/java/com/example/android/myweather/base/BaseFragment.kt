package com.example.android.myweather.base

import androidx.fragment.app.Fragment
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/*
* xuntil加载网络数据的步骤
* 1、声明整体模块
* 2、执行网络请求操作
* */
open class BaseFragment : Fragment(),Callback.CommonCallback<String>{
    fun loadData(path:String){
        val params:RequestParams = RequestParams(path)
        x.http().get(params,this)
    }

    //获取数据成功时回调
    override fun onSuccess(result: String?) {

    }

    //获取数据失败时回调
    override fun onError(ex: Throwable?, isOnCallback: Boolean) {

    }

    //取消请求时回调
    override fun onCancelled(cex: Callback.CancelledException?) {

    }

    //请求完成后回调
    override fun onFinished() {

    }
}