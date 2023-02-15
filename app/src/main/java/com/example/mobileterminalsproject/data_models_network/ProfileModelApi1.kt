package com.example.mobileterminalsproject.data_models_network


import java.time.Month
import java.time.MonthDay
import java.time.Year

data class ProfileModelApi1 (
    var year: Year,
    var month: Month,
    var day: MonthDay,
    var cycle: Float,
    var trend: Float,
)