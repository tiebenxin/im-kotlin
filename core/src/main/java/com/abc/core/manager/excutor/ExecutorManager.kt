package com.abc.core.manager.excutor

import java.util.concurrent.*

/**
 * @author Liszt
 * @date 2020/2/27
 * Description
 */
class ExecutorManager {

    private var writeThread: ThreadPoolExecutor? = null
    private var readThread: ThreadPoolExecutor? = null
    private var normalThread: ThreadPoolExecutor? = null
    private var socketThread: ThreadPoolExecutor? = null
    private var timerThread: ScheduledExecutorService? = null
    private var offlineThread: ThreadPoolExecutor? = null

    fun getWriteThread(): ThreadPoolExecutor {
        if (writeThread == null || writeThread!!.isShutdown) {
            writeThread = ThreadPoolExecutor(
                1, 1,
                0L, TimeUnit.MILLISECONDS,
                LinkedBlockingQueue(50),
                NamedThreadFactory(WRITE_THREAD_NAME),
                RejectedHandler()
            )
        }
        return writeThread!!
    }

    fun getReadThread(): ThreadPoolExecutor {
        if (readThread == null || readThread!!.isShutdown) {
            readThread = ThreadPoolExecutor(
                1, 1,
                0L, TimeUnit.MILLISECONDS,
                LinkedBlockingQueue(50),
                NamedThreadFactory(READ_THREAD_NAME),
                RejectedHandler()
            )
        }
        return readThread!!
    }

    fun getNormalThread(): ThreadPoolExecutor {
        if (normalThread == null || normalThread!!.isShutdown) {
            normalThread = ThreadPoolExecutor(
                5, 5,
                0L, TimeUnit.MILLISECONDS,
                LinkedBlockingQueue(30),
                NamedThreadFactory(NORMAL_THREAD_NAME),
                RejectedHandler()
            )
        }
        return normalThread!!
    }

    fun getSocketThread(): ThreadPoolExecutor {
        if (socketThread == null || socketThread!!.isShutdown) {
            socketThread = ThreadPoolExecutor(
                1, 2,
                0L, TimeUnit.MILLISECONDS,
                LinkedBlockingQueue(10),
                NamedThreadFactory(NORMAL_THREAD_NAME),
                RejectedHandler()
            )
        }
        return socketThread!!
    }

    //定时执行线程池
    fun getTimerThread(): ScheduledExecutorService {
        if (timerThread == null || timerThread!!.isShutdown) {
            timerThread = ScheduledThreadPoolExecutor(
                1,
                NamedThreadFactory(TIMER_THREAD_NAME),
                RejectedHandler()
            )
        }
        return timerThread!!
    }

    fun getOfflineThread(): ThreadPoolExecutor {
        if (offlineThread == null || offlineThread!!.isShutdown) {
            offlineThread = ThreadPoolExecutor(
                1, 1,
                0L, TimeUnit.MILLISECONDS,
                LinkedBlockingQueue(50),
                NamedThreadFactory(REALM_THREAD_NAME),
                RejectedHandler()
            )
        }
        return offlineThread!!
    }


    private class RejectedHandler : RejectedExecutionHandler {

        override fun rejectedExecution(r: Runnable, executor: ThreadPoolExecutor) {
            //            LogUtil.getLog().i("a task was rejected r=%s", r.toString());
        }
    }

    companion object {
        val THREAD_NAME_PREFIX = "im-client-"
        val WRITE_THREAD_NAME = THREAD_NAME_PREFIX + "write-t"
        val READ_THREAD_NAME = THREAD_NAME_PREFIX + "read-t"
        val TIMER_THREAD_NAME = THREAD_NAME_PREFIX + "timer-t"
        val NORMAL_THREAD_NAME = THREAD_NAME_PREFIX + "normal-t"
        val REALM_THREAD_NAME = THREAD_NAME_PREFIX + "realm-t"


        val INSTANCE = ExecutorManager()
    }


}
