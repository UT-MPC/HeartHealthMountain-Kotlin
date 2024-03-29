package com.example.hearthealthmountain

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.healthgamifylib.Points
import com.example.healthgamifylib.Progress
import java.lang.Integer.max
import java.lang.Integer.min

class Heart(maxValue: Int) : Points(maxValue) {
    private val tag = "Heart"

    override fun update(value: Any?) {
        if (value is Boolean) {
            this.value = calculatePoints(value as Boolean)
        }
        Log.i(tag, "Num hearts: ${this.value}")
    }
    private fun calculatePoints(v: Boolean) : Int {
        return if (v ) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                min(this.value +1, maxValue)
            } else {
                if (this.value+1 < maxValue) this.value+1 else maxValue
            }
        } else {
            if (this.value - 1 >= 0) this.value - 1 else 0
        }
    }

}

class Mountain(totalProgress: Int) : Progress(totalProgress) {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun update(value: Any?) {
        if (value is Boolean) {
            if (value) {
                currentProgress ++
            }
            else {
                currentProgress = max(currentProgress-1, 0)
            }
        }
        Log.i("Mountain", "current progress: $currentProgress")
    }
}

//class Coins(maxValue: Int) : Points(maxValue) {
//    override fun update(value: Int, time: Date?) {
//        TODO("Not yet implemented")
//    }
//
//}