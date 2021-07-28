package com.example.hearthealthmountain

class PushButtonSubject : Subject {
    override var observers: MutableList<(Any?) -> Unit> = mutableListOf()
    override fun registerObserver(whatToCall: (Any?) -> Unit) {
        observers.add(whatToCall)
    }

    override fun removeObserver(whatNotToCall: (Any?) -> Unit) {
        observers.remove(whatNotToCall)
    }

    override fun notifyObservers() {
        for (o in observers) {
            o(null)
        }
    }
}