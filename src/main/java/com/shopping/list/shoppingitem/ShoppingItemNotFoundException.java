package com.shopping.list.shoppingitem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShoppingItemNotFoundException {
    public ShoppingItemNotFoundException(Long id) {
        super();
    }

}
