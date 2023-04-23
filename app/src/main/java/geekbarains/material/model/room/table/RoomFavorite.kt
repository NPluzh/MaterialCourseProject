package geekbarains.material.model.room.table

import androidx.room.Entity
import androidx.room.PrimaryKey

/*будем использовать отдельный класс RoomFavorite для работы с базой, чтобы не
 вносить изменений в существующие сущности во избежание создания зависимости логики от Room
 RoomFavorite будет представлять класс Favorite*/

@Entity
class RoomFavorite (
    @PrimaryKey var date:String,
    var title:String,
    var url:String,
    var type:String,
    var description :String
)