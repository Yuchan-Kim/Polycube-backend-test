package kr.co.polycube.backendtest.controller;

import kr.co.polycube.backendtest.model.User;
import kr.co.polycube.backendtest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService){
         this.userService = userService;
    }
    
    // 사용자 등록 API: POST /users, 요청 JSON 예: {"name": "사용자이름"}, 응답: {"id": 사용자id}
    @PostMapping
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody Map<String, String> body) {
         String name = body.get("name");
         if (name == null || name.trim().isEmpty()){
              throw new IllegalArgumentException("사용자 이름은 필수입니다.");
         }
         User user = userService.createUser(name);
         return ResponseEntity.ok(Map.of("id", user.getId()));
    }
    
    // 사용자 조회 API: GET /users/{id}, 응답: {"id": 사용자id, "name": "사용자이름"}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
         User user = userService.getUser(id)
                     .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
         return ResponseEntity.ok(user);
    }
    
    // 사용자 수정 API: PUT /users/{id}, 요청 JSON 예: {"name": "새사용자이름"}, 응답: 업데이트된 사용자 정보
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody Map<String, String> body) {
         String name = body.get("name");
         if (name == null || name.trim().isEmpty()){
              throw new IllegalArgumentException("새 사용자 이름은 필수입니다.");
         }
         User user = userService.updateUser(id, name);
         return ResponseEntity.ok(user);
    }
}