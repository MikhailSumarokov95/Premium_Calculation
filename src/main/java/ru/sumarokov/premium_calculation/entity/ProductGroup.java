package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "product_group")
public class ProductGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double factorPremium;
    private Double minPremium;
    private Double maxPremium;
    private Boolean isCoc;
    private Double minAmountForCalculatingCreditPremium;

    public ProductGroup() {
    }

    public ProductGroup(Long id, String name, Double factorPremium,
                        Double minPremium, Double maxPremium, Boolean isCoc,
                        Double minAmountForCalculatingCreditPremium) {
        this.id = id;
        this.name = name;
        this.factorPremium = factorPremium;
        this.minPremium = minPremium;
        this.maxPremium = maxPremium;
        this.isCoc = isCoc;
        this.minAmountForCalculatingCreditPremium = minAmountForCalculatingCreditPremium;
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

    public Double getFactorPremium() {
        return factorPremium;
    }

    public void setFactorPremium(Double factorPremium) {
        this.factorPremium = factorPremium;
    }

    public Double getMinPremium() {
        return minPremium;
    }

    public void setMinPremium(Double minPremium) {
        this.minPremium = minPremium;
    }

    public Double getMaxPremium() {
        return maxPremium;
    }

    public void setMaxPremium(Double maxPremium) {
        this.maxPremium = maxPremium;
    }

    public Boolean getIsCoc() {
        return isCoc;
    }

    public void setIsCoc(Boolean COC) {
        isCoc = COC;
    }

    public Double getMinAmountForCalculatingCreditPremium() {
        return minAmountForCalculatingCreditPremium;
    }

    public void setMinAmountForCalculatingCreditPremium(Double minAmountForCalculatingCreditPremium) {
        this.minAmountForCalculatingCreditPremium = minAmountForCalculatingCreditPremium;
    }

//    public List<Credit> getCredits() {
//        return credits;
//    }
//
//    public void setCredits(List<Credit> credits) {
//        this.credits = credits;
//    }
}
