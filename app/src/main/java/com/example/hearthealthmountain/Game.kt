package com.example.hearthealthmountain

import com.example.healthgamifylib.Observer

class Game {
}
class GameGoal

abstract class Points (var maxValue: Int) : Observer {
    var value: Int = 0
}

abstract class Prompt : Observer