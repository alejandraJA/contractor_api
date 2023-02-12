package com.invoice.constratista.datasource.database.event

import com.invoice.constratista.datasource.database.event.EventEntity
import jakarta.persistence.*
import lombok.Data

@Entity(name = "[note]")
@Table
@Data
data class NoteEntity(
    @Id val id: String,
    val note: String,
) {
    @ManyToOne
    @JoinColumn(name = "event_id")
    var event: EventEntity? = null
}
