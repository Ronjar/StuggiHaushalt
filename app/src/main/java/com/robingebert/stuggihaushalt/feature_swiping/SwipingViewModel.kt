package com.robingebert.stuggihaushalt.feature_swiping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robingebert.stuggihaushalt.feature_swiping.repository.Proposal
import com.robingebert.stuggihaushalt.feature_swiping.repository.SwipingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SwipingViewModel(private val repository: SwipingRepository): ViewModel() {

    private val _proposals = MutableStateFlow<List<Proposal>>(emptyList())
    val proposals: StateFlow<List<Proposal>> = _proposals

    init {
        getNewProposals()
    }

    private fun getNewProposals(){
        viewModelScope.launch {
            repository.getNewViews().onSuccess {
                _proposals.emit(it)
            }.onFailure {

            }
        }
    }
}