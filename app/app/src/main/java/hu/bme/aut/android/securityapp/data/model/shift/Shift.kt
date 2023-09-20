package hu.bme.aut.android.securityapp.data.model.shift

import hu.bme.aut.android.securityapp.data.model.job.DetailedJob
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.data.model.wage.Wage
import java.time.Duration
import java.time.LocalDateTime

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

private val dateTimePattern: String = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"

fun Shift.getStart(): LocalDateTime{
    return LocalDateTime.parse(startTime)
}

fun Shift.getStartDate(): String{
    val startTime = getStart()

    return String.format("%04d.%02d.%02d",
        startTime.year,
        startTime.month.value,
        startTime.dayOfMonth
    )
}

fun Shift.getStartTime(): String{
    val startTime = getStart()

    return String.format("%02d:%02d",
        startTime.hour,
        startTime.minute
    )
}

fun Shift.getEnd(): LocalDateTime?{
    endTime ?: return null
    return LocalDateTime.parse(endTime)
}

fun Shift.getEndDate(): String{
    val endTime = getEnd() ?: return ""

    return String.format("%04d.%02d.%02d",
        endTime.year,
        endTime.month.value,
        endTime.dayOfMonth
    )
}

fun Shift.getEndTime(): String{
    val endTime = getEnd() ?: return ""

    return String.format("%02d:%02d",
        endTime.hour,
        endTime.minute
    )
}

fun Shift.getElapsedTimeString(): String{
    val duration = Duration.between(this.getStart(), LocalDateTime.now())

    val hours = duration.toHours()
    val minutes = (duration.toMinutes() % 60)
    val seconds = (duration.seconds % 60)

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

fun Shift.getTotalHours(): Double{
    this.endTime ?: return 0.0

    val duration = Duration.between(this.getStart(), this.getEnd())
    val hours = duration.toMinutes() / 60 + (duration.toMinutes() % 60) / 100.0

    return hours
}

fun Shift.getEarnedMoney(): Double{
    val duration = Duration.between(this.getStart(), LocalDateTime.now())
    val totalHours = duration.toHours().toDouble() + (duration.toMinutes().toDouble() % 60) / 60

    return this.wage.price * totalHours
}