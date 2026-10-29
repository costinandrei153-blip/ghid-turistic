package com.example.obiective.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.obiective.Category
import com.example.obiective.Place

private val NoInsets = WindowInsets(0, 0, 0, 0)

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var user by rememberSaveable { mutableStateOf("") }
    var pass by rememberSaveable { mutableStateOf("") }
    Column(
        Modifier.fillMaxSize().systemBarsPadding().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Ghid turistic", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(user, { user = it }, label = { Text("Utilizator") },
            singleLine = true, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(pass, { pass = it }, label = { Text("Parolă") }, singleLine = true,
            visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        Button(onClick = onLogin, enabled = user.isNotBlank() && pass.isNotBlank(),
            modifier = Modifier.fillMaxWidth()) { Text("Intră") }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceListScreen(
    places: List<Place>,
    selected: Category?,
    onSelect: (Category?) -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Categorii") }) },
        contentWindowInsets = NoInsets
    ) { padding ->
        Column(Modifier.padding(padding)) {
            Row(Modifier.horizontalScroll(rememberScrollState()).padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected == null, { onSelect(null) }, label = { Text("Toate") })
                Category.entries.forEach { c ->
                    FilterChip(selected == c, { onSelect(c) }, label = { Text(c.label) })
                }
            }
            LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(places, key = { it.id }) { p ->
                    Card(Modifier.fillMaxWidth().clickable { onNavigateToDetail(p.id) }) {
                        Column(Modifier.padding(16.dp)) {
                            Text(p.name, style = MaterialTheme.typography.titleMedium)
                            Text("${p.category.label} · ${p.locality}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailScreen(
    place: Place,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detaliu") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Înapoi")
                    }
                }
            )
        },
        contentWindowInsets = NoInsets
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(place.name, style = MaterialTheme.typography.headlineSmall)
            Text("Categorie: ${place.category.label}")
            Text("Localitate: ${place.locality}")
            Text(place.description)
            Spacer(Modifier.height(8.dp))
            Button(onClick = onToggleFavorite) {
                Text(if (isFavorite) "Elimină din favorite" else "Adaugă la favorite")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(places: List<Place>, onRemove: (Int) -> Unit) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Favorite") }) },
        contentWindowInsets = NoInsets
    ) { padding ->
        if (places.isEmpty()) {
            Box(Modifier.padding(padding).fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
                Text("Nu ai niciun favorit încă. Deschide un obiectiv din Categorii și apasă „Adaugă la favorite”.")
            }
        } else {
            LazyColumn(Modifier.padding(padding), contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(places, key = { it.id }) { p ->
                    Card(Modifier.fillMaxWidth()) {
                        Row(Modifier.padding(start = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f).padding(vertical = 12.dp)) {
                                Text(p.name, style = MaterialTheme.typography.titleMedium)
                                Text("${p.category.label} · ${p.locality}")
                            }
                            IconButton(onClick = { onRemove(p.id) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Elimină")
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen() {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Despre") }) },
        contentWindowInsets = NoInsets
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Ghid turistic Maramureș", style = MaterialTheme.typography.headlineSmall)
            Text("Autor: NUME PRENUME")          // TODO
            Text("Versiune: 1.0")
            Text("Aplicație demonstrativă pentru navigare cu Jetpack Compose (Navigation Compose, rute type-safe).")
        }
    }
}
