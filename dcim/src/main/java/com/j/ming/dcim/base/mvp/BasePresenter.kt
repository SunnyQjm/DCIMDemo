package com.j.ming.dcim.base.mvp

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast
import com.j.ming.dcim.extensions.toast

abstract class BasePresenter<V: BaseView, M: BaseModel>{
    protected lateinit var mView: V
    protected lateinit var mModel: M

    fun toast(info: String, duration: Int = Toast.LENGTH_SHORT){
        if(mView is Context)
            (mView as Context).toast(info, duration)
    }

    fun toast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT){
        if(mView is Context)
            (mView as Context).toast(stringRes, duration)
    }
}

