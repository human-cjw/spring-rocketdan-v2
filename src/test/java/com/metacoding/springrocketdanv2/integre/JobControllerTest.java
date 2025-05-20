package com.metacoding.springrocketdanv2.integre;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metacoding.springrocketdanv2.MyRestDoc;
import com.metacoding.springrocketdanv2._core.util.JwtUtil;
import com.metacoding.springrocketdanv2.job.JobRequest;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    }
}
