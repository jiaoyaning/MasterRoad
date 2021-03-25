package com.jyn.common.Utils

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/*
 * https://www.jianshu.com/p/edeaa2a13f78?utm_campaign=haruki
 *
 * private val mmkv: MMKV by lazy { MMKV.defaultMMKV() }
 *
 * private var boolean by mmkv.boolean(key = "boolean", defaultValue = false)
 * private var int by mmkv.int(key = "int", defaultValue = 0)
 * private var long by mmkv.long("long", 0L)
 * private var float by mmkv.float(key = "float", defaultValue = 0.0F)
 * private var double by mmkv.double(key = "double", defaultValue = 0.0)
 * private var byteArray by mmkv.byteArray(key = "byteArray")
 * private var string by mmkv.string(key = "string")
 * private var stringSet by mmkv.stringSet(key = "stringSet")
 * private var parcelable by mmkv.parcelable<UserData>("parcelable")
 *
 */
private inline fun <T> MMKV.delegate(
        key: String? = null, defaultValue: T,
        crossinline getter: MMKV.(String, T) -> T,
        crossinline setter: MMKV.(String, T) -> Boolean): ReadWriteProperty<Any, T> =

        object : ReadWriteProperty<Any, T> {
            override fun getValue(thisRef: Any, property: KProperty<*>): T {
                return getter(key ?: property.name, defaultValue)
            }

            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
                setter(key ?: property.name, value)
            }
        }

private inline fun <T> MMKV.nullableDefaultValueDelegate(
        key: String? = null, defaultValue: T?,
        crossinline getter: MMKV.(String, T?) -> T,
        crossinline setter: MMKV.(String, T) -> Boolean): ReadWriteProperty<Any, T> =

        object : ReadWriteProperty<Any, T> {
            override fun getValue(thisRef: Any, property: KProperty<*>): T =
                    getter(key ?: property.name, defaultValue)

            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
                setter(key ?: property.name, value)
            }
        }

fun MMKV.boolean(key: String? = null, defaultValue: Boolean = false): ReadWriteProperty<Any, Boolean> =
        delegate(key, defaultValue, MMKV::decodeBool, MMKV::encode)

fun MMKV.int(key: String? = null, defaultValue: Int = 0): ReadWriteProperty<Any, Int> =
        delegate(key, defaultValue, MMKV::decodeInt, MMKV::encode)

fun MMKV.long(key: String? = null, defaultValue: Long = 0L): ReadWriteProperty<Any, Long> =
        delegate(key, defaultValue, MMKV::decodeLong, MMKV::encode)

fun MMKV.float(key: String? = null, defaultValue: Float = 0.0F): ReadWriteProperty<Any, Float> =
        delegate(key, defaultValue, MMKV::decodeFloat, MMKV::encode)

fun MMKV.double(key: String? = null, defaultValue: Double = 0.0): ReadWriteProperty<Any, Double> =
        delegate(key, defaultValue, MMKV::decodeDouble, MMKV::encode)

fun MMKV.byteArray(key: String? = null, defaultValue: ByteArray? = null): ReadWriteProperty<Any, ByteArray?> =
        nullableDefaultValueDelegate(key, defaultValue, MMKV::decodeBytes, MMKV::encode)

fun MMKV.string(key: String? = null, defaultValue: String? = null): ReadWriteProperty<Any, String?> =
        nullableDefaultValueDelegate(key, defaultValue, MMKV::decodeString, MMKV::encode)

fun MMKV.stringSet(key: String? = null, defaultValue: Set<String>? = null): ReadWriteProperty<Any, Set<String>?> =
        nullableDefaultValueDelegate(key, defaultValue, MMKV::decodeStringSet, MMKV::encode)


