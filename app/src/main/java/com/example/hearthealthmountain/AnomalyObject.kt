package com.example.hearthealthmountain

import com.example.healthgamifylib.Anomaly
import com.example.healthgamifylib.Context
import com.example.healthgamifylib.HealthData
import java.time.Duration

class WeightAnomaly(observedData: HealthData, threshold: Int, context: Context?,
                    duration: Duration?
) : Anomaly(observedData, threshold,
    context, duration
) {
    override fun checkAnomaly() {
        TODO("Not yet implemented")
    }

    override fun notifyObservers() {
        TODO("Not yet implemented")
    }
}