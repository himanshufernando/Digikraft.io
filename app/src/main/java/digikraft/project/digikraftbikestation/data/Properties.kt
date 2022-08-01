package digikraft.project.digikraftbikestation.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Properties(
    val bike_racks: String,
    val bikes: String,
    val free_racks: String,
    val label: String,
    val updated: String
): Parcelable