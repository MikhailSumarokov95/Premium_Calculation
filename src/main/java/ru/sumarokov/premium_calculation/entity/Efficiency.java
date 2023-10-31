package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;
import ru.sumarokov.premium_calculation.helper.ProductivityLevel;

@Entity
@Table(name = "efficiency")
public class Efficiency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ProductivityLevel productivityLevel;
    private Double totalPremium;
    private Double premiumForCredits;
    private Double furBonus;
    private Double totalProductivity;
    private Double premiumInsurance;
    private Double premiumForAdditionalProducts;

    public Efficiency() {
    }

    public Efficiency(Long id, ProductivityLevel productivityLevel, Double totalPremium,
                      Double premiumForCredits, Double furBonus, Double totalProductivity,
                      Double premiumInsurance, Double premiumForAdditionalProducts) {
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

    public Double getTotalPremium() {
        return totalPremium;
    }

    public void setTotalPremium(Double totalPremium) {
        this.totalPremium = totalPremium;
    }

    public Double getPremiumForCredits() {
        return premiumForCredits;
    }

    public void setPremiumForCredits(Double premiumForCredits) {
        this.premiumForCredits = premiumForCredits;
    }

    public Double getFurBonus() {
        return furBonus;
    }

    public void setFurBonus(Double furBonus) {
        this.furBonus = furBonus;
    }

    public Double getTotalProductivity() {
        return totalProductivity;
    }

    public void setTotalProductivity(Double totalProductivity) {
        this.totalProductivity = totalProductivity;
    }

    public Double getPremiumInsurance() {
        return premiumInsurance;
    }

    public void setPremiumInsurance(Double premiumInsurance) {
        this.premiumInsurance = premiumInsurance;
    }

    public Double getPremiumForAdditionalProducts() {
        return premiumForAdditionalProducts;
    }

    public void setPremiumForAdditionalProducts(Double premiumForAdditionalProducts) {
        this.premiumForAdditionalProducts = premiumForAdditionalProducts;
    }
}
