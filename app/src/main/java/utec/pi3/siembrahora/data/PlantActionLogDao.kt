package utec.pi3.siembrahora.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantActionLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: PlantActionLog): Long

    @Update
    suspend fun update(log: PlantActionLog)

    @Delete
    suspend fun delete(log: PlantActionLog)

    @Query("SELECT * FROM plant_actions WHERE plantId = :plantId ORDER BY actionTimestamp ASC")
    fun getLogsForPlant(plantId: Int): Flow<List<PlantActionLog>>

    @Query("SELECT * FROM plant_actions WHERE id = :logId")
    fun getLogById(logId: Int): Flow<PlantActionLog?>
}