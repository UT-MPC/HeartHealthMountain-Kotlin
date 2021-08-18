package com.example.hearthealthmountain

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import org.bson.Document

interface SaveState {
    fun store(toStore: Any)
    fun retrieve(): Any
}

class HeartSaveState: SaveState {
    val password = "8dNIq16h7hLPzFYk"
    val url = "mongodb+srv://dbUser:$password@cluster0.iy0tp.mongodb.net/HeartHealthMountain?retryWrites=true&w=majority"
    val connectionString = ConnectionString(url)
    val settings = MongoClientSettings.builder().applyConnectionString(connectionString).build()
    val mongoClient = MongoClients.create(settings)
    val db = mongoClient.getDatabase("HeartHealthMountain")
    val collection = db.getCollection("Points")
    var currentHearts: Int = 0

    override fun store(toStore: Any) {
        currentHearts = toStore as Int
        val document = Document("hearts", currentHearts.toString())
        collection.insertOne(document)
    }

    override fun retrieve(): Any {
        return currentHearts
    }
}