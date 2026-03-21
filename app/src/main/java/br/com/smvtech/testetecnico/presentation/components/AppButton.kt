package br.com.smvtech.testetecnico.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.smvtech.testetecnico.presentation.ui.theme.AppTheme
import br.com.smvtech.testetecnico.presentation.ui.theme.Typography

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    text: String? = null,
    icon: (@Composable (() -> Unit))? = null,
    leadingIcon: (@Composable (() -> Unit))? = null,
    trailingIcon: (@Composable (() -> Unit))? = null,
    textColor: Color = Color.Unspecified,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    style: TextStyle = LocalTextStyle.current,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    iconSpacing: Dp = 8.dp
) {
    val alpha by animateFloatAsState(if (isLoading) 0.5f else 1f, label = "")
    val buttonColors = if (enabled) {
        colors
    } else {
        ButtonDefaults.buttonColors(
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
        )
    }
    Button(
        modifier = modifier.alpha(alpha),
        onClick = {
            if (!isLoading) {
                onClick()
            }
        },
        enabled = enabled && !isLoading,
        shape = shape,
        colors = buttonColors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = modifier.size(24.dp),
                color = textColor,
                strokeWidth = 2.dp
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(modifier = Modifier.width(iconSpacing))
                }
                when {
                    text != null -> {
                        Text(
                            text = text,
                            textAlign = TextAlign.Center,
                            color = textColor,
                            style = style,
                        )
                    }

                    icon != null -> {
                        icon()
                    }
                }
            }
        }
    }
}

@Composable
fun DefaultAppButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    icon: (@Composable (() -> Unit))? = null,
    leadingIcon: (@Composable (() -> Unit))? = null,
    shape: Shape = RoundedCornerShape(4.dp),
    style: TextStyle = Typography.titleMedium
) {
    AppButton(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        onClick = {
            onClick()
        },
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
        ),
        leadingIcon = leadingIcon,
        icon = icon,
        text = text,
        style = style
    )
}

@Preview(showBackground = true)
@Composable
private fun AppButtonPreview() {
    AppTheme {
        val isLoading by remember { mutableStateOf(false) }
        AppButton(
            onClick = {},
            isLoading = isLoading,
            text = "Basic button"
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun DefaultAppButtonPreview() {
    AppTheme {
        Row(
            modifier = Modifier
                .width(327.dp)
                .padding(24.dp)
        ) {
            DefaultAppButton(
                onClick = {},
                text = "Create Account",
            )
        }

    }
}
