package com.metacoding.springrocketdanv2.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserRepository.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void save_test() {
        // given
        User user = User.builder()
                .username("testname")
                .password("1234")
                .email("testname@nate.com")
                .build();

        // when
        User userPS = userRepository.save(user);

        // eye
        System.out.println(userPS);
    }

    @Test
    public void findByUsername_test() {
        // given
        String username = "user01";

        // when
        User userPS = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("해당 username에 대한 유저가 없다"));

        // eye
        System.out.println(userPS);
    }

    @Test
    public void findById_test() {
        // given
        Integer userId = 1;

        // when
        User userPS = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저가 없다"));

        // eye
        System.out.println(userPS);
    }
}
