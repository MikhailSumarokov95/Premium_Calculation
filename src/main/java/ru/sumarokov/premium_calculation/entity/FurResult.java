package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "fur_result")
public class FurResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal bonus;
    private Long countCreditsCategoryFur;
    private Long countCreditsCategoryFurWithSms;
    private BigDecimal penetrationSmsCreditsCategoryFur;
    private BigDecimal sumAmountCreditsCategoryFur;
    @OneToOne()
    @MapsId
    @JoinColumn(name = "users_id")
    private User user;

    public FurResult() {
    }

    public FurResult(User user) {
        this.user = user;
    }

    public FurResult(BigDecimal bonus, Long countCreditsCategoryFur,
                     Long countCreditsCategoryFurWithSms,
                     BigDecimal penetrationSmsCreditsCategoryFur,
                     BigDecimal sumAmountCreditsCategoryFur,
                     User user) {
        this.bonus = bonus;
        this.countCreditsCategoryFur = countCreditsCategoryFur;
        this.countCreditsCategoryFurWithSms = countCreditsCategoryFurWithSms;
        this.penetrationSmsCreditsCategoryFur = penetrationSmsCreditsCategoryFur;
        this.sumAmountCreditsCategoryFur = sumAmountCreditsCategoryFur;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    public Long getCountCreditsCategoryFur() {
        return countCreditsCategoryFur;
    }

    public void setCountCreditsCategoryFur(Long countCreditsCategoryFur) {
        this.countCreditsCategoryFur = countCreditsCategoryFur;
    }

    public Long getCountCreditsCategoryFurWithSms() {
        return countCreditsCategoryFurWithSms;
    }

    public void setCountCreditsCategoryFurWithSms(Long countCreditsCategoryFurWithSms) {
        this.countCreditsCategoryFurWithSms = countCreditsCategoryFurWithSms;
    }

    public BigDecimal getPenetrationSmsCreditsCategoryFur() {
        return penetrationSmsCreditsCategoryFur;
    }

    public void setPenetrationSmsCreditsCategoryFur(BigDecimal penetrationSmsCreditsCategoryFur) {
        this.penetrationSmsCreditsCategoryFur = penetrationSmsCreditsCategoryFur;
    }

    public BigDecimal getSumAmountCreditsCategoryFur() {
        return sumAmountCreditsCategoryFur;
    }

    public void setSumAmountCreditsCategoryFur(BigDecimal sumAmountCreditsCategoryFur) {
        this.sumAmountCreditsCategoryFur = sumAmountCreditsCategoryFur;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
