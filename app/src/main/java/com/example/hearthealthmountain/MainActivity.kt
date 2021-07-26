package com.example.hearthealthmountain

import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.Duration
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var weight: Weight
    private lateinit var weighed: Weighed
    private lateinit var heart: Heart
    private lateinit var pushButtonsGoal: PushButtonsGoal
    @RequiresApi(Build.VERSION_CODES.O)
    private lateinit var dailyWeighIn: DailyWeighIn
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weight = Weight(healthDataSource = "someDevice", name = "weight")
        weighed = Weighed(healthDataSource = "computed", name = "weighed")
        heart = Heart(1000)
        dailyWeighIn = DailyWeighIn(targetValue = 1, start = Date(), window = Duration.ofDays(1),
            subject = weighed
        )

        weight.registerObserver(weighed::update)
//        pushButtonsGoal = PushButtonsGoal(5, 3, Date(), Duration.ofMinutes(1))
//        TODO("create a subject for pushButtonGoal to observe")

        val pushButton: Button = findViewById(R.id.button)
        pushButton.setOnClickListener {  }
        TODO("link this with pushButtonGoal")

//        dailyWeighIn.registerObserver(heart::update)
//        var repeatingDailyWeighIn = RepeatingWindowGoal(repetitions = 30,
//                targetValue = 1,
//                start = Date(),
//                window = Duration.ofDays(1),
//                subject = weighed
//        )
    }
}
