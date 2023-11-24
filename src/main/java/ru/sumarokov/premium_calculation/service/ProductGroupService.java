package ru.sumarokov.premium_calculation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.ProductGroup;
import ru.sumarokov.premium_calculation.exception.EntityNotFoundException;
import ru.sumarokov.premium_calculation.repository.ProductGroupRepository;

import java.util.List;

@Service
public class ProductGroupService {

    private final ProductGroupRepository productGroupRepository;

    @Autowired
    public ProductGroupService(ProductGroupRepository productGroupRepository) {
        this.productGroupRepository = productGroupRepository;
    }

    public List<ProductGroup> getProductGroups() {
        return productGroupRepository.findAllByOrderByFactorPremiumDesc();
    }

    public ProductGroup getProductGroup(Long id) {
        return productGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ProductGroup.class, id));
    }

    public void saveProductGroup(ProductGroup productGroup) {
        productGroupRepository.save(productGroup);
    }

    public void deleteProductGroup(Long id) {
        if (!productGroupRepository.existsById(id)) {
            throw new EntityNotFoundException(ProductGroup.class, id);
        }
        productGroupRepository.deleteById(id);
    }
}
