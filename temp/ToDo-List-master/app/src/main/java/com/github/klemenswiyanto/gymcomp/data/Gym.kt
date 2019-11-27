package com.github.klemenswiyanto.gymcomp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fitness_table")
data class Gym(

    @ColumnInfo(name = "Title")
    var title: String,

    @ColumnInfo(name = "Description")
    var sets: String,

    @ColumnInfo(name = "Time")
    var reps: String,

    @ColumnInfo(name = "Date")
    var duedate: String
) {
    //does it matter if these are private or not?
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}