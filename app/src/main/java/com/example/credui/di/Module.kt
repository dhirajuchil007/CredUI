package com.example.credui.di

import com.example.credui.repository.DummyUIRepo
import com.example.credui.repository.UIRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class Module {

    @Binds
    abstract fun bindsUIRepo(repo: DummyUIRepo): UIRepo
}