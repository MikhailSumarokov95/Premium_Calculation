package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "preliminary_credit_result")
public class PreliminaryCreditResult {

    @Id
    @Column(name = "credit_id")
    private Long id;
    private BigDecimal premium;
    private BigDecimal creditTotal;
    private BigDecimal insuranceBonus;
    private BigDecimal insuranceVolume;
    private BigDecimal creditPreviously;

    @OneToOne()
    @MapsId
    @JoinColumn(name = "credit_id")
    private Credit credit;

    public PreliminaryCreditResult() {
    }

    public PreliminaryCreditResult(Long id, BigDecimal premium, BigDecimal creditTotal,
                                   BigDecimal insuranceBonus, BigDecimal insuranceVolume,
                                   BigDecimal creditPreviously, Credit credit) {
        this.id = id;
        this.premium = premium;
        this.creditTotal = creditTotal;
        this.insuranceBonus = insuranceBonus;
        this.insuranceVolume = insuranceVolume;
        this.creditPreviously = creditPreviously;
        this.credit = credit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public BigDecimal getCreditTotal() {
        return creditTotal;
    }

    public void setCreditTotal(BigDecimal creditTotal) {
        this.creditTotal = creditTotal;
    }

    public BigDecimal getInsuranceBonus() {
        return insuranceBonus;
    }

    public void setInsuranceBonus(BigDecimal insuranceBonus) {
        this.insuranceBonus = insuranceBonus;
    }

    public BigDecimal getInsuranceVolume() {
        return insuranceVolume;
    }

    public void setInsuranceVolume(BigDecimal insuranceVolume) {
        this.insuranceVolume = insuranceVolume;
    }

    public BigDecimal getCreditPreviously() {
        return creditPreviously;
    }

    public void setCreditPreviously(BigDecimal creditPreviously) {
        this.creditPreviously = creditPreviously;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }


}
