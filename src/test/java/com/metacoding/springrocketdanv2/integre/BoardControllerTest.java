package com.metacoding.springrocketdanv2.integre;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metacoding.springrocketdanv2.MyRestDoc;
import com.metacoding.springrocketdanv2.board.BoardRequest;
import org.junit.jupiter.api.AfterEach;
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
public class BoardControllerTest extends MyRestDoc {

    @Autowired
    private ObjectMapper om;

    @AfterEach
    public void tearDown() { // 끝나고 나서 마무리 함수
        // 테스트 후 정리할 코드
        System.out.println("tearDown");
    }

    @Test
    public void verify_password_test() throws Exception {
        // given
        Integer boardId = 1;
        BoardRequest.VerifyDTO reqDTO = new BoardRequest.VerifyDTO();
        reqDTO.setPassword("1234");

        String requestBody = om.writeValueAsString(reqDTO);
        System.out.println(requestBody);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/api/check-board-password/{boardId}", boardId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.success").value(true));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.message").value("비밀번호가 맞습니다."));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void list_test() throws Exception {
        // given

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/board")
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.boards.length()").value(5));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.boards[0].title").value("프로젝트 팀원 모집"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.boards[1].title").value("자바 공부 방법"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.boards[2].title").value("부산 개발자 모임 안내"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.boards[3].title").value("스프링 프로젝트 질문"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.boards[4].title").value("첫 번째 게시글"));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void write_board_test() throws Exception {
        // given
        BoardRequest.SaveDTO reqDTO = new BoardRequest.SaveDTO();
        reqDTO.setTitle("새로운 글 제목");
        reqDTO.setContent("새로운 글 내용입니다.");
        reqDTO.setPassword("1234");

        String requestBody = om.writeValueAsString(reqDTO);
        System.out.println(requestBody);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/api/board")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(6));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.title").value("새로운 글 제목"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.content").value("새로운 글 내용입니다."));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    public void update_test() throws Exception {
        // given
        Integer boardId = 1;
        BoardRequest.UpdateDTO reqDTO = new BoardRequest.UpdateDTO();
        reqDTO.setTitle("테스트 글제목");
        reqDTO.setContent("테스트하는 글 내용입니다.");

        String requestBody = om.writeValueAsString(reqDTO);
        System.out.println(requestBody);

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .put("/api/board/{boardId}", boardId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.title").value("테스트 글제목"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.content").value("테스트하는 글 내용입니다."));
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }

    @Test
    public void delete_test() throws Exception {
        // given
        Integer boardId = 1;

        // when
        ResultActions actions = mvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/board/{boardId}", boardId)
        );

        // eye
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body").doesNotExist()); // 또는 .value(null)
        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
