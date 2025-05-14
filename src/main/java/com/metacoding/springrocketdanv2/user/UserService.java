package com.metacoding.springrocketdanv2.user;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2.company.Company;
import com.metacoding.springrocketdanv2.company.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        User userPS = userRepository.findByUsername(joinDTO.getUsername());
        if (userPS != null) {
            throw new ExceptionApi400("잘못된 요청입니다");
        }
        userRepository.save(joinDTO.toEntity());
    }

    public UserResponse.SessionUserDTO 로그인(UserRequest.LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new RuntimeException("아이디 또는 비밀번호가 틀렸습니다.");
        }

        String companyName;

        if (user.getCompanyId() == null) {
            companyName = null;
        } else {
            Company companyPC = companyRepository.findById(user.getCompanyId());
            companyName = companyPC.getNameKr();
        }

        UserResponse.SessionUserDTO sessionUserDTO = new UserResponse.SessionUserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFileUrl(),
                user.getUserType(),
                user.getCompanyId(),
                companyName
        );

        return sessionUserDTO;
    }
}