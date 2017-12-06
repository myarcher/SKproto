package com.mini.skproto.until

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import java.io.*
import kotlin.reflect.KProperty

class ShareUntil {
    companion object {
        private var shareUntil: ShareUntil? = null
        fun get(name: String, context: Context): ShareUntil {
            if (shareUntil == null) {
                shareUntil = ShareUntil(name, context)
            }
            return shareUntil!!
        }
    }

    constructor(name: String, context: Context) {
        prefs = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        editor = prefs!!.edit()
    }

    var prefs: SharedPreferences? = null
    private val editor: Editor


    fun clearPreference(key: String) {
        editor.remove(key).commit()
    }

    fun clearPreference() {
        editor.clear().commit()
    }

    fun <T> gets(name: String, default: T): T {
        return findPreference(name, default)
    }

    fun sets(name: String, value: Any) {
        putPreference(name, value)
    }


    private fun <A> findPreference(name: String, default: A): A = with(prefs!!) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> deSerialization(getString(name, serialize(default)))
        }
        res as A
    }

    private fun putPreference(name: String, value: Any): ShareUntil = with(prefs!!.edit())
    {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> putString(name, serialize(value))
        }.apply()
        return shareUntil!!
    }

    @Throws(IOException::class)
    private fun <A> serialize(obj: A): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(
                byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    /**
     * 反序列化对象

     * @param str
     * *
     * @return
     * *
     * @throws IOException
     * *
     * @throws ClassNotFoundException
     */
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun <A> deSerialization(str: String): A {
        val redStr = java.net.URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(
                redStr.toByteArray(charset("ISO-8859-1")))
        val objectInputStream = ObjectInputStream(
                byteArrayInputStream)
        val obj = objectInputStream.readObject() as A
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }

}
