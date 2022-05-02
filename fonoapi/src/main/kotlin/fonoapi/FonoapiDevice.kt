package fonoapi

import com.fasterxml.jackson.annotation.JsonProperty

data class FonoapiDevice(
    val technology: String,
    @JsonProperty("_2g_bands") val _2g_bands: String,
    @JsonProperty("_3g_bands") val _3g_bands: String,
    @JsonProperty("_4g_bands") val _4g_bands: String
)
