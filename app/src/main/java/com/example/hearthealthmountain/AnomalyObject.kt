package com.example.hearthealthmountain

import android.util.Log
import com.example.healthgamifylib.Anomaly
import com.example.healthgamifylib.Context
import com.example.healthgamifylib.HealthData
import com.example.healthgamifylib.Observer
import java.time.Duration
import java.util.*

class WeightAnomaly(
    observedData: HealthData, threshold: Int, context: Context,
    duration: Duration?
) : Anomaly(
    observedData, threshold,
    context, duration
) {
    override fun checkAnomaly(value: Any?): Boolean {
        Log.i("WeightAnomaly", "$value")
        return (value as Int > threshold)
    }

    override fun notifyObservers() {
        for (o in observers) {
            o("Overweight")
        }
    }

    init {
        observedData.registerObserver(this::update)
    }
}

class WeightTrigger() : Observer {
    override fun update(value: Any?) {
        Log.i("WeightTrigger", value as String)
    }

}

class WeightDiffAnomaly(
    observedData: HealthData, threshold: Int, context: Context,
    duration: Duration?
) : Anomaly(
    observedData, threshold,
    context, duration
) {
    var prevWeight = -1
    override fun checkAnomaly(value: Any?): Boolean {
        if (prevWeight == -1) {
            prevWeight = value as Int
            return false
        }
        val diff = prevWeight - value as Int
        prevWeight = value
        return (diff > threshold)
    }

    override fun updateContext() {
        context.timeA = observedData.time!!
        context.timeB = Date(context.timeA.time + 86400)
    }

    override fun notifyObservers() {
        for (o in observers) {
            o("Weight diff too much. Prev weight = $prevWeight")
        }
    }

    override fun printLog() {
        Log.i(
            "WeightDiffAnomaly",
            "update function called, context timeA is ${context.timeA}, context timeB is ${context.timeB}, observedData time is ${observedData.time}"
        )
    }

    init {
        observedData.registerObserver(this::update)
    }
}