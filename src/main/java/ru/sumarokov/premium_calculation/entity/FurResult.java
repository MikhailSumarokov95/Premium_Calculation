package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "fur_result")
public class FurResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal bonus;
    private Integer countCreditsCategoryFur;
    private Integer countCreditsCategoryFurWithSms;
    private BigDecimal shareCreditsCategoryFurWithSms;
    private BigDecimal sumAmountCreditsCategoryFur;

    public FurResult() {
    }

    public FurResult(BigDecimal bonus, Integer countCreditsCategoryFur, Integer countCreditsCategoryFurWithSms,
                     BigDecimal shareCreditsCategoryFurWithSms, BigDecimal sumAmountCreditsCategoryFur) {
        this.bonus = bonus;
        this.countCreditsCategoryFur = countCreditsCategoryFur;
        this.countCreditsCategoryFurWithSms = countCreditsCategoryFurWithSms;
        this.shareCreditsCategoryFurWithSms = shareCreditsCategoryFurWithSms;
        this.sumAmountCreditsCategoryFur = sumAmountCreditsCategoryFur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    public Integer getCountCreditsCategoryFur() {
        return countCreditsCategoryFur;
    }

    public void setCountCreditsCategoryFur(Integer countCreditsCategoryFur) {
        this.countCreditsCategoryFur = countCreditsCategoryFur;
    }

    public Integer getCountCreditsCategoryFurWithSms() {
        return countCreditsCategoryFurWithSms;
    }

    public void setCountCreditsCategoryFurWithSms(Integer countCreditsCategoryFurWithSms) {
        this.countCreditsCategoryFurWithSms = countCreditsCategoryFurWithSms;
    }

    public BigDecimal getShareCreditsCategoryFurWithSms() {
        return shareCreditsCategoryFurWithSms;
    }

    public void setShareCreditsCategoryFurWithSms(BigDecimal shareCreditsCategoryFurWithSms) {
        this.shareCreditsCategoryFurWithSms = shareCreditsCategoryFurWithSms;
    }

    public BigDecimal getSumAmountCreditsCategoryFur() {
        return sumAmountCreditsCategoryFur;
    }

    public void setSumAmountCreditsCategoryFur(BigDecimal sumAmountCreditsCategoryFur) {
        this.sumAmountCreditsCategoryFur = sumAmountCreditsCategoryFur;
    }
}
