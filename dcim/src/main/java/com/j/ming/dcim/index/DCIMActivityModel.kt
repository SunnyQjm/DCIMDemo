package com.j.ming.dcim.index

import android.content.Context
import android.util.Log
import com.j.ming.dcim.model.FileItem
import com.j.ming.dcim.model.SelectItem
import com.j.ming.dcim.util.PictureGetter
import java.util.ArrayList

class DCIMActivityModel(val mPresenter: DCIMActivityPresenter) : DCIMActivityContract.Model{
    private val fileItems = ArrayList<FileItem>()
    private val selectItems = mutableListOf<SelectItem>()
    private var stringListMap: Map<String, List<FileItem>>? = null
    private val handler = android.os.Handler()

    override fun getFileItems(): List<FileItem> {
        return fileItems
    }

    override fun getSelectItems(): List<SelectItem> {
        return selectItems
    }

    override fun getData(context: Context) {
        PictureGetter.getPictureSync(context,
                object : PictureGetter.GetDataCallback {
                    override fun onSuccess(map: Map<String, List<FileItem>>, dirs: List<SelectItem>) {
                        Log.e("onSuccess", Thread.currentThread().name)
                        fileItems.clear()
                        selectItems.clear()
                        this@DCIMActivityModel.stringListMap = map
                        if (stringListMap!!.isEmpty())
                            return
                        selectItems.addAll(dirs)
                        selectItems[0].selected = true
                        fileItems.addAll(stringListMap!![selectItems[0].directItem.path]!!)
                        handler.post { mPresenter.getDataSuccess(true) }
                    }

                    override fun onFail(msg: String) {
                        Log.i("tag", msg)
                    }
                })
    }

    override fun selected(position: Int) {
        if (stringListMap == null || stringListMap!!.size <= position)
            return
        val key = selectItems[position]
        selectItems.forEach {
            it.selected = false
        }
        key.selected = true
        fileItems.clear()
        fileItems.addAll(stringListMap!![key.directItem.path]!!)
        mPresenter.getDataSuccess(false)
    }
}