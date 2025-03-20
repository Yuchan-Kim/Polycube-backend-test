package kr.co.polycube.backendtest.service;

import kr.co.polycube.backendtest.model.Lotto;
import kr.co.polycube.backendtest.model.Winner;
import kr.co.polycube.backendtest.repository.LottoRepository;
import kr.co.polycube.backendtest.repository.WinnerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class LottoService {
    private final LottoRepository lottoRepository;
    private final WinnerRepository winnerRepository;
    private final Random random = new Random();

    public LottoService(LottoRepository lottoRepository, WinnerRepository winnerRepository) {
        this.lottoRepository = lottoRepository;
        this.winnerRepository = winnerRepository;
    }

    // 로또 번호 발급
    public List<Integer> generateLottoNumbers() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 45; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers.subList(0, 6);
    }

    // 로또 번호 저장
    public Lotto saveLottoNumbers(List<Integer> numbers) {
        Collections.sort(numbers);
        Lotto lotto = new Lotto(
            numbers.get(0),
            numbers.get(1),
            numbers.get(2),
            numbers.get(3),
            numbers.get(4),
            numbers.get(5)
        );
        return lottoRepository.save(lotto);
    }

    // 당첨 확인 (일치하는 번호 개수에 따라 등수 결정)
    public String checkWinningRank(Lotto userLotto, Lotto winningLotto) {
        int[] userNumbers = {userLotto.getNumber1(), userLotto.getNumber2(), userLotto.getNumber3(),
                           userLotto.getNumber4(), userLotto.getNumber5(), userLotto.getNumber6()};
        int[] winningNumbers = {winningLotto.getNumber1(), winningLotto.getNumber2(), winningLotto.getNumber3(),
                              winningLotto.getNumber4(), winningLotto.getNumber5(), winningLotto.getNumber6()};
        
        int matchCount = 0;
        for (int userNumber : userNumbers) {
            for (int winningNumber : winningNumbers) {
                if (userNumber == winningNumber) {
                    matchCount++;
                    break;
                }
            }
        }
        
        switch (matchCount) {
            case 6: return "1등";
            case 5: return "2등";
            case 4: return "3등";
            case 3: return "4등";
            case 2: return "5등";
            default: return "미당첨";
        }
    }

    // 로또 당첨자 검수 Batch 처리
    public void processLottoWinners() {
        // 당첨 번호 생성 (임의로 생성)
        List<Integer> winningNumbers = generateLottoNumbers();
        Lotto winningLotto = saveLottoNumbers(winningNumbers);
        
        // 모든 로또 번호 조회
        List<Lotto> allLottos = lottoRepository.findAll();
        
        // 각 로또 번호의 당첨 여부 확인 및 결과 저장
        for (Lotto lotto : allLottos) {
            // 당첨 번호와 비교하지 않음
            if (lotto.getId().equals(winningLotto.getId())) {
                continue;
            }
            
            String rank = checkWinningRank(lotto, winningLotto);
            
            // 당첨된 경우에만 저장 (1등 ~ 5등)
            if (!rank.equals("미당첨")) {
                Winner winner = new Winner(lotto, rank);
                winnerRepository.save(winner);
            }
        }
    }
}