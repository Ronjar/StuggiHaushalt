package com.robingebert.stuggihaushalt.di


import com.robingebert.stuggihaushalt.data.DataStoreManager
import com.robingebert.stuggihaushalt.data.KtorClient
import com.robingebert.stuggihaushalt.feature_login.LoginViewModel
import com.robingebert.stuggihaushalt.feature_swiping.SwipingViewModel
import com.robingebert.stuggihaushalt.feature_swiping.repository.SwipingRepository
import com.robingebert.stuggihaushalt.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


object AppModule {
    fun modules() = commonModule + viewModelModule
}

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel{ LoginViewModel(client = get()) }
    viewModel { SwipingViewModel(repository = get()) }

    single { SwipingRepository(client = get()) }
}

val commonModule = module {
    single { DataStoreManager(androidContext()) }
    single {
        KtorClient()
    }
}