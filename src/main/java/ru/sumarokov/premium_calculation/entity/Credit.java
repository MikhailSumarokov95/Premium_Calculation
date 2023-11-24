package ru.sumarokov.premium_calculation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "credit")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_group_id", nullable = false)
    private ProductGroup productGroup;
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 0, message = "Значение должно быть больше 0")
    private BigDecimal amount;
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 0, message = "Значение должно быть больше 0")
    private Integer term;
    @NotNull(message = "Поле должно быть заполнено")
    @Min(value = 0, message = "Значение должно быть больше 0")
    private BigDecimal rate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "insurance_id", nullable = false)
    private Insurance insurance;
    private Boolean isConnectedSms;
    private Boolean isFur;
    private Boolean isConsultantAvailability;
    private Boolean isUsedSelfReject;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;
    @OneToOne(mappedBy = "credit", cascade = CascadeType.ALL)
    private PreliminaryCreditResult preliminaryCreditResult;

    public Credit() {
    }

    public Credit(User user) {
        this.user = user;
    }

    public Credit(ProductGroup productGroup,
                  BigDecimal amount,
                  Integer term,
                  BigDecimal rate,
                  Insurance insurance,
                  Boolean isConnectedSms,
                  Boolean isFur,
                  Boolean isConsultantAvailability,
                  Boolean isUsedSelfReject,
                  User user) {
        this.productGroup = productGroup;
        this.amount = amount;
        this.term = term;
        this.rate = rate;
        this.insurance = insurance;
        this.isConnectedSms = isConnectedSms;
        this.isFur = isFur;
        this.isConsultantAvailability = isConsultantAvailability;
        this.isUsedSelfReject = isUsedSelfReject;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public Boolean getIsConnectedSms() {
        return isConnectedSms;
    }

    public void setIsConnectedSms(Boolean connectedSMS) {
        isConnectedSms = connectedSMS;
    }

    public Boolean getIsFur() {
        return isFur;
    }

    public void setIsFur(Boolean fur) {
        isFur = fur;
    }

    public Boolean getIsConsultantAvailability() {
        return isConsultantAvailability;
    }

    public void setIsConsultantAvailability(Boolean consultantAvailability) {
        isConsultantAvailability = consultantAvailability;
    }

    public Boolean getIsUsedSelfReject() {
        return isUsedSelfReject;
    }

    public void setIsUsedSelfReject(Boolean usedSelfReject) {
        isUsedSelfReject = usedSelfReject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PreliminaryCreditResult getPreliminaryCreditResult() {
        return preliminaryCreditResult;
    }

    public void setPreliminaryCreditResult(PreliminaryCreditResult preliminaryCreditResult) {
        this.preliminaryCreditResult = preliminaryCreditResult;
    }
}

