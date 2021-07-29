package com.example.hearthealthmountain

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.Duration
import java.util.*
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
class Weight(healthDataSource: String, name: String = "weight") : HealthData(healthDataSource, name) {

    override var observers: MutableList<(Any?) -> Unit> = mutableListOf()

    override fun registerObserver(whatToCall: (Any?) -> Unit) {
        observers.add(whatToCall)
    }

    override fun removeObserver(whatNotToCall: (Any?) -> Unit) {
        observers.remove (whatNotToCall)
    }

    override fun notifyObservers() {
        for (o in observers) {
            o(value)  // calls Weighed.update()
        }
    }

    inner class newValue(): TimerTask(){
        override fun run() {
            if (Random.nextInt(0,10) % 10 != 0) {
                value = Random.nextInt(100, 200)
                time = Date()
                notifyObservers()
                Log.i("Weight", "weight is $value")
            } else {
                value = -1
                time = null
                Log.i("Weight", "weight is null")
            }
        }
//        TODO("Get new weight in a less fake way!!")
    }

    var updatePeriod: Long

    init {
        updatePeriod = 20
        val timer = Timer()
        timer.schedule(newValue(), Date(), Duration.ofSeconds(updatePeriod).seconds*1000)
    }


}

class Weighed(healthDataSource: String, name: String) : HealthData(healthDataSource, name), Subject, Observer {
    override var observers: MutableList<(Any?) -> Unit> = mutableListOf()

    override fun registerObserver(whatToCall: (Any?) -> Unit) {
        observers.add(whatToCall)
    }

    override fun removeObserver(whatNotToCall: (Any?) -> Unit) {
        observers.remove (whatNotToCall)
    }

    override fun notifyObservers() {
        for (o in observers) {
            o(value)
        }
    }

    override fun update(value: Any?) {
        Log.i("Weighed", "value is $value")
        this.value = value as Int
        notifyObservers()
    }
}