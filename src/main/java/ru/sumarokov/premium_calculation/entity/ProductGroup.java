package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product_group")
public class ProductGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal factorPremium;
    private BigDecimal minPremium;
    private BigDecimal maxPremium;
    //TODO:переделать на enum
    private Boolean isCoc;
    private BigDecimal minAmountForCalculatingCreditPremium;

    public ProductGroup() {
    }

    public ProductGroup(Long id, String name, BigDecimal factorPremium,
                        BigDecimal minPremium, BigDecimal maxPremium,
                        Boolean isCoc, BigDecimal minAmountForCalculatingCreditPremium) {
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

    public BigDecimal getFactorPremium() {
        return factorPremium;
    }

    public void setFactorPremium(BigDecimal factorPremium) {
        this.factorPremium = factorPremium;
    }

    public BigDecimal getMinPremium() {
        return minPremium;
    }

    public void setMinPremium(BigDecimal minPremium) {
        this.minPremium = minPremium;
    }

    public BigDecimal getMaxPremium() {
        return maxPremium;
    }

    public void setMaxPremium(BigDecimal maxPremium) {
        this.maxPremium = maxPremium;
    }

    public Boolean getIsCoc() {
        return isCoc;
    }

    public void setIsCoc(Boolean coc) {
        isCoc = coc;
    }

    public BigDecimal getMinAmountForCalculatingCreditPremium() {
        return minAmountForCalculatingCreditPremium;
    }

    public void setMinAmountForCalculatingCreditPremium(BigDecimal minAmountForCalculatingCreditPremium) {
        this.minAmountForCalculatingCreditPremium = minAmountForCalculatingCreditPremium;
    }
}
