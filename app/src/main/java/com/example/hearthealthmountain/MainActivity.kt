package com.example.hearthealthmountain

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.time.Duration
import java.util.*
import com.example.hearthealthmountain.DailyStepGoal as DailyStepGoal1

// private key: 8b22a2fe-4245-400c-b225-a18dcf509f38
// realm-cli login --api-key onheejte --private-api-key 8b22a2fe-4245-400c-b225-a18dcf509f38
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

        //-----MongoDB Realm------//
        Realm.init(this) // context, usually an Activity or Application

        val realmName: String = "My Project"
        val config = RealmConfiguration.Builder().name(realmName).build()

        val backgroundThreadRealm : Realm = Realm.getInstance(config)
        //-----MongoDB Realm------//

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
        timer.schedule(updateTextView(), Date(), 20*1000)

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


enum class TaskStatus(val displayName: String) {
    Open("Open"),
    InProgress("In Progress"),
    Complete("Complete"),
}

open class Task() : RealmObject() {
    @PrimaryKey
    var name: String = "task"

    @Required
    var status: String = TaskStatus.Open.name
    var statusEnum: TaskStatus
        get() {
            // because status is actually a String and another client could assign an invalid value,
            // default the status to "Open" if the status is unreadable
            return try {
                TaskStatus.valueOf(status)
            } catch (e: IllegalArgumentException) {
                TaskStatus.Open
            }
        }
        set(value) { status = value.name }
}


