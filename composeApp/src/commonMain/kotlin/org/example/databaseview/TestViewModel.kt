package org.example.databaseview

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.math.BigDecimal

class TestViewModel: ViewModel() {

    init {
        connectToDatabase()
    }

    val positions = MutableStateFlow(emptyList<Pair<String, BigDecimal>>())


    fun getData(){
        viewModelScope.launch(Dispatchers.IO) {
            positions.update {
                fetchPositions()
            }
        }
    }
}