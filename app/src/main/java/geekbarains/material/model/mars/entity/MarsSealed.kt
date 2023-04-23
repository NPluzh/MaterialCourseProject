package geekbarains.material.model.mars.entity

sealed class MarsSealed {
    data class Success(val marsResponseData: MarsResponseData) : MarsSealed()
    data class Error(val error: Throwable) : MarsSealed()
    data class Loading(val progress: Int?) : MarsSealed()
}