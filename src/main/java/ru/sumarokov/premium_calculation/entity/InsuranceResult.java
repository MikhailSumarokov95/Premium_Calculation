package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "insurance_result")
public class InsuranceResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalBonus;
    private BigDecimal penetration;
    private BigDecimal sumInsuranceVolume;
    @OneToOne()
    @MapsId
    @JoinColumn(name = "users_id")
    private User user;

    public InsuranceResult() {
    }

    public InsuranceResult(User user) {
        this.user = user;
    }

    public InsuranceResult(BigDecimal totalBonus,
                           BigDecimal penetration,
                           BigDecimal sumInsuranceVolume,
                           User user) {
        this.totalBonus = totalBonus;
        this.penetration = penetration;
        this.sumInsuranceVolume = sumInsuranceVolume;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalBonus() {
        return totalBonus;
    }

    public void setTotalBonus(BigDecimal totalBonus) {
        this.totalBonus = totalBonus;
    }

    public BigDecimal getPenetration() {
        return penetration;
    }

    public void setPenetration(BigDecimal penetration) {
        this.penetration = penetration;
    }

    public BigDecimal getSumInsuranceVolume() {
        return sumInsuranceVolume;
    }

    public void setSumInsuranceVolume(BigDecimal sumInsuranceVolume) {
        this.sumInsuranceVolume = sumInsuranceVolume;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
