package com.metacoding.springrocketdanv2.integre;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metacoding.springrocketdanv2.MyRestDoc;
import com.metacoding.springrocketdanv2._core.util.JwtUtil;
import com.metacoding.springrocketdanv2.company.CompanyRequest;
import com.metacoding.springrocketdanv2.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.nullValue;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CompanyControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;
    @Autowired
    private EntityManager em;

    private String userAccessToken;
    private String companyAccessToken;

    @BeforeEach
    public void setUp1() {
        // 테스트 시작 전에 실행할 코드
        System.out.println("setUp1");
        User user01 = User.builder()
                .id(1)
                .username("user01")
                .build();
        userAccessToken = JwtUtil.create(user01);
    }

    @BeforeEach
    public void setUp2() {
        // 테스트 시작 전에 실행할 코드
        System.out.println("setUp2");
        User company01 = User.builder()
                .id(51)
                .username("company01")
                .companyId(1)
                .build();
        companyAccessToken = JwtUtil.create(company01);
    }

    @BeforeEach
    void resetAutoIncrement() {
        // 더미는 이미 들어가 있는 상태이므로, 테이블 데이터는 지우지 않고 시퀀스만 리셋
        em.createNativeQuery("ALTER TABLE company_tech_stack_tb ALTER COLUMN id RESTART WITH 274").executeUpdate();
    }

    @AfterEach
    public void tearDown() { // 끝나고 나서 마무리 함수
        // 테스트 후 정리할 코드
        System.out.println("tearDown");
    }

    @Test
    public void detail_test() throws Exception {
        // given
        Integer companyId = 1;

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/company/{companyId}", companyId)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.nameKr").value("에이아이랩"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.nameEn").value("AILab"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.ceo").value("김태영"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.businessNumber").value("1001001001"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.email").value("info@ailab.com"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.phone").value("02-1111-1111"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.address").value("서울특별시 강남구 테헤란로"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.introduction").value("에이아이랩은 2017년 설립된 AI 소프트웨어 전문기업으로, 자연어처리와 이미지 인식 분야에서 독자적인 알고리즘을 개발하고 있습니다. 클라우드 기반 서비스와 대규모 데이터 처리 경험을 바탕으로 다양한 산업에 혁신을 제공합니다. 연구개발 중심의 조직문화를 유지하며, 직원 역량 강화를 위해 연 2회 이상 사내 해커톤과 교육 프로그램을 운영합니다. 국내외 200여 고객사와 협력하며, 기술력과 신뢰를 기반으로 빠르게 성장하고 있습니다."));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.oneLineIntro").value("AI 혁신, 데이터로 현실이 되다"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.homepageUrl").value("https://www.ailab.com"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.logoImageUrl").value(nullValue()));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.infoImageUrl").value(nullValue()));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.contactManager").value("박지현"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.startDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.createdAt",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.workField.id").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.workField.name").value("IT/소프트웨어"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.techStacks[0].id").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.techStacks[0].name").value("Java"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.owner").value(false));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void list_test() throws Exception {
        // given

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/company")
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.companies[0].id").value(50));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.companies[0].nameKr").value("시티헬스"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.companies[0].logoImageUrl").value(nullValue()));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void save_test() throws Exception {
        // given
        CompanyRequest.SaveDTO reqDTO = new CompanyRequest.SaveDTO();
        reqDTO.setNameKr("테스트기업");
        reqDTO.setNameEn("testCompany");
        reqDTO.setIntroduction("테스트 기업 설명란");
        reqDTO.setOneLineIntro("테스트 기업 설명란");
        reqDTO.setStartDate("2099-03-01");
        reqDTO.setBusinessNumber("1001001111");
        reqDTO.setTechStackIds(List.of(1, 2));
        reqDTO.setEmail("test@test.com");
        reqDTO.setContactManager("테스트매니저");
        reqDTO.setPhone("01011111111");
        reqDTO.setCeo("테스터");
        reqDTO.setAddress("서울특별시 강남구 테헤란로");
        reqDTO.setWorkFieldId(1);
        reqDTO.setHomepageUrl("https://www.test.com");

        String requestBody = om.writeValueAsString(reqDTO);
//        System.out.println(requestBody);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/s/api/company")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userAccessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.status().is(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(51));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.userId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.nameKr").value("테스트기업"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.nameEn").value("testCompany"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.ceo").value("테스터"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.businessNumber").value("1001001111"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.email").value("test@test.com"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.phone").value("01011111111"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.address").value("서울특별시 강남구 테헤란로"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.introduction").value("테스트 기업 설명란"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.oneLineIntro").value("테스트 기업 설명란"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.homepageUrl").value("https://www.test.com"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.logoImageUrl").value(nullValue()));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.infoImageUrl").value(nullValue()));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.contactManager").value("테스트매니저"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.startDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.createdAt",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.workFieldId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.companyTechStacks[0].techStackId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.companyTechStacks[0].companyId").value(51));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.companyTechStacks[0].id").value(274));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.token.accessToken",
                matchesPattern("^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.token.refreshToken",
                matchesPattern("^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$")));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void update_test() throws Exception {
        // given
        Integer companyId = 1;
        CompanyRequest.UpdateDTO reqDTO = new CompanyRequest.UpdateDTO();
        reqDTO.setNameKr("테스트기업");
        reqDTO.setNameEn("testCompany");
        reqDTO.setIntroduction("테스트 기업 설명란");
        reqDTO.setOneLineIntro("테스트 기업 설명란");
        reqDTO.setStartDate("2099-03-01");
        reqDTO.setBusinessNumber("1001001111");
        reqDTO.setTechStackIds(List.of(1, 2));
        reqDTO.setEmail("test@test.com");
        reqDTO.setContactManager("테스트매니저");
        reqDTO.setPhone("01011111111");
        reqDTO.setCeo("테스터");
        reqDTO.setAddress("서울특별시 강남구 테헤란로");
        reqDTO.setWorkFieldId(1);
        reqDTO.setHomepageUrl("https://www.test.com");

        String requestBody = om.writeValueAsString(reqDTO);
        System.out.println(requestBody);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .put("/s/api/company/{companyId}", companyId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + companyAccessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.status().is(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.nameKr").value("테스트기업"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.nameEn").value("testCompany"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.ceo").value("테스터"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.businessNumber").value("1001001111"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.email").value("test@test.com"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.phone").value("01011111111"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.address").value("서울특별시 강남구 테헤란로"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.introduction").value("테스트 기업 설명란"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.oneLineIntro").value("테스트 기업 설명란"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.homepageUrl").value("https://www.test.com"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.logoImageUrl").value(nullValue()));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.infoImageUrl").value(nullValue()));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.contactManager").value("테스트매니저"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.startDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.workFieldId").value(1));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.companyTechStacks[0].techStackId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.companyTechStacks[0].companyId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.companyTechStacks[0].id").value(274));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void manage_test() throws Exception {
        // given

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/s/api/company/job")
                        .header("Authorization", "Bearer " + companyAccessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[0].id").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[0].title").value("AI 백엔드 개발자 모집"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[0].careerLevel").value("경력"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[0].createdAt",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[0].jobGroup.id").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[0].jobGroup.name").value("백엔드 개발자"));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void application_list_test() throws Exception {
        // given
        Integer jobId = 1;

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/s/api/company/job/{jobId}/application", jobId)
                        .header("Authorization", "Bearer " + companyAccessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobTitle").value("AI 백엔드 개발자 모집"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.status").value("접수"));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applications[0].id").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applications[0].username").value("user01"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applications[0].title").value("백엔드 개발자 이력서"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applications[0].careerLevel").value("경력"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applications[0].applyCreatedAt",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void company_application_test() throws Exception {
        // given
        Integer applicationId = 1;

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/s/api/company/application/{applicationId}", applicationId)
                        .header("Authorization", "Bearer " + companyAccessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.createdAt",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.resumeId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.title").value("백엔드 개발자 이력서"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.summary").value("Java와 Spring Boot 기반의 백엔드 시스템 개발 경험이 있습니다. RESTful API 설계, 데이터베이스 최적화, 대용량 트래픽 처리에 능숙하며, 코드 리뷰와 협업 문화에 익숙합니다. 문제 해결과 지속적인 기술 학습에 열정을 가지고 있습니다."));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.gender").value("남"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.careerLevel").value("경력"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.education").value("서울대학교"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.birthdate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.major").value("컴퓨터공학"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.graduationType").value("졸업"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.phone").value("010-1000-0001"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.portfolioUrl").value("https://github.com/user01"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.enrollmentDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.graduationDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.username").value("user01"));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.salaryRange.id").value(3));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.salaryRange.minSalary").value(5000));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.salaryRange.maxSalary").value(6000));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.salaryRange.label").value("5000-6000"));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobGroup.id").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobGroup.name").value("백엔드 개발자"));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.techStacks[0].id").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.techStacks[0].name").value("Java"));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
