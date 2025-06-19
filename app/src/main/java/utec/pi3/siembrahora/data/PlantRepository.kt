package utec.pi3.siembrahora.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class PlantRepository(private val db: AppDatabase) {
    private val seedDao = db.seedDao()
    private val ciDao = db.careInstructionDao()
    private val plantDao = db.plantDao()
    private val logDao = db.plantActionLogDao()
    private val photoDao = db.plantPhotoDao()

    // Example: add a new seed template
    suspend fun addSeed(seed: Seed) = seedDao.insert(seed)

    // Example: insert instructions for a seed
    suspend fun addInstructions(seedId: Int, instructions: List<CareInstruction>) {
        // ensure each instruction.seedId = seedId, stepOrder set properly
        ciDao.insertAll(instructions.map { it.copy(seedId = seedId) })
    }

    // Observe all seeds with their instructions
    fun observeSeedWithInstructions(seedId: Int): Flow<SeedWithInstructions?> =
        seedDao.getSeedWithInstructions(seedId)

    // Planting a new plant
    suspend fun plantSeed(seedId: Int, nickname: String?) : Long {
        val now = System.currentTimeMillis()
        val newPlant = Plant(seedId = seedId, plantedAt = now, nickname = nickname)
        return plantDao.insert(newPlant)
    }

    // Observe a plant with details
    fun observePlantDetails(plantId: Int): Flow<PlantWithDetails?> =
        plantDao.getPlantWithDetails(plantId)

    // Record action when user completes an instruction
    suspend fun recordAction(plantId: Int, instructionId: Int) {
        val now = System.currentTimeMillis()
        logDao.insert(PlantActionLog(plantId = plantId, instructionId = instructionId, actionTimestamp = now))
        // Optionally update plant.currentInstructionIndex++
        // You might fetch the Plant first, then update its index.
        val plant = plantDao.getPlantById(plantId).firstOrNull()
        plant?.let {
            val nextIndex = it.currentInstructionIndex + 1
            plantDao.update(it.copy(currentInstructionIndex = nextIndex))
        }
    }

    // Save a photo
    suspend fun savePhoto(plantId: Int, photoUri: String) {
        val now = System.currentTimeMillis()
        photoDao.insert(PlantPhoto(plantId = plantId, photoUri = photoUri, timestamp = now))
    }

    // Other logic, e.g., checking deadlines: in ViewModel or repository,
    // combine flow of PlantWithDetails, then compute from careInstructions and actionLogs
    // whether the next step is due, overdue, or in critical state.
}
