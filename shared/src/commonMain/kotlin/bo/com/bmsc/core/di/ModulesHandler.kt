package bo.com.bmsc.core.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import bo.com.bmsc.core.database.BarikDatabase
import bo.com.bmsc.core.database.DatabaseFactory
import bo.com.bmsc.core.di.modules.cardModule
import bo.com.bmsc.core.di.modules.commonModules
import bo.com.bmsc.core.di.modules.resourceModules
import bo.com.bmsc.core.di.modules.viewModules
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect val platformModule: Module

lateinit var koinInstance: Koin

fun initModules(
  config: KoinAppDeclaration? = null,
) {
  if (::koinInstance.isInitialized) {
    return
  }

  val databaseModule: Module = module {
    single {
      get<DatabaseFactory>().create()
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
    }
//    single { get<BarikDatabase>().resourceDao }
  }

  koinInstance = startKoin {
    config?.invoke(this)
    modules(
      databaseModule,
      platformModule,
      commonModules,
      resourceModules,
      cardModule,
      viewModules,
    )
  }.koin
}
