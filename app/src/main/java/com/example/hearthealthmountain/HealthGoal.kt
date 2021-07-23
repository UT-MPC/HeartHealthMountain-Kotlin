package com.example.hearthealthmountain

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.Duration
import java.util.*

abstract class HealthGoal(var targetValue: Int) {
}

abstract class WindowGoal(targetValue: Int, var start: Date, var window: Duration, var subject: Subject) : HealthGoal(targetValue), Observer {
    var goal: Boolean = false  // whether the goal is achieved or not

    protected fun finalizeGoal() : Unit {}  // a hook. Called only if this goal is time triggered
    fun startWindow() : Unit {
        Log.i("Window goal", "window started")
        subject.registerObserver(this::update)
        /**
         * schedule a timertask that calls endWindow
         */
    }  // register as observer of data
    fun endWindow() : Unit {
        subject.removeObserver(this::update)
        finalizeGoal()
    }  // unregister as observer of data; call finalizeGoal
    init {
        TODO("schedule a timertask that calls startWindow")
    }
}

abstract class TargetWindowGoal(targetValue: Int, start: Date, window: Duration, subject: Subject) : WindowGoal(targetValue, start, window,
    subject
)

abstract class RangeWindowGoal(targetValue: Int, start: Date, window: Duration, subject: Subject) : WindowGoal(targetValue, start, window,
    subject
)

abstract class StatisticsGoal(targetValue: Int) : HealthGoal(targetValue)

abstract class DeadlineGoal(targetValue: Int) : StatisticsGoal(targetValue)

@RequiresApi(Build.VERSION_CODES.O)
class RepeatingWindowGoal(var windowGoal: WindowGoal, var repetitions: Int) {  // TODO("inherit WindowGoal")
    private var timer:Timer = Timer()
    private val TAG = "RepeatingWindowGoal"
    // TODO("an array of goals: Boolean, size repetitions")

    inner class UpdateWindowGoal() : TimerTask() {
        // create #repetitions of windowGoals
        @RequiresApi(Build.VERSION_CODES.O)
        override fun run() {
            Log.i(TAG, "run CreateWindowGoal run")
            /**
             * store windowGoal's goal variable in the goal array
             * update windowGoal's parameters
             */
            if (--repetitions <= 0) {
                timer.cancel()
            }
        }
    }
    init {
        timer.schedule(this.UpdateWindowGoal(), Date(), windowGoal.window.seconds * 1000 /** period is time in milliseconds between successive task executions*/)
    }
}