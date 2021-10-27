package com.example.hearthealthmountain

import com.example.healthgamifylib.HealthData
import com.example.healthgamifylib.Observer
import com.example.healthgamifylib.Subject
import java.time.Duration

abstract class Context(val name: String, val duration: Duration?)

abstract class Anomaly (val inputData: HealthData, var threshold: Int, val context: Context?, val duration: Duration?) {
    operator fun plus(anomaly: Anomaly): Anomaly {
        return this
    }
}

class AnomalyDetection(val threshold: Int, val context: Context?, val duration: Duration?): Subject(), Observer {
//    val anomalyMutableList = mutableListOf<Anomaly>()
    var isAnomaly: Boolean = false
    lateinit var anomaly: Anomaly
    override fun update(value: Any?) {
        if (((value as HealthData).value as Int) > threshold){
            notifyObservers()
        }
    }
    override fun notifyObservers() {
        for (o in observers) {
            TODO("pass a concrete Anomaly to the observers?")
            o(isAnomaly)
        }
    }
}