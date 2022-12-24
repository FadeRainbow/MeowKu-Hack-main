
package Kotlin.Today.Meowku.setting.delegate

import dev.lovelyneru.MeowKu.features.setting.Setting

import java.util.function.Predicate
import kotlin.reflect.KProperty

class Value<T>: Setting<T> {
    constructor(name: String, defaultValue: T) : super(name, defaultValue)

    constructor(name: String, defaultValue: T, min: T, max: T) : super(name, defaultValue, min, max)

    constructor(
        name: String,
        defaultValue: T,
        min: T,
        max: T,
        visibility: Predicate<T>,
        description: String
    ) : super(name, defaultValue, min, max, visibility, description)

    constructor(name: String, defaultValue: T, min: T, max: T, visibility: Predicate<T>) : super(
        name,
        defaultValue,
        min,
        max,
        visibility
    )

    constructor(name: String, defaultValue: T, visibility: Predicate<T>) : super(name, defaultValue, visibility)

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = this.value

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }

    fun setLimiter(block: (T) -> T) {

    }
}