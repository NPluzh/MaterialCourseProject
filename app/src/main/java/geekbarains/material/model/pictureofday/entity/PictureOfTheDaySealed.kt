package geekbarains.material.model.pictureofday.entity

sealed class PictureOfTheDaySealed {
    data class Success(val serverResponseData: PODServerResponseData) : PictureOfTheDaySealed()
    data class Error(val error: Throwable) : PictureOfTheDaySealed()
    data class Loading(val progress: Int?) : PictureOfTheDaySealed()
}
