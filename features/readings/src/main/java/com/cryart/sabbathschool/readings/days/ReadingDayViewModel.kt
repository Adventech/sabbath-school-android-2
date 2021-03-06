package com.cryart.sabbathschool.readings.days

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ss.lessons.data.model.SSReadComments
import app.ss.lessons.data.model.SSReadHighlights
import app.ss.lessons.data.repository.lessons.LessonsRepository
import com.cryart.sabbathschool.core.response.Resource
import com.cryart.sabbathschool.core.extensions.coroutines.SchedulerProvider
import com.cryart.sabbathschool.core.extensions.logger.timber
import com.cryart.sabbathschool.readings.components.model.ReadingDay
import com.cryart.sabbathschool.readings.days.components.ReadingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingDayViewModel @Inject constructor(
    private val lessonsRepository: LessonsRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private val logger by timber()

    private val _readData = MutableStateFlow<ReadingData>(ReadingData.Empty)
    val readDataFlow: StateFlow<ReadingData> get() = _readData

    fun loadData(day: ReadingDay) = viewModelScope.launch(schedulerProvider.io) {
        val resource = try {
            lessonsRepository.getDayRead(day.index)
        } catch (er: Throwable) {
            logger.e(er)
            Resource.error(er)
        }

        val dayRead = resource.data
        if (dayRead != null) {
            _readData.emit(
                ReadingData.Content(
                    dayRead.content.trim(),
                    SSReadHighlights(day.index),
                    SSReadComments(day.index, emptyList())
                )
            )
        }
    }
}
