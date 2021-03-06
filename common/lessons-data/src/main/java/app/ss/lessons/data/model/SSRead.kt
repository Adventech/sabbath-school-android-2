/*
 * Copyright (c) 2021. Adventech <info@adventech.io>
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
package app.ss.lessons.data.model

import androidx.annotation.Keep
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.IgnoreExtraProperties

@Keep
@IgnoreExtraProperties
data class SSRead(
    val id: String = "",
    val date: String = "",
    val index: String = "",
    val title: String = "",
    val content: String = "",
    val bible: List<SSBibleVerses> = mutableListOf()
) {
    constructor(snapshot: DataSnapshot) : this(
        snapshot.child("id").getValue(String::class.java) ?: "",
        snapshot.child("date").getValue(String::class.java) ?: "",
        snapshot.child("index").getValue(String::class.java) ?: "",
        snapshot.child("title").getValue(String::class.java) ?: "",
        snapshot.child("content").getValue(String::class.java) ?: "",
        snapshot.child("bible").children.mapNotNull { SSBibleVerses(it) }
    )
}
