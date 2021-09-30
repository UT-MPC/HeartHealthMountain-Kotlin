package com.example.healthgamifylib

class Game {
}
class GameGoal

abstract class Points (var maxValue: Int) : Observer {
    var value: Int = 0
}

abstract class Progress (val totalProgress: Int) : Observer {
    var currentProgress = 0
    override fun update(value: Any?) {
        if (value is Boolean && value) {
            currentProgress += 1
        }
    }
}

abstract class Prompt : Observer
