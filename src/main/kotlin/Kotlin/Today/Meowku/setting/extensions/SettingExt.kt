
package Kotlin.Today.Meowku.setting.extensions

import Kotlin.Today.Meowku.setting.delegate.Value

inline fun <reified T: Any> Value<T>.visible(noinline block: (T) -> Boolean): Value<T> =
    this.also { it.setVisibility(block) }

inline fun <reified T: Any> Value<T>.limit(noinline block: (T) -> T): Value<T> {
    return this.also { it.setLimiter(block) }
}