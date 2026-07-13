package com.example.pftandroidmockproject.domain.use_case.activity

import com.example.pftandroidmockproject.domain.repository.ActivityRepository
import javax.inject.Inject

class DeleteActivityEntryUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {

    suspend operator fun invoke(entryId: Int) {
        require(entryId > 0) {
            "Invalid activity entry"
        }

        activityRepository.deleteActivityEntry(entryId)
    }
}