package com.ivzb.craftlog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ivzb.craftlog.ui.theme.CraftLogTheme

@Composable
fun CraftLogApp() {
    CraftLogTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AddExpenseScreen()
        }
    }
}