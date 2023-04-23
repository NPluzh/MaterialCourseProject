package geekbarains.material.model.room

data class Favorite(
    var date:String? = null,
    var title:String? = null,
    var url:String? = null,
    var type:String? = null,
    var description: String? = title
)