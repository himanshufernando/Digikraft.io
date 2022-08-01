package digikraft.project.digikraftbikestation.repo.bikestationrepo

import digikraft.project.digikraftbikestation.data.BikeStations
import digikraft.project.digikraftbikestation.services.api.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


@ExperimentalCoroutinesApi
@FlowPreview
class BikeStationRepo(private var client: APIInterface) : BikeStationRepoInterface {

    override fun getBikeStation(mtype: String, co: String): Flow<BikeStations> {
        return flow {
            val searchResult = client.getBikeStation(
                mtype, co
            )
            emit(searchResult)
        }.flowOn(Dispatchers.IO)
    }


}