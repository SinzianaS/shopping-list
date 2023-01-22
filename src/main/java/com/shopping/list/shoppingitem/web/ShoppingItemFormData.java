package com.shopping.list.shoppingitem.web;

import jakarta.validation.constraints.NotBlank;

public class ShoppingItemFormData {
    private String title;
    @NotBlank
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
