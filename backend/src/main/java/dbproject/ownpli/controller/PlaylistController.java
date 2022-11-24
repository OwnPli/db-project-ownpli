package dbproject.ownpli.controller;

import dbproject.ownpli.domain.music.MusicEntity;
import dbproject.ownpli.domain.playlist.PlaylistEntity;
import dbproject.ownpli.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    /**
     * 회원의 모든 Playlist 조회
     * @param userId
     * @return
     * @address
     * /playlist/getlist
     */
    @GetMapping("/getlist")
    public ResponseEntity<List<PlaylistEntity>> findAllPlaylists(@RequestBody String userId) {
        List<PlaylistEntity> playlistEntities = playlistService.findPlaylistByUserId(userId);
        return new ResponseEntity<>(playlistEntities, HttpStatus.OK);
    }

    /**
     * playlistId로 playlist에 포함된 음악 list 조회
     * @param playlistId
     * @return
     * @address
     * /playlist/getlist/playlistId
     * @container
     *  `@PathVariable` 어노테이션 뒤에 {} 안에 적은 변수 명을 name 속성의 값으로 넣는다.
     */
    @GetMapping("/getlist/{playlistId}")
    public ResponseEntity<MusicEntity[]> findMusicList(@PathVariable(name = "playlistId") String playlistId) {
        MusicEntity[] musicEntities = playlistService.findMusicsByPlaylistId(playlistId).toArray(new MusicEntity[0]);
        return new ResponseEntity<>(musicEntities, HttpStatus.OK);
    }

    /**
     * 플레이리스트 생성
     * @param userId
     * @param title
     * @param musicId
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<String> createPlaylist(@RequestParam String userId, @RequestParam String title, @RequestParam List<String> musicId) {
        String playlistId = playlistService.savePlaylist(userId, title, musicId);

        if(playlistId == null)
            return new ResponseEntity<>("생성 실패", HttpStatus.BAD_REQUEST);

        return new ResponseEntity("생성 성공", HttpStatus.OK);
    }

    /**
     * 플레이리스트 삭제
     * @param playlistId
     * @return
     */
    @PostMapping("/delete")
    public ResponseEntity<String> deletePlaylist(@RequestParam String playlistId) {
        playlistService.deletePlaylist(playlistId);

        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

}
