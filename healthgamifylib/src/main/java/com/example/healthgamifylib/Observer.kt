package com.example.healthgamifylib

interface Observer { //subscriber
    fun update(value: Any?) : Unit
}

abstract class Subject {
    var observers: MutableList<(Any?) -> Unit> = mutableListOf()

    fun registerObserver(whatToCall: (Any?) -> Unit) : Unit {
        observers.add(whatToCall)
    }
    fun removeObserver(whatNotToCall: (Any?) -> Unit) : Unit{
        observers.remove(whatNotToCall)
    }
    abstract fun notifyObservers() : Unit
}