package com.dpeco.bemobilegnb.dagger

import com.dpeco.bemobilegnb.features.dashboard.app.viewmodel.DashboardViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by dpeco
 * Component to use dagger, imports every module and needed injects
 */
@Singleton
@Component(modules = [
    RetrofitModule::class,
    MapperModule::class,
    ServiceModule::class,
    UseCaseModule::class])
interface GnbComponent {
    fun inject(dashboardViewModel: DashboardViewModel)
}