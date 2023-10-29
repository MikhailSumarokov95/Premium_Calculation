package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "insurance")
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double factorInsuranceVolume;
    private Double factorInsuranceBonus;

    public Insurance() {
    }

    public Insurance(Long id, String name, Double factorInsuranceVolume,
                     Double factorInsuranceBonus) {
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

    public Double getFactorInsuranceVolume() {
        return factorInsuranceVolume;
    }

    public void setFactorInsuranceVolume(Double factorInsuranceVolume) {
        this.factorInsuranceVolume = factorInsuranceVolume;
    }

    public Double getFactorInsuranceBonus() {
        return factorInsuranceBonus;
    }

    public void setFactorInsuranceBonus(Double factorInsuranceBonus) {
        this.factorInsuranceBonus = factorInsuranceBonus;
    }
}
