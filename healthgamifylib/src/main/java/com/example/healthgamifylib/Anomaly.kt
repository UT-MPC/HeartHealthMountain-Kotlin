package com.example.healthgamifylib

import java.time.Duration
import java.util.*

abstract class Context(val name: String, var timeA: Date, var timeB: Date)

abstract class Anomaly(
    val observedData: HealthData,
    var threshold: Int,
    var context: Context?,
    val duration: Duration?
) : Subject(), Observer {
    // if the anomaly has a context, then specify it in the constructor
    protected var isAnomaly: Boolean = false  // never used
    override fun update(value: Any?) {
        updateContext()
//        if (!(observedData.time?.after(context?.timeA) == true && observedData.time?.before(context?.timeB) == true)) {
//            return  // no need to check for anomaly if the observed data doesn't happen during the context
//        }  // check this only if context isn't null

        if (checkAnomaly(value)) {
            notifyObservers()
        }
    }

    abstract fun checkAnomaly(value: Any?) : Boolean
    open fun updateContext()  // for the context that might change depending on input data
    {
    }
//    init {
//        observedData.registerObserver(this::update)
//    } /* leaking this in constructor of non-final class kotlin */
}