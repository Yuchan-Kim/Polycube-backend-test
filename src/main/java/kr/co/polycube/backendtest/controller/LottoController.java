package kr.co.polycube.backendtest.controller;

import kr.co.polycube.backendtest.model.Lotto;
import kr.co.polycube.backendtest.service.LottoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lottos")
public class LottoController {
    private final LottoService lottoService;
    
    public LottoController(LottoService lottoService) {
        this.lottoService = lottoService;
    }
    
    @PostMapping
    public ResponseEntity<Map<String, List<Integer>>> generateLottoNumbers() {
        List<Integer> numbers = lottoService.generateLottoNumbers();
        lottoService.saveLottoNumbers(numbers);
        return ResponseEntity.ok(Map.of("numbers", numbers));
    }
}