package com.jsp.ecommerce.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
	@Size(min = 3, max = 50, message = "* Name should be 3~50 charecters")
	private String name;
	@Size(min = 10, max = 200, message = "* Description should be 10~200 charecters")
	private String description;
	private MultipartFile image;
	@Min(value = 25, message = "* Minimum Product Price should be 25")
	@Max(value = 200000, message = "* Maximum Product Price allowed is 1,00,000")
	private double price;
	@Min(value = 1, message = "* Atleast 1 stock is required")
	@Max(value = 100, message = "* At max 100 stocks are available")
	private int stock;
	@NotEmpty(message = "* It is required")
	private String category;
}
