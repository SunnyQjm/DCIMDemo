package com.j.ming.dcim.manager

import android.app.Activity
import android.content.Context
import com.j.ming.dcim.extensions.jumpForResult
import com.j.ming.dcim.extensions.jumpTo
import com.j.ming.dcim.index.DCIMActivity
import com.j.ming.dcim.model.event.DCIMIntentEvent
import com.j.ming.dcim.model.params.EasyBarParams
import com.j.ming.dcim.model.params.IntentParam
import org.greenrobot.eventbus.EventBus

object EasyDCIM{

    const val MODE_SELECT_SINGLE = DCIMActivity.MODE_SELECT_SINGLE
    const val MODE_SELECT_MULTI = DCIMActivity.MODE_SELECT_MULTI

    fun with(activity: Activity): IntentToDCIM{
        return IntentToDCIM(activity)
    }


    class IntentToDCIM(private val activity: Activity, private var easyBarParam: EasyBarParams = EasyBarParams(),
                       private var mode: Int = MODE_SELECT_MULTI,
                       private var isSaveSate: Boolean = false){
        fun setEasyBarParam(easyBarParam: EasyBarParams): IntentToDCIM{
            this.easyBarParam = easyBarParam
            return this
        }

        fun setMode(mode: Int): IntentToDCIM{
            this.mode = mode
            return this
        }

        fun setSaveState(isSaveSate: Boolean): IntentToDCIM{
            this.isSaveSate = isSaveSate
            return this
        }

        fun jump(){
            activity.jumpTo(DCIMActivity::class.java, IntentParam()
                    .add(DCIMActivity.PARAM_SAVE_STATE, isSaveSate)
                    .add(DCIMActivity.PARAM_MODE, mode))
            EventBus.getDefault().postSticky(DCIMIntentEvent(easyBarParam))
        }

        fun jumpForResult(requestCode: Int){
            activity.jumpForResult(DCIMActivity::class.java, requestCode, IntentParam()
                    .add(DCIMActivity.PARAM_SAVE_STATE, isSaveSate)
                    .add(DCIMActivity.PARAM_MODE, mode))
            EventBus.getDefault().postSticky(DCIMIntentEvent(easyBarParam))
        }
    }
}