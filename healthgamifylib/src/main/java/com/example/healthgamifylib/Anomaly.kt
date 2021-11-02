package com.example.healthgamifylib

import java.time.Duration
import java.util.*

class Context(var isNull: Boolean, val name: String = "null", var timeA: Date = Date(0), var timeB: Date = Date(0)) {
    init {
        if (!isNull && timeA == Date(0) && timeB == Date(0)) {
            throw Exception("Time A and Time B cannot be 0")
        }
    }
}

abstract class Anomaly(
    val observedData: HealthData,
    var threshold: Int,
    val context: Context,
    val duration: Duration?
) : Subject(), Observer {
    // if the anomaly has a context, then specify it in the constructor
    protected var isAnomaly: Boolean = false  // never used
    override fun update(value: Any?) {
        printLog()
        if (context.isNull ||
            (observedData.time?.after(context.timeA) == true && observedData.time?.before(context.timeB) == true)
        ) {
            /** if context is null,
             * or if the time of the observed data falls within context,
             * then check anomaly */
            if (checkAnomaly(value)) {
                notifyObservers()
            }
        }
        updateContext()
    }

    open fun printLog() {}
    open fun updateContext() {}
    abstract fun checkAnomaly(value: Any?): Boolean
//    init {
//        observedData.registerObserver(this::update)
//    } /* leaking this in constructor of non-final class kotlin */
}