package com.ivzb.craftlog.feature.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivzb.craftlog.domain.model.Expense
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.domain.model.Note
import com.ivzb.craftlog.feature.expenses.usecase.DeleteExpenseUseCase
import com.ivzb.craftlog.feature.expenses.usecase.GetExpensesUseCase
import com.ivzb.craftlog.feature.investments.usecase.DeleteInvestmentUseCase
import com.ivzb.craftlog.feature.investments.usecase.GetInvestmentsUseCase
import com.ivzb.craftlog.feature.notes.usecase.DeleteNoteUseCase
import com.ivzb.craftlog.feature.notes.usecase.GetNotesUseCase
import com.ivzb.craftlog.util.zip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val getInvestmentsUseCase: GetInvestmentsUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val deleteInvestmentUseCase: DeleteInvestmentUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    fun getUserName() {
        state = state.copy(userName = "Ivan")
        // TODO: Get user name from DB
    }

    fun getGreeting() {
        state = state.copy(greeting = "Greeting")
        // TODO: Get greeting by checking system time
    }

    fun load() {
        viewModelScope.launch {
            val expenses = getExpensesUseCase.getExpenses()
            val investments = getInvestmentsUseCase.getInvestments()
            val notes = getNotesUseCase.getNotes()

            zip(expenses, investments, notes) { expensesList, investmentsList, notesList ->
                state.copy(
                    expenses = expensesList.sortedByDescending { it.date }.take(LIMIT),
                    investments = investmentsList.sortedByDescending { it.date }.take(LIMIT),
                    notes = notesList.sortedByDescending { it.date }.take(LIMIT),
                    loading = false,
                )
            }.onEach {
                state = it
            }.launchIn(viewModelScope)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            deleteExpenseUseCase.deleteExpense(expense.copy())
        }
    }

    fun deleteInvestment(investment: Investment) {
        viewModelScope.launch {
            deleteInvestmentUseCase.deleteInvestment(investment.copy())
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase.deleteNote(note.copy())
        }
    }

    companion object {

        private const val LIMIT = 3
    }

}
