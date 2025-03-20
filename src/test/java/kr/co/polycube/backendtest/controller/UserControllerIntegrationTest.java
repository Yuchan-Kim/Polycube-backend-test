package kr.co.polycube.backendtest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.polycube.backendtest.BackendTestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = BackendTestApplication.class)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // 사용자 등록 및 조회 API 테스트
    @Test
    public void testRegisterAndGetUser() throws Exception {
        // 1. 사용자 등록 테스트
        String registerJson = "{\"name\":\"홍길동\"}";
        String response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        // 응답에서 id 추출
        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
        Number idNumber = (Number) responseMap.get("id");
        Long userId = idNumber.longValue();
        
        // 2. 사용자 조회 테스트
        mockMvc.perform(get("/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("홍길동"));
    }
    
    // 사용자 수정 API 테스트
    @Test
    public void testUpdateUser() throws Exception {
        // 1. 먼저 사용자 등록
        String registerJson = "{\"name\":\"김철수\"}";
        String response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
        Number idNumber = (Number) responseMap.get("id");
        Long userId = idNumber.longValue();
        
        // 2. 등록된 사용자 정보 수정
        String updateJson = "{\"name\":\"김철수(수정)\"}";
        mockMvc.perform(put("/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("김철수(수정)"));
        
        // 3. 수정된 정보 확인
        mockMvc.perform(get("/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("김철수(수정)"));
    }
    
    // URL 필터 테스트 - 허용되지 않는 특수문자가 포함된 URL
    @Test
    public void testUrlFilter() throws Exception {
        // 1. 먼저 사용자 등록
        String registerJson = "{\"name\":\"이영희\"}";
        String response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
        Number idNumber = (Number) responseMap.get("id");
        Long userId = idNumber.longValue();
        
        // 2. URL에 허용되지 않는 특수문자(!)가 포함된 요청은 400 Bad Request 응답
        mockMvc.perform(get("/users/" + userId + "?name=test!!"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason").exists());
    }
} 