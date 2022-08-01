package digikraft.project.digikraftbikestation.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Feature(
    var count: Int,
    val geometry: Geometry,
    val id: String,
    val properties: Properties,
    val type: String
): Parcelable