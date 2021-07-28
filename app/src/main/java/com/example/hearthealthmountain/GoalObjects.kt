package com.example.hearthealthmountain

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class PushButtonsGoal(repetitions: Int,
                      targetValue: Int,
                      start: Date,
                      window: Duration,
                      subject: Subject) :
        RepeatingWindowGoal(repetitions,
                targetValue,
                start,
                window,
                subject
        ) {
    var pushes: Int = 0
    override fun update(value: Any?) {
        if (++pushes == targetValue) {
            goal = true
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
class DailyWeighIn(targetValue: Int, start: Date, window: Duration, subject: Subject) : WindowGoal(targetValue, start, window, subject), Subject {
    override var observers: MutableList<(Any?) -> Unit> = mutableListOf()

    override fun update(value: Any?) {
        goal = true
        // TODO("that's all?")
    }

    /**
     * Game points subscribe to health goals
     */

    override fun registerObserver(whatToCall: (Any?) -> Unit) {
        observers.add(whatToCall)
        // TODO("Not yet implemented")
    }

    override fun removeObserver(whatNotToCall: (Any?) -> Unit) {
        observers.remove(whatNotToCall)
        // TODO("Not yet implemented")
    }

    override fun notifyObservers() {
        for (o in observers) {
            o(goal)
        }
    }

}