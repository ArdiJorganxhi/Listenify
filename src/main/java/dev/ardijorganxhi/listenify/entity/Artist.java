package dev.ardijorganxhi.listenify.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "artists")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String listenifyname;

    private boolean deleted = false;

    @OneToMany(mappedBy = "artist")
    private List<Song> songs;




}
