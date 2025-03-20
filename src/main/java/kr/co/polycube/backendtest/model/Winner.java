package kr.co.polycube.backendtest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "winner")
public class Winner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "lotto_id")
    private Lotto lotto;
    
    private String rank;
    
    public Winner() {
    }
    
    public Winner(Lotto lotto, String rank) {
        this.lotto = lotto;
        this.rank = rank;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Lotto getLotto() {
        return lotto;
    }
    
    public void setLotto(Lotto lotto) {
        this.lotto = lotto;
    }
    
    public String getRank() {
        return rank;
    }
    
    public void setRank(String rank) {
        this.rank = rank;
    }
}