package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "premium_limit")
public class PremiumLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 0, message = "Значение должно быть больше 0")
    private BigDecimal maxTotalPremium;
    private Boolean isActual;

    public PremiumLimit() {
    }

    public PremiumLimit(BigDecimal maxTotalPremium, Boolean isActual) {
        this.maxTotalPremium = maxTotalPremium;
        this.isActual = isActual;
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

    public Boolean getIsActual() {
        return isActual;
    }

    public void setIsActual(Boolean actual) {
        isActual = actual;
    }
}
