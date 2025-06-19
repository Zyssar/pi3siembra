package utec.pi3.siembrahora.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CareInstructionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(instruction: CareInstruction): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(instructions: List<CareInstruction>)

    @Update
    suspend fun update(instruction: CareInstruction)

    @Delete
    suspend fun delete(instruction: CareInstruction)

    @Query("SELECT * FROM care_instructions WHERE seedId = :seedId ORDER BY stepOrder ASC")
    fun getInstructionsForSeed(seedId: Int): Flow<List<CareInstruction>>

    @Query("SELECT * FROM care_instructions WHERE id = :instructionId")
    fun getInstructionById(instructionId: Int): Flow<CareInstruction?>
}