package geekbarains.material.view.fragments.favorite

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import geekbarains.material.R
import geekbarains.material.model.room.Favorite
import kotlinx.android.synthetic.main.dialog_add_description.view.*

class DialogAddDescription(val transmitDescription:TransmitDescription, val favorite: Favorite)
    :DialogFragment() {

    companion object{
       const val TAG = "33333"
    }

    interface TransmitDescription{
        fun onTransmit(description:String, favorite: Favorite)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        //принудительно вызываем клавиатуру - повторный вызов ее скроет
        takeOnAndOffSoftInput()

        val builder =  AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_add_description, null)
        builder.setView(view)
        builder.setTitle("Сделайте описание ")
        builder.setPositiveButton("Готово" ) { _, _ ->
               //читаем задержку в строке ввода
               val  description  = view.editTextDescr.text.toString()
            transmitDescription.onTransmit(description, favorite)
                //принудительно прячем  клавиатуру - повторный вызов ее покажет
                takeOnAndOffSoftInput()
        }
        builder.setNegativeButton( "Отмена" ) { _, _ ->
            takeOnAndOffSoftInput()
        }
        //если делать запрет на закрытие окна при щелчке за пределами окна, то можно так
        val dialog: Dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    //принудительно вызываем клавиатуру - повторный вызов ее скроет
    private fun takeOnAndOffSoftInput() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}