package utec.pi3.siembrahora.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantPhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: PlantPhoto): Long

    @Update
    suspend fun update(photo: PlantPhoto)

    @Delete
    suspend fun delete(photo: PlantPhoto)

    @Query("SELECT * FROM plant_photos WHERE plantId = :plantId ORDER BY timestamp ASC")
    fun getPhotosForPlant(plantId: Int): Flow<List<PlantPhoto>>

    @Query("SELECT * FROM plant_photos WHERE id = :photoId")
    fun getPhotoById(photoId: Int): Flow<PlantPhoto?>
}