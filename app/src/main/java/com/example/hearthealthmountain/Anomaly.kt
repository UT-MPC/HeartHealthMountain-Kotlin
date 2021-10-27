package com.example.hearthealthmountain

import com.example.healthgamifylib.HealthData
import com.example.healthgamifylib.Observer
import com.example.healthgamifylib.Subject
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
//    init {
//        observedData.registerObserver(this::update)
//    }
}