package com.metacoding.springrocketdanv2.user;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi401;
import com.metacoding.springrocketdanv2._core.util.JwtUtil;
import com.metacoding.springrocketdanv2.application.Application;
import com.metacoding.springrocketdanv2.application.ApplicationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public UserResponse.DTO 회원가입(UserRequest.JoinDTO reqDTO) {
        Optional<User> userOP = userRepository.findByUsername(reqDTO.getUsername());
        if (userOP.isPresent()) throw new ExceptionApi400("이미 존재하는 username 입니다");

        String encPassword = BCrypt.hashpw(reqDTO.getPassword(), BCrypt.gensalt());
        reqDTO.setPassword(encPassword);

        User userPS = userRepository.save(reqDTO.toEntity());
        return new UserResponse.DTO(userPS);
    }

    public UserResponse.TokenDTO 로그인(UserRequest.LoginDTO reqDTO) {
        User userPS = userRepository.findByUsername(reqDTO.getUsername())
                .orElseThrow(() -> new ExceptionApi401("유저네임 혹은 비밀번호가 틀렸습니다"));

        Boolean isMatched = BCrypt.checkpw(reqDTO.getPassword(), userPS.getPassword());

        if (!isMatched) {
            throw new ExceptionApi401("유저네임 혹은 비밀번호가 틀렸습니다");
        }

        String accessToken = JwtUtil.create(userPS);
        String refreshToken = JwtUtil.createRefresh(userPS);

        return UserResponse.TokenDTO.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }


    public UserResponse.ListForUserDTO 내지원목록보기(Integer sessionUserId, String status) {
        List<Application> applicationsPS = applicationRepository.findAllByUserIdAndStatus(sessionUserId, status);

        return new UserResponse.ListForUserDTO(applicationsPS);
    }

//    public void 내지원보기(Integer applicationId, Integer sessionUserId) {
//        applicationRepository
//    }
}