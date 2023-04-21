package dev.ardijorganxhi.listenify.model.embedded;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class SongPlaylistId implements Serializable {

    private Long songId;
    private Long playlistId;
}
