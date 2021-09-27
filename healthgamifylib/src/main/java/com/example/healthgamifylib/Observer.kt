package com.example.healthgamifylib

interface Observer { //subscriber
    fun update(value: Any?) : Unit
}

interface Subject {
    var observers: MutableList<(Any?) -> Unit>
    fun registerObserver(whatToCall: (Any?) -> Unit) : Unit {
        observers.add(whatToCall)
    }
    fun removeObserver(whatNotToCall: (Any?) -> Unit) : Unit{
        observers.remove(whatNotToCall)
    }
    fun notifyObservers() : Unit
}