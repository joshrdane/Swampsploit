package me.smoothhacker.swampsploit.util

import java.net.InetAddress

abstract class Parameter constructor(open val value: Any?, open val required: Boolean) {
    val valid: Boolean
        get() {
            return !required || value != null
        }
}

data class InternetAddress constructor(override val value: InetAddress? = null, override val required: Boolean = true) : Parameter(value, required)
data class Port constructor(override val value: Int? = null, override val required: Boolean = true) : Parameter(value, required)
data class Path constructor(override val value: String? = null, override val required: Boolean = true) : Parameter(value, required)
data class Period constructor(override val value: Int? = null, override val required: Boolean = true) : Parameter(value, required) // value is in ms