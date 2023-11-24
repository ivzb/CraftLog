package com.ivzb.craftlog.data

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.util.Date

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun stringToBigDecimal(input: String?): BigDecimal {
        if (input.isNullOrBlank()) {
            return BigDecimal.valueOf(0.0)
        }

        return input.toBigDecimalOrNull() ?: BigDecimal.valueOf(0.0)
    }

    @TypeConverter
    fun bigDecimalToString(input: BigDecimal?): String {
        return input?.toPlainString() ?: ""
    }
}
