package com.example.hearthealthmountain

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.Duration
import java.util.*
import com.example.hearthealthmountain.DailyStepGoal as DailyStepGoal1

class MainActivity : AppCompatActivity() {
    private lateinit var weight: Weight
    private lateinit var weighed: Weighed
    private lateinit var heartUI: TextView
    private lateinit var weightUI: TextView
    private lateinit var heart: Heart

    //    private lateinit var heart: Heart
    private lateinit var pushButtonsGoal: PushButtonsGoal

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

        weight = Weight(healthDataSource = "someDevice", name = "weight")
        weighed = Weighed(healthDataSource = "computed", name = "weighed")
        weight.registerObserver(weighed::update)
        weightUI = findViewById<TextView>(R.id.editTextWeight)

        val timer = Timer()
        timer.schedule(updateTextView(), Date())

        val dailyWeighIn = DailyWeighIn(
            repetitions = 30,
            streak = 25,
            targetValue = 1,
            start = startDate,
            window = Duration.ofSeconds(weight.updatePeriod),
            subject = weighed
        )
        dailyWeighIn.registerObserver(heart::update)

        val step = Step(healthDataSource = "someDevice", name = "step")
        val dailyStepGoal = DailyStepGoal1(
            repetitions = 30,
            streak = 30,
            targetValue = 1600,
            start = startDate,
            window = Duration.ofSeconds(step.updatePeriod*20),
            subject = step
        )
        dailyStepGoal.registerObserver(heart::update)




//         val pushButtonSubject = PushButtonSubject()
//         pushButtonsGoal = PushButtonsGoal(5, 3, Date(), Duration.ofMinutes(1), pushButtonSubject)
//
//         var pushes: Int = 0
        val pushButton: Button = findViewById(R.id.button)
        pushButton.setOnClickListener {
//             pushButtonSubject.notifyObservers()
//             pushButton.text = (++pushes).toString()
        }
    }

    inner class updateTextView(): TimerTask() {
        override fun run() {
            Log.i("updateTextView", "updating")
            heartUI.text = heart.value.toString()
            weightUI.text = weight.value.toString()
        }

    }

}
