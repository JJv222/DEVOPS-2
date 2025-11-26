package com.piis.cinema.infrastructure.database.entity;

import com.piis.cinema.domain.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NamedEntityGraph(
        name = "TicketEntity.screening.film",
        attributeNodes = {
                @NamedAttributeNode(value = "screening", subgraph = "filmSubgraph"),
                @NamedAttributeNode(value = "seat"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "filmSubgraph",
                        attributeNodes = {
                                @NamedAttributeNode("film"),
                                @NamedAttributeNode("room")
                        }
                )
        }
)
public class TicketEntity {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "screening_id")
    private ScreeningEntity screening;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private SeatEntity seat;

    @Column(nullable = false, unique = true)
    private String reservationCode;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}
