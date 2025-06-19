package utec.pi3.siembrahora.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Seed::class,
        CareInstruction::class,
        Plant::class,
        PlantActionLog::class,
        PlantPhoto::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun seedDao(): SeedDao
    abstract fun careInstructionDao(): CareInstructionDao
    abstract fun plantDao(): PlantDao
    abstract fun plantActionLogDao(): PlantActionLogDao
    abstract fun plantPhotoDao(): PlantPhotoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "plant_care_db"
                )
                    // For early dev, you might use fallbackToDestructiveMigration():
                    // .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}