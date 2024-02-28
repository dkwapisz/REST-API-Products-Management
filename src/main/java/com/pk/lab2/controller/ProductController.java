package com.pk.lab2.controller;

import com.pk.lab2.model.Product;
import com.pk.lab2.model.ProductDTO;
import com.pk.lab2.model.ProductSummaryDTO;
import com.pk.lab2.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static com.pk.lab2.validation.ProductValidation.isProductValidForCreate;
import static com.pk.lab2.validation.ProductValidation.isProductValidForUpdate;
import static java.util.Objects.nonNull;


@RestController
@RequestMapping(("/api/products"))
@Tag(name = "Products management API")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get list of all products with short details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returning list of all products with short details",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductSummaryDTO.class)))}),
            @ApiResponse(responseCode = "500", description = "Internal server error - something is wrong on server side",
                    content = @Content)})
    public ResponseEntity<?> getAllProducts() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get product by Id with all details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returning product by given Id with long details",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "404", description = "Product with given Id not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error - something is wrong on server side",
                    content = @Content)})
    public ResponseEntity<?> getProductById(@PathVariable String productId) {
        try {
            Product product = productService.getProductById(productId);
            if (nonNull(product)) {
                return ResponseEntity.status(HttpStatus.OK).body(product);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyMap());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Create new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product has been created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Received invalid values in some of Product fields",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error - something is wrong on server side",
                    content = @Content)})
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            if (!isProductValidForCreate(productDTO)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyMap());
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/{productId}")
    @Operation(summary = "Update existing product by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product with given Id has been updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Received invalid values in some of Product fields",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product with given Id not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error - something is wrong on server side",
                    content = @Content)})
    public ResponseEntity<?> updateProduct(@PathVariable String productId, @io.swagger.v3.oas.annotations.parameters
            .RequestBody(description = "Updated product details", required = true,
            content = @Content(schema = @Schema(implementation = ProductDTO.class))) @RequestBody ProductDTO productDTO) {
        try {
            if (!isProductValidForUpdate(productDTO)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyMap());
            }

            Product product = productService.updateProduct(productId, productDTO);
            if (nonNull(product)) {
                return ResponseEntity.status(HttpStatus.OK).body(product);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyMap());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete existing product by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product with given Id has been deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product with given Id not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error - something is wrong on server side",
                    content = @Content)})
    public ResponseEntity<?> deleteProduct(@PathVariable String productId) {
        try {
            boolean deleted = productService.deleteProduct(productId);
            if (deleted) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyMap());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyMap());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
