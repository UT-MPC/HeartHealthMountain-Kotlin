package com.example.hearthealthmountain

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.healthgamifylib.HealthData
import com.example.healthgamifylib.RepeatingWindowGoal
import com.example.healthgamifylib.WindowGoal
import java.time.Duration
import java.util.*
import java.util.concurrent.locks.ReentrantLock

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
class DailyStepGoal(repetitions: Int, streak: Int = 0, embeddedWindowGoal: WindowGoal
) : RepeatingWindowGoal(repetitions, streak, embeddedWindowGoal
)

@RequiresApi(Build.VERSION_CODES.O)
class StepGoal(targetValue: Int, observedData: HealthData, start: Date, window: Duration,
               lock: ReentrantLock
) : WindowGoal(targetValue, observedData,
    start,
    window, lock
)

@RequiresApi(Build.VERSION_CODES.O)
class DailyWeighIn(repetitions: Int, streak: Int = 0, embeddedWindowGoal: WindowGoal
) : RepeatingWindowGoal(repetitions, streak, embeddedWindowGoal
)

@RequiresApi(Build.VERSION_CODES.O)
class WeighInGoal(targetValue: Int, observedData: HealthData, start: Date, window: Duration,
                  lock: ReentrantLock
) : WindowGoal(targetValue,
    observedData, start, window, lock
)