package com.example.hearthealthmountain

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.healthgamifylib.HealthData
import com.example.healthgamifylib.HealthDataSource
import java.time.Duration
import java.util.*
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
class WithingsScale(name: String): HealthDataSource(name) {
    override fun updateHealthDataFromSource() {
//        if (Random.nextInt(0,10) % 10 != 0) {
//            value = Random.nextInt(100, 200)
//            Log.i("Weight", "weight is $value")
//        } else {
//            value = -1
//            Log.i("Weight", "weight is null")
//        }
        value = Random.nextInt(100, 200)
    }

    inner class CallUpdateValue(): TimerTask(){
        override fun run() {
            updateValue()
        }
    }

    var updatePeriod: Long = 20

    init {
        val timer = Timer()
        timer.schedule(CallUpdateValue(), Date(), Duration.ofSeconds(updatePeriod).seconds*1000)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
class ActivityTracker(name: String) : HealthDataSource(name){
    override fun updateHealthDataFromSource() {
//        if (Random.nextInt(0,10) % 5 != 0) {
//            value += Random.nextInt(0, 200)
//            Log.i("Step", "step is $value")
//        } else {
//            value = -1
//            Log.i("Step", "step is null")
//        }
        value = Random.nextInt(0, 200)
    }

    inner class CallUpdateValue(): TimerTask(){
        override fun run() {
            updateValue()
        }
    }
    var updatePeriod: Long = 1  // updating step data every second

    init {
        val timer = Timer()
        timer.schedule(CallUpdateValue(), Date(), Duration.ofSeconds(updatePeriod).seconds*1000)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
class Step(healthDataSource: HealthDataSource, name: String) : HealthData(healthDataSource, name){
// observes Activity Tracker
    override fun updateValue(value: Any?) {
        this.value = value as Int
        notifyObservers()
    
    }

    init {
        healthDataSource.registerObserver(this::update)
    }
}

class Weighed(healthDataSource: HealthDataSource, name: String) : HealthData(healthDataSource, name){

    override fun updateValue(value: Any?) {
        this.value = value as Int
        notifyObservers()
    }

    init {
        healthDataSource.registerObserver(this::update)
    }
}

//class WeightAnomaly(var upperBound: Int, var lowerBound: Int, inputData: HealthData) : AnomalyDetection(inputData){
//
//    override fun update(value: Any?) {
//        if (upperBound!=-1) {
//            isAnomaly = (value as Int > upperBound) || (lowerBound > value as Int)
//            if (isAnomaly) {
//                notifyObservers()
//            }
//        }
//
//        // update upperBound and lowerBound
//        upperBound = inputData.value as Int + 30
//        lowerBound = inputData.value as Int - 30
//
//        Log.i("Weight Anomaly", "Current weight is $value")
//    }
//    init {
//        inputData.registerObserver(this::update)
//    }
//}
//
//class Trigger(val weightAnomaly: WeightAnomaly) : Observer {
//    init {
//        weightAnomaly.registerObserver(this::update)
//    }
//
//    override fun update(value: Any?) {
//        if (value as Boolean) {
//            Log.i("weight trigger", "It was an anomaly")
//        }
//        else {
//            Log.i("weight trigger", "not an anomaly")
//        }
//    }
//}