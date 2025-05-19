package com.metacoding.springrocketdanv2.user;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserRepository.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void save_test() {
        // given
        User user = User.builder()
                .username("이름")
                .password("1234")
                .email("testname@nate.com")
                .userType("user")
                .build();

        // when
        userRepository.save(user);
        em.flush();
        em.clear();

        // eye
        User result = userRepository.findByUserId(user.getId()).orElseThrow();
        System.out.println("ID: " + result.getId());
        System.out.println("Username: " + result.getUsername());
        System.out.println("Password: " + result.getPassword());
        System.out.println("Email: " + result.getEmail());
        System.out.println("FileUrl: " + result.getFileUrl());
        System.out.println("UserType: " + result.getUserType());
        System.out.println("CompanyId: " + result.getCompanyId());
        System.out.println("CreatedAt: " + result.getCreatedAt());
    }

    @Test
    public void findByUsername_test() {
        // given
        String username = "user01";

        // when
        User userPS = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("해당 username에 대한 유저가 없다"));

        // eye
        if (userPS == null) {
            System.out.println("User not found!!!!");
        } else {
            System.out.println("Id:" + userPS.getId() + ", password: " + userPS.getPassword()
                    + ", email: " + userPS.getEmail() + ", fileUrl: " + userPS.getFileUrl()
                    + ", userType: " + userPS.getUserType() + ", companyId: " + userPS.getCompanyId()
                    + ", createdAt: " + userPS.getCreatedAt());
        }
    }

    @Test
    public void findById_test() {
        // given
        Integer userId = 2;

        // when
        User userPS = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 없다"));

        // eye
        if (userPS == null) {
            System.out.println("User not found!!!!");
        } else {
            System.out.println("username: " + userPS.getUsername() + ", password: " + userPS.getPassword()
                    + ", email: " + userPS.getEmail() + ", fileUrl: " + userPS.getFileUrl()
                    + ", userType: " + userPS.getUserType() + ", companyId: " + userPS.getCompanyId()
                    + ", createdAt: " + userPS.getCreatedAt());
        }
    }
}