package dev.muthuram.roomdatabase.di

import dev.muthuram.roomdatabase.data.db.AppDatabase
import dev.muthuram.roomdatabase.data.repository.UserRepositoryImpl
import dev.muthuram.roomdatabase.domain.repository.UserRepository
import dev.muthuram.roomdatabase.domain.usecase.GetUserUseCase
import dev.muthuram.roomdatabase.domain.usecase.UpdateUserUseCase
import dev.muthuram.roomdatabase.presentation.HomeViewModel
import dev.muthuram.roomdatabase.presentation.SavedTextViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun platformModule(): Module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            repositoryModule,
            useCaseModule,
            viewModelModule,
            platformModule()
        )
    }

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::SavedTextViewModel)
}

val useCaseModule = module {
    singleOf(::GetUserUseCase)
    singleOf(::UpdateUserUseCase)
}

val repositoryModule = module {
    singleOf(::UserRepositoryImpl).bind(UserRepository::class)
    single { get<AppDatabase>().userDao() }
}