package geekbarains.material.model.map.coord.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import geekbarains.material.model.map.coord.entity.Coord
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CapitalCoords (
    @Expose val name:String? = null,
    @Expose val coord: Coord? = null
): Parcelable