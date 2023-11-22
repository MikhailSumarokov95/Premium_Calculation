package ru.sumarokov.premium_calculation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.sumarokov.premium_calculation.config.AbstractApplicationTest;
import ru.sumarokov.premium_calculation.entity.InsuranceResult;
import ru.sumarokov.premium_calculation.entity.User;
import ru.sumarokov.premium_calculation.helper.Role;
import ru.sumarokov.premium_calculation.service.InsuranceResultService;

import java.math.BigDecimal;


public class InsuranceResultServiceTest extends AbstractApplicationTest {

    @Autowired
    private InsuranceResultRepository insuranceResultRepository;
    @Autowired
    private InsuranceResultService insuranceResultService;

    @PreAuthorize("authenticated")
    public String getMessage() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return "Hello " + authentication;
    }


    public void getMessageUnauthenticated() {
        getMessage();
    }


    public void getInsuranceResultTest() {
//        User userOne = new User("userOne", "pass", "emailOne@mail.ru", Role.ROLE_CREDIT_SPECIALIST);
//        User userTwo = new User("userTwo", "pass", "emailTwo@mail.ru", Role.ROLE_CREDIT_SPECIALIST);
//        User userThree = new User("userThree", "pass", "emailThree@mail.ru", Role.ROLE_CREDIT_SPECIALIST);
//        insuranceResultRepository.save(new InsuranceResult(BigDecimal.valueOf(1000), BigDecimal.valueOf(75), BigDecimal.valueOf(10000), userOne));
//        insuranceResultRepository.save(new InsuranceResult(BigDecimal.valueOf(2000), BigDecimal.valueOf(95), BigDecimal.valueOf(20000), userTwo));
//        insuranceResultRepository.save(new InsuranceResult(BigDecimal.valueOf(3000), BigDecimal.valueOf(95), BigDecimal.valueOf(30000), userThree));

        System.out.println(SecurityContextHolder.getContext().getAuthentication());
//        InsuranceResult insuranceResults = insuranceResultService.getInsuranceResult();

    }
}
