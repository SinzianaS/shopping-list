package com.shopping.list.shoppingitem.web;

import com.shopping.list.shoppingitem.ShoppingItem;
import com.shopping.list.shoppingitem.ShoppingItemNotFoundException;
import com.shopping.list.shoppingitem.ShoppingItemRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}/toggle")
    public String toggleSelection(@PathVariable("id") Long id) {
        ShoppingItem shoppingItem = repository.findById(id)
                .orElseThrow();


        shoppingItem.setCompleted(!shoppingItem.isCompleted());
        repository.save(shoppingItem);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id) {
        repository.deleteById(id);

        return "redirect:/";
    }
    @PutMapping("/{id}/edit")
    public String editShoppingItem(@PathVariable("id") Long id,
                                   @Valid @ModelAttribute("item")
                                   ShoppingItemFormData formData) {
        ShoppingItem shoppingItem = repository.findById(id)
                .orElseThrow();
        shoppingItem.setTitle(formData.getTitle());
        repository.save(shoppingItem);
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
