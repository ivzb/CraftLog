package com.ivzb.craftlog.analytics

object AnalyticsEvents {

    const val EMPTY_CARD_SHOWN = "empty_card_shown"

    const val ADD_EXPENSE_CLICKED_EMPTY_CARD = "add_expense_clicked_empty_card"
    const val ADD_EXPENSE_CLICKED_DAILY_OVERVIEW = "add_expense_clicked_daily_overview"
    const val ADD_EXPENSE_CLICKED_FAB = "add_expense_clicked_fab"

    const val ADD_EXPENSE_ON_BACK_CLICKED = "add_expense_on_back_clicked"
    const val ADD_EXPENSE_VALUE_INVALIDATED = "add_expense_%s_invalidated"
    const val ADD_EXPENSE_ON_SAVE_CLICKED = "add_expense_on_save_clicked"

    const val EXPENSE_SAVED = "expense_saved"
    
    const val EXPENSE_DETAIL_ON_BACK_CLICKED = "expense_detail_on_back_clicked"
    const val EXPENSE_DETAIL_DONE_CLICKED = "expense_detail_done_clicked"

    const val HOME_TAB_CLICKED = "home_tab_clicked"
    const val EXPENSES_TAB_CLICKED = "expenses_tab_clicked"

    const val HOME_CALENDAR_PREVIOUS_WEEK_CLICKED = "home_calendar_previous_week_clicked"
    const val HOME_CALENDAR_NEXT_WEEK_CLICKED = "home_calendar_next_week_clicked"
    const val HOME_NEW_DATE_SELECTED = "home_new_date_clicked"

    const val BUDGET_DETAIL_ON_BACK_CLICKED = "budget_detail_on_back_clicked"
    const val BUDGET_DETAIL_DONE_CLICKED = "budget_detail_done_clicked"

    const val ADD_INVESTMENT_CLICKED_FAB = "add_investment_clicked_fab"

    const val ADD_INVESTMENT_VALUE_INVALIDATED = "add_investment_%s_invalidated"
    const val ADD_INVESTMENT_ON_SAVE_CLICKED = "add_investment_on_save_clicked"

}