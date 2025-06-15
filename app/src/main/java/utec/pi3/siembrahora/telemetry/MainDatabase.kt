package utec.pi3.siembrahora.telemetry

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [UserActions::class], version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun userActionDao(): UserActionsDataAccess
    companion object {
        @Volatile private var INSTANCE: MainDatabase? = null
        fun getInstance(context: Context): MainDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE?: Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java, "telemetry.db"
                ).build().also { INSTANCE = it}
            }
    }
}