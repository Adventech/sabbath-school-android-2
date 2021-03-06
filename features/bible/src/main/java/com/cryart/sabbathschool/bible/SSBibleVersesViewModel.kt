/*
 * Copyright (c) 2016 Adventech <info@adventech.io>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.cryart.sabbathschool.bible

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ss.lessons.data.model.SSBibleVerses
import app.ss.lessons.data.repository.lessons.LessonsRepository
import com.cryart.sabbathschool.core.extensions.coroutines.flow.stateIn
import com.cryart.sabbathschool.core.extensions.prefs.SSPrefs
import com.cryart.sabbathschool.core.misc.SSConstants
import com.cryart.sabbathschool.core.model.SSReadingDisplayOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SSBibleVersesViewModel @Inject constructor(
    private val preferences: SSPrefs,
    private val lessonsRepository: LessonsRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val bibleVersesFlow: StateFlow<List<SSBibleVerses>> = flowOf(
        savedStateHandle.get<String>(SSConstants.SS_READ_INDEX_EXTRA)
    ).map { readIndex ->
        readIndex?.let {
            val data = lessonsRepository.getDayRead(readIndex)
            data.data?.bible ?: emptyList()
        } ?: emptyList()
    }
        .catch { Timber.e(it) }
        .stateIn(
            scope = viewModelScope,
            initialValue = emptyList()
        )

    fun getLastBibleUsed() = preferences.getLastBibleUsed()

    fun setLastBibleUsed(bibleId: String) = preferences.setLastBibleUsed(bibleId)

    fun displayOptions(callback: (SSReadingDisplayOptions) -> Unit) = preferences.getDisplayOptions(callback)
}
