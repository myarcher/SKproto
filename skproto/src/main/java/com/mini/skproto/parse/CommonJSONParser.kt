package com.mini.skproto.parse

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList
import java.util.HashMap


class CommonJSONParser {
    fun parse(jsonStr: String?): Map<String, Any>? {
        var result: Map<String, Any>? = null

        if (null != jsonStr) {
            try {

                val jsonObject = JSONObject(jsonStr)
                result = parseJSONObject(jsonObject)

            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }

        return result
    }


    @Throws(JSONException::class)
    fun parseValue(inputObject: Any?): Any? {
        var outputObject: Any? = null

        if (null != inputObject) {
            if (inputObject is JSONArray) {
                outputObject = parseJSONArray(inputObject as JSONArray?)
            } else if (inputObject is JSONObject) {
                outputObject = parseJSONObject(inputObject as JSONObject?)
            } else if (inputObject is String || inputObject is Boolean || inputObject is Int || inputObject is Float || inputObject is Double) {
                outputObject = inputObject
            }

        }

        return outputObject
    }

    @Throws(JSONException::class)
    fun parseJSONArray(jsonArray: JSONArray?): List<Any>? {

        var valueList: MutableList<Any>? = null

        if (null != jsonArray) {
            valueList = ArrayList()

            for (i in 0 until jsonArray.length()) {
                val itemObject = jsonArray.get(i)
                if (null != itemObject) {
                    valueList.add(parseValue(itemObject)!!)
                }
            } // for (int i = 0; i < jsonArray.length(); i++) 
        } // if (null != valueStr) 

        return valueList
    }

    @Throws(JSONException::class)
    fun parseJSONObject(jsonObject: JSONObject?): Map<String, Any>? {

        var valueObject: MutableMap<String, Any>? = null
        if (null != jsonObject) {
            valueObject = HashMap()

            val keyIter = jsonObject.keys()
            while (keyIter.hasNext()) {
                val keyStr = keyIter.next()
                val itemObject = jsonObject.opt(keyStr)
                if (null != itemObject) {
                    valueObject.put(keyStr, parseValue(itemObject)!!)
                } // if (null != itemValueStr) 

            } // while (keyIter.hasNext()) 
        } // if (null != valueStr) 

        return valueObject
    }
} 

