package com.ivzb.craftlog.analytics

object AnalyticsEvents {

    const val EMPTY_CARD_SHOWN = "empty_card_shown"

    const val ADD_EXPENSE_CLICKED_EMPTY_CARD = "add_expense_clicked_empty_card"
    const val ADD_EXPENSE_CLICKED_DAILY_OVERVIEW = "add_expense_clicked_daily_overview"
    const val ADD_EXPENSE_CLICKED_FAB = "add_expense_clicked_fab"

    const val ADD_EXPENSE_ON_BACK_CLICKED = "add_expense_on_back_clicked"
    const val ADD_EXPENSE_EXPENSE_VALUE_INVALIDATED = "add_expense_expense_%s_invalidated"
    const val ADD_EXPENSE_NAVIGATING_TO_EXPENSE_CONFIRM = "add_expense_navigating_to_expense_confirm"

    const val EXPENSE_CONFIRM_ON_BACK_CLICKED = "expense_confirm_on_back_clicked"
    const val EXPENSE_CONFIRM_ON_CONFIRM_CLICKED = "expense_confirm_on_confirm_clicked"

    const val EXPENSE_SAVED = "expense_saved"

    const val HOME_TAB_CLICKED = "home_tab_clicked"
    const val EXPENSES_TAB_CLICKED = "expenses_tab_clicked"

}