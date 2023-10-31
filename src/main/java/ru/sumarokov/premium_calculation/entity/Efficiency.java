package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;
import ru.sumarokov.premium_calculation.helper.ProductivityLevel;

import java.math.BigDecimal;

@Entity
@Table(name = "efficiency")
public class Efficiency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ProductivityLevel productivityLevel;
    private BigDecimal totalPremium;
    private BigDecimal premiumForCredits;
    private BigDecimal furBonus;
    private BigDecimal totalProductivity;
    private BigDecimal premiumInsurance;
    private BigDecimal premiumForAdditionalProducts;

    public Efficiency() {
    }

    public Efficiency(Long id, ProductivityLevel productivityLevel, BigDecimal totalPremium,
                      BigDecimal premiumForCredits, BigDecimal furBonus, BigDecimal totalProductivity,
                      BigDecimal premiumInsurance, BigDecimal premiumForAdditionalProducts) {
        this.id = id;
        this.productivityLevel = productivityLevel;
        this.totalPremium = totalPremium;
        this.premiumForCredits = premiumForCredits;
        this.furBonus = furBonus;
        this.totalProductivity = totalProductivity;
        this.premiumInsurance = premiumInsurance;
        this.premiumForAdditionalProducts = premiumForAdditionalProducts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductivityLevel getProductivityLevel() {
        return productivityLevel;
    }

    public void setProductivityLevel(ProductivityLevel productivityLevel) {
        this.productivityLevel = productivityLevel;
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

    public BigDecimal getPremiumForAdditionalProducts() {
        return premiumForAdditionalProducts;
    }

    public void setPremiumForAdditionalProducts(BigDecimal premiumForAdditionalProducts) {
        this.premiumForAdditionalProducts = premiumForAdditionalProducts;
    }
}
