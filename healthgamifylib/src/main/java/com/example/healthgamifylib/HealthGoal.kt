package com.example.healthgamifylib

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.Duration
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
abstract class WindowGoal(
    var targetValue: Int,
    var start: Date,
    var window: Duration,
    var subject: Subject
) : Subject(), Observer {

    override fun notifyObservers() {
        for (o in observers) {
            o(goal)
        }
    }

    private val timer = Timer()
    private val tag = "WindowGoal"

    var goal: Boolean = false  // whether the goal is achieved or not
        set(value) {
            field = value
            if (value) {
                Log.i("WindowGoal", "Set goal true")
            }
        }

    protected open fun finalizeGoal() {}  // a hook. Called only if this goal is time triggered

    @RequiresApi(Build.VERSION_CODES.O)
    private inner class StartWindow() : TimerTask() {
        override fun run() {
            /**
             * Reset goal
             * Register as observer
             */
            goal = false
            subject.registerObserver(this@WindowGoal::update)
            Log.i(tag, "=====Window Starting=====")
        }
    }

    protected fun endWindow() {
        /**
         * Calling removeObserver first because last minute updates shouldn't be allowed.
         * When goals are being finalized, there shouldn't be any stragglers.
         */
        subject.removeObserver(this@WindowGoal::update)
        finalizeGoal()
    }

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
abstract class RepeatingWindowGoal(
    private var repetitions: Int, var streak: Int = 0,
    targetValue: Int,
    start: Date, window: Duration,
    subject: Subject
) :
    WindowGoal(
        targetValue,
        start,
        window,
        subject
    ) {
    /**
     * Since RepeatingWindowGoal is a WindowGoal, the super class is the window goal.
     * UpdateWindowGoal will update start, then call startWindow()
     * goalArray.size keeps track of how many repetitions have been done
     */
    private val timer: Timer = Timer()
    private val tag = "RepeatingWindowGoal"
    private val goalArray = mutableListOf<Boolean>()  // bool goalArray[repetitions]

    inner class UpdateWindowGoal() : TimerTask() {
        // create #repetitions of windowGoals
        @RequiresApi(Build.VERSION_CODES.O)
        override fun run() {
            /**
             * store windowGoal's goal variable in the goal array
             * update windowGoal's parameters
             */
            goalArray.add(goal)
            endWindow()  // end the previous window before starting new one
            Log.i(tag, "Ending ${goalArray.size} rep, goal is ${goal.toString()}")

            if (goalArray.size == repetitions) {  // finished all the repetitions
                timer.cancel()
                Log.i(tag, "Finished all repetitions.\n Goals: ${goalArray.toString()}")
            } else {  // still more reps to do
                start = Date(start.time + window.seconds * 1000)
                startWindow()
            }
        }
    }

    init {
        timer.schedule(
            this.UpdateWindowGoal(),
            Date(start.time + window.seconds * 1000),
            /** Because RepeatingWindowGoal is a WindowGoal,
             * the default constructor creates the first WindowGoal
             * so schedule the next
             */
            window.seconds * 1000
            /** period is time in milliseconds between successive task executions*/
        )
    }
}
