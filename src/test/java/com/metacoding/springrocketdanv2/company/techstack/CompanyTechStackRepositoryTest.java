package com.metacoding.springrocketdanv2.company.techstack;

import com.metacoding.springrocketdanv2.company.Company;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(CompanyTechStackRepository.class)
@DataJpaTest
public class CompanyTechStackRepositoryTest {
    @Autowired
    private CompanyTechStackRepository companyTechStackRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void save_test() {
        // given
        CompanyTechStack companyTechStack = CompanyTechStack.builder()
                .company(Company.builder().id(1).build())
                .techStack(TechStack.builder().id(1).build())
                .build();

        // when
        companyTechStackRepository.save(companyTechStack);

        // eye
        em.flush();  // 영속성 컨텍스트 → DB 반영
        em.clear();  // 1차 캐시 초기화 후 DB에서 다시 조회 (확실한 테스트를 위해)

        CompanyTechStack companyTechStackPS = em.find(CompanyTechStack.class, companyTechStack.getId());

        System.out.println("기업 아이디" + companyTechStackPS.getCompany().getId());
        System.out.println("기술스택 아이디" + companyTechStackPS.getTechStack().getId());
    }

    @Test
    public void deleteByCompanyId_test() {
        // given
        Integer companyId = 1;

        // when
        companyTechStackRepository.deleteByCompanyId(companyId);

        // eye
        List<CompanyTechStack> companyTechStacksPS = em.createQuery("SELECT cts FROM CompanyTechStack cts WHERE cts.company.id = :companyId", CompanyTechStack.class)
                .setParameter("companyId", companyId)
                .getResultList();

        if (companyTechStacksPS.isEmpty()) {
            System.out.println("삭제 성공");
        } else {
            System.out.println("삭제 실패");
        }
    }

}
