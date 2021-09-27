package com.example.hearthealthmountain

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.healthgamifylib.HealthData
import com.example.healthgamifylib.RepeatingWindowGoal
import java.time.Duration
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
//class PushButtonsGoal(repetitions: Int,
//                      targetValue: Int,
//                      start: Date,
//                      window: Duration,
//                      subject: HealthData
//) :
//        RepeatingWindowGoal(repetitions = repetitions,
//                targetValue = targetValue,
//                start = start,
//                window = window,
//                subject = subject
//        ) {
//
//    var pushes: Int = 0
//    override fun update(value: Any?) {
//        /**
//         * If target value is achieved, then update goal
//         */
//        if (!goal && ++pushes == targetValue) {
//            goal = true
//            // reset pushes
//            pushes = 0
//
//            notifyObservers()  // game elements should be notified
//        }
//    }
//
//    override fun notifyObservers() {
//        for (o in observers) {
//            o(null)
//        }
//    }
//}
class DailyStepGoal(targetValue: Int, observedData: HealthData, start: Date, window: Duration,
                    repetitions: Int
) : RepeatingWindowGoal(targetValue,
    observedData, start, window, repetitions
)


@RequiresApi(Build.VERSION_CODES.O)
class DailyWeighIn(targetValue: Int, observedData: HealthData, start: Date, window: Duration,
                   repetitions: Int
) : RepeatingWindowGoal(targetValue,
    observedData, start, window, repetitions
)