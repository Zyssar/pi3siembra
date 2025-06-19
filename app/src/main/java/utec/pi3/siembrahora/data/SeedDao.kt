package utec.pi3.siembrahora.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(seed: Seed): Long

    @Update
    suspend fun update(seed: Seed)

    @Delete
    suspend fun delete(seed: Seed)

    @Query("SELECT * FROM seeds ORDER BY name ASC")
    fun getAllSeeds(): Flow<List<Seed>>

    @Query("SELECT * FROM seeds WHERE id = :seedId")
    fun getSeedById(seedId: Int): Flow<Seed?>

    // Fetch seed with its instructions
    @Transaction
    @Query("SELECT * FROM seeds WHERE id = :seedId")
    fun getSeedWithInstructions(seedId: Int): Flow<SeedWithInstructions?>
}