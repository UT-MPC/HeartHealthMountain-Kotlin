package com.example.healthgamifylib

import java.util.*

abstract class HealthDataSource(val name: String) : Subject() {
    var value: Int = -1

    override fun notifyObservers() {
        for (o in observers) {
            o(value)
        }
    }

    open fun updateValue() {}  // the hook, overridden by sensor integrator
}

abstract class HealthData(val healthDataSource: HealthDataSource, name: String) : Subject(), Observer {
    protected var creationDate: Date = Date()  // allows backtracking
    var value: Int = -1
    open fun updateValue() {}  // a hook
    protected var time: Date? = null // time doesn't exist until its base class Data has value

    override fun notifyObservers() {
        for (o in observers) {
            o(value)
        }
    }

}