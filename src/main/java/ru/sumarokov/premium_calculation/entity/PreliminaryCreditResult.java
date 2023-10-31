package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "preliminary_credit_result")
public class PreliminaryCreditResult {

    @Id
    @Column(name = "credit_id")
    private Long id;
    private Double premium;
    private Double creditTotal;
    private Double insuranceBonus;
    private Double insuranceVolume;
    private Double creditPreviously;

    @OneToOne()
    @MapsId
    @JoinColumn(name = "credit_id")
    private Credit credit;

    public PreliminaryCreditResult() {
    }

    public PreliminaryCreditResult(Long id, Double premium, Double creditTotal,
                                   Double insuranceBonus, Double insuranceVolume,
                                   Double creditPreviously, Credit credit) {
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

    public Double getPremium() {
        return premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }

    public Double getCreditTotal() {
        return creditTotal;
    }

    public void setCreditTotal(Double creditTotal) {
        this.creditTotal = creditTotal;
    }

    public Double getInsuranceBonus() {
        return insuranceBonus;
    }

    public void setInsuranceBonus(Double insuranceBonus) {
        this.insuranceBonus = insuranceBonus;
    }

    public Double getInsuranceVolume() {
        return insuranceVolume;
    }

    public void setInsuranceVolume(Double insuranceVolume) {
        this.insuranceVolume = insuranceVolume;
    }

    public Double getCreditPreviously() {
        return creditPreviously;
    }

    public void setCreditPreviously(Double creditPreviously) {
        this.creditPreviously = creditPreviously;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }


}
