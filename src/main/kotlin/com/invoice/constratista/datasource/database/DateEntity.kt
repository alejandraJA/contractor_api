package com.invoice.constratista.datasource.database

import com.fasterxml.jackson.annotation.JsonIgnore
import com.invoice.constratista.datasource.database.event.EventEntity
import jakarta.persistence.*
import lombok.Data
import java.sql.Date

@Entity
@Table(name = "date")
@Data
data class DateEntity(
    @Id
    var id: Long = 0,
    @Column(name = "id_reference")
    var idReference: String,
    var date: Date,
    var name: String
) {
    @ManyToOne
    @JoinColumn(name = "event_entity_id")
    @JsonIgnore
    var eventEntity: EventEntity? = null

    // Constructor vacío necesario para JPA
    constructor() : this(0, "", Date(java.util.Date().time), "")

}
