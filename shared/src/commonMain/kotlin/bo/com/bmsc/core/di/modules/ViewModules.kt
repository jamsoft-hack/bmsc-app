package bo.com.bmsc.core.di.modules

import bo.com.bmsc.core.navigation.NavigationHelper
import bo.com.bmsc.gamification.presentation.GamificationViewModel
import bo.com.bmsc.home.presentation.HomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModules = module {
  singleOf(::NavigationHelper)
  viewModelOf(::HomeViewModel)
  viewModelOf(::GamificationViewModel)
//  viewModelOf(::MapListViewModel)
//  viewModelOf(::SingleMapViewModel)
}
