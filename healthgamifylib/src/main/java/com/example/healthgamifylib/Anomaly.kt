package com.example.healthgamifylib

import java.time.Duration

abstract class Context(val name: String, val duration: Duration?)

abstract class Anomaly (val observedData: HealthData, var threshold: Int, val context: Context?, val duration: Duration?) : Subject(), Observer {
    protected var isAnomaly: Boolean = false  // never used
    override fun update(value: Any?) {
        checkAnomaly(value)
        // no need to call notifyObservers because checkAnomaly will call it
    }
    abstract fun checkAnomaly(value: Any?)
//    init {
//        observedData.registerObserver(this::update)
//    } /* leaking this in constructor of non-final class kotlin */
}