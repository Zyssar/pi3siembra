package utec.pi3.siembrahora.telemetry

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserActionsDataAccess {
    @Insert
    suspend fun insert(action: UserActions)

    @Query("SELECT * FROM acciones ORDER BY timestamp DESC")
    suspend fun getAllActions(): List<UserActions>
}