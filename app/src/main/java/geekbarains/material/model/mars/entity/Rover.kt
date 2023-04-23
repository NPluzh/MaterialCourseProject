package geekbarains.material.model.mars.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
class Rover (
    @Expose val name:String? = null,
    @Expose val status:String? = null
    ): Parcelable