package utec.pi3.siembrahora.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "plants",
    foreignKeys = [
        ForeignKey(
            entity = Seed::class,
            parentColumns = ["id"],
            childColumns = ["seedId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Plant(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val seedId: Int,
    val plantedAt: Long,             // System.currentTimeMillis() when started
    val nickname: String? = null,
    val currentInstructionIndex: Int = 0, // which step is active
    val isDead: Boolean = false
)