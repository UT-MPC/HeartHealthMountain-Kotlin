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

    protected fun finalizeGoal() {}  // a hook. Called only if this goal is time triggered
    @RequiresApi(Build.VERSION_CODES.O)
    fun startWindow() {
        Log.i("Window goal", "window started")
        subject.registerObserver(this::update)
        /**
         * schedule a timertask that calls endWindow
         */
        val timer = Timer()
        timer.schedule(CallEndWindow(), window.seconds * 1000)
    }  // register as observer of data
    fun endWindow() {
        subject.removeObserver(this::update)
        finalizeGoal()
    }  // unregister as observer of data; call finalizeGoal

    inner class CallStartWindow() : TimerTask() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun run() {
            startWindow()
        }
    }
    inner class CallEndWindow() : TimerTask() {
        override fun run() {
            endWindow()
        }
    }
    init {
        val timer = Timer()
        /**
         * Corresponding to each Timer object is a single background thread that is used to execute all of the timer's tasks,
         * sequentially. Timer tasks should complete quickly.
         * If a timer task takes excessive time to complete, it "hogs" the timer's task execution thread.
         */
        timer.schedule(CallStartWindow(), start)
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
class RepeatingWindowGoal(var repetitions: Int, targetValue: Int, start: Date, window: Duration, subject: Subject) : WindowGoal(targetValue, start, window, subject) {  // TODO("inherit WindowGoal")
    private val timer:Timer = Timer()
    private val TAG = "RepeatingWindowGoal"
    private val goalArray = MutableList(repetitions) { false }  // bool goalArray[repetitions]
    private lateinit var windowGoal:WindowGoal

    inner class UpdateWindowGoal() : TimerTask() {
        // create #repetitions of windowGoals
        @RequiresApi(Build.VERSION_CODES.O)
        override fun run() {
            Log.i(TAG, "run UpdateWindowGoal run")
            /**
             * store windowGoal's goal variable in the goal array
             * update windowGoal's parameters
             */
        }
    }
    init {
        timer.schedule(this.UpdateWindowGoal(), Date(), window.seconds * 1000 /** period is time in milliseconds between successive task executions*/)
    }

    override fun update(value: Int, time: Date?) {
        /**
         * Does RepeatingWindowGoal observe anyone???
         */
    }
}