package com.sample.frontproxykt.utils

import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

@Component
class JSONConverter {

    fun <T : Any> toMap(obj: T): Map<String, Any?> {
        return (obj::class as KClass<T>).memberProperties.associate { prop ->
            prop.name to prop.get(obj)?.let { value ->
                if (value::class.isData) {
                    toMap(value)
                } else {
                    value
                }
            }
        }
    }
}