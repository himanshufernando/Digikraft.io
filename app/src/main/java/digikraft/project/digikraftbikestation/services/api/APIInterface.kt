package digikraft.project.digikraftbikestation.services.api

import digikraft.project.digikraftbikestation.data.BikeStations
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("map_service.htm")
    suspend fun getBikeStation(@Query("mtype") mtype: String,@Query("co") co: String): BikeStations

}