package com.j.ming.dcim.index

import android.content.Context
import com.j.ming.dcim.model.FileItem
import com.j.ming.dcim.model.SelectItem

class DCIMActivityPresenter(dcimActivity: DCIMActivity) : DCIMActivityContract.Presenter(){
    init {
        mView = dcimActivity
        mModel = DCIMActivityModel(this)
    }

    override fun getFileItems(): List<FileItem> {
        return mModel.getFileItems()
    }

    override fun getSelectItems(): List<SelectItem> {
        return mModel.getSelectItems()
    }

    override fun getData(context: Context) {
        mModel.getData(context)
    }

    override fun getDataSuccess(isFirst: Boolean) {
        mView.getDataSuccess(isFirst)
    }

    override fun selected(position: Int) {
        mModel.selected(position)
    }
}