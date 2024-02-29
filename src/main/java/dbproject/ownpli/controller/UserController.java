package dbproject.ownpli.controller;

import dbproject.ownpli.domain.UserEntity;
import dbproject.ownpli.dto.UserDTO;
import dbproject.ownpli.dto.UserJoinRequest;
import dbproject.ownpli.dto.UserSignInRequest;
import dbproject.ownpli.dto.UserSignInResponse;
import dbproject.ownpli.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@RequestBody UserJoinRequest request) {
        userService.join(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<UserSignInResponse> login(@RequestBody UserSignInRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    /**
     * 유저 정보 가져오기
     * @param param
     * @return
     */
    @PostMapping("/mypage")
    public ResponseEntity<UserDTO> homeLogin(@RequestBody LinkedHashMap param) {
        String userId = param.get("userId").toString();
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserEntity loginUser = userService.findByUserId(userId);

        if (loginUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(UserDTO.from(loginUser), HttpStatus.OK);
    }

    /**
     * 유저 닉네임 변경
     * @param param
     * @return
     */
    @PostMapping("/mypage/update")
    public ResponseEntity<UserDTO> changeName(@RequestBody LinkedHashMap param) {
        String userId = param.get("userId").toString();
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String name = param.get("name").toString();

        UserDTO userDTO = userService.updateNicknameByUserId(name, userId);
        if(userDTO == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }

}
