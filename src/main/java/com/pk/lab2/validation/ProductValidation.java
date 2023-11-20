package com.pk.lab2.validation;

import com.pk.lab2.model.ProductDTO;
import lombok.experimental.UtilityClass;

import static java.util.Objects.nonNull;

@UtilityClass
public class ProductValidation {

    public static boolean isProductValidForCreate(ProductDTO productDTO) {
        return nonNull(productDTO) &&
                isStringValidCreate(productDTO.getName()) &&
                isStringValidCreate(productDTO.getDescription()) &&
                isIntegerValidCreate(productDTO.getQuantity()) &&
                isFloatValidCreate(productDTO.getPrice()) &&
                isFloatValidCreate(productDTO.getWeight()) &&
                nonNull(productDTO.getAvailable()) &&
                nonNull(productDTO.getProductCategory());
    }

    public static boolean isProductValidForUpdate(ProductDTO productDTO) {
        boolean valid = false;

        if (nonNull(productDTO)) {
            if (nonNull(productDTO.getName())) {
                if (!productDTO.getName().isBlank()) {
                    valid = true;
                } else {
                    return false;
                }
            }
            if (nonNull(productDTO.getDescription())) {
                if (!productDTO.getDescription().isBlank()) {
                    valid = true;
                } else {
                    return false;
                }
            }
            if (nonNull(productDTO.getQuantity())) {
                if (productDTO.getQuantity() >= 0) {
                    valid = true;
                } else {
                    return false;
                }
            }
            if (nonNull(productDTO.getPrice())) {
                if (productDTO.getPrice() > 0) {
                    valid = true;
                } else {
                    return false;
                }
            }
            if (nonNull(productDTO.getWeight())) {
                if (productDTO.getWeight() > 0) {
                    valid = true;
                } else {
                    return false;
                }
            }
            if (nonNull(productDTO.getAvailable())) {
                valid = true;
            }
            if (nonNull(productDTO.getProductCategory())) {
                valid = true;
            }
        }

        return valid;
    }

    private static boolean isStringValidCreate(String value) {
        return nonNull(value) && !value.isBlank();
    }

    private static boolean isIntegerValidCreate(Integer value) {
        return nonNull(value) && value >= 0;
    }

    private static boolean isFloatValidCreate(Float value) {
        return nonNull(value) && value > 0;
    }
}