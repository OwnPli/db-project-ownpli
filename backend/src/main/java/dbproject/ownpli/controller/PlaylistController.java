package dbproject.ownpli.controller;

import dbproject.ownpli.dto.MusicDTO;
import dbproject.ownpli.dto.PlaylistDTO;
import dbproject.ownpli.dto.PlaylistMusicDTO;
import dbproject.ownpli.service.MusicService;
import dbproject.ownpli.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final MusicService musicService;

    /**
     * 회원의 모든 Playlist 조회
     * @param userId
     * @return
     * @address
     * /playlist/getlist
     */
    @GetMapping("/getlist")
    public ResponseEntity<List<PlaylistDTO>> findAllPlaylists(@CookieValue(name = "userId") String userId) {
        List<PlaylistDTO> playlistDTOList = playlistService.findPlaylistByUserId(userId);
        return new ResponseEntity<>(playlistDTOList, HttpStatus.OK);
    }

    /**
     * playlistId로 playlist에 포함된 음악 list 정보 조회
     * @param param
     * @return
     * @address
     * /playlist/getlist/playlistId
     * @container
     *  `@PathVariable` 어노테이션 뒤에 {} 안에 적은 변수 명을 name 속성의 값으로 넣는다.
     */
    @PostMapping("/getlist")
    public ResponseEntity<PlaylistMusicDTO> findMusicList(@RequestBody LinkedHashMap param) {
        String playlistId = param.get("playlistId").toString();
        List<String> musicsByPlaylistId = playlistService.findMusicsByPlaylistId(playlistId);
        List<MusicDTO> musicInfosByPlaylist = musicService.findMusicInfosByPlaylist(musicsByPlaylistId);
        return new ResponseEntity<>(
            PlaylistMusicDTO.from(playlistService.getPlaylistDTOByPlaylistId(playlistId), musicInfosByPlaylist),
            HttpStatus.OK);
    }

    /**
     * 플레이리스트 생성
     * @param userId
     * @param param
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<String> createPlaylist(@CookieValue(name = "userId") String userId,
                                                 @RequestBody LinkedHashMap param) {
        String title = param.get("title").toString();
        String musicId = param.get("musicIds").toString();
        List<String> musicIds = musicService.divString(musicId);
        String playlistId = playlistService.savePlaylist(userId, title, musicIds);

        if(playlistId == null)
            return new ResponseEntity<>("생성 실패", HttpStatus.BAD_REQUEST);

        return new ResponseEntity("생성 성공", HttpStatus.OK);
    }

    /**
     * 플레이리스트에 음악 추가
     * @param userId
     * @param param
     * @return
     */
    @PostMapping("/addSongs")
    public ResponseEntity<String> updatePlaylist(@CookieValue(name = "userId") String userId,
                                                 @RequestBody LinkedHashMap param) {

        String playlistId = param.get("playlistId").toString();
        String musicId = param.get("songsId").toString();
        List<String> musicIds = musicService.divString(musicId);
        String result = playlistService.addPlaylist(userId, playlistId, musicIds);

        if(result == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 플레이리스트 삭제
     * @param param
     * @return
     */
    @PostMapping("/delete")
    public ResponseEntity<String> deletePlaylist(@RequestBody LinkedHashMap param) {
        String playlistId = param.get("playlistId").toString();
        playlistService.deletePlaylist(playlistId);

        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

}
