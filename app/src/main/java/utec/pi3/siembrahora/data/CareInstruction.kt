package utec.pi3.siembrahora.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "care_instructions",
    foreignKeys = [
        ForeignKey(
            entity = Seed::class,
            parentColumns = ["id"],
            childColumns = ["seedId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["seedId", "stepOrder"], unique = true)
    ]
)
data class CareInstruction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val seedId: Int,
    val stepOrder: Int,         // sequence index: 0,1,2,...
    val instructionText: String,
    val delaySeconds: Int,      // wait time after previous step
    val type: String,           // e.g., "plant", "water", "fertilize", "harvest"
    val criticalDelaySeconds: Int? = null // optional: after deadline, treat as critical
)
