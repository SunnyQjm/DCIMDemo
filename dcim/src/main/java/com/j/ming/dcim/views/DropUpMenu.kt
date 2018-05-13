package com.j.ming.dcim.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.PopupWindow
import com.j.ming.dcim.R
import com.j.ming.dcim.index.adapter.SelectDirAdapter
import com.j.ming.dcim.model.SelectItem
import com.j.ming.dcim.util.DensityUtil

class DropUpMenu(context: Context, private val adapter: SelectDirAdapter,
                 var onSelectListener: (selectItem: SelectItem, position: Int) -> Unit)
    : PopupWindow(){
    private val menuView: View = LayoutInflater.from(context)
            .inflate(R.layout.drop_up_menu_layout, null, false)
    private val recyclerView = menuView.findViewById<RecyclerView>(R.id.recyclerView)

    init {
        initView(context)
    }

    private fun initView(context: Context) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(RecyclerViewDividerItem(context))
        adapter.bindToRecyclerView(recyclerView)
        //initPopup
        // 设置AccessoryPopup的view
        this.contentView = menuView
        // 设置AccessoryPopup弹出窗体的宽度
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        // 设置AccessoryPopup弹出窗体的高度
        this.height = DensityUtil.getScreenHeight(context) * 2 / 3
//                Math.min(DensityUtil.getScreenHeight(context) * 2 / 3,
//                adapter.data.size * context.resources.getDimensionPixelOffset(R.dimen.select_item_height))
        // 设置AccessoryPopup弹出窗体可点击
        this.isFocusable = true
        this.isOutsideTouchable = true
        this.animationStyle = R.style.BottomPopupWindowStyle
        // 实例化一个ColorDrawable颜色为半透明
        val dw = ColorDrawable(Color.TRANSPARENT)
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw)

        adapter.setOnItemClickListener { adapter, view, position ->
            adapter as SelectDirAdapter
            adapter.getItem(position)?.let {
                onSelectListener(it, position)
            }
        }
    }

    fun show(view: View) {
        showAtLocation(view, Gravity.BOTTOM, 0, view.measuredHeight)
    }

}