package utec.pi3.siembrahora.telemetry
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "acciones")
data class UserActions(
    @PrimaryKey(autoGenerate = true)
            val id: Long = 0,
            val tipo: String,
            val timestamp: Long,
            val info: String? = null
)