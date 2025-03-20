package kr.co.polycube.backendtest.globalExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    // 잘못된 인자(Exception) 처리: HTTP 400 상태와 함께 {"reason": "에러 메시지"} 응답 반환
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("reason", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 해당 엔드포인트를 찾을 수 없는 경우(NoHandlerFoundException): HTTP 404 상태와 함께 응답 반환
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("reason", "해당 엔드포인트를 찾을 수 없습니다: " + ex.getRequestURL());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 그 외 모든 예외(Exception) 처리: 기본적으로 HTTP 400 상태와 함께 응답 반환
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("reason", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}