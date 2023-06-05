package com.invoice.constratista.datasource.database.event

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.Data

@Entity(name = "note")
@Table
@Data
data class NoteEntity(
    @Id val id: String,
    val note: String,
) {
    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonIgnore
    var event: EventEntity? = null
}
