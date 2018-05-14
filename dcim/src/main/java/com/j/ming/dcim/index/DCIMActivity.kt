package com.j.ming.dcim.index

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.j.ming.dcim.R
import com.j.ming.dcim.base.MVPBaseActivity
import com.j.ming.dcim.index.adapter.DCIMAdapter
import com.j.ming.dcim.index.adapter.SelectDirAdapter
import com.j.ming.dcim.manager.SelectPictureManager
import com.j.ming.dcim.views.DividerGridItemDecoration
import com.j.ming.dcim.views.DropUpMenu
import com.j.ming.dcim.views.WindowUtil
import com.j.ming.easybar2.EasyBar
import com.j.ming.easybar2.init
import kotlinx.android.synthetic.main.activity_dcim.*
import com.j.ming.dcim.extensions.toast
import com.j.ming.dcim.model.event.DCIMIntentEvent
import com.j.ming.easybar2.EasyBarParams
import kotlinx.android.synthetic.main.dir_select_bottom.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.EventBus


class DCIMActivity : MVPBaseActivity<DCIMActivityPresenter>(), DCIMActivityContract.View {

    companion object {
        const val PARAM_MODE = "PARAM_MODE"
        const val PARAM_SAVE_STATE = "PARAM_SAVE_STATE"

        const val RESULT_PATHS = "RESULT_PATHS"

        const val MODE_SELECT_SINGLE = 0
        const val MODE_SELECT_MULTI = 1
    }

    override fun getDataSuccess(isFirst: Boolean) {
        displayProgressCircle(false)
        dcimAdapter.notifyDataSetChanged()
        selectAdapter.notifyDataSetChanged()
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onCretePresenter(): DCIMActivityPresenter =
            DCIMActivityPresenter(this)

    private lateinit var dcimAdapter: DCIMAdapter
    private lateinit var selectAdapter: SelectDirAdapter
    private lateinit var dropMenu: DropUpMenu
    private var mode: Int = MODE_SELECT_MULTI
    private var saveState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dcim)
        mode = intent.getIntExtra(PARAM_MODE, MODE_SELECT_MULTI)
        saveState = intent.getBooleanExtra(PARAM_SAVE_STATE, false)
        initView()
    }

    private fun initView() {
        // init bar
        easyBar.init(mode = EasyBar.Mode.ICON, titleRes = R.string.dcim, leftRes = R.drawable.back,
                rightRes = R.drawable.icon_sure,
                leftCallback = {
                    onBackPressed()
                }, rightCallback = {
            Intent().let {
                it.putExtra(RESULT_PATHS, SelectPictureManager.selectedPath())
                setResult(0, it)
                onBackPressed()
            }
        })
        val backgroundColorRes = R.color.colorPrimary
        easyBar.setBackgroundColor(resources.getColor(backgroundColorRes))

        dcimAdapter = DCIMAdapter(mPresenter.getFileItems())
        recyclerView.layoutManager = GridLayoutManager(this, 3,
                GridLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerGridItemDecoration(this))
        dcimAdapter.bindToRecyclerView(recyclerView)
        dcimAdapter.setOnItemClickListener { adapter, view, position ->
            adapter as DCIMAdapter
            adapter.getItem(position)?.let { item->
                when(mode){
                    MODE_SELECT_SINGLE -> {     //单选模式，则选中就返回
                        Intent().let {
                            it.putExtra(RESULT_PATHS, arrayOf(item.path))
                            setResult(0, it)
                            onBackPressed()
                        }
                    }
                    MODE_SELECT_MULTI -> {      //多选模式，则切换点中item的选中状态
                        SelectPictureManager.toggle(item)
                        adapter.notifyItemChanged(position)
                    }
                }
            }
        }
        when(mode){
            MODE_SELECT_MULTI -> {
                dcimAdapter.showMultiSelect = true
            }
            MODE_SELECT_SINGLE -> {
                dcimAdapter.showMultiSelect = false
                saveState = false
            }
        }


        selectAdapter = SelectDirAdapter(this, mPresenter.getSelectItems())
        dropMenu = DropUpMenu(this, selectAdapter, { selctedItem, position ->
            mPresenter.selected(position = position)
            dropMenu.dismiss()
            selectedText.text = selctedItem.directItem.name
        })

        bottom.setOnClickListener {
            if (dropMenu.isShowing) {
                dropMenu.dismiss()
                WindowUtil.changeWindowAlpha(this, false)
            } else {
                dropMenu.show(it)
                WindowUtil.changeWindowAlpha(this, true)
            }
        }
        dropMenu.setOnDismissListener {
            WindowUtil.changeWindowAlpha(this, false)
        }
        //解决android6.0权限动态申请问题(先放着，自行解决)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //没有授权
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 0)
            } else {
                //已经授权
                loadData()
            }
        } else {
            loadData()
        }
    }

    private fun loadData() {
        mPresenter.getData(this)
    }

    override fun onBackPressed() {
        if (!saveState)
            SelectPictureManager.clear()
        super.onBackPressed()
    }

    /**
     * 获取权限回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户点击了同意授权
                loadData()
            } else {
                //用户拒绝了授权
                toast(R.string.permission_deny)
            }
            else -> {
            }
        }
    }

    /**
     * 是否显示菊花
     */
    private fun displayProgressCircle(display: Boolean) {
        if (display)
            progressCircle.visibility = View.VISIBLE
        else
            progressCircle.visibility = View.INVISIBLE
    }

    /**
     * 接收EasyBar外观参数
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onMessageEvent(dcimIntentEvent: DCIMIntentEvent) {
        easyBar.init(EasyBar.Mode.ICON, dcimIntentEvent.easyBarParams.title,
                leftRes = dcimIntentEvent.easyBarParams.leftRes, rightRes = dcimIntentEvent.easyBarParams.rightRes,
                leftText = dcimIntentEvent.easyBarParams.leftText, rightText = dcimIntentEvent.easyBarParams.rightText,
                titleRes = dcimIntentEvent.easyBarParams.titleRes, isCoverCallback = false)
        easyBar.setBackgroundResource(dcimIntentEvent.easyBarParams.barBgColor)
    }
}
