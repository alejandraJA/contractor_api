package com.invoice.constratista.datasource.repository.sql;

import com.invoice.constratista.datasource.database.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<CustomerEntity, String> {
}