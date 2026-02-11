package com.example.testexchangeratesdataapi.domain.usecase

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class UpdateLastRefreshTimeUseCase @Inject constructor() {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("H:mm")

    operator fun invoke(): String {
        return LocalTime.now().format(formatter)
    }
}

