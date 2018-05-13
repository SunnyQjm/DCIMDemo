package com.j.ming.dcim.index.adapter

import android.content.Context
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.j.ming.dcim.R
import com.j.ming.dcim.extensions.load
import com.j.ming.dcim.model.SelectItem

class SelectDirAdapter(context: Context, mList: List<SelectItem>)
    : BaseQuickAdapter<SelectItem, BaseViewHolder>(R.layout.select_item, mList){
    override fun convert(helper: BaseViewHolder?, item: SelectItem?) {
        if(helper == null || item == null)
            return
        helper.setText(R.id.tvDirName, item.directItem.name)
                .setText(R.id.tvPicturesCount, "${item.childCount}")
                .setVisible(R.id.imgSelected, item.selected)
                .getView<ImageView>(R.id.imgPre)
                .load(item.firstItem?.path ?: "")
    }
}