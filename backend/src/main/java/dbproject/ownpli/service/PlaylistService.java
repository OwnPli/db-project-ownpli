package dbproject.ownpli.service;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.playlist.PlaylistEntity;
import dbproject.ownpli.domain.playlist.PlaylistMusicEntity;
import dbproject.ownpli.repository.MusicRepository;
import dbproject.ownpli.repository.PlaylistMusicRepository;
import dbproject.ownpli.repository.PlaylistRepository;
import dbproject.ownpli.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMusicRepository playlistMusicRepository;
    private final MusicRepository musicRepository;
    private final UserRepository userRepository;

    /**
     * 유저이메일로 플레이리스트 목록 찾기
     * @param userId
     * @return
     */
    public List<PlaylistEntity> findPlaylistByUserId(String userId) {
        return (List<PlaylistEntity>) playlistRepository.findByUserId(userId);
    }

    /**
     * playlistID로 음악 리스트 찾기
     * @param playlistId
     * @return
     */
    public List<MusicEntity> findMusicsByPlaylistId(String playlistId) {
        List<String> musicIds = playlistMusicRepository.findMusicIdsByPlaylistId(playlistId);
        List<MusicEntity> musics = musicRepository.findByMusicId(musicIds);

        return musics;
    }

    /**
     * 새 플레이리스트 저장
     * @param userId
     * @param title
     * @param musicIds
     */
    public void savePlaylist(String userId, String title, List<String> musicIds) {
        String id = playlistRepository.findTop1ByUserIdOrderByPlaylistIdDesc(userId).getPlaylistId();
        StringTokenizer st = new StringTokenizer("p", id);
        Long idLong = Long.parseLong(st.nextToken());

        idLong++;

        id = "p" + idLong;

        playlistRepository.save(
            PlaylistEntity.builder().playlistId(id)
                .playlistTitle("title")
                .userId(userRepository.findById(userId).get())
                .build()
        );

        for(int i = 0; i < musicIds.size(); i++) {
            playlistMusicRepository.save(
                PlaylistMusicEntity.builder()
                    .playlistId(playlistRepository.findById(id).get())
                    .musicId(musicRepository.findById(musicIds.get(i)).get())
                    .build());
        }

        log.info("플레이리스트 저장");
    }

    private String toYYYY_MM_DD(Date date) {
        if(date!=null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(date);
        }else{
            return null;
        }
    }

}
