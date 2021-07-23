package com.example.hearthealthmountain

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class DailyWeighIn(targetValue: Int, start: Date, window: Duration, subject: Subject) : TargetWindowGoal(targetValue, start, window, subject), Subject {
    /**
     * Maybe change this to a DailyBooleanGoal? Since it's generic
    */

    override var observers: MutableMap<(Int, Date?) -> Unit, (Int, Date?) -> Unit> = mutableMapOf()

    override fun update(value: Int, time: Date?) {
        goal = true
        // TODO("that's all?")
    }

    /**
     * Game points subscribe to health goals
     */

    override fun registerObserver(whatToCall: (Int, Date?) -> Unit) {
        observers[whatToCall] = whatToCall
        // TODO("Not yet implemented")
    }

    override fun removeObserver(whatNotToCall: (Int, Date?) -> Unit) {
        observers.remove (whatNotToCall)
        // TODO("Not yet implemented")
    }

    override fun notifyObservers() {
        for (o in observers.values) {
            o(if (goal) 1 else 0, start)
        }
    }

}