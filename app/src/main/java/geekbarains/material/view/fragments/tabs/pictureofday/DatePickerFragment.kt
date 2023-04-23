package geekbarains.material.view.fragments.tabs.pictureofday

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment(private val listener:OnItemClickListener, val date:String)
    : DialogFragment(),  DatePickerDialog.OnDateSetListener {

    companion object{const val TAG = "33333"}

    interface OnItemClickListener{fun onItemClick(date:String) }
    interface OnItemClickListenerRover{fun onItemClick(date:String, typeRover:Int) }

    //открываем DatePicker с текущей датой
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "DatePickerFragment onCreateDialog ")

        //парсим строку с датой
        val yy = date.take(4).toInt()
        val mm = (date.take(7)).takeLast(2).toInt()
        val dd = date.takeLast(2).toInt()

        //обязательно в конструктор передавать слушатель, иначе onDateSet не сработает
        val datePicker = DatePickerDialog(requireContext(), this, yy, /*индекс с 0*/mm-1, dd )
        datePicker.datePicker.maxDate = System.currentTimeMillis()
        datePicker.datePicker.minDate = GregorianCalendar(1995, /*индекс с 0*/5, 16).timeInMillis

        return datePicker
    }
    //выбираем дату и после нажатия ok попадаем в этот метод
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date:String = dateFormat.format(GregorianCalendar(year, month, dayOfMonth).time)
            listener.onItemClick(date)
            Log.d(TAG, "DatePickerFragment onDateSet  $date")
    }
}