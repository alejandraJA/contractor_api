package com.invoice.constratista.datasource.database.event

import com.invoice.constratista.datasource.database.AddressEntity
import jakarta.persistence.*
import lombok.Data

@Entity(name = "[schedule]")
@Table
@Data
data class ScheduleEntity(
    @Id val id: String,
    val date: String,
    val state: String,
    val note: String,
    val name: String,
) {
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "event_id")
    var event: EventEntity? = null

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "address_id")
    var address: AddressEntity? = null
}
