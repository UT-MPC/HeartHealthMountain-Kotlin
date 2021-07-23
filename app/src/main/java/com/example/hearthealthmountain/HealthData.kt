package com.example.hearthealthmountain

import java.util.*

abstract class Data(val name: String) {
    var creationDate: Date = Date()  // allows backtracking
    var value: Int = -1
    open fun newValue(){}  // a hook that lets data object update var value
}

abstract class HealthData(val healthDataSource: String, name: String) : Data(name) {
    var time: Date? = null // time doesn't exist until its base class Data has value
}
