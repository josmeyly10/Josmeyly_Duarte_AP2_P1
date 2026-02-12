package com.example.josmeyly_duarte_ap2_p1.di
import com.example.josmeyly_duarte_ap2_p1.data.repository.CervezaRepositoryImpl
import com.example.josmeyly_duarte_ap2_p1.domain.repository.CervezaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class CervezasRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCervezasRepository(
        cervezasRepositoryImpl: CervezaRepositoryImpl
    ): CervezaRepository
}