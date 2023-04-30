package dev.ardijorganxhi.listenify.entity;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "songs")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String genre;

    private boolean deleted = false;

    @OneToMany(mappedBy = "song")
    private List<SongPlaylist> playlists;

   @ManyToOne
   @JoinColumn(name = "artist_id")
   private Artist artist;

   @ManyToOne
   @JoinColumn(name = "album_id")
   private Album album;



}
