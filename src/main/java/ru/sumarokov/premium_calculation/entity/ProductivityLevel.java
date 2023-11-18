package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Entity
@Table(name = "productivity_level")
public class ProductivityLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Поле должно быть заполнено")
    @Size(max = 64, message = "Максимальная длина названия 64 символа")
    private String name;
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 0, message = "Значение должно быть больше 0")
    private BigDecimal premium;
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 0, message = "Значение должно быть больше 0")
    private Integer minCountCredits;
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 0, message = "Значение должно быть больше 0")
    private BigDecimal minSumAmountCredits;
    @NotNull(message = "Поле должно быть заполнено")
    @Range(min = 0, max = 100, message = "Значение должно находится в пределах от 0 до 100")
    private BigDecimal minPenetrationSms;
    @NotNull(message = "Поле должно быть заполнено")
    @Range(min = 0, max = 100, message = "Значение должно находится в пределах от 0 до 100")
    private BigDecimal minPenetrationInsurance;

    public ProductivityLevel() {
    }

    public ProductivityLevel(Long id, String name,
                             BigDecimal premium,
                             Integer minCountCredits,
                             BigDecimal minSumAmountCredits,
                             BigDecimal minPenetrationSms,
                             BigDecimal minPenetrationInsurance) {
        this.id = id;
        this.name = name;
        this.premium = premium;
        this.minCountCredits = minCountCredits;
        this.minSumAmountCredits = minSumAmountCredits;
        this.minPenetrationSms = minPenetrationSms;
        this.minPenetrationInsurance = minPenetrationInsurance;
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

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public Integer getMinCountCredits() {
        return minCountCredits;
    }

    public void setMinCountCredits(Integer minCountCredits) {
        this.minCountCredits = minCountCredits;
    }

    public BigDecimal getMinSumAmountCredits() {
        return minSumAmountCredits;
    }

    public void setMinSumAmountCredits(BigDecimal minSumAmountCredits) {
        this.minSumAmountCredits = minSumAmountCredits;
    }

    public BigDecimal getMinPenetrationSms() {
        return minPenetrationSms;
    }

    public void setMinPenetrationSms(BigDecimal minPenetrationSms) {
        this.minPenetrationSms = minPenetrationSms;
    }

    public BigDecimal getMinPenetrationInsurance() {
        return minPenetrationInsurance;
    }

    public void setMinPenetrationInsurance(BigDecimal minPenetrationInsurance) {
        this.minPenetrationInsurance = minPenetrationInsurance;
    }
}
