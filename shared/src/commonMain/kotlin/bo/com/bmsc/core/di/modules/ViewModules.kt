package bo.com.bmsc.core.di.modules

<<<<<<< HEAD
import bo.com.bmsc.core.navigation.NavigationHelper
import bo.com.bmsc.gamification.presentation.GamificationViewModel
=======
import bo.com.bmsc.auth.presentation.LoginViewModel
>>>>>>> 80f06f9 (implementation login)
import bo.com.bmsc.home.presentation.HomeViewModel
import bo.com.bmsc.streak.presentation.CreateStreakViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModules = module {
  singleOf(::NavigationHelper)
  viewModelOf(::HomeViewModel)
<<<<<<< HEAD
  viewModelOf(::GamificationViewModel)
//  viewModelOf(::MapListViewModel)
//  viewModelOf(::SingleMapViewModel)
=======
  viewModelOf(::LoginViewModel)
  viewModelOf(::CreateStreakViewModel)
>>>>>>> 80f06f9 (implementation login)
}
