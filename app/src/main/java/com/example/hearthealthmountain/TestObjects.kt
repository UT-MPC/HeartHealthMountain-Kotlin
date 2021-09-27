package com.example.hearthealthmountain

import com.example.healthgamifylib.Subject

class PushButtonSubject : Subject() {

    override fun notifyObservers() {
        for (o in observers) {
            o(null)
        }
    }
}