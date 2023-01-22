package com.shopping.list.shoppingitem.web;

import com.shopping.list.shoppingitem.ShoppingItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ShoppingItemController {
    private final ShoppingItemRepository repository;
    public ShoppingItemController(ShoppingItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("totalNumberOfItems", repository.count());
        return "index";
    }
}
