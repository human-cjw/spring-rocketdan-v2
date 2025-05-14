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

    }

    @Test
    public void findByUserName_test() {

    }

    @Test
    public void findById_test() {

    }

    @Test
    public void findAll_test() {

    }
}
