package com.example.hearthealthmountain

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.Duration
import java.util.*

// private key: 8b22a2fe-4245-400c-b225-a18dcf509f38
// realm-cli login --api-key onheejte --private-api-key 8b22a2fe-4245-400c-b225-a18dcf509f38
class MainActivity : AppCompatActivity() {
    private lateinit var weighed: Weighed
    private lateinit var heartUI: TextView
    private lateinit var weightUI: TextView
    private lateinit var heart: Heart
    private lateinit var withingsScale: WithingsScale
    private lateinit var activityTracker: ActivityTracker

    //    private lateinit var heart: Heart
    // private lateinit var pushButtonsGoal: PushButtonsGoal

    @RequiresApi(Build.VERSION_CODES.O)
    private lateinit var dailyWeighIn: DailyWeighIn

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("onCreate", "start")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startDate = Date(Date().time + 5000)  // 5 seconds after onCreate starts running

        heart = Heart(1000)
        heartUI = findViewById<TextView> (R.id.editTextHeart)
        heart.value = 500  // let there be 500 hearts in the beginning
        heartUI.text = heart.value.toString()

        withingsScale = WithingsScale("Withings Scale")
        activityTracker = ActivityTracker("Activity Tracker")

        weighed = Weighed(healthDataSource = withingsScale, name = "weighed")
        weightUI = findViewById<TextView>(R.id.editTextWeight)

//        val weightAnomaly = WeightAnomaly(-1, -1, weighed)
//        val trigger = Trigger(weightAnomaly)
//        val mountain = Mountain(totalProgress = 20)

        val timer = Timer()
        timer.schedule(updateTextView(), Date(), 20*1000)

        val dailyWeighIn = DailyWeighIn(
            repetitions = 30,
            targetValue = 1,
            start = startDate,
            window = Duration.ofSeconds(withingsScale.updatePeriod),
            observedData = weighed
        )
        dailyWeighIn.registerObserver(heart::update)
//        dailyWeighIn.registerObserver(mountain::update)
        dailyWeighIn.log = false

        val step = Step(healthDataSource = activityTracker, name = "step")
        val dailyStepGoal = DailyStepGoal(
            repetitions = 30,
            observedData = step,
            targetValue = 1600,
            start = startDate,
            window = Duration.ofSeconds(activityTracker.updatePeriod*20)
        )
        dailyStepGoal.registerObserver(heart::update)
        //dailyStepGoal.registerObserver(mountain::update)




//         val pushButtonSubject = PushButtonSubject()
//         pushButtonsGoal = PushButtonsGoal(5, 3, Date(), Duration.ofMinutes(1), pushButtonSubject)
//
//         var pushes: Int = 0
//        val pushButton: Button = findViewById(R.id.button)
//        pushButton.setOnClickListener {
////             pushButtonSubject.notifyObservers()
////             pushButton.text = (++pushes).toString()
//        }
    }

    inner class updateTextView(): TimerTask() {
        override fun run() {
            Log.i("updateTextView", "updating")
            heartUI.text = heart.value.toString()
            weightUI.text = weighed.value.toString()
        }

    }

}


