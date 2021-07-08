package app.ss.widgets.di

import app.ss.lessons.data.repository.lessons.LessonsRepository
import app.ss.widgets.WidgetDataProvider
import app.ss.widgets.WidgetDataProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object WidgetModule {

    @Provides
    @Singleton
    internal fun provideWidgetDataProvider(
        repository: LessonsRepository
    ): WidgetDataProvider = WidgetDataProviderImpl(repository)
}