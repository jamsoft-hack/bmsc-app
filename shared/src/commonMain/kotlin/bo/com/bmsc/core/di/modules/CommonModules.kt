package bo.com.bmsc.core.di.modules

import bo.com.bmsc.core.data.HttpClientFactory
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModules = module {
  single { HttpClientFactory.create(get<HttpClientEngine>()) }

//  singleOf(::InvoiceRepositoryImpl).bind<InvoiceRepository>()
}
