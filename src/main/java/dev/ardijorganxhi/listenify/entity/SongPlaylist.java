package dev.ardijorganxhi.listenify.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.ardijorganxhi.listenify.model.embedded.SongPlaylistId;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "songs_playlists")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongPlaylist {

    @EmbeddedId
    private SongPlaylistId songPlaylistId = new SongPlaylistId();

    @ManyToOne
    @MapsId("songId")
    @JoinColumn(name = "song_id")
    @JsonIgnore
    private Song song;

    @ManyToOne
    @MapsId("playlistId")
    @JoinColumn(name = "playlist_id")
    @JsonIgnore
    private Playlist playlist;

    private boolean deleted = false;
}
