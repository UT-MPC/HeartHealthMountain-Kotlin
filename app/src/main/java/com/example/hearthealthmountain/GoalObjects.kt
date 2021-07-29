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
        }
        Log.i("DailyWeighIn", "value: $value")
    }
}