package com.metacoding.springrocketdanv2.integre;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metacoding.springrocketdanv2.MyRestDoc;
import com.metacoding.springrocketdanv2._core.util.JwtUtil;
import com.metacoding.springrocketdanv2.career.CareerRequest;
import com.metacoding.springrocketdanv2.certification.CertificationRequest;
import com.metacoding.springrocketdanv2.resume.ResumeRequest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ResumeControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;
    @Autowired
    private EntityManager em;

    private String accessToken;

    @BeforeEach
    void resetAutoIncrement() {
        // 더미는 이미 들어가 있는 상태이므로, 테이블 데이터는 지우지 않고 시퀀스만 리셋
        em.createNativeQuery("ALTER TABLE certification_tb ALTER COLUMN id RESTART WITH 51").executeUpdate();
        em.createNativeQuery("ALTER TABLE career_tb ALTER COLUMN id RESTART WITH 51").executeUpdate();
    }

    @BeforeEach
    public void setUp() {
        // 테스트 시작 전에 실행할 코드
        System.out.println("setUp");
        User ssar = User.builder()
                .id(1)
                .username("user01")
                .build();
        accessToken = JwtUtil.create(ssar);
    }

    @AfterEach
    public void tearDown() { // 끝나고 나서 마무리 함수
        // 테스트 후 정리할 코드
        System.out.println("tearDown");
    }

    @Test
    public void detail_test() throws Exception {
        // given
        Integer resumeId = 1;

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/resume/{resumeId}", resumeId)
                        .header("Authorization", "Bearer " + accessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(jsonPath("$.status").value(200));
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.body.id").value(1));
        actions.andExpect(jsonPath("$.body.title").value("백엔드 개발자 이력서"));
        actions.andExpect(jsonPath("$.body.summary").value("Java와 Spring Boot 기반의 백엔드 시스템 개발 경험이 있습니다. RESTful API 설계, 데이터베이스 최적화, 대용량 트래픽 처리에 능숙하며, 코드 리뷰와 협업 문화에 익숙합니다. 문제 해결과 지속적인 기술 학습에 열정을 가지고 있습니다."));
        actions.andExpect(jsonPath("$.body.gender").value("남"));
        actions.andExpect(jsonPath("$.body.careerLevel").value("경력"));
        actions.andExpect(jsonPath("$.body.education").value("서울대학교"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.birthdate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(jsonPath("$.body.major").value("컴퓨터공학"));
        actions.andExpect(jsonPath("$.body.graduationType").value("졸업"));
        actions.andExpect(jsonPath("$.body.phone").value("010-1000-0001"));
        actions.andExpect(jsonPath("$.body.portfolioUrl").value("https://github.com/user01"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.enrollmentDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.graduationDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(jsonPath("$.body.createdAt",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(jsonPath("$.body.isDefault").value(true));

        actions.andExpect(jsonPath("$.body.user.id").value(1));
        actions.andExpect(jsonPath("$.body.user.username").value("user01"));
        actions.andExpect(jsonPath("$.body.user.email").value("user01@example.com"));
        actions.andExpect(jsonPath("$.body.user.fileUrl").doesNotExist());
        actions.andExpect(jsonPath("$.body.user.userType").value("user"));
        actions.andExpect(jsonPath("$.body.user.companyId").doesNotExist());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.createdAt",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));

        actions.andExpect(jsonPath("$.body.certifications[0].id").value(1));
        actions.andExpect(jsonPath("$.body.certifications[0].name").value("정보처리기사"));
        actions.andExpect(jsonPath("$.body.certifications[0].issuer").value("한국산업인력공단"));
        actions.andExpect(jsonPath("$.body.certifications[0].issuedDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));

        actions.andExpect(jsonPath("$.body.careers[0].id").value(1));
        actions.andExpect(jsonPath("$.body.careers[0].companyName").value("네이버"));
        actions.andExpect(jsonPath("$.body.careers[0].startDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(jsonPath("$.body.careers[0].endDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));

        actions.andExpect(jsonPath("$.body.isOwner").value(false));

        actions.andExpect(jsonPath("$.body.salaryRange.id").value(3));
        actions.andExpect(jsonPath("$.body.salaryRange.minSalary").value(5000));
        actions.andExpect(jsonPath("$.body.salaryRange.maxSalary").value(6000));
        actions.andExpect(jsonPath("$.body.salaryRange.label").value("5000-6000"));

        actions.andExpect(jsonPath("$.body.jobGroup.id").value(1));
        actions.andExpect(jsonPath("$.body.jobGroup.name").value("백엔드 개발자"));

        actions.andExpect(jsonPath("$.body.techStacks[0].id").value(1));
        actions.andExpect(jsonPath("$.body.techStacks[0].name").value("Java"));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void update_test() throws Exception {
        // given
        Integer resumeId = 1;

        CareerRequest.DTO career1 = new CareerRequest.DTO();
        career1.setCompanyName("카카오");
        career1.setStartDate("2019-01-01");
        career1.setEndDate("2022-12-31");

        List<CareerRequest.DTO> careers = List.of(career1);

        CertificationRequest.DTO cert1 = new CertificationRequest.DTO();
        cert1.setName("정보처리기사");
        cert1.setIssuer("한국산업인력공단");
        cert1.setIssuedDate("2018-06-01");

        List<CertificationRequest.DTO> certifications = List.of(cert1);

        ResumeRequest.UpdateDTO reqDTO = new ResumeRequest.UpdateDTO();
        reqDTO.setTitle("백엔드 개발자 이력서");
        reqDTO.setSummary("Java와 Spring Boot 기반의 개발 경험을 보유한 백엔드 개발자입니다.");
        reqDTO.setPortfolioUrl("https://github.com/example");
        reqDTO.setGender("남");
        reqDTO.setEducation("서울대학교");
        reqDTO.setBirthdate("1995-01-01");
        reqDTO.setMajor("컴퓨터공학");
        reqDTO.setGraduationType("졸업");
        reqDTO.setPhone("01012345678");
        reqDTO.setEnrollmentDate("2014-03-01");
        reqDTO.setGraduationDate("2018-02-01");
        reqDTO.setCareerLevel("경력");
        reqDTO.setIsDefault(true);
        reqDTO.setCareers(careers);
        reqDTO.setCertifications(certifications);
        reqDTO.setSalaryRangeId(1);
        reqDTO.setJobGroupId(1);
        reqDTO.setTechStackIds(List.of(1, 2, 3));

        String requestBody = om.writeValueAsString(reqDTO);
        System.out.println(requestBody);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .put("/s/api/resume/{resumeId}", resumeId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(jsonPath("$.status").value(200));
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.body.id").value(1));
        actions.andExpect(jsonPath("$.body.title").value("백엔드 개발자 이력서"));
        actions.andExpect(jsonPath("$.body.summary").value("Java와 Spring Boot 기반의 개발 경험을 보유한 백엔드 개발자입니다."));
        actions.andExpect(jsonPath("$.body.portfolioUrl").value("https://github.com/example"));
        actions.andExpect(jsonPath("$.body.gender").value("남"));
        actions.andExpect(jsonPath("$.body.education").value("서울대학교"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.birthdate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(jsonPath("$.body.major").value("컴퓨터공학"));
        actions.andExpect(jsonPath("$.body.graduationType").value("졸업"));
        actions.andExpect(jsonPath("$.body.phone").value("01012345678"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.enrollmentDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.graduationDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(jsonPath("$.body.careerLevel").value("경력"));
        actions.andExpect(jsonPath("$.body.isDefault").value(true));
        actions.andExpect(jsonPath("$.body.userId").value(1));
        actions.andExpect(jsonPath("$.body.certifications[0].id").value(51));
        actions.andExpect(jsonPath("$.body.certifications[0].name").value("정보처리기사"));
        actions.andExpect(jsonPath("$.body.certifications[0].issuer").value("한국산업인력공단"));
        actions.andExpect(jsonPath("$.body.certifications[0].issuedDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(jsonPath("$.body.careers.[0].id").value(51));
        actions.andExpect(jsonPath("$.body.careers.[0].companyName").value("카카오"));
        actions.andExpect(jsonPath("$.body.careers.[0].startDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(jsonPath("$.body.careers.[0].endDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(jsonPath("$.body.salaryRangeId").value(1));
        actions.andExpect(jsonPath("$.body.jobGroupId").value(1));
        actions.andExpect(jsonPath("$.body.techStackIds[0]").value(1));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void list_test() throws Exception {
        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/s/api/user/resume")
                        .param("isDefault", "false")
                        .header("Authorization", "Bearer " + accessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(jsonPath("$.status").value(200));
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.body.resumes[0].id").value(1));
        actions.andExpect(jsonPath("$.body.resumes[0].title").value("백엔드 개발자 이력서"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.resumes[0].createdAt",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(jsonPath("$.body.resumes[0].isDefault").value(true));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void delete_test() throws Exception {
        // given
        Integer resumeId = 1;

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .delete("/s/api/resume/" + resumeId)
                        .header("Authorization", "Bearer " + accessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        // then
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$.status").value(200));
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.body").doesNotExist());
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void save_test() throws Exception {

        // given
        CareerRequest.DTO career1 = new CareerRequest.DTO();
        career1.setCompanyName("카카오");
        career1.setStartDate("2019-01-01");
        career1.setEndDate("2022-12-31");

        List<CareerRequest.DTO> careers = List.of(career1);

        CertificationRequest.DTO cert1 = new CertificationRequest.DTO();
        cert1.setName("정보처리기사");
        cert1.setIssuer("한국산업인력공단");
        cert1.setIssuedDate("2018-06-01");

        List<CertificationRequest.DTO> certifications = List.of(cert1);

        ResumeRequest.SaveDTO reqDTO = new ResumeRequest.SaveDTO();
        reqDTO.setTitle("백엔드 개발자 이력서");
        reqDTO.setSummary("Java와 Spring Boot 기반의 개발 경험을 보유한 백엔드 개발자입니다.");
        reqDTO.setPortfolioUrl("https://github.com/example");
        reqDTO.setGender("남");
        reqDTO.setEducation("서울대학교");
        reqDTO.setBirthdate("1995-01-01");
        reqDTO.setMajor("컴퓨터공학");
        reqDTO.setGraduationType("졸업");
        reqDTO.setPhone("01012345678");
        reqDTO.setEnrollmentDate("2014-03-01");
        reqDTO.setGraduationDate("2018-02-01");
        reqDTO.setCareerLevel("경력");
        reqDTO.setIsDefault(true);
        reqDTO.setCareers(careers);
        reqDTO.setCertifications(certifications);
        reqDTO.setSalaryRangeId(1);
        reqDTO.setJobGroupId(1);
        reqDTO.setTechStackIds(List.of(1, 2, 3));

        String requestBody = om.writeValueAsString(reqDTO);
        System.out.println(requestBody);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/s/api/resume")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        // then
        actions.andExpect(jsonPath("$.status").value(200));
        actions.andExpect(jsonPath("$.msg").value("성공"));
        actions.andExpect(jsonPath("$.body.id").value(51));
        actions.andExpect(jsonPath("$.body.title").value("백엔드 개발자 이력서"));
        actions.andExpect(jsonPath("$.body.summary").value("Java와 Spring Boot 기반의 개발 경험을 보유한 백엔드 개발자입니다."));
        actions.andExpect(jsonPath("$.body.gender").value("남"));
        actions.andExpect(jsonPath("$.body.careerLevel").value("경력"));
        actions.andExpect(jsonPath("$.body.education").value("서울대학교"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.birthdate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(jsonPath("$.body.major").value("컴퓨터공학"));
        actions.andExpect(jsonPath("$.body.graduationType").value("졸업"));
        actions.andExpect(jsonPath("$.body.phone").value("01012345678"));
        actions.andExpect(jsonPath("$.body.portfolioUrl").value("https://github.com/example"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.enrollmentDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.graduationDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.createdAt",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(jsonPath("$.body.isDefault").value(true));
        actions.andExpect(jsonPath("$.body.userId").value(1));
        actions.andExpect(jsonPath("$.body.certifications[0].id").value(51));
        actions.andExpect(jsonPath("$.body.certifications[0].name").value("정보처리기사"));
        actions.andExpect(jsonPath("$.body.certifications[0].issuer").value("한국산업인력공단"));
        actions.andExpect(jsonPath("$.body.certifications[0].issuedDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(jsonPath("$.body.careers[0].id").value(51));
        actions.andExpect(jsonPath("$.body.careers[0].companyName").value("카카오"));
        actions.andExpect(jsonPath("$.body.careers[0].startDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(jsonPath("$.body.careers[0].endDate",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(jsonPath("$.body.salaryRangeId").value(1));
        actions.andExpect(jsonPath("$.body.jobGroupId").value(1));
        actions.andExpect(jsonPath("$.body.techStackIds[0]").value(1));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}