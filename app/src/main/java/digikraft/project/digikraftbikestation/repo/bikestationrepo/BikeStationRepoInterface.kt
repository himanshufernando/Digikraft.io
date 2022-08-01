package digikraft.project.digikraftbikestation.repo.bikestationrepo

import digikraft.project.digikraftbikestation.data.BikeStations
import digikraft.project.digikraftbikestation.data.ResultWrapper
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface BikeStationRepoInterface {

    fun getBikeStation(@Query("mtype") mtype: String, @Query("co") co: String): Flow<BikeStations>
}