package digikraft.project.digikraftbikestation.repo.bikestationrepo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import digikraft.project.digikraftbikestation.services.api.APIInterface
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideConversationRepository(aPIInterface: APIInterface): BikeStationRepo {
        return BikeStationRepo(
            client = aPIInterface
        )
    }
}