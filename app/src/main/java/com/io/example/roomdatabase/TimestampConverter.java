package com.io.example.roomdatabase;

import androidx.room.TypeConverter;

import java.sql.Timestamp;

public class TimestampConverter {
        @TypeConverter
        public static Timestamp fromTimestamp(Long value) {
            return value != null ? new Timestamp(value) : null;
        }

        @TypeConverter
        public static Long toTimestamp(Timestamp timestamp) {
            return timestamp != null ? timestamp.getTime() : null;
        }
    }
