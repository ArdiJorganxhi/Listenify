package dev.ardijorganxhi.listenify.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "albums")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private boolean deleted = false;

    @OneToMany(mappedBy = "album")
    private List<Song> songs;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
}
