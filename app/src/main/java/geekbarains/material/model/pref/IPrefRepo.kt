package geekbarains.material.model.pref

interface IPrefRepo {
    fun saveDatePickerDate(date:String)
    fun getDatePickerDate(): String

    fun getDatePickerDateCur(): String
    fun getDatePickerDateOpp(): String
    fun getDatePickerDateSpir(): String

    fun saveDatePickerDateCur(date:String)
    fun saveDatePickerDateOpp(date:String)
    fun saveDatePickerDateSpir(date:String)
}