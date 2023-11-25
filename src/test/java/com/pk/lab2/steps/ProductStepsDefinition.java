package com.pk.lab2.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.lab2.enums.ProductCategory;
import com.pk.lab2.model.Product;
import com.pk.lab2.model.ProductDTO;
import com.pk.lab2.model.ProductSummaryDTO;
import com.pk.lab2.repository.ProductRepository;
import com.pk.lab2.validation.ProductValidation;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.Assert.*;

@CucumberContextConfiguration
@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductStepsDefinition {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private ResponseEntity<?> responseEntity;

    private ProductDTO productDTO;

    @Given("database contains 3 products with different names")
    public void theProductListIsNotEmpty() {
        productRepository.save(Product.builder().name("product1").build());
        productRepository.save(Product.builder().name("product2").build());
        productRepository.save(Product.builder().name("product3").build());
    }

    @When("the client requests all products")
    public void whenTheClientRequestsAllProducts() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products",
                ProductSummaryDTO.class)).andReturn();
        responseEntity = new ResponseEntity<>(result.getResponse().getContentAsString(),
                HttpStatusCode.valueOf(result.getResponse().getStatus()));
    }

    @Then("the response status should be {int}")
    public void thenTheGetResponseStatusShouldBe(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), responseEntity.getStatusCode());
    }

    @And("the response should contain all product details")
    public void andTheResponseShouldContainProductDetails() throws JsonProcessingException {
        String response = (String) responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> responseList = objectMapper.readValue(response, List.class);
        assertNotNull(responseList);
        assertEquals(3, responseList.size());
        assertTrue(responseList.toString().contains("product1"));
        assertTrue(responseList.toString().contains("product2"));
        assertTrue(responseList.toString().contains("product3"));
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
    public void whenTheClientRequestsNewProductCreation() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO))).andReturn();
        responseEntity = new ResponseEntity<>(result.getResponse().getContentAsString(),
                HttpStatusCode.valueOf(result.getResponse().getStatus()));
    }

    @Then("create product validation fails")
    public void thenProductValidationFails() {
        assertFalse(ProductValidation.isProductValidForCreate(productDTO));
    }

    @And("the response should contain the emptyMap")
    public void andTheResponseShouldContainTheCreatedProductDetails() {
        assertEquals("{}", responseEntity.getBody());
    }

    @Given("the client provides valid product data for update non-existing product with ID 123")
    public void givenNonExistingProduct() {
        productDTO = ProductDTO.builder()
                .quantity(3000)
                .build();
    }

    @When("the client requests non-existing product update with ID {string}")
    public void whenClientRequestsToUpdateNonExistingProduct(String productId) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .patch("/products/" + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productDTO))).andReturn();
        responseEntity = new ResponseEntity<>(result.getResponse().getContentAsString(),
                HttpStatusCode.valueOf(result.getResponse().getStatus()));
    }

    @Then("update product validation passes")
    public void thenProductValidationPasses() {
        assertTrue(ProductValidation.isProductValidForUpdate(productDTO));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
