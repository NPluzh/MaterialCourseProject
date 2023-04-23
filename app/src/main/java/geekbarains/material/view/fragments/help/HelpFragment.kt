package geekbarains.material.view.fragments.help

import android.graphics.Typeface.BOLD
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import geekbarains.material.R
import geekbarains.material.view.constants.Constants
import geekbarains.material.viewmodel.HelpViewModel
import kotlinx.android.synthetic.main.fragment_help.*

class HelpFragment: Fragment(){

    companion object{
        const val TAG = "33333"

        const val PHOTO_OF_DAY: String = "ФОТО ДНЯ"
        const val EARTH: String = "ЗЕМЛЯ"
        const val MARS: String = "МАРС"
        const val SEARCH_IN_WIKI: String = "поиска в Википедии"
        const val WORLD_MAP: String = "КАРТА МИРА"
        const val BY_DATE: String = "По дате"
        const val SELECTED: String = "Избранное"
    }

    private lateinit var helpViewModel: HelpViewModel
    lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?)=
        View.inflate(context, R.layout.fragment_help, null)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController =Navigation.findNavController(view)

        helpViewModel = ViewModelProvider(this).get(HelpViewModel::class.java)

        val helpText = helpViewModel.getHelpText()
        val spanHelp = SpannableString(helpText)
        val color = requireActivity().getColor(R.color.colorPrimaryDarkPurple)

        helpText?. let{
            setStyle(it, "Краткое описание приложения.", spanHelp)
            makeLinks(it, PHOTO_OF_DAY, color, spanHelp)
            makeLinks(it, BY_DATE, color, spanHelp)
            makeLinks(it, EARTH, color, spanHelp)
            makeLinks(it, MARS, color, spanHelp)
            makeLinks(it, WORLD_MAP, color, spanHelp)
            makeLinks(it, SEARCH_IN_WIKI, color, spanHelp)
            makeLinks(it, SELECTED, color, spanHelp)

            //Чтобы TextView корректно обрабатывал клик на подстроке, нужно настроить параметр
            // movementMethod. Он указывает, кому делегировать touch event. В нашем случае
            // мы просим TextView делегировать клик в LinkMovementMethod, который ищет
            // ClickableSpan и вызывает на них onClick.
            tv_help.movementMethod = LinkMovementMethod.getInstance()
            tv_help.setText(spanHelp, TextView.BufferType.SPANNABLE)
         }
     }

    private fun setStyle(text:String, phrase:String, spanHelp:Spannable){
        val start = text.indexOf(phrase)
        val end = start + phrase.length

        spanHelp.setSpan(
            StyleSpan(BOLD),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    private fun makeLinks(text: String, phrase: String, phraseColor:Int, spanHelp:Spannable ){

        val clickableSpan = object : ClickableSpan() {

            override fun updateDrawState(ds: TextPaint) {
                ds.color = phraseColor // устанавливаем наш цвет
                ds.isUnderlineText = true // нижнее подчеркивание
            }

            override fun onClick(view: View) {
                Log.d(TAG, "HelpFragment makeLinks onClick")
                when(phrase){
                    PHOTO_OF_DAY -> navController.navigate(R.id.action_helpFragment_to_apiFragment)

                    EARTH ->{
                      val bundle =   Bundle().apply { putInt(Constants.PAGER_POSITION, 1) }
                        navController.navigate(R.id.action_helpFragment_to_apiFragment, bundle)
                    }

                    MARS -> {
                        val bundle =   Bundle().apply { putInt(Constants.PAGER_POSITION, 2) }
                        navController.navigate(R.id.action_helpFragment_to_apiFragment, bundle)
                    }

                    SEARCH_IN_WIKI
                     -> navController.navigate(R.id.action_helpFragment_to_searchFragment)

                    SELECTED-> navController.navigate(R.id.action_helpFragment_to_favoriteFragment)

                    BY_DATE-> navController.navigate(R.id.action_helpFragment_to_apiFragment)

                    WORLD_MAP-> {
                        val bundle =   Bundle().apply { putInt(Constants.PAGER_POSITION, 3) }
                        navController.navigate(R.id.action_helpFragment_to_apiFragment, bundle)
                    }

                    else -> {
                        val bundle =   Bundle().apply { putInt(Constants.PAGER_POSITION, 0) }
                        navController.navigate(R.id.action_helpFragment_to_apiFragment, bundle)
                    }
                    }
                }
            }
        
        val start = text.indexOf(phrase)
        val end = start + phrase.length
        spanHelp.setSpan(
            clickableSpan,
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE )
    }

}