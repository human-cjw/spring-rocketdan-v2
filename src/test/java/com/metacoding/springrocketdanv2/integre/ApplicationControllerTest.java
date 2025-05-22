package com.metacoding.springrocketdanv2.integre;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metacoding.springrocketdanv2.MyRestDoc;
import com.metacoding.springrocketdanv2._core.util.JwtUtil;
import com.metacoding.springrocketdanv2.application.ApplicationRequest;
import com.metacoding.springrocketdanv2.user.User;
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

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ApplicationControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;

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
                // 컴퍼니 아이디를 넣어야 합니다
                .companyId(1)
                .build();
        companyAccessToken = JwtUtil.create(company01);
    }


    @AfterEach
    public void tearDown() { // 끝나고 나서 마무리 함수
        // 테스트 후 정리할 코드
        System.out.println("tearDown");
    }

    @Test
    public void save_test() throws Exception {
        // given
        ApplicationRequest.SaveDTO reqDTO = new ApplicationRequest.SaveDTO();
        reqDTO.setJobId(3);
        reqDTO.setResumeId(1);

        String requestBody = om.writeValueAsString(reqDTO);
        System.out.println(requestBody);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/s/api/application")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userAccessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applicationId").value(101));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.status").value("접수"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.createdAt").value("2025-05-21"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobId").value(3));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.resumeId").value(1));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void update_status_test() throws Exception {

        // given
        Integer applicationId = 1;
        ApplicationRequest.UpdateDTO respDTO = new ApplicationRequest.UpdateDTO();
        respDTO.setStatus("검토");
        String requestBody = om.writeValueAsString(respDTO);
        System.out.println(requestBody);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders.put("/s/api/application/{applicationId}/status", applicationId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + companyAccessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applicationId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.status").value("검토"));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
