package com.metacoding.springrocketdanv2.company;

import com.metacoding.springrocketdanv2.workfield.WorkField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@DataJpaTest
@Import(CompanyRepository.class)
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void updateByCompanyId_test() {
        // given
        Integer companyId = 1;
        CompanyRequest.UpdateDTO reqDTO = new CompanyRequest.UpdateDTO();
        reqDTO.setNameKr("변경된이름");
        reqDTO.setOneLineIntro("한줄");
        reqDTO.setWorkFieldId(1);

        // when
        companyRepository.updateByCompanyId(companyId, reqDTO);
        Company companyPS = companyRepository.findByCompanyId(companyId)
                .orElseThrow(() -> new RuntimeException("companyId not found"));

        // eye
        System.out.println("컴퍼니 아이디: " + companyPS.getId());
        System.out.println("컴퍼니 이름: " + companyPS.getNameKr());
        System.out.println("컴퍼니 한줄: " + companyPS.getOneLineIntro());
        System.out.println("컴퍼니 업종: " + companyPS.getWorkField().getId());
        System.out.println("컴퍼니 ceo: " + companyPS.getCeo());
    }

    @Test
    public void findByCompanyIdJoinFetchAll_test() {
        // given
        Integer companyId = 1;

        // when
        Company companyPS = companyRepository.findByCompanyIdJoinFetchAll(companyId)
                .orElseThrow(() -> new RuntimeException("companyId not found"));

        // eye
        System.out.println("컴퍼니 아이디: " + companyPS.getId());
        System.out.println("컴퍼니 이름: " + companyPS.getNameKr());
        System.out.println("유저 아이디: " + companyPS.getUser().getId());
        System.out.println("유저 이름: " + companyPS.getUser().getUsername());
        System.out.println("업종 아이디: " + companyPS.getWorkField().getId());
        System.out.println("업종 이름: " + companyPS.getWorkField().getName());
        companyPS.getCompanyTechStacks().forEach(cs -> {
            System.out.print("기술스택 아이디: " + cs.getTechStack().getId() + ", ");
            System.out.println("기술스택 이름: " + cs.getTechStack().getName());
        });
    }

    @Test
    public void findByCompanyId_test() {
        // given
        Integer companyId = 1;

        // when
        Company companyPS = companyRepository.findByCompanyId(companyId)
                .orElseThrow(() -> new RuntimeException("companyId not found"));

        // eye
        System.out.println("컴퍼니 아이디: " + companyPS.getId());
        System.out.println("컴퍼니 이름: " + companyPS.getNameKr());
    }

    @Test
    public void findAll_test() {
        // given

        // when
        List<Company> companiesPS = companyRepository.findAll();

        // eye
        for (int i = 0; i < 5; i++) {
            System.out.print("컴퍼니 아이디: " + companiesPS.get(i).getId() + ", ");
            System.out.println("컴퍼니 이름: " + companiesPS.get(i).getNameKr());
        }
    }

    @Test
    public void save_test() {
        // given
        Company company = Company.builder()
                .nameKr("새로운 기업")
                .workField(WorkField.builder().id(1).build())
                .build();

        // when
        Company companyPS = companyRepository.save(company);

        // eye
        System.out.println("컴퍼니 아이디: " + companyPS.getId());
        System.out.println("컴퍼니 이름: " + companyPS.getNameKr());
        System.out.println("컴퍼니 업종 아이디: " + companyPS.getWorkField().getId());
        System.out.println("컴퍼니 업종 이름: " + companyPS.getWorkField().getName());
    }
}

