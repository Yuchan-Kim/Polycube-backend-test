package kr.co.polycube.backendtest.config;

import kr.co.polycube.backendtest.service.LottoService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class BatchConfig {
    private final LottoService lottoService;
    
    public BatchConfig(LottoService lottoService) {
        this.lottoService = lottoService;
    }
    
    // 매주 일요일 0시에 실행 (일요일 = 0, 0시 0분 0초)
    @Scheduled(cron = "0 0 0 * * 0")
    public void runLottoWinnerBatch() {
        lottoService.processLottoWinners();
    }
}