package com.example.dolares.dummy_test_data

import com.example.dolares.data.local.model.Capsule
import com.google.gson.Gson

fun createTestCapsuleDUMMY1(): Capsule {
    val jsonString = """{
    "reuse_count": 1,
    "water_landings": 1,
    "land_landings": 0,
    "last_update": "Reentered after three weeks in orbit",
    "launches": [
        "5eb87cdeffd86e000604b330"
    ],
    "serial": "C101",
    "status": "retired",
    "type": "Dragon 1.0",
    "id": "5e9e2c5bf35918ed873b2664"
}"""
    val gson = Gson()

    return gson.fromJson(jsonString, Capsule::class.java)
}

fun createTestCapsuleDUMMY2(): Capsule {
    val jsonString = """{
    "reuse_count": 1,
    "water_landings": 1,
    "land_landings": 0,
    "last_update": "Reentered after three weeks in orbit",
    "launches": [
        "5eb87cdeffd86e000604b330"
    ],
    "serial": "C101",
    "status": "retired",
    "type": "Dragon 1.0",
    "id": "5e9e2c5asdfbf35918ed873b2664"
}"""
    val gson = Gson()

    return gson.fromJson(jsonString, Capsule::class.java)
}