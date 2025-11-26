package com.piis.cinema.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="film")
@SQLDelete(sql = "UPDATE film SET is_active = false WHERE id = ?")
@Where(clause = "is_active = true")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NamedEntityGraph(
        name = "FilmEntity.screenings.room",
        attributeNodes = @NamedAttributeNode(value="screenings", subgraph = "roomSubgraph"),
        subgraphs =
        @NamedSubgraph(
                name = "roomSubgraph",
                attributeNodes = @NamedAttributeNode("room")
        )
)
public class FilmEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @Column(name = "is_active")
    private Boolean isActive;
    private String image;
    private String description;
    @OneToMany(mappedBy = "film",fetch = FetchType.LAZY)
    private Set<ScreeningEntity> screenings;
}