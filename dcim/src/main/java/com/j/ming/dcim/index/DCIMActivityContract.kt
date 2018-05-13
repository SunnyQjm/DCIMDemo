package com.j.ming.dcim.index

import android.content.Context
import com.j.ming.dcim.base.mvp.BaseModel
import com.j.ming.dcim.base.mvp.BasePresenter
import com.j.ming.dcim.base.mvp.BaseView
import com.j.ming.dcim.model.FileItem
import com.j.ming.dcim.model.SelectItem

interface DCIMActivityContract {
    interface View : BaseView {
        fun getDataSuccess(isFirst: Boolean)
    }

    interface Model : BaseModel {
        fun getFileItems(): List<FileItem>
        fun getSelectItems(): List<SelectItem>
        fun getData(context: Context)
        fun selected(position: Int)
    }

    abstract class Presenter : BasePresenter<View, Model>() {
        internal abstract fun getFileItems(): List<FileItem>
        internal abstract fun getSelectItems(): List<SelectItem>
        internal abstract fun getData(context: Context)
        internal abstract fun getDataSuccess(isFirst: Boolean)
        internal abstract fun selected(position: Int)
    }
}