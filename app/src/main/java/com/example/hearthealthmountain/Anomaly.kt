package com.example.hearthealthmountain

import com.example.healthgamifylib.HealthData
import java.time.Duration

abstract class Context(val name: String, val duration: Duration?)

abstract class Anomaly (val inputData: HealthData, var threshold: Int, val context: Context?, val duration: Duration?) {

}