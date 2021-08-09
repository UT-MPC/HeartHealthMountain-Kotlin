package com.example.healthgamifylib

interface Observer { //subscriber
    fun update(value: Any?) : Unit
}

interface Subject {
    var observers: MutableList<(Any?) -> Unit>
    fun registerObserver(whatToCall: (Any?) -> Unit) : Unit
    fun removeObserver(whatNotToCall: (Any?) -> Unit) : Unit
    fun notifyObservers() : Unit
}