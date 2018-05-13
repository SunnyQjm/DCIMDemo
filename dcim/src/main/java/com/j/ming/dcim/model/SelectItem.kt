package com.j.ming.dcim.model

class SelectItem(val directItem: FileItem,  var firstItem: FileItem? = null, var childCount: Int = 0, var selected: Boolean = false){
    override fun hashCode(): Int = directItem.name.hashCode() + directItem.lastModifiedTime.hashCode()
    override fun equals(other: Any?): Boolean {
        if(other == null || other !is SelectItem)
            return false
        return directItem.name == other.directItem.name && directItem.lastModifiedTime == other.directItem.lastModifiedTime
    }
}