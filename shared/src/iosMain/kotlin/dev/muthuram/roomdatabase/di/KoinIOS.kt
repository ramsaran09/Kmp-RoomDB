package dev.muthuram.roomdatabase.di

import dev.muthuram.roomdatabase.presentation.HomeViewModel
import dev.muthuram.roomdatabase.presentation.SavedTextViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.Module
import org.koin.dsl.module

// Actual platform-specific module for iOS.
actual fun platformModule(): Module = module { }

// iOS-specific Koin initialization function.
fun initKoin() {
    dev.muthuram.roomdatabase.di.initKoin {
        // We can add iOS-specific Koin configuration here if needed.
    }
}

// ViewModelProvider object to easily access ViewModels from Swift
object ViewModelProvider : KoinComponent {
    val homeViewModel: HomeViewModel by inject()
    val savedTextViewModel: SavedTextViewModel by inject() // Added this as it's likely needed too
}
