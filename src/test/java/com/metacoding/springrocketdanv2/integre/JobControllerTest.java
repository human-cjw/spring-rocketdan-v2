package com.metacoding.springrocketdanv2.integre;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metacoding.springrocketdanv2.MyRestDoc;
import com.metacoding.springrocketdanv2._core.util.JwtUtil;
import com.metacoding.springrocketdanv2.job.JobRequest;
import com.metacoding.springrocketdanv2.user.User;
import jakarta.persistence.EntityManager;
import org.hamcrest.Matchers;
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

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class JobControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EntityManager em;

    private String accessToken;

    @BeforeEach
    public void setUp() {
        System.out.println("setUp");
        User ssar = User.builder()
                .id(51)
                .username("company01")
                .companyId(1)
                .build();
        accessToken = JwtUtil.create(ssar);
    }

    @AfterEach
    public void tearDown() { // 끝나고 나서 마무리 함수
        // 테스트 후 정리할 코드
        System.out.println("tearDown");
    }

    @Test
    public void save_test() throws Exception {
        // given
        JobRequest.SaveDTO reqDTO = new JobRequest.SaveDTO();
        reqDTO.setTitle("백엔드 개발자 모집");
        reqDTO.setDescription("Spring Boot 기반 서비스 개발");
        reqDTO.setLocation("서울특별시 강남구 테헤란로");
        reqDTO.setEmploymentType("정규직");
        reqDTO.setDeadline("2025-12-31");
        reqDTO.setStatus("OPEN");
        reqDTO.setCareerLevel("신입");
        reqDTO.setJobGroupId(1);
        reqDTO.setWorkFieldId(1);
        reqDTO.setSalaryRangeId(2);
        reqDTO.setTechStackIds(List.of(1, 2));

        String requestBody = om.writeValueAsString(reqDTO);
        System.out.println(requestBody);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/s/api/job")
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
        actions.andExpect(jsonPath("$.body.id").value(51));
        actions.andExpect(jsonPath("$.body.title").value("백엔드 개발자 모집"));
        actions.andExpect(jsonPath("$.body.description").value("Spring Boot 기반 서비스 개발"));
        actions.andExpect(jsonPath("$.body.location").value("서울특별시 강남구 테헤란로"));
        actions.andExpect(jsonPath("$.body.employmentType").value("정규직"));
        actions.andExpect(jsonPath("$.body.deadline").value("2025-12-31"));
        actions.andExpect(jsonPath("$.body.status").value("OPEN"));
        actions.andExpect(jsonPath("$.body.careerLevel").value("신입"));
        actions.andExpect(jsonPath("$.body.jobGroupId").value(1));
        actions.andExpect(jsonPath("$.body.workFieldId").value(1));
        actions.andExpect(jsonPath("$.body.salaryRangeId").value(2));
        actions.andExpect(jsonPath("$.body.techStackIds[0]").value(1));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void update_test() throws Exception {
        // given
        Integer jobId = 1;

        JobRequest.UpdateDTO reqDTO = new JobRequest.UpdateDTO();
        reqDTO.setTitle("백엔드 개발자 채용 수정");
        reqDTO.setDescription("Spring 기반 백엔드 개발");
        reqDTO.setLocation("서울시 마포구 독막로");
        reqDTO.setEmploymentType("계약직");
        reqDTO.setDeadline("2026-01-31");
        reqDTO.setStatus("CLOSED");
        reqDTO.setJobGroupId(1);
        reqDTO.setWorkFieldId(1);
        reqDTO.setCareerLevel("경력");
        reqDTO.setSalaryRangeId(2);
        reqDTO.setTechStackIds(List.of(1, 2));

        String requestBody = om.writeValueAsString(reqDTO);
        System.out.println("requestBody = " + requestBody);
        System.out.println("jobId" + jobId);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .put("/s/api/job/" + jobId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.title").value("백엔드 개발자 채용 수정"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.description").value("Spring 기반 백엔드 개발"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.location").value("서울시 마포구 독막로"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.employmentType").value("계약직"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.deadline").value("2026-01-31"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.status").value("CLOSED"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobGroupId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.workFieldId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.careerLevel").value("경력"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.salaryRangeId").value(2));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.techStackIds[0]").value(1));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void list_test() throws Exception {
        // given
        Integer page = 1;
        String keyword = "백엔드";

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/job")
                        .param("page", page.toString())
                        .param("keyword", keyword)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[0].jobId").value(50));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[0].title").value("공공 헬스케어 데이터 분석가"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[0].careerLevel").value("경력"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[0].companyName").value("시티헬스"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[0].bookmarkId").value(Matchers.nullValue()));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[1].jobId").value(49));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[1].title").value("스마트 건설 프로젝트 매니저"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[1].careerLevel").value("경력"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[1].companyName").value("스마트인프라"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[1].bookmarkId").value(Matchers.nullValue()));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[2].jobId").value(48));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[2].title").value("스마트 홈케어 서비스 매니저"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[2].careerLevel").value("신입"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[2].companyName").value("클린에너지"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobs[2].bookmarkId").value(Matchers.nullValue()));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.bookmarkCount").value(0));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void detail_test() throws Exception {
        // given
        Integer jobId = 1;

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/job/" + jobId)
                        .header("Authorization", "Bearer " + accessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.title").value("AI 백엔드 개발자 모집"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.description").value(Matchers.startsWith("AI 기반 소프트웨어 플랫폼의 백엔드 시스템 개발을 담당할 인재를 찾습니다")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.location").value("서울특별시 강남구 테헤란로"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.employmentType").value("정규직"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.deadline").value("2025-12-31"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.status").value("OPEN"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.careerLevel").value("경력"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applications[0].createdAt",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.updatedAt").value(Matchers.nullValue()));

        // company 정보
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.company.companyId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.company.name").value("에이아이랩"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.company.phone").value("02-1111-1111"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.company.contactManager").value("박지현"));

        // salaryRange 정보
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.salaryRange.salaryRangeId").value(4));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.salaryRange.minSalary").value(6000));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.salaryRange.maxSalary").value(7000));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.salaryRange.label").value("6000-7000"));

        // workField
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.workField.id").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.workField.name").value("IT/소프트웨어"));

        // jobGroup
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobGroup.id").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobGroup.name").value("백엔드 개발자"));

        // techStacks
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.techStacks[0].techStackId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.techStacks[0].name").value("Java"));

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobBookmarkId").value(Matchers.nullValue()));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.owner").value(false));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @Test
    public void delete_test() throws Exception {
        // given
        Integer jobId = 1;

        // 삭제 전 북마크 삭제
        em.createQuery("DELETE FROM JobBookmark jb WHERE jb.job.id = :jobId")
                .setParameter("jobId", jobId)
                .executeUpdate();

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .delete("/s/api/job/" + jobId)
                        .header("Authorization", "Bearer " + accessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body").value(Matchers.nullValue()));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }
}
