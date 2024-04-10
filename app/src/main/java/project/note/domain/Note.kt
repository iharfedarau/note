package project.note.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(val title: String,
                val content: String,
                val modificationDate: Long,
                val alarmDate: Long? = null,
                val alarmMessage: String? = null,
                val id: Long? = null): Parcelable
