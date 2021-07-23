package com.example.hearthealthmountain

import java.lang.Integer.min
import java.util.*

class Heart(maxValue: Int) : Points(maxValue) {
    override fun update(value: Int, time: Date?) {
        this.value = calculatePoints(value)
        TODO("Update the number of hearts, ie this.value")
    }
    private fun calculatePoints(v: Int) : Int {
        return if (v > 0) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                min(this.value+1, maxValue)
            } else {
                if (this.value+1 < maxValue) this.value+1 else maxValue
            }
        } else {
            if (this.value - 1 >= 0) this.value - 1 else 0
        }
        TODO("Do some computation")
    }
}

class Coins(maxValue: Int) : Points(maxValue) {
    override fun update(value: Int, time: Date?) {
        TODO("Not yet implemented")
    }

}