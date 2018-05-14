
package com.j.ming.dcimdemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.j.ming.dcim.index.DCIMActivity
import com.j.ming.dcim.manager.EasyDCIM
import com.j.ming.dcim.model.params.EasyBarParams
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSelect.setOnClickListener {
            EasyDCIM.with(this)
                    .setMode(EasyDCIM.MODE_SELECT_SINGLE)
                    .setSaveState(false)
                    .setEasyBarParam(EasyBarParams(titleRes = R.string.title, barBgColor = R.color.colorAccent,
                            rightRes = R.drawable.cat))
                    .jumpForResult(0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            0 -> {
                data?.getStringArrayExtra(DCIMActivity.RESULT_PATHS)?.let {
                    result.text = Arrays.toString(it)
                }
            }
        }
    }
}
