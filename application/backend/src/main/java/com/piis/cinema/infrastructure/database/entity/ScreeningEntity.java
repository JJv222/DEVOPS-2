package com.piis.cinema.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "screening")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NamedEntityGraph(
        name = "screenings.room",
        attributeNodes = {
                @NamedAttributeNode("film"),
                @NamedAttributeNode("room")
        }
)
@NamedEntityGraph(
        name = "screenings.details",
        attributeNodes = {
                @NamedAttributeNode("film"),
                @NamedAttributeNode(value = "tickets", subgraph = "ticketsSubgraph"),
                @NamedAttributeNode(value = "room", subgraph = "roomSubgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "roomSubgraph",
                        attributeNodes = {
                                @NamedAttributeNode("seats")
                        }
                ),
                @NamedSubgraph(
                        name = "ticketsSubgraph",
                        attributeNodes = {
                                @NamedAttributeNode("seat")
                        }
                )
        }
)
public class ScreeningEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dateTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "film_id")
    private FilmEntity film;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @OneToMany(mappedBy = "screening")
    private Set<TicketEntity> tickets;

    @Column(nullable = false)
    private int durationMinutes;
}