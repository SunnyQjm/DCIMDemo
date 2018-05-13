package com.j.ming.dcim.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import com.j.ming.dcim.base.mvp.BasePresenter

abstract class MVPBaseActivity<P : BasePresenter<*, *>> : AppCompatActivity() {
    protected lateinit var mPresenter: P
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = onCretePresenter()
    }

    abstract fun onCretePresenter(): P
}