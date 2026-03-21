package br.com.smvtech.testetecnico.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.smvtech.testetecnico.R
import br.com.smvtech.testetecnico.core.utils.MaskUtils
import br.com.smvtech.testetecnico.presentation.components.AppPasswordTextField
import br.com.smvtech.testetecnico.presentation.components.AppTextField
import br.com.smvtech.testetecnico.presentation.components.DefaultAppButton
import br.com.smvtech.testetecnico.presentation.ui.theme.AppTheme
import br.com.smvtech.testetecnico.presentation.ui.theme.Typography
import br.com.smvtech.testetecnico.presentation.viewmodel.LoginViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewmodel = hiltViewModel()) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures {
                focusManager.clearFocus()
            }
        },
        topBar = {
            TopAppBar(
                title = {},
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(paddingValues)
                    .padding(16.dp),
            ) {
                Column(
                    modifier = Modifier
                ) {
                    Text(
                        text = stringResource(R.string.sign_in_screen_title),
                        color = MaterialTheme.colorScheme.primary,
                        style = Typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.sign_in_subtitle),
                        color = MaterialTheme.colorScheme.tertiary,
                        style = Typography.titleSmall,
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    AppTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = viewModel.document.value,
                        placeholder = stringResource(R.string.document_placeholder),
                        onValueChange = { newValue ->
                            val digitsOnly = newValue.filter { it.isDigit() }
                            if (digitsOnly.length <= 11) {
                                viewModel.document.value = digitsOnly
                            }
                        },
                        visualTransformation = MaskUtils.CpfVisualTransformation(),
                        isError = viewModel.documentError.value.isNotBlank(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_document_24),
                                contentDescription = stringResource(R.string.document_icon_description)
                            )
                        }
                    )
                    if (viewModel.documentError.value.isNotBlank()) {
                         Text(
                             text = viewModel.documentError.value,
                             color = MaterialTheme.colorScheme.error,
                             style = MaterialTheme.typography.bodySmall,
                             modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                         )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    AppPasswordTextField(
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = stringResource(R.string.password_placeholder),
                        value = viewModel.password.value,
                        onValueChange = { newValue ->
                            viewModel.password.value = newValue
                        }
                    )
                    if (viewModel.passwordError.value.isNotBlank()) {
                        Text(
                            text = viewModel.passwordError.value,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))


                    Spacer(modifier = Modifier.height(36.dp))
                    DefaultAppButton(
                        onClick = {
                            viewModel.login(navController)
                        },
                        text = stringResource(R.string.login)
                    )
                }
            }
        }
    )
}


@Preview
@Composable
private fun LoginScreenPreview() {
    val navController = rememberNavController()

    AppTheme {
        LoginScreen(navController)
    }
    
}