package com.abc.core.manager.excutor

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Liszt on 2017/9/23.
 */

open class NamedThreadFactory(protected val namePrefix: String) : ThreadFactory {
    private val threadNumber = AtomicInteger(1)
    protected val group: ThreadGroup? = Thread.currentThread().threadGroup

    private fun newThread(name: String, r: Runnable): Thread {
        return Thread(r, name)
    }

    override fun newThread(r: Runnable): Thread {
        val t = newThread(namePrefix + threadNumber.getAndIncrement(), r)
        if (t.isDaemon)
            t.isDaemon = false
        return t
    }

}
