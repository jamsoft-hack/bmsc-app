package bo.com.bmsc.core.di.modules

import bo.com.bmsc.home.presentation.HomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModules = module {
  viewModelOf(::HomeViewModel)
//  viewModelOf(::MapListViewModel)
//  viewModelOf(::SingleMapViewModel)
}
