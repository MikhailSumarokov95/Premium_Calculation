package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "insurance")
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal factorInsuranceVolume;
    private BigDecimal factorInsuranceBonus;

    public Insurance() {
    }

    public Insurance(Long id, String name, BigDecimal factorInsuranceVolume,
                     BigDecimal factorInsuranceBonus) {
        this.id = id;
        this.name = name;
        this.factorInsuranceVolume = factorInsuranceVolume;
        this.factorInsuranceBonus = factorInsuranceBonus;
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

    public BigDecimal getFactorInsuranceVolume() {
        return factorInsuranceVolume;
    }

    public void setFactorInsuranceVolume(BigDecimal factorInsuranceVolume) {
        this.factorInsuranceVolume = factorInsuranceVolume;
    }

    public BigDecimal getFactorInsuranceBonus() {
        return factorInsuranceBonus;
    }

    public void setFactorInsuranceBonus(BigDecimal factorInsuranceBonus) {
        this.factorInsuranceBonus = factorInsuranceBonus;
    }
}
