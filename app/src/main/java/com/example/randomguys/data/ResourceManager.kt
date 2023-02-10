package com.example.randomguys.data

import android.content.Context
import javax.inject.Inject

class ResourceManager @Inject constructor(
    private val context: Context
) {

    fun string(id: Int, vararg args: Any): String =
        context.getString(id, args)

    fun stringArray(id: Int): Array<String> =
        context.resources.getStringArray(id)

}
