package com.chami537.datememory.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.chami537.datememory.R
import androidx.lifecycle.viewModelScope
import com.chami537.datememory.data.AppDatabase
import com.chami537.datememory.data.EventRepository
import com.chami537.datememory.model.DateEvent
import com.chami537.datememory.utils.BackupManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EventRepository
    val allEvents: StateFlow<List<DateEvent>>

    init {
        val eventDao = AppDatabase.getDatabase(application).eventDao()
        repository = EventRepository(eventDao)
        allEvents = repository.allEvents.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )
    }

    fun addEvent(event: DateEvent) {
        viewModelScope.launch {
            repository.insert(event)
        }
    }

    fun deleteEvent(event: DateEvent) {
        viewModelScope.launch {
            repository.delete(event)
        }
    }

    fun updateEventsOrder(events: List<DateEvent>) {
        viewModelScope.launch {
            repository.updateEvents(events)
        }
    }

    // 导出备份
    fun exportBackup(uri: Uri, onResult: (Boolean, String) -> Unit) {
        val app = getApplication<Application>()
        viewModelScope.launch {
            try {
                val events = withContext(Dispatchers.IO) {
                    repository.getAllEventsOnce()
                }
                withContext(Dispatchers.IO) {
                    BackupManager.exportToZip(app, events, uri).getOrThrow()
                }
                onResult(true, app.getString(R.string.backup_success))
            } catch (e: Exception) {
                onResult(false, app.getString(R.string.backup_failed, e.localizedMessage ?: app.getString(R.string.unknown_error)))
            }
        }
    }

    // 导入备份
    fun importBackup(uri: Uri, onResult: (Boolean, String) -> Unit) {
        val app = getApplication<Application>()
        viewModelScope.launch {
            try {
                val events = withContext(Dispatchers.IO) {
                    BackupManager.importFromZip(app, uri).getOrThrow()
                }
                withContext(Dispatchers.IO) {
                    repository.deleteAllAndInsertAll(events)
                }
                onResult(true, app.getString(R.string.restore_success, events.size))
            } catch (e: Exception) {
                onResult(false, app.getString(R.string.restore_failed, e.localizedMessage ?: app.getString(R.string.unknown_error)))
            }
        }
    }
}
