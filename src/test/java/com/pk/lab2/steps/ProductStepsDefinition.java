package com.pk.lab2.steps;

import com.pk.lab2.controller.ProductController;
import com.pk.lab2.enums.ProductCategory;
import com.pk.lab2.model.ProductDTO;
import com.pk.lab2.model.ProductSummaryDTO;
import com.pk.lab2.service.ProductService;
import com.pk.lab2.validation.ProductValidation;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@CucumberContextConfiguration
public class ProductStepsDefinition {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ResponseEntity<?> responseEntity;

    private ProductSummaryDTO productSummaryDTO_mock1;
    private ProductSummaryDTO productSummaryDTO_mock2;
    private ProductSummaryDTO productSummaryDTO_mock3;

    private ProductDTO productDTO;

    @Given("the product list is not empty")
    public void givenTheProductListIsNotEmpty() {
        productSummaryDTO_mock1 = mock(ProductSummaryDTO.class);
        productSummaryDTO_mock2 = mock(ProductSummaryDTO.class);
        productSummaryDTO_mock3 = mock(ProductSummaryDTO.class);

        when(productService.getAllProducts()).thenReturn(List.of(productSummaryDTO_mock1, productSummaryDTO_mock2,
                productSummaryDTO_mock3));
    }

    @When("the client requests all products")
    public void whenTheClientRequestsAllProducts() {
        responseEntity = productController.getAllProducts();
    }

    @Then("the response status should be {int}")
    public void thenTheGetResponseStatusShouldBe(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), responseEntity.getStatusCode());
    }

    @And("the response should contain product details")
    public void andTheResponseShouldContainProductDetails() {
        List<ProductSummaryDTO> productList = (List<ProductSummaryDTO>) responseEntity.getBody();

        assertNotNull(productList);
        assertEquals(productList.size(), 3);
        assertEquals(productList.get(0), productSummaryDTO_mock1);
        assertEquals(productList.get(1), productSummaryDTO_mock2);
        assertEquals(productList.get(2), productSummaryDTO_mock3);
    }

    @Given("the client provide product with invalid data")
    public void givenTheProductWithValidData() {
        productDTO = ProductDTO.builder()
                .name("Samsung S20")
                .description("It's a smartphone")
                .quantity(-10)
                .price(20.0F)
                .weight(2.5F)
                .productCategory(ProductCategory.ELECTRONICS)
                .build();
    }

    @When("the client requests new product creation")
    public void whenTheClientRequestsNewProductCreation() {
        responseEntity = productController.createProduct(productDTO);
    }

    @Then("create product validation fails")
    public void thenProductValidationFails() {
        assertFalse(ProductValidation.isProductValidForCreate(productDTO));
    }

    @And("the response should contain the emptyMap")
    public void andTheResponseShouldContainTheCreatedProductDetails() {
        assertEquals(responseEntity.getBody(), Collections.emptyMap());
    }

    @Given("the client provides valid product data for update non-existing product with ID {string}")
    public void givenNonExistingProduct(String productId) {
        productDTO = ProductDTO.builder()
                .quantity(3000)
                .price(300.50F)
                .build();

        when(productService.updateProduct(productId, productDTO)).thenReturn(null);
    }

    @When("the client requests non-existing product update with ID {string}")
    public void whenClientRequestsToUpdateNonExistingProduct(String productId) {
        responseEntity = productController.updateProduct(productId, productDTO);
    }

    @Then("update product validation passes")
    public void thenProductValidationPasses() {
        assertTrue(ProductValidation.isProductValidForUpdate(productDTO));
    }
}
