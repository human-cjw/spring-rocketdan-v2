package com.metacoding.springrocketdanv2.integre;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metacoding.springrocketdanv2.MyRestDoc;
import com.metacoding.springrocketdanv2._core.util.JwtUtil;
import com.metacoding.springrocketdanv2.company.CompanyRequest;
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

import java.util.List;

import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.nullValue;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CompanyController extends MyRestDoc {

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
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.techStacks[0].techStackId").value(1));
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
        reqDTO.setPhone("01011111111"); // '-' 없이 11자리
        reqDTO.setCeo("테스터");
        reqDTO.setAddress("서울특별시 강남구 테헤란로");
        reqDTO.setWorkFieldId(1); // 예: IT/소프트웨어

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
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.companyId").value(51));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.nameKr").value("테스트기업"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.nameEn").value("testCompany"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.ceo").value("테스터"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.businessNumber").value("1001001111"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.email").value("test@test.com"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.phone").value("01011111111"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.address").value("서울특별시 강남구 테헤란로"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.introduction").value("테스트 기업 설명란"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.oneLineIntro").value("테스트 기업 설명란"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.homepageUrl").value(nullValue()));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.logoImageUrl").value(nullValue()));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.infoImageUrl").value(nullValue()));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.contactManager").value("테스트매니저"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.startDate").value("2099-03-01"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.createdAt").value("2025-05-20"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.workFieldId").value(1));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.techStackIds[0]").value(1));
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
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("성공"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.companyId").value(51));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.nameKr").value("테스트기업"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.nameEn").value("testCompany"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.ceo").value("테스터"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.businessNumber").value("1001001111"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.email").value("test@test.com"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.phone").value("01011111111"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.address").value("서울특별시 강남구 테헤란로"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.introduction").value("테스트 기업 설명란"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.oneLineIntro").value("테스트 기업 설명란"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.homepageUrl").value(nullValue()));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.logoImageUrl").value(nullValue()));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.infoImageUrl").value(nullValue()));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.contactManager").value("테스트매니저"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.startDate").value("2099-03-01"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.createdAt").value("2025-05-20"));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.workFieldId").value(1));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.techStackIds[0]").value(1));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.token.accessToken",
//                matchesPattern("^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$")));
//        actions.andExpect(MockMvcResultMatchers.jsonPath("$.body.token.refreshToken",
//                matchesPattern("^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$")));
//        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}
