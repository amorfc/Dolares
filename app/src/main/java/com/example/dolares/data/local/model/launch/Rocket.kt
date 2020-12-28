package com.example.dolares.data.local.model.launch

import com.google.gson.annotations.SerializedName

data class Rocket(
    @SerializedName("fairings")
    val fairings: Fairings?,
    @SerializedName("first_stage")
    val firstStage: FirstStage?,
    @SerializedName("rocket_id")
    val rocketId: String?,
    @SerializedName("rocket_name")
    val rocketName: String?,
    @SerializedName("rocket_type")
    val rocketType: String?,
    @SerializedName("second_stage")
    val secondStage: SecondStage?
) {
    data class Fairings(
        @SerializedName("recovered")
        val recovered: Boolean?,
        @SerializedName("recovery_attempt")
        val recoveryAttempt: Boolean?,
        @SerializedName("reused")
        val reused: Boolean?,
        @SerializedName("ship")
        val ship: Any?
    )

    data class FirstStage(
        @SerializedName("cores")
        val cores: List<Core?>?
    ) {
        data class Core(
            @SerializedName("block")
            val block: Any?,
            @SerializedName("core_serial")
            val coreSerial: String?,
            @SerializedName("flight")
            val flight: Int?,
            @SerializedName("gridfins")
            val gridfins: Boolean?,
            @SerializedName("land_success")
            val landSuccess: Any?,
            @SerializedName("landing_intent")
            val landingIntent: Boolean?,
            @SerializedName("landing_type")
            val landingType: Any?,
            @SerializedName("landing_vehicle")
            val landingVehicle: Any?,
            @SerializedName("legs")
            val legs: Boolean?,
            @SerializedName("reused")
            val reused: Boolean?
        )
    }

    data class SecondStage(
        @SerializedName("block")
        val block: Int?,
        @SerializedName("payloads")
        val payloads: List<Payload?>?
    ) {
        data class Payload(
            @SerializedName("customers")
            val customers: List<String?>?,
            @SerializedName("manufacturer")
            val manufacturer: String?,
            @SerializedName("nationality")
            val nationality: String?,
            @SerializedName("norad_id")
            val noradId: List<Any?>?,
            @SerializedName("orbit")
            val orbit: String?,
            @SerializedName("payload_id")
            val payloadId: String?,
            @SerializedName("payload_mass_kg")
            val payloadMassKg: Double?,
            @SerializedName("payload_mass_lbs")
            val payloadMassLbs: Double?,
            @SerializedName("payload_type")
            val payloadType: String?,
            @SerializedName("reused")
            val reused: Boolean?
        )
    }
}