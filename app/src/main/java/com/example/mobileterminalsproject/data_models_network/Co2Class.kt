package com.example.mobileterminalsproject.data_models_network

import java.time.Month
import java.time.MonthDay
import java.time.Year

class Co2Class {
    //variables
    private var year: Year = Year.now()
    private var month: Month = Month.JANUARY
    private var day: MonthDay = MonthDay.now()
    private var cycle: Float = Float.NaN
    private var trend: Float = Float.NaN

    //methods
    public fun getYear() : Year { return year }
    public fun setYear(year : Year){ this.year = year }
    public fun getMonth() : Month { return month }
    public fun setMonth(month : Month){ this.month = month }
    public fun getDay() : MonthDay { return day }
    public fun setDay(day : MonthDay){ this.day = day }
    public fun getCycle() : Float { return cycle }
    public fun setCycle(cycle : Float){ this.cycle = cycle }
    public fun getTrend() : Float { return trend }
    public fun setTrend(trend : Float){ this.trend = trend }
}