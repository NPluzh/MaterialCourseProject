package geekbarains.material.model.mars.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarsResponseData (
    @Expose val photos: List<FotosOfMars>? = null
    ): Parcelable