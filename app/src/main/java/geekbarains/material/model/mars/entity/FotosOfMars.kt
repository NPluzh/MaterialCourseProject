package geekbarains.material.model.mars.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize


@Parcelize
class FotosOfMars (
    @Expose val sol:Int? = null,
    @Expose val img_src:String? = null,
    @Expose val earth_date:String? = null,
    @Expose val rover:Rover? = null
): Parcelable