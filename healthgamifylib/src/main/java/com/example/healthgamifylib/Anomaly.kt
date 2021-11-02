package com.example.healthgamifylib

import java.time.Duration
import java.util.*

abstract class Context(val name: String, var timeA: Date, var timeB: Date)

abstract class Anomaly(
    val observedData: HealthData,
    var threshold: Int,
    val context: Context?,
    val duration: Duration?
) : Subject(), Observer {
    // if the anomaly has a context, then specify it in the constructor
    protected var isAnomaly: Boolean = false  // never used
    override fun update(value: Any?) {

        if ((context == null) ||
            (observedData.time?.after(context?.timeA) == true && observedData.time?.before(context?.timeB) == true)
        ) {
            /** if context is null,
             * or if the time of the observed data falls within context,
             * then check anomaly */
            if (checkAnomaly(value)) {
                notifyObservers()
            }
        }
    }

    abstract fun checkAnomaly(value: Any?): Boolean
//    init {
//        observedData.registerObserver(this::update)
//    } /* leaking this in constructor of non-final class kotlin */
}