package com.example.hearthealthmountain

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.Duration
import java.util.*

abstract class HealthGoal(var targetValue: Int)

@RequiresApi(Build.VERSION_CODES.O)
abstract class WindowGoal(targetValue: Int, var start: Date, var window: Duration, var subject: Subject) : HealthGoal(targetValue), Observer {
    var goal: Boolean = false  // whether the goal is achieved or not
    private val timer = Timer()
    private val tag = "WindowGoal"

    protected fun finalizeGoal() {}  // a hook. Called only if this goal is time triggered
    @RequiresApi(Build.VERSION_CODES.O)
    private inner class StartWindow() : TimerTask() {
        override fun run() {
            Log.i(tag, "window started")
            subject.registerObserver(this@WindowGoal::update)
            timer.schedule(EndWindow(), window.seconds * 1000)
        }
    }  // register as observer of data
    private inner class EndWindow() : TimerTask() {
        override fun run() {
            subject.removeObserver(this@WindowGoal::update)
            finalizeGoal()
            timer.cancel()
            Log.i(tag, "Window ending, goal is ${goal.toString()}")
        }

    }  // unregister as observer of data; call finalizeGoal

    init {
        /**
         * Corresponding to each Timer object is a single background thread that is used to execute all of the timer's tasks,
         * sequentially. Timer tasks should complete quickly.
         * If a timer task takes excessive time to complete, it "hogs" the timer's task execution thread.
         */
        startWindow()
    }
    protected fun startWindow() {
        timer.schedule(StartWindow(), start)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
abstract class RepeatingWindowGoal(private var repetitions: Int,
                          targetValue: Int,
                          start: Date, window: Duration,
                          subject: Subject) :
        WindowGoal(targetValue,
                start,
                window,
                subject) {
    /**
     * Since RepeatingWindowGoal is a WindowGoal, the super class is the window goal.
     * UpdateWindowGoal will update start, then call startWindow()
     * goalArray.size keeps track of how many repetitions have been done
     */
    private val timer:Timer = Timer()
    private val tag = "RepeatingWindowGoal"
    private val goalArray = mutableListOf<Boolean>()  // bool goalArray[repetitions]

    inner class UpdateWindowGoal() : TimerTask() {
        // create #repetitions of windowGoals
        @RequiresApi(Build.VERSION_CODES.O)
        override fun run() {
            Log.i(tag, "UpdateWindowGoal ending ${goalArray.size} + 1 rep")
            /**
             * store windowGoal's goal variable in the goal array
             * update windowGoal's parameters
             */
            goalArray.add(goal)
            if (goalArray.size == repetitions) {  // finished all the repetitions
                timer.cancel()
            } else {  // still more reps to do
                start = Date(start.time + window.seconds * 1000)
                goal = false
                startWindow()
            }
        }
    }
    init {
        timer.schedule(
                this.UpdateWindowGoal(),
                Date(start.time + window.seconds * 1000), /** the first window goal has started, so schedule the next*/
                window.seconds * 1000 /** period is time in milliseconds between successive task executions*/
        )
    }
}
