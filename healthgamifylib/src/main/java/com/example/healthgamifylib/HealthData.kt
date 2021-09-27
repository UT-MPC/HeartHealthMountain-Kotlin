package com.example.healthgamifylib

import java.util.*

abstract class HealthDataSource(val name: String) : Subject {
    override var observers: MutableList<(Any?) -> Unit> = mutableListOf()
    var value: Int = -1

    override fun notifyObservers() {
        for (o in observers) {
            o(value)
        }
    }

    open fun updateValue() {}  // the hook, overridden by sensor integrator
}

abstract class Data(val name: String) {
    protected var creationDate: Date = Date()  // allows backtracking
    var value: Int = -1
    open fun updateValue() {}  // a hook
}

abstract class HealthData(val healthDataSource: HealthDataSource, name: String) : Data(name), Subject, Observer {
    protected var time: Date? = null // time doesn't exist until its base class Data has value

    override var observers: MutableList<(Any?) -> Unit> = mutableListOf()

    override fun notifyObservers() {
        for (o in observers) {
            o(value)
        }
    }

}