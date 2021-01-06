package com.example.dolares.data.local.model


import com.google.gson.annotations.SerializedName

data class Rocket(
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("boosters")
    val boosters: Int?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("cost_per_launch")
    val costPerLaunch: Int?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("diameter")
    val diameter: Diameter?,
    @SerializedName("engines")
    val engines: Engines?,
    @SerializedName("first_flight")
    val firstFlight: String?,
    @SerializedName("first_stage")
    val firstStage: FirstStage?,
    @SerializedName("flickr_images")
    val flickrImages: List<String?>?,
    @SerializedName("height")
    val height: Height?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("landing_legs")
    val landingLegs: LandingLegs?,
    @SerializedName("mass")
    val mass: Mass?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("payload_weights")
    val payloadWeights: List<PayloadWeight?>?,
    @SerializedName("second_stage")
    val secondStage: SecondStage?,
    @SerializedName("stages")
    val stages: Int?,
    @SerializedName("success_rate_pct")
    val successRatePct: Int?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("wikipedia")
    val wikipedia: String?
) {
    data class Diameter(
        @SerializedName("feet")
        val feet: Double?,
        @SerializedName("meters")
        val meters: Double?
    )

    data class Engines(
        @SerializedName("engine_loss_max")
        val engineLossMax: Int?,
        @SerializedName("isp")
        val isp: Isp?,
        @SerializedName("layout")
        val layout: String?,
        @SerializedName("number")
        val number: Int?,
        @SerializedName("propellant_1")
        val propellant1: String?,
        @SerializedName("propellant_2")
        val propellant2: String?,
        @SerializedName("thrust_sea_level")
        val thrustSeaLevel: ThrustSeaLevel?,
        @SerializedName("thrust_to_weight")
        val thrustToWeight: Double?,
        @SerializedName("thrust_vacuum")
        val thrustVacuum: ThrustVacuum?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("version")
        val version: String?
    ) {
        data class Isp(
            @SerializedName("sea_level")
            val seaLevel: Int?,
            @SerializedName("vacuum")
            val vacuum: Int?
        )

        data class ThrustSeaLevel(
            @SerializedName("kN")
            val kN: Int?,
            @SerializedName("lbf")
            val lbf: Int?
        )

        data class ThrustVacuum(
            @SerializedName("kN")
            val kN: Int?,
            @SerializedName("lbf")
            val lbf: Int?
        )
    }

    data class FirstStage(
        @SerializedName("burn_time_sec")
        val burnTimeSec: Int?,
        @SerializedName("engines")
        val engines: Int?,
        @SerializedName("fuel_amount_tons")
        val fuelAmountTons: Int?,
        @SerializedName("reusable")
        val reusable: Boolean?,
        @SerializedName("thrust_sea_level")
        val thrustSeaLevel: ThrustSeaLevel?,
        @SerializedName("thrust_vacuum")
        val thrustVacuum: ThrustVacuum?
    ) {
        data class ThrustSeaLevel(
            @SerializedName("kN")
            val kN: Int?,
            @SerializedName("lbf")
            val lbf: Int?
        )

        data class ThrustVacuum(
            @SerializedName("kN")
            val kN: Int?,
            @SerializedName("lbf")
            val lbf: Int?
        )
    }

    data class Height(
        @SerializedName("feet")
        val feet: Double?,
        @SerializedName("meters")
        val meters: Int?
    )

    data class LandingLegs(
        @SerializedName("material")
        val material: String?,
        @SerializedName("number")
        val number: Int?
    )

    data class Mass(
        @SerializedName("kg")
        val kg: Int?,
        @SerializedName("lb")
        val lb: Int?
    )

    data class PayloadWeight(
        @SerializedName("id")
        val id: String?,
        @SerializedName("kg")
        val kg: Int?,
        @SerializedName("lb")
        val lb: Int?,
        @SerializedName("name")
        val name: String?
    )

    data class SecondStage(
        @SerializedName("burn_time_sec")
        val burnTimeSec: Int?,
        @SerializedName("engines")
        val engines: Int?,
        @SerializedName("fuel_amount_tons")
        val fuelAmountTons: Int?,
        @SerializedName("payloads")
        val payloads: Payloads?,
        @SerializedName("reusable")
        val reusable: Boolean?,
        @SerializedName("thrust")
        val thrust: Thrust?
    ) {
        data class Payloads(
            @SerializedName("composite_fairing")
            val compositeFairing: CompositeFairing?,
            @SerializedName("option_1")
            val option1: String?
        ) {
            data class CompositeFairing(
                @SerializedName("diameter")
                val diameter: Diameter?,
                @SerializedName("height")
                val height: Height?
            ) {
                data class Diameter(
                    @SerializedName("feet")
                    val feet: Double?,
                    @SerializedName("meters")
                    val meters: Double?
                )

                data class Height(
                    @SerializedName("feet")
                    val feet: Int?,
                    @SerializedName("meters")
                    val meters: Double?
                )
            }
        }

        data class Thrust(
            @SerializedName("kN")
            val kN: Int?,
            @SerializedName("lbf")
            val lbf: Int?
        )
    }
}