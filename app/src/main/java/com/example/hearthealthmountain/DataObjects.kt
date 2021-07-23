package com.example.hearthealthmountain

import java.util.*
import kotlin.random.Random

class Weight(healthDataSource: String, name: String = "weight") : HealthData(healthDataSource, name), Subject {

    override lateinit var observers: MutableMap<(Int, Date?) -> Unit, (Int, Date?) -> Unit>
//        get() = TODO("Not yet implemented")
//        set(value) {}

    init {
        observers = mutableMapOf()
    }

    override fun registerObserver(whatToCall: (Int, Date?) -> Unit) {
        observers[whatToCall] = whatToCall // observers.put(whatToCall, whatToCall)
//        TODO("Not done implementing")
    }

    override fun removeObserver(whatNotToCall: (Int, Date?) -> Unit) {
        observers.remove (whatNotToCall)
//        TODO("Not done implementing")
    }

    override fun notifyObservers() {
        for (o in observers.values) {
            o(value, time)  // calls Weighed.update()
        }
    }

    override fun newValue(){
        if (Random.nextInt(0,10) % 10 == 0) {
            value = Random.nextInt(100, 200)
            time = Date()
            notifyObservers()
        } else {
            value = -1
            time = null
        }
//        TODO("Get new weight in a less fake way!!")
    }

}

class Weighed(healthDataSource: String, name: String) : HealthData(healthDataSource, name), Subject, Observer {
    override lateinit var observers: MutableMap<(Int, Date?) -> Unit, (Int, Date?) -> Unit>

    init {
        observers = mutableMapOf<(Int, Date?) -> Unit, (Int, Date?) -> Unit>()
    }

    override fun registerObserver(whatToCall: (Int, Date?) -> Unit) {
        observers[whatToCall] = whatToCall // observers.put(whatToCall, whatToCall)
//        TODO("Not done implementing")
    }

    override fun removeObserver(whatNotToCall: (Int, Date?) -> Unit) {
        observers.remove (whatNotToCall)
//        TODO("Not done implementing")
    }

    override fun notifyObservers() {
        for (o in observers.values) {
            o(value, time)
        }
    }

    override fun update(v: Int, t: Date?) {
        value = 1
        if (t != null) {
            time = t
        }
        notifyObservers()
    }
}