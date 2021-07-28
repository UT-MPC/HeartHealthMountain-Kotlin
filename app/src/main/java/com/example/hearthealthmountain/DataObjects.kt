package com.example.hearthealthmountain

import java.util.*
import kotlin.random.Random

class Weight(healthDataSource: String, name: String = "weight") : HealthData(healthDataSource, name), Subject {

    override var observers: MutableList<(Any?) -> Unit> = mutableListOf()

    override fun registerObserver(whatToCall: (Any?) -> Unit) {
        observers.add(whatToCall) // observers.put(whatToCall, whatToCall)
//        TODO("Not done implementing")
    }

    override fun removeObserver(whatNotToCall: (Any?) -> Unit) {
        observers.remove (whatNotToCall)
//        TODO("Not done implementing")
    }

    override fun notifyObservers() {
        for (o in observers) {
            o(value)  // calls Weighed.update()
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
    override var observers: MutableList<(Any?) -> Unit> = mutableListOf()

    override fun registerObserver(whatToCall: (Any?) -> Unit) {
        observers.add(whatToCall)  // observers.put(whatToCall, whatToCall)
//        TODO("Not done implementing")
    }

    override fun removeObserver(whatNotToCall: (Any?) -> Unit) {
        observers.remove (whatNotToCall)
//        TODO("Not done implementing")
    }

    override fun notifyObservers() {
        for (o in observers) {
            o(value)
        }
    }

    override fun update(value: Any?) {
        this.value = value as Int
        notifyObservers()
    }
}