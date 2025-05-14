package com.metacoding.springrocketdanv2.company.Techstack;

import com.metacoding.springrocketdanv2.companyTechStack.CompanyTechStackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(CompanyTechStackRepository.class)
@DataJpaTest
public class TechStackRepositoryTest {
    @Autowired
    private CompanyTechStackRepository companyTechStackRepository;
}
