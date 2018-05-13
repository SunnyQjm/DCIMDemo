
package com.j.ming.dcimdemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.j.ming.dcim.extensions.jumpForResult
import com.j.ming.dcim.extensions.load
import com.j.ming.dcim.index.DCIMActivity
import com.j.ming.dcim.extensions.jumpTo
import com.j.ming.dcimdemo.R.id.imageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView.load("http://img4.imgtn.bdimg.com/it/u=3639748931,2936958645&fm=27&gp=0.jpg")
        imageView.setOnClickListener {
            jumpForResult(DCIMActivity::class.java)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            0 -> {
                data?.getStringArrayExtra(DCIMActivity.RESULT_PATHS)?.let {
                    it.forEach {
                        println(it)
                    }
                }
            }
        }
    }
}
