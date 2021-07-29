package com.example.hearthealthmountain

import java.util.*

abstract class Data(val name: String) {
    protected var creationDate: Date = Date()  // allows backtracking
    protected var value: Int = -1
}

abstract class HealthData(val healthDataSource: String, name: String) : Data(name), Subject {
    protected var time: Date? = null // time doesn't exist until its base class Data has value
}