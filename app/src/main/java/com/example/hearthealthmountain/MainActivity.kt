package com.example.hearthealthmountain

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.Duration
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var weight: Weight
    private lateinit var weighed: Weighed
    private lateinit var heart: Heart
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
        weighed.registerObserver(dailyWeighIn::update)
//        dailyWeighIn.registerObserver(heart::update)
        var repeatingDailyWeighIn = RepeatingWindowGoal(dailyWeighIn, 30)
    }
}
