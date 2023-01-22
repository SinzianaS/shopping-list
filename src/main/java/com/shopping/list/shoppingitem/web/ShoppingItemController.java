package com.shopping.list.shoppingitem.web;

import com.shopping.list.shoppingitem.ShoppingItem;
import com.shopping.list.shoppingitem.ShoppingItemRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class ShoppingItemController {
    private final ShoppingItemRepository repository;

    public ShoppingItemController(ShoppingItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("item", new ShoppingItemFormData());
        model.addAttribute("shoppingItems", getShoppingItems());
        model.addAttribute("totalNumberOfItems", repository.count());
        return "index";
    }

    @PostMapping
    public String addNewShoppingItem(@Valid @ModelAttribute("item") ShoppingItemFormData formData) {
        repository.save(new ShoppingItem(formData.getTitle(), false));
        return "redirect:/";
    }

    private List<ShoppingItemDto> getShoppingItems() {
        return repository.findAll()
                .stream()
                .map(shoppingItem -> new ShoppingItemDto(shoppingItem.getId(),
                                                         shoppingItem.getTitle(),
                                                         shoppingItem.isCompleted()))
                .collect(Collectors.toList());
    }
    public static record ShoppingItemDto(long id, String title, boolean completed) {
    }


}
