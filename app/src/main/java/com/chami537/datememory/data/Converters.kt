package com.chami537.datememory.data

import androidx.room.TypeConverter
import com.chami537.datememory.model.DisplayMode

class Converters {
    @TypeConverter
    fun fromDisplayMode(mode: DisplayMode): String {
        return mode.name
    }

    @TypeConverter
    fun toDisplayMode(mode: String): DisplayMode {
        return DisplayMode.valueOf(mode)
    }
}
