package com.example.healthgamifylib

import java.time.Duration

abstract class Context(val name: String, val duration: Duration?)

abstract class Anomaly (val observedData: HealthData, var threshold: Int, val context: Context?, val duration: Duration?) : Subject(), Observer {
    protected var isAnomaly: Boolean = false
    override fun update(value: Any?) {
        checkAnomaly()
        if (isAnomaly){
            notifyObservers()
        }
    }
    abstract fun checkAnomaly()
}