package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.Insurance;
import ru.sumarokov.premium_calculation.entity.ProductGroup;
import ru.sumarokov.premium_calculation.repository.InsuranceRepository;
import ru.sumarokov.premium_calculation.repository.ProductGroupRepository;

import java.util.List;

@Service
public class ProductGroupService {

    private final ProductGroupRepository productGroupRepository;

    @Autowired
    public ProductGroupService(ProductGroupRepository productGroupRepository) {
        this.productGroupRepository = productGroupRepository;
    }

    public List<ProductGroup> getProductGroup() {
        return productGroupRepository.findAll();
    }
}
