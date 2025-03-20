package kr.co.polycube.backendtest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "lotto")
public class Lotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int number1;
    private int number2;
    private int number3;
    private int number4;
    private int number5;
    private int number6;
    
    public Lotto() {
    }
    
    public Lotto(int number1, int number2, int number3, int number4, int number5, int number6) {
        this.number1 = number1;
        this.number2 = number2;
        this.number3 = number3;
        this.number4 = number4;
        this.number5 = number5;
        this.number6 = number6;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public int getNumber1() {
        return number1;
    }
    
    public void setNumber1(int number1) {
        this.number1 = number1;
    }
    
    public int getNumber2() {
        return number2;
    }
    
    public void setNumber2(int number2) {
        this.number2 = number2;
    }
    
    public int getNumber3() {
        return number3;
    }
    
    public void setNumber3(int number3) {
        this.number3 = number3;
    }
    
    public int getNumber4() {
        return number4;
    }
    
    public void setNumber4(int number4) {
        this.number4 = number4;
    }
    
    public int getNumber5() {
        return number5;
    }
    
    public void setNumber5(int number5) {
        this.number5 = number5;
    }
    
    public int getNumber6() {
        return number6;
    }
    
    public void setNumber6(int number6) {
        this.number6 = number6;
    }
}