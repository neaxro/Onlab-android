package hu.bme.aut.android.securityapp.data.model.shift

import hu.bme.aut.android.securityapp.data.model.job.DetailedJob
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Shift(
    val id: Int = 0,
    val startTime: String = "",
    val endTime: String? = null,
    val earnedMoney: Double? = null,
    val person: Person = Person(),
    val job: DetailedJob = DetailedJob(),
    val status: Status = Status(),
    val wage: Wage = Wage(),
)

fun Shift.getStartTime(): LocalDateTime{
    return LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))
}

fun Shift.getStartTimeString(): String{
    val startTime = getStartTime()

    return String.format("%04d.%02d.%02d\t%02d:%02d",
        startTime.year,
        startTime.month.value,
        startTime.dayOfMonth,
        startTime.hour,
        startTime.minute
    )
}

fun Shift.getEndTime(): LocalDateTime?{
    return if(endTime != null){
        LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))
    }
    else{
        null
    }
}

fun Shift.getElapsedTimeString(): String{
    val duration = Duration.between(this.getStartTime(), LocalDateTime.now())

    val hours = duration.toHours()
    val minutes = (duration.toMinutes() % 60)
    val seconds = (duration.seconds % 60)

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

fun Shift.getEarnedMoney(): Double{
    val duration = Duration.between(this.getStartTime(), LocalDateTime.now())
    val totalHours = duration.toHours().toDouble() + (duration.toMinutes().toDouble() % 60) / 60

    return this.wage.price * totalHours
}