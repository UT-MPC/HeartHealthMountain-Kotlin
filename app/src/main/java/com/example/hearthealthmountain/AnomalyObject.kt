package com.example.hearthealthmountain

import android.util.Log
import com.example.healthgamifylib.Anomaly
import com.example.healthgamifylib.Context
import com.example.healthgamifylib.HealthData
import com.example.healthgamifylib.Observer
import java.time.Duration

class WeightAnomaly(observedData: HealthData, threshold: Int, context: Context?,
                    duration: Duration?
) : Anomaly(observedData, threshold,
    context, duration
) {
    override fun checkAnomaly(value: Any?) {
        Log.i("WeightAnomaly", "$value")
        if (value as Int > threshold) {
            notifyObservers()
        }
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