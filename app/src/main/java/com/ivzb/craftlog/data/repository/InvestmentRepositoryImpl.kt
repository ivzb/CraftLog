package com.ivzb.craftlog.data.repository

import com.ivzb.craftlog.data.dao.InvestmentDao
import com.ivzb.craftlog.data.mapper.toInvestment
import com.ivzb.craftlog.data.mapper.toInvestmentEntity
import com.ivzb.craftlog.domain.model.Investment
import com.ivzb.craftlog.domain.repository.InvestmentRepository
import com.ivzb.craftlog.util.getDateRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class InvestmentRepositoryImpl(
    private val dao: InvestmentDao
) : InvestmentRepository {

    override suspend fun insertInvestment(investment: Investment) {
        val entity = investment.toInvestmentEntity()
        dao.insertInvestment(entity)
    }

    override suspend fun deleteInvestment(investment: Investment) {
        dao.deleteInvestment(investment.toInvestmentEntity())
    }

    override suspend fun updateInvestment(investment: Investment) {
        dao.updateInvestment(investment.toInvestmentEntity())
    }

    override fun getAllInvestments(): Flow<List<Investment>> {
        return dao.getAllInvestments().map { entities ->
            entities.map { it.toInvestment() }
        }
    }

    override fun getInvestmentsForDate(date: Date): Flow<List<Investment>> {
        return dao.getInvestmentsForDate(
            date = date
        ).map { entities ->
            entities.map { it.toInvestment() }
        }
    }

    override fun getInvestmentsForRange(year: Int, month: Int): Flow<List<Investment>> {
        val (startDate, endDate) = getDateRange(year, month)

        return dao.getInvestmentsForDateRange(
            startDate,
            endDate
        ).map { entities ->
            entities.map { it.toInvestment() }
        }
    }
}
