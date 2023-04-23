package geekbarains.material.model.earth.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  PictureOfEarth (
        @Expose val date:String? = null,
        @Expose val image: String? = null
    ):   Parcelable