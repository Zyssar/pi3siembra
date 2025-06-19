package utec.pi3.siembrahora.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "plant_photos",
    foreignKeys = [
        ForeignKey(
            entity = Plant::class,
            parentColumns = ["id"],
            childColumns = ["plantId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PlantPhoto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val plantId: Int,
    val photoUri: String,       // e.g., content:// or file:// URI
    val timestamp: Long         // System.currentTimeMillis() when photo taken
)