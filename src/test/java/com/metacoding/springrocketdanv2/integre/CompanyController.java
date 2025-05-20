package com.metacoding.springrocketdanv2.integre;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metacoding.springrocketdanv2.MyRestDoc;
import com.metacoding.springrocketdanv2._core.util.JwtUtil;
import com.metacoding.springrocketdanv2.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CompanyController extends MyRestDoc {

    @Autowired
    private ObjectMapper om;

    private String accessToken;

    @BeforeEach
    public void setUp() {
        // 테스트 시작 전에 실행할 코드
        System.out.println("setUp");
        User ssar = User.builder()
                .id(1)
                .username("ssar")
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
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(4));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.title").value("제목4"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.content").value("내용4"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.isPublic").value(true));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.isBoardOwner").value(false));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.isLove").value(false));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.loveCount").value(2));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.username").value("love"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.loveId").value(nullValue()));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.replies[0].id").value(3));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.replies[0].content").value("댓글3"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.replies[0].username").value("ssar"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.replies[0].isReplyOwner").value(false));
//        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
