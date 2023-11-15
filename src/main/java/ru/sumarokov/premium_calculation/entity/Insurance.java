package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "insurance")
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Поле должно быть заполнено")
    @Size(max = 64, message = "Максимальная длина названия 64 символа")
    private String name;
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 0, message = "Значение должно быть больше 0")
    private BigDecimal factorInsuranceVolume;
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 0, message = "Значение должно быть больше 0")
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
