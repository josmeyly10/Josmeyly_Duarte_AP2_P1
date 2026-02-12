package com.example.josmeyly_duarte_ap2_p1.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.josmeyly_duarte_ap2_p1.data.local.entities.CervezaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CervezaDao {
    @Upsert
    suspend fun upsert(cerveza: CervezaEntity)

    @Query("DELETE FROM Cervezas WHERE cervezaId=:id")
    suspend fun delete(id:Int)

    @Query("SELECT *FROM Cervezas WHERE cervezaId=:id")
    suspend fun getById(id:Int): CervezaEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM Cervezas WHERE LOWER(nombre)=LOWER(:nombre)AND cervezaId !=:excludeId)")
    suspend fun existsByNombre(nombre:String,excludeId:Int): Boolean

    @Query("SELECT * FROM Cervezas WHERE cervezaId=:id")
    fun observeById(id:Int): Flow<CervezaEntity>

    @Query("SELECT * FROM Cervezas ORDER BY cervezaId DESC")
    fun observeAll(): Flow<List<CervezaEntity>>

}








