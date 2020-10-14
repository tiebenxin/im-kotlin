package com.abc.core.constans

import android.os.Environment

/**
 *@author Liszt
 *@date 2020/10/14
 *Description
 */
object FileConfig {
    /** 应用根目录  */
    val PATH_BASE = Environment.getExternalStorageDirectory().absolutePath + "/abc/"
    /** 应用Log日志  */
    val PATH_LOG = PATH_BASE + "log/"

    /** 应用图片缓存  */
    val PATH_CACHE = PATH_BASE + "cache/"

    /** 应用文件下载路径  */
    val PATH_DOWNLOAD = PATH_BASE + "download/"
}