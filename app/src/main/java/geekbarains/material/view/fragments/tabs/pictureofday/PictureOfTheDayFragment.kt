package geekbarains.material.view.fragments.tabs.pictureofday

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import coil.api.clear
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import geekbarains.material.R
import geekbarains.material.model.pictureofday.entity.FavoriteSealed
import geekbarains.material.model.pictureofday.entity.PictureOfTheDaySealed
import geekbarains.material.model.room.Favorite
import geekbarains.material.util.toast
import geekbarains.material.view.constants.Constants
import geekbarains.material.viewmodel.PictureOfTheDayViewModel
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayFragment : Fragment() , DatePickerFragment.OnItemClickListener {

    companion object {
        const val TAG = "33333"
        private var isExpanded = false
    }
    var isErrorTrue = false

    var favorite:Favorite? = null //экземпляр класса Favorite со всеми полями = null
    private val dateFormat: DateFormat =SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    lateinit var navController: NavController

    private  val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    //метод обратного вызова из класса DatePickerFragment - календарь
    override fun onItemClick(date: String) {
        viewModel.saveDatePickerDate(date)
        chipGroupMain.clearCheck() //убираем выделение
        viewModel. sendServerRequest(date)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "PictureOfTheDayFragment onViewCreated  ")

        navController = Navigation.findNavController(view)

        //разрешаем показ меню во фрагменте
           setHasOptionsMenu(true)
        //находим корневой лейаут и подключаем BottomSheet
        val bottomSheet: ConstraintLayout = initBottomSheet(view)
        //инициализация группы чипсов фрагмента
        initChipGroup()
        initDatePicker()
        initFavoritListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

            //если грузим видео с фазами луны в 2021 то val todayAsString = "2021-01-11"
            //но мы грузим картинку дня и поэтому
            val todayAsString =
                dateFormat.format(Calendar.getInstance().apply { add(Calendar.DATE, 0) }.time )
            Log.d(TAG, "PictureOfTheDayFragment onActivityCreated todayAsString = $todayAsString")

            if (savedInstanceState == null){
                Log.d(TAG, "savedInstanceState == null")
                viewModel. sendServerRequest(todayAsString)
                chip1.isChecked = true
            }

            viewModel.getData()
                .observe(viewLifecycleOwner, Observer<PictureOfTheDaySealed> { renderData(it) })

    }

    private fun initFavoritListener() {

        btn_addToFavorite.setOnClickListener {
            favorite?. let{
                viewModel.addToFavorite(it)
            }
            toast(getString(R.string.toast_add))
        }
        btn_removeFavorite.setOnClickListener {
            favorite?. let{
                viewModel.removeFavorite(it)
            }
            toast(getString(R.string.toast_remove))
        }
    }

    private fun initDatePicker() {
        chip4.setOnClickListener {
            //показываем календарь в диалоге для выбора даты
            DatePickerFragment(this, viewModel.getDatePickerDate())
                .show(childFragmentManager, "DatePicker")
        }
    }

        //находим корневой лейаут и подключаем BottomSheet
    private fun initBottomSheet(view: View): ConstraintLayout {
        val bottomSheet: ConstraintLayout = view.findViewById(R.id.bottom_sheet_container)
        bottomSheet.visibility = View.VISIBLE
        setBottomSheetBehavior(bottomSheet)
        return bottomSheet
    }

        private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun initChipGroup() {
        chipGroupMain.setOnCheckedChangeListener { group, id ->
            Log.d(TAG, "PictureOfTheDayFragment onViewCreated setOnCheckedChangeListener")
            //нужно проверять на null иначе крэш при повторном нажатии на ту же иконку  - id = -1
            //val id = group.findViewById<Chip>(position)?.id
            when (id) {
                R.id.chip1 -> {
                    val todayAsString =
                        dateFormat.format(
                            Calendar.getInstance().apply { add(Calendar.DATE, 0) }.time )
                    Log.d(TAG,"PictureOfTheDayFragment onActivityCreated todayAsString = $todayAsString")
                    image_view.clear()
                    viewModel.sendServerRequest(todayAsString)
                }
                R.id.chip2 -> {
                    val yesterdayAsString =
                        dateFormat.format(
                            Calendar.getInstance().apply { add(Calendar.DATE, -1) }.time)
                    Log.d(TAG,"PictureOfTheDayFragment onActivityCreated yesterdayAsString = $yesterdayAsString")
                    image_view.clear()
                    viewModel.sendServerRequest(yesterdayAsString)
                }
                R.id.chip3 -> {
                    val beforeYesterdayAsString =
                        dateFormat.format(
                            Calendar.getInstance().apply { add(Calendar.DATE, -2) }.time)
                    Log.d(TAG,"PictureOfTheDayFragment onActivityCreated beforeYesterdayAsString = $beforeYesterdayAsString")
                    image_view.clear()
                    viewModel.sendServerRequest(beforeYesterdayAsString)
                }
            }
        }
    }

    private fun renderFavorite(data: FavoriteSealed){
        Log.d(TAG, "*** PictureOfTheDayFragment renderFavorite ")
        when(data){
            is FavoriteSealed.Success -> {
                Log.d(TAG, "#*#*# PictureOfTheDayFragment renderFavorite Success" +
                        " data.isFavorite = ${data.isFavorite}")
                if(data.isFavorite){
                    btn_addToFavorite.visibility = View.GONE
                    btn_removeFavorite.visibility= View.VISIBLE
                }else{
                    btn_addToFavorite.visibility = View.VISIBLE
                    btn_removeFavorite.visibility = View.GONE
                }
            }
            is FavoriteSealed.Error -> {
                toast(data.error.message)
                Log.d(TAG, "PictureOfTheDayFragment renderFavorite Error")
            }
        }
    }

    private fun renderData(data: PictureOfTheDaySealed) {
        Log.d(TAG, "*** PictureOfTheDayFragment renderData ")
        when (data) {
            is PictureOfTheDaySealed.Success -> {

                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                val mediaType = serverResponseData.mediaType

                //запоминаем в поле для того чтобы использовать при записи в избранное
                favorite = Favorite(serverResponseData.date, serverResponseData.title,
                     serverResponseData.url, serverResponseData.mediaType)

                favorite?. let{
                    viewModel.isFavoriteState(it).observe(viewLifecycleOwner, Observer {
                        renderFavorite(it)
                    })
                }

                //прекращаем показ прогрессбара
                progressBarNasa.visibility = View.GONE

                if(data.serverResponseData.mediaType == "image"){
                    Log.d(TAG, "*** PictureOfTheDayFragment renderData  mediaType = image " +
                            "url = $url" )
                    if (url.isNullOrEmpty()) {
                        //showError("Сообщение, что ссылка пустая")
                        toast("Link is empty")
                    } else {
                        image_view.visibility =View.VISIBLE
                        web_view.visibility =View.GONE
                        //грузим несуществующий файл
                        //web_view.loadUrl("file:///android_asset/nonexistent.html")
                        web_view.loadUrl(  "about:blank") //или так

                        //Koil image download  (аналог Picasso и Glide, написанный на Kotlin)
                        image_view.load(url) {
                            lifecycle(this@PictureOfTheDayFragment)
                            error(R.drawable.ic_load_error_vector)
                            placeholder(R.drawable.ic_no_photo_vector)
                        }
                        image_view.setOnClickListener {
                            Log.d(TAG, "PictureOfTheDayFragment onViewCreated setOnClickListener")
                            val bundle = bundleOf(
                                Constants.URL_ANIMATION to url,
                                Constants.MEDIA_TYPE_ANIMATION to mediaType  ) //так проще
                            navController.navigate(R.id.animationFragment, bundle)
                        }
                    }
                }else {
                    Log.d(TAG, "*** PictureOfTheDayFragment renderData  mediaType = video")
                    web_view.clearCache(true)
                    web_view.clearHistory()
                    web_view.settings.javaScriptEnabled = true  // небезопасно тащить скрипты
                    web_view.settings.javaScriptCanOpenWindowsAutomatically = true

                    image_view.visibility =View.GONE
                    web_view.visibility =View.VISIBLE
                    url?. let{web_view.loadUrl(url)}
                }
                //тестовый текст / в макете выставлены значения выезжания bottom sheet
                bottom_sheet_description_header?.text = serverResponseData.title
                bottom_sheet_description?.text = serverResponseData.explanation
            }

            is PictureOfTheDaySealed.Loading -> {
                //показываем прогресс-бар
                progressBarNasa.visibility = View.VISIBLE
            }
            is PictureOfTheDaySealed.Error -> {
                //если у нас уже завтра, а в NASA ещё вчера - пробуем 1 раз загрузить вчерашнюю картинку
                if (!isErrorTrue){
                    val todayAsString =
                        dateFormat.format(Calendar.getInstance().apply { add(Calendar.DATE, -1) }.time)
                    viewModel. sendServerRequest(todayAsString)
                    chip2.isChecked = true
                }
                isErrorTrue = true
                //прекращаем показ прогрессбара
                progressBarNasa.visibility = View.GONE
                toast(data.error.message)
                image_view.load(R.drawable.ic_load_error_vector)
                }
            }
        }
}
