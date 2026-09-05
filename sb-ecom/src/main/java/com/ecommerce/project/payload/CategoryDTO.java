package com.ecommerce.project.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    @Schema(description = "Category ID", example = "101")
    private Long categoryId;


    @NotBlank
    @Size(min = 5, message = "Category name must be at least 5 characters")
    @Schema(description = "Category name for category you wish to create", example = "IPhone 16")
    private String categoryName;
}
