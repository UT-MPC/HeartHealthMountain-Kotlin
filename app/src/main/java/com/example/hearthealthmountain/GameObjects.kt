package com.example.hearthealthmountain

import android.util.Log
import com.example.healthgamifylib.Points
import java.lang.Integer.min

class Heart(maxValue: Int) : Points(maxValue) {
    private val tag = "Heart"

    val saveState = HeartSaveState()

    override fun update(value: Any?) {
        this.value = calculatePoints(value as Boolean)
        saveState.store(this.value)
        Log.i(tag, "Num hearts: ${saveState.currentHearts}")
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

//class Coins(maxValue: Int) : Points(maxValue) {
//    override fun update(value: Int, time: Date?) {
//        TODO("Not yet implemented")
//    }
//
//}