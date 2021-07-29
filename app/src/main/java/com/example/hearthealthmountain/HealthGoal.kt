package com.example.hearthealthmountain

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.Duration
import java.util.*

abstract class HealthGoal(var targetValue: Int)

@RequiresApi(Build.VERSION_CODES.O)
abstract class WindowGoal(
    targetValue: Int,
    var start: Date,
    var window: Duration,
    var subject: Subject
) : HealthGoal(targetValue), Observer {
    private val timer = Timer()
    private val tag = "WindowGoal"

    var goal: Boolean = false  // whether the goal is achieved or not
        set(value) {
            field = value
            if (value) {
                Log.i("WindowGoal", "Set goal true")
            }
        }

    protected fun finalizeGoal() {}  // a hook. Called only if this goal is time triggered

    @RequiresApi(Build.VERSION_CODES.O)
    private inner class StartWindow() : TimerTask() {  // TODO(Should goal be refreshed here, or refreshed when window ends?)
        override fun run() {
            subject.registerObserver(this@WindowGoal::update)
//            timer.schedule(EndWindow(), window.seconds * 1000)
            Log.i(tag, "=====Window Starting=====")
        }
    }  // register as observer of data

    protected fun endWindow() {
        subject.removeObserver(this@WindowGoal::update)
        finalizeGoal()

        // reset goal
        goal = false

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
            Log.i(tag, "Ending ${goalArray.size + 1} rep, goal is ${goal.toString()}")
            /**
             * store windowGoal's goal variable in the goal array
             * update windowGoal's parameters
             */
            goalArray.add(goal)
            endWindow()  // end the previous window before starting new one
            if (goalArray.size == repetitions) {  // finished all the repetitions
                timer.cancel()
                Log.i(tag, "Finished all repetitions.\n Goals: ${goalArray.toString()}")
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
            Date(start.time + window.seconds * 1000),
            /** the first window goal has started, so schedule the next*/
            window.seconds * 1000
            /** period is time in milliseconds between successive task executions*/
        )
    }
}
