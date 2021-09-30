package com.example.healthgamifylib

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.Duration
import java.util.*
import java.util.concurrent.locks.ReentrantLock

@RequiresApi(Build.VERSION_CODES.O)
open class WindowGoal(
    var targetValue: Int, var observedData: HealthData,
    var start: Date, var window: Duration, val lock: ReentrantLock
) : Subject(), Observer {
    private val timer = Timer()
    var goalAchieved: Boolean = false
    private var log = false

    // satisfying the Subject contract
    override fun notifyObservers() {
        for (o in observers) {
            o(goalAchieved)
        }
    }

    // satisfying the Observer contract; can be overridden for more tailored behavior
    open override fun update(value: Any?) {
        if (!goalAchieved && value as Int >= targetValue) {
            goalAchieved = true
            notifyObservers()
        }
    }

    // finalizeGoal can be overridden if player is not penalized for not achieving the goal
    open fun finalizeGoal() {
        if (!goalAchieved) {
            notifyObservers()
        }
    }
    val tag = "Window Goal"
    private inner class StartWindow() : TimerTask() {
        override fun run() {
            lock.lock()
            if (log) {
                Log.i(tag, "====Window Starting ${observedData.name}===")
            }
            goalAchieved = false
            observedData.registerObserver(this@WindowGoal::update)
        }
    }

    private inner class EndWindow() : TimerTask() {
        override fun run() {
            if (log) {
                Log.i(tag, "====Window Ending ${observedData.name}===")
            }
            observedData.removeObserver(this@WindowGoal::update)
            finalizeGoal()
            lock.unlock()
        }
    }

    init {
        if (log) {
            Log.i(tag, "constructor, ${observedData.name}")
        }
        timer.schedule(StartWindow(), start)
        timer.schedule(EndWindow(), window.seconds*1000)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
open class RepeatingWindowGoal(
    val targetValue: Int, val observedData: HealthData,
    var start: Date, val window: Duration,
    var repetitions: Int, var streak: Int = 0
) : Subject() {
    var embeddedWindowGoal: WindowGoal
    private val timer: Timer = Timer()
    val goalArray = mutableListOf<Boolean>()
    var repetitionsCompleted = 0
    val lock = ReentrantLock()
    var currentStreak = 0
    private var log = false

    private inner class UpdateWindowGoal() : TimerTask() {
        override fun run() {
            goalArray.add(embeddedWindowGoal.goalAchieved)
            if (log) {
                Log.i("Repeating", "goalArray: ${goalArray.toString()}")
            }
            repetitionsCompleted++
            if (embeddedWindowGoal.goalAchieved) {
                currentStreak++
                if (currentStreak >= streak){
                    notifyObservers()
                }
            } else {
                currentStreak = 0
            }
            if (goalArray.size < repetitions) {
                // start = Date(start.time + goalArray.size * window.seconds * 1000)
                embeddedWindowGoal = WindowGoal(targetValue, observedData, Date(), window, lock)
                for (o in observers) {
                    embeddedWindowGoal.registerObserver(o)
                }
            }
            else {
                if (log) {
                    Log.i(
                        "RepeatingWindowGoal",
                        "repetitions completed.\n Goals: ${goalArray.toString()}"
                    )
                }
            }
        }
    }

    init {
        // this creates and starts the first repetition of the window goal
        embeddedWindowGoal = WindowGoal(targetValue, observedData, start, window, lock)
        for (o in observers) {
            embeddedWindowGoal.registerObserver(o)
        }
        timer.schedule(UpdateWindowGoal(), Date(start.time + window.seconds * 1000), window.seconds * 1000)
    }

    override fun notifyObservers() {
        for (o in observers) {
            o(currentStreak)
        }
    }
}
