package com.j.ming.dcim.util

import android.content.Context
import android.provider.MediaStore
import com.j.ming.dcim.model.FileItem
import com.j.ming.dcim.model.SelectItem
import java.io.File
import java.util.ArrayList
import java.util.HashMap

/**
 * 执行异步操作
 */
fun doAsync(runnable: () -> Unit) {
    Thread(Runnable {
        runnable()
    }).start()
}


object PictureGetter{
    interface GetDataCallback {
        fun onSuccess(map: Map<String, List<FileItem>>, dirs: List<SelectItem>)

        fun onFail(msg: String)
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
     */
    fun getPictureSync(context: Context, callback: GetDataCallback) {
        doAsync {
            val mGroupMap = HashMap<String, MutableList<FileItem>>()
            val dirs = mutableListOf<SelectItem>()
            val mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val mContentResolver = context.contentResolver

            //只查询jpeg和png的图片
            val mCursor = mContentResolver.query(mImageUri, null,
                    MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?",
                    arrayOf("image/jpeg", "image/png"), MediaStore.Images.Media.DATE_MODIFIED)
            if (mCursor == null) {
                callback.onFail("mCursor is null")
                return@doAsync
            }
            while (mCursor.moveToNext()) {
                //获取图片的路径
                val path = mCursor.getString(mCursor
                        .getColumnIndex(MediaStore.Images.Media.DATA))
                File(path).let {
                    val item = FileItem(it.name, it.length(), it.lastModified(), it.path)

                    //根据父路径名将图片放入到mGruopMap中
                    if (!mGroupMap.containsKey(it.parentFile.path)) { //新路径出现
                        val chileList = ArrayList<FileItem>()
                        val selectItem = SelectItem(FileItem(it.parentFile.name, it.parentFile.length(), it.parentFile.lastModified(), it.parentFile.path,
                                true))
                        chileList.add(item)
                        dirs.add(selectItem)
                        mGroupMap[selectItem.directItem.path] = chileList
                    } else {
                        mGroupMap[it.parentFile.path]!!
                                .add(FileItem(it.name, it.length(), it.lastModified(), it.path))
                    }
                }

            }
            mCursor.close()
            for (element in mGroupMap.values)
                element.reverse()
            dirs.forEach {  si ->
                mGroupMap[si.directItem.path]?.let {
                    si.childCount = it.size
                    si.firstItem = it.first()
                }
            }
            callback.onSuccess(mGroupMap, dirs)
        }
    }
}