package geekbarains.material.model.pref

import android.util.Log
import androidx.preference.PreferenceManager
import geekbarains.material.App
import geekbarains.material.R
import geekbarains.material.view.constants.Constants
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PrefRepo(val app: App) :IPrefRepo{

    companion object {
        const val TAG = "33333"
    }

    override fun saveDatePickerDate(date: String) {
        PreferenceManager.getDefaultSharedPreferences(app)
            .edit()
            .putString(Constants.DATE_DATA_PICKER, date)
            .apply()
        Log.d(TAG,"PreferenceHelper saveDatePickerDate date = $date"
        )
    }

    override fun getDatePickerDate():String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val now  = dateFormat.format(GregorianCalendar().time)
        val date = PreferenceManager.getDefaultSharedPreferences(app)
            .getString(Constants.DATE_DATA_PICKER, now)
        Log.d(TAG, "PreferenceHelper getDatePickerDate date = $date")
        return date!!
    }

    override fun getDatePickerDateCur(): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val now  = dateFormat.format(GregorianCalendar().time)
        val date = PreferenceManager.getDefaultSharedPreferences(app)
            .getString(Constants.DATE_DATA_PICKER_CUR, now)
        Log.d(TAG, "PreferenceHelper getDatePickerDateCuriosity date = $date")
        return date!!
    }

    override fun getDatePickerDateOpp(): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val now  = dateFormat.format( GregorianCalendar(2018, /*индекс с 0*/5, 12).timeInMillis)
        val date = PreferenceManager.getDefaultSharedPreferences(app)
            .getString(Constants.DATE_DATA_PICKER_OPP, now)
        Log.d(TAG, "PreferenceHelper getDatePickerDateOpportunity date = $date")
        return date!!
    }

    override fun getDatePickerDateSpir(): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val now  = dateFormat.format(GregorianCalendar(2010, /*индекс с 0*/1, 5).timeInMillis)
        val date = PreferenceManager.getDefaultSharedPreferences(app)
            .getString(Constants.DATE_DATA_PICKER_SPIR, now)
        Log.d(TAG, "PreferenceHelper getDatePickerDateSpirit date = $date")
        return date!!
    }

    override fun saveDatePickerDateCur(date: String) {
        PreferenceManager.getDefaultSharedPreferences(app)
            .edit()
            .putString(Constants.DATE_DATA_PICKER_CUR, date)
            .apply()
        Log.d(TAG,"PreferenceHelper saveDatePickerDateCur date = $date"
        )
    }

    override fun saveDatePickerDateOpp(date: String) {
        PreferenceManager.getDefaultSharedPreferences(app)
            .edit()
            .putString(Constants.DATE_DATA_PICKER_OPP, date)
            .apply()
        Log.d(TAG,"PreferenceHelper saveDatePickerDateOpp date = $date"
        )
    }

    override fun saveDatePickerDateSpir(date: String) {
        PreferenceManager.getDefaultSharedPreferences(app)
            .edit()
            .putString(Constants.DATE_DATA_PICKER_SPIR, date)
            .apply()
        Log.d(TAG,"PreferenceHelper saveDatePickerDateSpir date = $date"
        )
    }
}