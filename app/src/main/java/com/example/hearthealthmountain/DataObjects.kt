package com.example.hearthealthmountain

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.healthgamifylib.HealthData
import com.example.healthgamifylib.HealthDataSource
import java.time.Duration
import java.util.*
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
class WithingsScale(name: String): HealthDataSource(name) {
    override fun updateValue() {
        val timer = Timer()
        timer.schedule(NewValue(), Date(), Duration.ofSeconds(updatePeriod).seconds*1000)
    }

    inner class NewValue(): TimerTask(){
        override fun run() {
            if (Random.nextInt(0,10) % 10 != 0) {
                value = Random.nextInt(100, 200)
                notifyObservers()
            } else {
                value = -1
                Log.i("Weight", "weight is null")
            }
        }
    }

    var updatePeriod: Long = 20

    init {
        updateValue()
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//class Step(healthDataSource: HealthDataSource, name: String) : HealthData(healthDataSource, name){
//
//    override fun updateValue() {
//        value = 0
//        val timer = Timer()
//        timer.schedule(NewValue(), Date(), Duration.ofSeconds(updatePeriod).seconds*1000)
//    }
//
//    override fun update(value: Any?) {
//        TODO("Not yet implemented")
//    }
//
//    inner class NewValue(): TimerTask(){
//        override fun run() {
//            if (Random.nextInt(0,10) % 5 != 0) {
//                value += Random.nextInt(0, 200)
//                time = Date()
//                notifyObservers()
//            } else {
//                value = -1
//                time = null
//                Log.i("Step", "step is null")
//            }
//        }
////        TODO("Get new weight in a less fake way!!")
//    }
//
//    var updatePeriod: Long = 1  // updating step data every second
//
//    init {
//        updateValue()
//    }
//}

class Weighed(healthDataSource: HealthDataSource, name: String) : HealthData(healthDataSource, name){
    override fun update(value: Any?) {
        this.value = value as Int
        notifyObservers()
    }

    init {
        healthDataSource.registerObserver(this::update)
    }
}