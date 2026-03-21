package br.com.smvtech.testetecnico.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppCheckbox(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(false) }
    Checkbox(
        modifier = modifier.size(20.dp),
        checked = checked,
        onCheckedChange = { checked = !checked },
        colors = CheckboxDefaults.colors(
            checkedColor = MaterialTheme.colorScheme.secondary,
            uncheckedColor = MaterialTheme.colorScheme.primary
        )
    )
}