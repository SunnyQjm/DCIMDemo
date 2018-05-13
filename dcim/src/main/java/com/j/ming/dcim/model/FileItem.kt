package com.j.ming.dcim.model

class FileItem(val name: String, val size: Long, val lastModifiedTime: Long,
               val path: String, val isDirect: Boolean = false)