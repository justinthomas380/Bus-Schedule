/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.busschedule.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.busschedule.BusScheduleApplication
import com.example.busschedule.data.BusSchedule
import com.example.busschedule.data.BusScheduleDao
import kotlinx.coroutines.flow.Flow

class BusScheduleViewModel(
    private val busScheduleDao: BusScheduleDao): ViewModel() {

    /** Gets the full schedule from the room in ASC order.
     * NOTES
     * Flow<List<BusSchedule>> is the return type and is inferred by kotlin.
     * This is added for readability and the return type is ran against the getAll()
     * function return type at compile time to ensure compatibility.
     * Since this is compared at compile time there is not runtime overhead.
     */
    fun getFullSchedule(): Flow<List<BusSchedule>> = busScheduleDao.getAll()

    /** Gets the schedule by stop name in ASC order.
     * NOTES
     * Flow<List<BusSchedule>> is the return type and is inferred by kotlin.
     * This is added for readability and the return type is ran against the getByStopName()
     * functions return type at compile time to ensure compatibility.
     * Since this is compared at compile time there is not runtime overhead.
     */
    fun getScheduleFor(stopName: String): Flow<List<BusSchedule>> = busScheduleDao.getByStopName(stopName)

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BusScheduleApplication)
                BusScheduleViewModel(application.database.busScheduleDao())
            }
        }
    }
}


