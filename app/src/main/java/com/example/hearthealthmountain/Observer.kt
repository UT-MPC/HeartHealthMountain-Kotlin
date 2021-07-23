package com.example.hearthealthmountain

import java.util.*

// TODO("Maybe change to Kotlin's Observable")
interface Observer { //subscriber
    fun update(value: Int, time: Date? = null) : Unit
}

interface Subject {
    var observers: MutableMap<(Int, Date?) -> Unit, (Int, Date?) -> Unit>
    fun registerObserver(whatToCall: (Int, Date?)->Unit) : Unit
    fun removeObserver(whatNotToCall: (Int, Date?)->Unit) : Unit
    fun notifyObservers() : Unit
}