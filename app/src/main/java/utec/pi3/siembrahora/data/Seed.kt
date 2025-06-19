package utec.pi3.siembrahora.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seeds")
data class Seed(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val imageUri: String,
    val info: String,
    val estimatedDurationSeconds: Int
)