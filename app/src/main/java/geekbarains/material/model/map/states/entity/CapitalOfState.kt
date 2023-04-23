package geekbarains.material.model.map.states.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CapitalOfState (
    @Expose val name :String? = null,
    @Expose val capital :String? = null
): Parcelable