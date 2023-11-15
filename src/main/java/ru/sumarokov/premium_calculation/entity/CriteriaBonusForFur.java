package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Entity
@Table(name = "criteria_bonus_for_fur")
public class CriteriaBonusForFur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 0, message = "Значение должно быть больше 0")
    private BigDecimal minSum;
    @NotNull(message = "Поле должно быть заполнено")
    @Range(min = 0, max = 100, message = "Значение должно находится в пределах от 0 до 100")
    private BigDecimal minSms;
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 0, message = "Значение должно быть больше 0")
    private BigDecimal bonus;

    public CriteriaBonusForFur() {
    }

    public CriteriaBonusForFur(Long id, BigDecimal minSum, BigDecimal minSms, BigDecimal bonus) {
        this.id = id;
        this.minSum = minSum;
        this.minSms = minSms;
        this.bonus = bonus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMinSum() {
        return minSum;
    }

    public void setMinSum(BigDecimal minSum) {
        this.minSum = minSum;
    }

    public BigDecimal getMinSms() {
        return minSms;
    }

    public void setMinSms(BigDecimal minSms) {
        this.minSms = minSms;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }
}
