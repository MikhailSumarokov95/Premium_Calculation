package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "premium_limit")
public class PremiumLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal maxTotalPremium;

    public PremiumLimit() {
    }

    public PremiumLimit(Long id, BigDecimal maxTotalPremium) {
        this.id = id;
        this.maxTotalPremium = maxTotalPremium;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMaxTotalPremium() {
        return maxTotalPremium;
    }

    public void setMaxTotalPremium(BigDecimal maxTotalPremium) {
        this.maxTotalPremium = maxTotalPremium;
    }
}
