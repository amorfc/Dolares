package com.example.dolares.di

import android.preference.PreferenceManager
import androidx.room.Room
import com.example.dolares.data.local.model.SpaceXDatabase
import com.example.dolares.data.remote.SpacexApiService
import com.example.dolares.data.repository.*
import com.example.dolares.ui.capsules.CapsulesViewModel
import com.example.dolares.ui.cores.CoresViewModel
import com.example.dolares.ui.launch_pads.LaunchPadsViewModel
import com.example.dolares.ui.launches.LaunchDetailsViewModel
import com.example.dolares.ui.launches.LaunchesViewModel
import com.example.dolares.util.SPACEX_API_URL
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            SpaceXDatabase::class.java,
            "space_x_database.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { PreferenceManager.getDefaultSharedPreferences(get()) }

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
            get(),
            get()
        )
    }

    //ViewModelInstance of CapsulesViewModel

    viewModel { CapsulesViewModel(get()) }

}

val coresModule = module {

    single { get<SpaceXDatabase>().coreDao() }

    single {
        CoresRepository(
            get(),
            get(),
            get()
        )
    }

    viewModel { CoresViewModel(get()) }

}

val launchesModule = module {
    single { get<SpaceXDatabase>().launchesDao() }

    single {
        LaunchesRepository(
            get(),
            get(),
            get()
        )
    }



    viewModel { LaunchesViewModel(get()) }
}
val launchDetailsModule = module{

    single {
        LaunchDetailsRepository(
            get(),
            get()
        )
    }
    viewModel { LaunchDetailsViewModel(get()) }
}
val launchPadsModule = module {

    single { get<SpaceXDatabase>().launchPadsDao() }

    single {
        LaunchPadsRepository(
            get(),
            get(),
            get()
        )
    }

    viewModel { LaunchPadsViewModel(get()) }

}



