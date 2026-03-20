package br.com.smvtech.testetecnico.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.smvtech.testetecnico.R
import br.com.smvtech.testetecnico.presentation.components.AppCheckbox
import br.com.smvtech.testetecnico.presentation.components.AppPasswordTextField
import br.com.smvtech.testetecnico.presentation.components.AppTextField
import br.com.smvtech.testetecnico.presentation.components.DefaultAppButton
import br.com.smvtech.testetecnico.presentation.navigation.Screens
import br.com.smvtech.testetecnico.presentation.ui.theme.AppTheme
import br.com.smvtech.testetecnico.presentation.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
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
//                        value = viewmodel.document.value,
                        value = "",
                        placeholder = stringResource(R.string.document_placeholder),
                        onValueChange = { newValue ->
//                            viewModel.email.value = newValue
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_document_24),
                                contentDescription = stringResource(R.string.document_icon_description)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AppPasswordTextField(
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = stringResource(R.string.password_placeholder),
//                        value = viewModel.password.value,
                        value = "",
                        onValueChange = { newValue ->
//                            viewModel.password.value = newValue
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.padding(0.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            AppCheckbox(modifier = Modifier.padding(end = 8.dp))
                            Text(
                                text = stringResource(R.string.remember_credentials),
                                style = Typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(36.dp))
                    DefaultAppButton(
                        onClick = {
                            navController.navigate(Screens.HOME_SCREEN.name)

//                            viewModel.signInWithEmailAndPassword()
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