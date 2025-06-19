package utec.pi3.siembrahora.data

import androidx.room.Embedded
import androidx.room.Relation

// 3.1 Seed with its instructions
data class SeedWithInstructions(
    @Embedded val seed: Seed,
    @Relation(
        parentColumn = "id",
        entityColumn = "seedId",
        entity = CareInstruction::class
    )
    val instructions: List<CareInstruction>
)

// 3.2 Plant with Seed, Instructions (for that seed), Action Logs, Photos
data class PlantWithDetails(
    @Embedded val plant: Plant,

    // The associated Seed
    @Relation(
        parentColumn = "seedId",
        entityColumn = "id",
        entity = Seed::class
    )
    val seed: Seed,

    // All instructions for that seed (we can filter in code by currentInstructionIndex)
    @Relation(
        parentColumn = "seedId",
        entityColumn = "seedId",
        entity = CareInstruction::class
    )
    val careInstructions: List<CareInstruction>,

    // All action logs for THIS plant
    @Relation(
        parentColumn = "id",
        entityColumn = "plantId",
        entity = PlantActionLog::class
    )
    val actionLogs: List<PlantActionLog>,

    // All photos for THIS plant
    @Relation(
        parentColumn = "id",
        entityColumn = "plantId",
        entity = PlantPhoto::class
    )
    val photos: List<PlantPhoto>
)
