package br.com.smvtech.testetecnico.presentation.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.smvtech.testetecnico.R
import br.com.smvtech.testetecnico.core.utils.MaskUtils
import br.com.smvtech.testetecnico.domain.model.workshop.Workshop
import br.com.smvtech.testetecnico.presentation.components.AppButton
import br.com.smvtech.testetecnico.presentation.components.AppTextField
import br.com.smvtech.testetecnico.presentation.components.DefaultAppButton
import br.com.smvtech.testetecnico.presentation.navigation.Screens
import br.com.smvtech.testetecnico.presentation.ui.theme.AppTheme
import br.com.smvtech.testetecnico.presentation.viewmodel.HomeScreenViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewmodel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val tabs = listOf("OFICINAS", "INDICAÇÃO")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val isGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            if (isGranted) {
                viewModel.getUserLocation()
            }
        }
    )

    LaunchedEffect(selectedTabIndex) {
        when (selectedTabIndex) {
            0 -> {
                val hasFineLocation = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

                val hasCoarseLocation = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

                if (hasFineLocation || hasCoarseLocation) {
                    viewModel.getUserLocation()
                } else {
                    locationPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
                if (viewModel.workshops.value.isEmpty()) viewModel.getAllWorkshops()
            }
        }
    }


    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hinova Oficinas",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SecondaryTabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(text = title) }
                    )
                }
            }
            when (selectedTabIndex) {
                0 -> WorkshopTabContet(
                    modifier = modifier,
                    viewModel = viewModel,
                    navController = navController
                )

                1 -> ReferralTabContent(modifier = modifier, viewModel = viewModel)
            }
        }
    }
}


@Composable
private fun WorkshopTabContet(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewmodel,
    navController: NavController
) {
    val workshops by viewModel.workshops
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AppTextField(
            modifier = modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
            placeholder = "Buscar oficinas",
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_search_24),
                    contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.ic_settings_24),
                        contentDescription = "Filter Icon"
                    )
                }
            },
        )

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = errorMessage ?: "Erro desconhecido",
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else if (workshops.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Nenhuma oficina encontrada")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(workshops) { workshop ->
                    WorkshopCardItem(workshop, navController)
                }
            }
        }
        LocationFooter(latitude = viewModel.latitude.value, longitude = viewModel.longitude.value)
    }
}


@Composable
private fun ReferralTabContent(modifier: Modifier = Modifier, viewModel: HomeScreenViewmodel) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Convide amigos",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Dados de associado",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Nome Completo",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.associateName.value,
                onValueChange = { value ->
                    viewModel.associateName.value = value
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                placeholder = "Ex: Joaquim da Silva"
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "CPF",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.associateDocument.value,
                onValueChange = { value ->
                    val digitsOnly = value.filter { it.isDigit() }
                    if (digitsOnly.length <= 11) {
                        viewModel.associateDocument.value = digitsOnly
                    }
                },
                visualTransformation = MaskUtils.CpfVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                placeholder = "000.000.000-00"
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Telefone de Contato",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.associatePhone.value,
                onValueChange = { value ->
                    val digitsOnly = value.filter { it.isDigit() }
                    if (digitsOnly.length <= 11) {
                        viewModel.associatePhone.value = value
                    }
                },
                visualTransformation = MaskUtils.PhoneVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                placeholder = "(00) 00000-0000"
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "E-mail",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.associateEmail.value,
                onValueChange = { value ->
                    viewModel.associateEmail.value = value
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                placeholder = "seuemail@exemplo.com.br"
            )
            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "Placa do Veículo",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.associateVehiclePlate.value,
                onValueChange = { value ->
                    viewModel.associateVehiclePlate.value = value
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                placeholder = "ABC1D23"
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Dados do Amigo",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Quem você quer indicar?",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.friendName.value,
                onValueChange = { value ->
                    viewModel.friendName.value = value
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                placeholder = "Nome completo do indicado"
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Telefone de Contato",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.friendPhone.value,
                onValueChange = { value ->
                    val digitsOnly = value.filter { it.isDigit() }
                    if (digitsOnly.length <= 11) {
                        viewModel.friendPhone.value = value
                    }
                },
                visualTransformation = MaskUtils.PhoneVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                placeholder = "(00) 00000-0000"
            )
            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "E-mail do Amigo",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.friendEmail.value,
                onValueChange = { value ->
                    viewModel.friendEmail.value = value
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                placeholder = "email@exemplo.com"
            )

            Spacer(modifier = Modifier.height(32.dp))

            DefaultAppButton(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                onClick = { viewModel.sendReferral() },
                text = "Enviar indicação"
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun LocationFooter(
    latitude: String,
    longitude: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_location_24),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                "Sua localização",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Latitude: $latitude Longitude: $longitude",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

    }
}

@Composable
private fun WorkshopCardItem(workshop: Workshop, navController: NavController) {

    val workshopImage = remember(workshop.photo) {
        try {
            val base64String = workshop.photo
            val imageBytes = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            decodedImage?.asImageBitmap()
        } catch (e: Exception) {
            null
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,

            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(MaterialTheme.colorScheme.primary), //Imagem!!!!
                contentAlignment = Alignment.Center
            ) {
                if (workshopImage != null) {
                    Image(
                        bitmap = workshopImage,
                        contentDescription = "Foto da oficina ${workshop.name}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(text = "🏠", style = MaterialTheme.typography.displayMedium)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = workshop.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.1f
                                ), shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_star),
                            contentDescription = "",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = workshop.userRating.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = workshop.shortDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = workshop.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AppButton(
                            onClick = {
                                navController.navigate(Screens.WORKSHOP_DETAIL_SCREEN.name)

                            },
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                            modifier = Modifier.height(32.dp),
                            text = "Detalhes",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
private fun HomeScreenPreview() {
    val navController = rememberNavController()

    AppTheme {
        HomeScreen(navController = navController)
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,orientation=landscape")
@Composable
private fun HomeScreenTabletPreview() {
    val navController = rememberNavController()

    AppTheme {
        HomeScreen(navController = navController)
    }
}