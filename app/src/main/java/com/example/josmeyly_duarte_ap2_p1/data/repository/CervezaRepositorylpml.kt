package com.example.josmeyly_duarte_ap2_p1.data.repository
import com.example.josmeyly_duarte_ap2_p1.data.local.dao.CervezaDao
import com.example.josmeyly_duarte_ap2_p1.data.local.entities.CervezaEntity
import com.example.josmeyly_duarte_ap2_p1.data.mapper.toCerveza
import com.example.josmeyly_duarte_ap2_p1.domain.model.Cerveza
import com.example.josmeyly_duarte_ap2_p1.domain.repository.CervezaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CervezaRepositorylpml @Inject constructor(
    private val dao: CervezaDao
): CervezaRepository {

    override suspend fun upsert(cerveza: CervezaEntity) {
        dao.upsert(cerveza.toEntity())
    }

    override suspend fun delete(id: Int) {
        dao.delete(id)
    }

    override suspend fun getById(id: Int): Cerveza? {
        return dao.getById(id)?.toCerveza()
    }

    override suspend fun existsByNombre(nombre: String, excludeId: Int): Boolean {
        return dao.existsByNombre(nombre, excludeId)
    }

    override fun observeById(id: Int): Flow<Cerveza?> {
        return dao.observeById(id).map { it?.toCerveza() }
    }

    fun observeAll(): Flow<List<Cerveza>> {
        return dao.observeAll().map { list ->
            list.map { it.toCerveza() }
        }
    }

}




