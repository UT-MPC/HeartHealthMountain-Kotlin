package com.example.hearthealthmountain

import android.util.Log
import com.example.healthgamifylib.Anomaly
import com.example.healthgamifylib.Context
import com.example.healthgamifylib.HealthData
import com.example.healthgamifylib.Observer
import java.time.Duration
import java.util.*

class WeightAnomaly(observedData: HealthData, threshold: Int, context: Context?,
                    duration: Duration?
) : Anomaly(observedData, threshold,
    context, duration
) {
    override fun checkAnomaly(value: Any?) : Boolean {
        Log.i("WeightAnomaly", "$value")
            return (value as Int > threshold)
    }

    override fun notifyObservers() {
        for (o in observers) {
            o(null)
        }
    }

    init {
        observedData.registerObserver(this::update)
    }
}

class WeightTrigger() : Observer {
    override fun update(value: Any?) {
        Log.i("WeightTrigger", "Overweight")
    }

}

class WeightDiffAnomaly(observedData: HealthData, threshold: Int, context: Context?,
                        duration: Duration?
) : Anomaly(observedData, threshold,
    context, duration
) {
    // TODO: test this class
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

    override fun updateContext() {  // TODO: this method sucks
        if (observedData.time != null) {  // TODO: how to make this atomic?
            context?.timeA = observedData.time!!
            context?.timeB = Date(context?.timeA?.time?.plus(86400)!!)
        }
    }

    override fun notifyObservers() {
        for (o in observers) {
            o(null)
        }
    }
}