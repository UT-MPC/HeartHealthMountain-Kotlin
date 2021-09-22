package com.example.healthgamifylib

import java.util.*

abstract class HealthDataSource(val name: String) : Subject {
    override var observers: MutableList<(Any?) -> Unit> = mutableListOf()
    var value: Int = -1
    override fun registerObserver(whatToCall: (Any?) -> Unit) {
        observers.add(whatToCall)
    }

    override fun notifyObservers() {
        for (o in observers) {
            o(value)
        }
    }

    override fun removeObserver(whatNotToCall: (Any?) -> Unit) {
        observers.remove(whatNotToCall)
    }
}

abstract class Data(val name: String) {
    protected var creationDate: Date = Date()  // allows backtracking
    var value: Int = -1
    open fun updateValue() {}  // a hook
}

abstract class HealthData(val healthDataSource: String, name: String) : Data(name), Subject {
    protected var time: Date? = null // time doesn't exist until its base class Data has value
}