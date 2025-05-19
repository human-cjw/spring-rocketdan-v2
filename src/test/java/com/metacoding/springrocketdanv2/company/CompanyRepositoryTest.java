package com.metacoding.springrocketdanv2.company;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@SpringBootTest
@Transactional

@Import(CompanyRepository.class)


public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void findById_test() {

    }

    @Test
    public void findAll_test() {

    }

    @Test
    public void save_test() {

    }

    @Test
    public void findByUserId_test() {

    }
}

