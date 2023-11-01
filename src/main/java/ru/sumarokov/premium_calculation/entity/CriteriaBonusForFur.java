package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "criteria_bonus_for_fur")
public class CriteriaBonusForFur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal minSum;
    private BigDecimal minSms;
    private BigDecimal bonus;

    public CriteriaBonusForFur() {
    }

    public CriteriaBonusForFur(Long id, BigDecimal minSum, BigDecimal minSms, BigDecimal bonus) {
        this.id = id;
        this.minSum = minSum;
        this.minSms = minSms;
        this.bonus = bonus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMinSum() {
        return minSum;
    }

    public void setMinSum(BigDecimal minSum) {
        this.minSum = minSum;
    }

    public BigDecimal getMinSms() {
        return minSms;
    }

    public void setMinSms(BigDecimal minSms) {
        this.minSms = minSms;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }
}
