package com.invoice.constratista.datasource.database.event

import com.fasterxml.jackson.annotation.JsonIgnore
import com.invoice.constratista.datasource.database.AddressEntity
import jakarta.persistence.*
import lombok.Data
import java.sql.Date

@Entity(name = "schedule")
@Table
@Data
data class ScheduleEntity(
    @Id val id: String,
    val date: Date,
    val state: String,
    val note: String,
    val name: String,
) {
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "event_id")
    @JsonIgnore
    var event: EventEntity? = null

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "address_id")
    var address: AddressEntity? = null
}
