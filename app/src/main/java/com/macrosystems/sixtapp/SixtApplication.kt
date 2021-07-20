package com.macrosystems.sixtapp

import android.app.Application
import com.macrosystems.sixtapp.data.CarRepositoryImpl
import com.macrosystems.sixtapp.ui.viewmodel.factory.ListFragmentViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class SixtApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@SixtApplication))

        bind() from singleton{ CarRepositoryImpl() }
        bind() from provider { ListFragmentViewModelFactory (instance()) }

    }
}