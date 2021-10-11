package com.example.healthgamifylib

import java.util.*

abstract class HealthDataSource(val name: String) : Subject() {
    var value: Any = -1

    override fun notifyObservers() {
        for (o in observers) {
            o(value)
        }
    }
    fun updateValue() {
        updateHealthDataFromSource()
        notifyObservers()
    }
    abstract fun updateHealthDataFromSource()
}

abstract class HealthData(val healthDataSource: HealthDataSource, val name: String) : Subject(), Observer {
    var value: Any = -1
    protected var time: Date? = null // time doesn't exist until its base class Data has value

    override fun notifyObservers() {
        for (o in observers) {
            o(value)
        }
    }

}

abstract class AnomalyDetection(val inputData: HealthData): Subject(), Observer {
    var isAnomaly: Boolean = false
    override fun notifyObservers() {
        for (o in observers) {
            o(isAnomaly)
        }
    }
}