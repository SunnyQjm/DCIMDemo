package com.j.ming.dcim.manager

import com.j.ming.dcim.model.FileItem

object SelectPictureManager{
    private val selectedItems: HashMap<String, FileItem> = hashMapOf()

    fun selectedPath(): Array<String> = selectedItems.keys.toTypedArray()


    fun toggle(fileItem: FileItem){
        if(selectedItems[fileItem.path] != null)
            remove(fileItem)
        else
            select(fileItem)
    }


    fun select(fileItem: FileItem){
        selectedItems[fileItem.path] = fileItem
    }

    fun remove(path: String){
        selectedItems.remove(path)
    }

    fun remove(fileItem: FileItem){
        remove(fileItem.path)
    }

    fun check(path: String) = selectedItems[path] != null

    fun check(fileItem: FileItem) = check(fileItem.path)

    fun clear(){
        selectedItems.clear()
    }
}