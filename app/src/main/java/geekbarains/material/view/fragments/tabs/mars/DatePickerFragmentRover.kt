package geekbarains.material.view.fragments.tabs.mars

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import geekbarains.material.view.constants.Constants.CURIOSITY
import geekbarains.material.view.constants.Constants.OPPORTUNITY
import geekbarains.material.view.constants.Constants.SPIRIT
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragmentRover (private val listener: OnItemClickListenerRover,
                               val date:String, private val roverType:Int)
    : DialogFragment(),  DatePickerDialog.OnDateSetListener {

    companion object{const val TAG = "33333"}

    interface OnItemClickListenerRover{fun onItemClick(date:String, typeRover:Int) }

    //открываем DatePicker с текущей датой
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "DatePickerFragmentRover onCreateDialog ")

        //парсим строку с датой
        val yy = date.take(4).toInt()
        val mm = (date.take(7)).takeLast(2).toInt()
        val dd = date.takeLast(2).toInt()

        //обязательно в конструктор передавать слушатель, иначе onDateSet не сработает
        val datePicker = DatePickerDialog(requireContext(), this, yy, /*индекс с 0*/mm-1, dd )
        when(roverType){
            CURIOSITY ->{
                datePicker.datePicker.maxDate =
                    GregorianCalendar(2019, /*индекс с 0*/6, 10).timeInMillis
                datePicker.datePicker.minDate =
                    GregorianCalendar(2012, /*индекс с 0*/7, 6).timeInMillis
            }
            OPPORTUNITY->{
                datePicker.datePicker.maxDate =
                    GregorianCalendar(2017, /*индекс с 0*/1, 20).timeInMillis
                datePicker.datePicker.minDate =
                    GregorianCalendar(2004, /*индекс с 0*/0, 26).timeInMillis
            }
            SPIRIT->{
                datePicker.datePicker.maxDate =
                    GregorianCalendar(2010, /*индекс с 0*/1, 5).timeInMillis
                datePicker.datePicker.minDate =
                    GregorianCalendar(2004, /*индекс с 0*/1, 13).timeInMillis
            }
        }
        return datePicker
    }
    //выбираем дату и после нажатия ok попадаем в этот метод
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date:String = dateFormat.format(GregorianCalendar(year, month, dayOfMonth).time)

        listener.onItemClick(date, roverType)
        Log.d(TAG, "DatePickerFragmentRover onDateSet  $date")
    }
}