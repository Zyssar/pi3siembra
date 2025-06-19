package utec.pi3.siembrahora.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "plant_actions",
    foreignKeys = [
        ForeignKey(
            entity = Plant::class,
            parentColumns = ["id"],
            childColumns = ["plantId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PlantActionLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val plantId: Int,
    val instructionId: Int,     // references CareInstruction.id
    val actionTimestamp: Long   // System.currentTimeMillis() when user did it
)
