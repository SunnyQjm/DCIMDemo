package com.j.ming.dcim.index.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.j.ming.dcim.R
import com.j.ming.dcim.extensions.load
import com.j.ming.dcim.manager.SelectPictureManager
import com.j.ming.dcim.model.FileItem

class DCIMAdapter(mList: List<FileItem>, var showMultiSelect: Boolean = true) :
        BaseQuickAdapter<FileItem, BaseViewHolder>(R.layout.dcim_item2, mList) {

    override fun convert(helper: BaseViewHolder?, item: FileItem?) {
        helper?.let { holder ->
            item?.let { fileItem ->
                holder.setVisible(R.id.imgSelect, showMultiSelect)
                        .getView<ImageView>(R.id.img_picture)
                        .load(fileItem.path)
                if (SelectPictureManager.check(fileItem)){
                    holder.setImageResource(R.id.imgSelect, R.drawable.icon_selected)
                } else {
                    holder.setImageResource(R.id.imgSelect, R.drawable.icon_unselect)
                }
            }
        }
    }
}