package com.abc.core.utils

import android.os.Handler
import android.os.Looper

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * @author Liszt
 * @date 2020/7/30
 * Description
 */
class ThreadUtil private constructor() {
    private val fixedThreadPool =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1)
    val singleThreadPool = Executors.newSingleThreadExecutor()

    fun singleExecute(runnable: Runnable?) {
        if (runnable != null) {
            this.singleThreadPool.execute(runnable)
        }

    }

    fun runMainThread(runnable: Runnable) {
        Handler(Looper.getMainLooper()).post(runnable)
    }

    fun execute(runnable: Runnable) {
        this.fixedThreadPool.execute(runnable)
    }

    fun shutdown() {
        this.fixedThreadPool.shutdown()
    }

    fun shutdownNow() {
        this.fixedThreadPool.shutdownNow()
    }

    companion object {
        private var instance: ThreadUtil? = null

        fun getInstance(): ThreadUtil {
            val var0 = ThreadUtil::class.java
            synchronized(ThreadUtil::class.java) {
                if (instance == null) {
                    instance = ThreadUtil()
                }
            }

            return instance
        }
    }
}
