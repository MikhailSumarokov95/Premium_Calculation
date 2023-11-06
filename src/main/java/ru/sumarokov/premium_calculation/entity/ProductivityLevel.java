package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "productivity_level")
public class ProductivityLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal premium;
    private Integer minCountCredits;
    private BigDecimal minSumAmountCredits;
    private BigDecimal minSms;
    private BigDecimal minInsurance;

    public ProductivityLevel() {
    }

    public ProductivityLevel(Long id, String name, BigDecimal premium,
                             Integer minCountCredits, BigDecimal minSumAmountCredits,
                             BigDecimal minSms, BigDecimal minInsurance) {
        this.id = id;
        this.name = name;
        this.premium = premium;
        this.minCountCredits = minCountCredits;
        this.minSumAmountCredits = minSumAmountCredits;
        this.minSms = minSms;
        this.minInsurance = minInsurance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public Integer getMinCountCredits() {
        return minCountCredits;
    }

    public void setMinCountCredits(Integer minCountCredits) {
        this.minCountCredits = minCountCredits;
    }

    public BigDecimal getMinSumAmountCredits() {
        return minSumAmountCredits;
    }

    public void setMinSumAmountCredits(BigDecimal minSumAmountCredits) {
        this.minSumAmountCredits = minSumAmountCredits;
    }

    public BigDecimal getMinSms() {
        return minSms;
    }

    public void setMinSms(BigDecimal minSms) {
        this.minSms = minSms;
    }

    public BigDecimal getMinInsurance() {
        return minInsurance;
    }

    public void setMinInsurance(BigDecimal minInsurance) {
        this.minInsurance = minInsurance;
    }
}
