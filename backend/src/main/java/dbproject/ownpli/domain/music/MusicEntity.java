package dbproject.ownpli.domain.music;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

/**
 * 조인으로 연동하기
 */
@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "music")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicEntity {

    @Id
    @Column(name = "music id", nullable = false, length = 50)
    private String musicId;

    //제목
    @Column(nullable = false, length = 200)
    private String title;

    //가수
    @Column(nullable = false, length = 200)
    private String singer;

    //앨범명
    @Column(length = 200)
    private String album;

    @Column(name = "genre num")
    private Long genreNum;

    //이미지파일경로
    @Column(name = "image file", nullable = false, length = 50)
    private String imageFile;

    @Column
    private Date date;

    //나라
    @Column(length = 50)
    private String country;

    //가사 파일 경로
    @Column(name = "lyrics file", length = 50)
    private String lyricsFile;

    //mp3 파일 경로
    @Column(name = "mp3 file", nullable = false, length = 50)
    private String mp3File;





}
