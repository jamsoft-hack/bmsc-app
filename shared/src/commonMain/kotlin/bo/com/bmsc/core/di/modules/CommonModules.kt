package bo.com.bmsc.core.di.modules

import bo.com.bmsc.auth.data.datasource.AuthRemoteDataSource
import bo.com.bmsc.auth.data.datasource.AuthRemoteDataSourceImpl
import bo.com.bmsc.auth.data.repository.AuthRepositoryImpl
import bo.com.bmsc.auth.domain.repository.AuthRepository
import bo.com.bmsc.core.data.HttpClientFactory
import bo.com.bmsc.streak.data.datasource.CreateStreakRemoteDataSource
import bo.com.bmsc.streak.data.datasource.CreateStreakRemoteDataSourceImpl
import bo.com.bmsc.streak.data.repository.CreateStreakRepositoryImpl
import bo.com.bmsc.streak.domain.repository.CreateStreakRepository
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModules = module {
  single { HttpClientFactory.create(get<HttpClientEngine>()) }

  singleOf(::AuthRemoteDataSourceImpl).bind<AuthRemoteDataSource>()
  singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

  singleOf(::CreateStreakRemoteDataSourceImpl).bind<CreateStreakRemoteDataSource>()
  singleOf(::CreateStreakRepositoryImpl).bind<CreateStreakRepository>()
}
