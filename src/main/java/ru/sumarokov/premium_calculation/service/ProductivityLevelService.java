package ru.sumarokov.premium_calculation.service;

import org.springframework.stereotype.Service;
import ru.sumarokov.premium_calculation.entity.ProductGroup;
import ru.sumarokov.premium_calculation.entity.ProductivityLevel;
import ru.sumarokov.premium_calculation.exception.EntityNotFoundException;
import ru.sumarokov.premium_calculation.repository.ProductivityLevelRepository;

import java.util.List;

@Service
public class ProductivityLevelService {

    private final ProductivityLevelRepository productivityLevelRepository;

    public ProductivityLevelService(ProductivityLevelRepository productivityLevelRepository) {
        this.productivityLevelRepository = productivityLevelRepository;
    }

    public List<ProductivityLevel> getProductivityLevels() {
        return productivityLevelRepository.findAllByOrderByPremiumAsc();
    }

    public ProductivityLevel getProductivityLevel(Long id) {
        return productivityLevelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ProductivityLevel.class, id));
    }

    public void saveProductivityLevel(ProductivityLevel productivityLevel) {
        productivityLevelRepository.save(productivityLevel);
    }

    public void deleteProductivityLevel(Long id) {
        if (!productivityLevelRepository.existsById(id)) {
            throw new EntityNotFoundException(ProductivityLevel.class, id);
        }
        productivityLevelRepository.deleteById(id);
    }
}
