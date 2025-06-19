package utec.pi3.siembrahora.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plant: Plant): Long

    @Update
    suspend fun update(plant: Plant)

    @Delete
    suspend fun delete(plant: Plant)

    @Query("SELECT * FROM plants ORDER BY plantedAt DESC")
    fun getAllPlants(): Flow<List<Plant>>

    @Query("SELECT * FROM plants WHERE id = :plantId")
    fun getPlantById(plantId: Int): Flow<Plant?>

    // Fetch with related data
    @Transaction
    @Query("SELECT * FROM plants WHERE id = :plantId")
    fun getPlantWithDetails(plantId: Int): Flow<PlantWithDetails?>

    // If you want all plants with details:
    @Transaction
    @Query("SELECT * FROM plants")
    fun getAllPlantsWithDetails(): Flow<List<PlantWithDetails>>
}