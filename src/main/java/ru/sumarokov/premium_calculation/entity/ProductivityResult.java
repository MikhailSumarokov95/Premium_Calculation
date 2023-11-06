package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "productivity_result")
public class ProductivityResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_level", nullable = false)
    private ProductivityLevel generalLevel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sum_amount_credits_level", nullable = false)
    private ProductivityLevel sumAmountCreditsLevel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "count_credits_level", nullable = false)
    private ProductivityLevel countCreditsLevel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_level", nullable = false)
    private ProductivityLevel insuranceLevel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "count_sms_level", nullable = false)
    private ProductivityLevel countSmsLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductivityLevel getGeneralLevel() {
        return generalLevel;
    }

    public void setGeneralLevel(ProductivityLevel generalLevel) {
        this.generalLevel = generalLevel;
    }

    public ProductivityLevel getSumAmountCreditsLevel() {
        return sumAmountCreditsLevel;
    }

    public void setSumAmountCreditsLevel(ProductivityLevel sumAmountCreditsLevel) {
        this.sumAmountCreditsLevel = sumAmountCreditsLevel;
    }

    public ProductivityLevel getCountCreditsLevel() {
        return countCreditsLevel;
    }

    public void setCountCreditsLevel(ProductivityLevel countCreditsLevel) {
        this.countCreditsLevel = countCreditsLevel;
    }

    public ProductivityLevel getInsuranceLevel() {
        return insuranceLevel;
    }

    public void setInsuranceLevel(ProductivityLevel insuranceLevel) {
        this.insuranceLevel = insuranceLevel;
    }

    public ProductivityLevel getCountSmsLevel() {
        return countSmsLevel;
    }

    public void setCountSmsLevel(ProductivityLevel countSmsLevel) {
        this.countSmsLevel = countSmsLevel;
    }

    @Override
    public String toString() {
        return "ProductivityResult{" +
                "id=" + id +
                ", generalLevel=" + generalLevel +
                ", sumAmountCreditsLevel=" + sumAmountCreditsLevel +
                ", countCreditsLevel=" + countCreditsLevel +
                ", insuranceLevel=" + insuranceLevel +
                ", countSmsLevel=" + countSmsLevel +
                '}';
    }
}
