

package Kotlin.Today.Meowku.setting.delegate

import dev.lovelyneru.MeowKu.features.modules.Module
import dev.lovelyneru.MeowKu.features.setting.Bind


fun Module.setting(name: String, defaultValue: Int, minValue: Int, maxValue: Int): Value<Int> {
    return Value(name, defaultValue, minValue, maxValue).also { register(it) }
}

fun Module.setting(name: String, defaultValue: Float, minValue: Float, maxValue: Float): Value<Float> {
    return Value(name, defaultValue, minValue, maxValue).also { register(it) }
}

fun Module.setting(name: String, defaultValue: Double, minValue: Double, maxValue: Double): Value<Double> {
    return Value(name, defaultValue, minValue, maxValue).also { register(it) }
}

fun Module.setting(name: String, defaultValue: Boolean): Value<Boolean> {
    return Value(name, defaultValue).also { register(it) }
}

fun Module.setting(name: String, bind: Bind): Value<Bind> {
    return Value(name, bind).also { register(it) }
}

fun Module.setting(name: String, stringIn: String): Value<String> {
    return Value(name, stringIn).also { register(it) }
}

fun<T> Module.setting(name: String, defaultValue: T, minValue: T, maxValue: T): Value<T> {
    return Value(name, defaultValue, minValue, maxValue).also { register(it) }
}

fun<T: Enum<T>> Module.setting(name: String, defaultValue: T): Value<T> {
    return Value(name, defaultValue).also { register(it) }
}

