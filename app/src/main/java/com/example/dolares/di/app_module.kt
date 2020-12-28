package com.example.dolares.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dolares.data.local.model.SpaceXDatabase
import com.example.dolares.data.remote.SpacexApiService
import com.example.dolares.data.repository.CapsulesRepository
import com.example.dolares.data.repository.CoresRepository
import com.example.dolares.ui.capsules.CapsulesViewModel
import com.example.dolares.ui.cores.CoresViewModel
import com.example.dolares.util.SPACEX_API_URL
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            SpaceXDatabase::class.java,
            "space_x_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}

val remoteDataSourceModule = module {

    // Create Retrofit instance
    single {
        Retrofit.Builder()
            .baseUrl(SPACEX_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Create retrofit Service
    single { get<Retrofit>().create(SpacexApiService::class.java) }
}

val capsulesModule = module {

    single { get<SpaceXDatabase>().capsulesDao() }

    single {
        CapsulesRepository(
            get(),
            get()
        )
    }

    //ViewModelInstance of CapsulesViewModel

    viewModel { CapsulesViewModel(get()) }

}

val coreModule = module {

    single { get<SpaceXDatabase>().coreDao() }

    single {
        CoresRepository(
            get(),
            get()
        )
    }

    viewModel { CoresViewModel(get()) }

}

