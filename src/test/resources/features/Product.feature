@ProductAPI
Feature: Product API
  The Product API allows clients to manage product information.
  This feature defines scenarios for retrieving, creating, and updating products.

  Background: Create sample Products

  Scenario: Retrieve all products
    Given database contains 3 products with different names
    When the client requests all products
    Then the response status should be 200
    And the response should contain all product details

  Scenario: Create product with invalid data
    Given the client provide product with invalid data
    When the client requests new product creation
    Then create product validation fails
    And the response status should be 400
    And the response should contain the emptyMap

  Scenario: Update non-existing product
    Given the client provides valid product data for update non-existing product with ID 123
    When the client requests non-existing product update with ID "123"
    Then update product validation passes
    And the response status should be 404
    And the response should contain the emptyMap