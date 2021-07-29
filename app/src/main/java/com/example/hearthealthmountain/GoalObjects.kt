package com.example.hearthealthmountain

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.Duration
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class PushButtonsGoal(repetitions: Int,
                      targetValue: Int,
                      start: Date,
                      window: Duration,
                      subject: Subject) :
        RepeatingWindowGoal(repetitions = repetitions,
                targetValue = targetValue,
                start = start,
                window = window,
                subject = subject
        ) {

    var pushes: Int = 0
    override fun update(value: Any?) {
        /**
         * If target value is achieved, then update goal
         */
        if (!goal && ++pushes == targetValue) {
            goal = true
            // reset pushes
            pushes = 0

            notifyObservers()  // game elements should be notified
        }
    }

    override var observers: MutableList<(Any?) -> Unit> = mutableListOf()

    override fun registerObserver(whatToCall: (Any?) -> Unit) {
        observers.add(whatToCall)
    }

    override fun removeObserver(whatNotToCall: (Any?) -> Unit) {
        observers.remove(whatNotToCall)
    }

    override fun notifyObservers() {
        for (o in observers) {
            o(null)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
class DailyWeighIn(
    repetitions: Int,
    streak: Int = 0,
    targetValue: Int,
    start: Date,
    window: Duration,
    subject: Subject
) : RepeatingWindowGoal(repetitions, streak, targetValue, start, window, subject) {

    override fun update(value: Any?) {
        if (!goal) {
            goal = true
            notifyObservers()
        }
        Log.i("DailyWeighIn", "value: $value")
    }

    override fun finalizeGoal() {
        if (!goal) {
            notifyObservers()
        }
    }

    override var observers: MutableList<(Any?) -> Unit> = mutableListOf()

    override fun registerObserver(whatToCall: (Any?) -> Unit) {
        observers.add(whatToCall)
    }

    override fun removeObserver(whatNotToCall: (Any?) -> Unit) {
        observers.remove(whatNotToCall)
    }

    override fun notifyObservers() {
        for (o in observers) {
            o(goal)
        }
    }
}