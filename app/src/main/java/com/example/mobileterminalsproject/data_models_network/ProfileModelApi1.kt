package com.example.mobileterminalsproject.data_models_network

import java.time.Month
import java.time.MonthDay
import java.time.Year
import kotlin.collections.List

class ProfileModelApi1 {
    val co2List = listOf<Co2Class>()
}


/*
data class ProfileModelApi1 (
    var year: Year,
    var month: Month,
    var day: MonthDay,
    var cycle: Float,
    var trend: Float,
)
*/


/*
class ProfileModelApi1 {
    var year: String? = null
    var month: String? = null
    var day: String? = null
    var cycle: String? = null
    var trend: String? = null

    override fun toString() : String {
        return "ProfileModelApi1(year=$year, month=$month, day=$day, cycle=$cycle, trend=$trend)"
    }
}
 */


data class ProfileModelApi1(
    val id: String,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val full_name: String,
    val name: String
)
*/

