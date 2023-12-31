package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.sumarokov.premium_calculation.helper.TypeCredit;

import java.math.BigDecimal;

@Entity
@Table(name = "product_group")
public class ProductGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Поле должно быть заполнено")
    @Size(max = 64, message = "Максимальная длина названия 64 символа")
    private String name;
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 0, message = "Значение должно быть больше 0")
    private BigDecimal factorPremium;
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 0, message = "Значение должно быть больше 0")
    private BigDecimal minPremium;
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 0, message = "Значение должно быть больше 0")
    private BigDecimal maxPremium;
    @Enumerated(EnumType.STRING)
    private TypeCredit typeCredit;
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 0, message = "Значение должно быть больше 0")
    private BigDecimal minAmountForCalculatingCreditPremium;

    public ProductGroup() {
    }

    public ProductGroup(String name,
                        BigDecimal factorPremium,
                        BigDecimal minPremium,
                        BigDecimal maxPremium,
                        TypeCredit typeCredit,
                        BigDecimal minAmountForCalculatingCreditPremium) {
        this.name = name;
        this.factorPremium = factorPremium;
        this.minPremium = minPremium;
        this.maxPremium = maxPremium;
        this.typeCredit = typeCredit;
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

    public TypeCredit getTypeCredit() {
        return typeCredit;
    }

    public void setTypeCredit(TypeCredit typeCredit) {
        this.typeCredit = typeCredit;
    }

    public BigDecimal getMinAmountForCalculatingCreditPremium() {
        return minAmountForCalculatingCreditPremium;
    }

    public void setMinAmountForCalculatingCreditPremium(BigDecimal minAmountForCalculatingCreditPremium) {
        this.minAmountForCalculatingCreditPremium = minAmountForCalculatingCreditPremium;
    }
}
