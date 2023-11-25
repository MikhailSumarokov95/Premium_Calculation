package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "efficiency")
public class Efficiency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalPremium;
    private BigDecimal premiumForCredits;
    private BigDecimal furBonus;
    private BigDecimal totalProductivity;
    private BigDecimal premiumInsurance;
    @OneToOne()
    @JoinColumn(name = "users_id")
    private User user;

    public Efficiency() {
    }

    public Efficiency(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPremium() {
        return totalPremium;
    }

    public void setTotalPremium(BigDecimal totalPremium) {
        this.totalPremium = totalPremium;
    }

    public BigDecimal getPremiumForCredits() {
        return premiumForCredits;
    }

    public void setPremiumForCredits(BigDecimal premiumForCredits) {
        this.premiumForCredits = premiumForCredits;
    }

    public BigDecimal getFurBonus() {
        return furBonus;
    }

    public void setFurBonus(BigDecimal furBonus) {
        this.furBonus = furBonus;
    }

    public BigDecimal getTotalProductivity() {
        return totalProductivity;
    }

    public void setTotalProductivity(BigDecimal totalProductivity) {
        this.totalProductivity = totalProductivity;
    }

    public BigDecimal getPremiumInsurance() {
        return premiumInsurance;
    }

    public void setPremiumInsurance(BigDecimal premiumInsurance) {
        this.premiumInsurance = premiumInsurance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
