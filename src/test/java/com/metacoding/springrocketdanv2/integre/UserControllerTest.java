package com.metacoding.springrocketdanv2.integre;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metacoding.springrocketdanv2.MyRestDoc;
import com.metacoding.springrocketdanv2._core.util.JwtUtil;
import com.metacoding.springrocketdanv2.user.User;
import com.metacoding.springrocketdanv2.user.UserRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.matchesPattern;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;

    private String accessToken;

    @BeforeEach
    public void setUp() {
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
    public void join_test() throws Exception {
        // given
        UserRequest.JoinDTO reqDTO = new UserRequest.JoinDTO();
        reqDTO.setUsername("newuser");
        reqDTO.setPassword("1234");
        reqDTO.setEmail("newuser@example.com");

        String requestBody = om.writeValueAsString(reqDTO);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/join")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.userId").value(101));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.username").value("newuser"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.email").value("newuser@example.com"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.fileUrl").doesNotExist()); // 또는 nullValue()도 가능
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.userType").value("user"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.companyId").value(Matchers.nullValue()));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.createdAt",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @Test
    public void encode_test() {
        // $2a$10$FK.8elgcVFKEhK2wjTkZbe6ZKek69/oILzwBU4fu7vbovjEfqGWs2
        String password = "1234";

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(encPassword);
    }

    @Test
    public void login_test() throws Exception {
        // given
        UserRequest.LoginDTO reqDTO = new UserRequest.LoginDTO();
        reqDTO.setUsername("user01");
        reqDTO.setPassword("1234");

        String requestBody = om.writeValueAsString(reqDTO);
        System.out.println(requestBody);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.token.accessToken",
                matchesPattern("^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.token.refreshToken",
                matchesPattern("^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$")));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void list_test() throws Exception {
        // given
        String status = "접수";

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/s/api/user/application")
                        .param("status", status)
                        .header("Authorization", "Bearer " + accessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applications[0].applicationId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applications[0].status").value("접수"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applications[0].createdAt",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applications[0].jobId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applications[0].jobTitle").value("AI 백엔드 개발자 모집"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applications[0].companyName").value("에이아이랩"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applications[0].careerLevel").value("경력"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applications[0].resumeId").value(1));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void userApplication_test() throws Exception {
        // given
        Integer applicationId = 1;

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/s/api/user/application/{applicationId}", applicationId)
                        .header("Authorization", "Bearer " + accessToken)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.applicationId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.createdAt",
                matchesPattern("\\d{4}-\\d{2}-\\d{2}")));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.status").value("접수"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.resumeId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.resumeTitle").value("백엔드 개발자 이력서"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.companyName").value("에이아이랩"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.careerLevel").value("경력"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.jobTitle").value("AI 백엔드 개발자 모집"));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}