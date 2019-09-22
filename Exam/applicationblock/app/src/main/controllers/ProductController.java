package main.controllers;

import main.entities.*;
import main.models.binding.ProductAddBindingModel;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summermvc.api.*;
import main.repositories.OrderRepository;
import main.repositories.ProductRepository;
import main.repositories.UserRepository;

import java.time.LocalDateTime;

@Controller
public class ProductController extends BaseController {
    private UserRepository userRepository;

    private ProductRepository productRepository;

    private OrderRepository orderRepository;

    public ProductController() {
        this.userRepository = new UserRepository();
        this.productRepository = new ProductRepository();
        this.orderRepository = new OrderRepository();
    }

    @GetMapping(route = "/products/details/{id}")
    public String details(@PathVariable(name = "id") String id, HttpSoletRequest request, Model model) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        }

        Product foundProduct = this.productRepository.findById(id);

        if(foundProduct == null) {
            return super.redirect("home");
        }

        model.addAttribute("id", foundProduct.getId());
        model.addAttribute("name", foundProduct.getName());
        model.addAttribute("price", foundProduct.getPrice());
        model.addAttribute("type", foundProduct.getType().toString());
        model.addAttribute("description", foundProduct.getDescription());

        if (super.getRole(request).equals(Role.ADMIN)) {
            return super.view("product-details-admin");
        }
        return super.view("product-details");
    }

    @GetMapping(route = "/products/order/{id}")
    public String order(@PathVariable(name = "id") String id, HttpSoletRequest request) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        }

        String currentUserId = request.getSession().getAttributes().get("user-id").toString();

        User currentUser = this.userRepository.findById(currentUserId);
        Product currentProduct = this.productRepository.findById(id);

        if(currentUser == null || currentProduct == null) {
            return super.redirect("home");
        }

        Order order = new Order();

        order.setClient(currentUser);
        order.setProduct(currentProduct);
        order.setOrderedOn(LocalDateTime.now());

        this.orderRepository.createOrder(order);

        return super.redirect("home");
    }

    @GetMapping(route = "/products/create")
    public String create(HttpSoletRequest request) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        } else if (super.getRole(request).equals(Role.USER)) {
            return super.redirect("home");
        }

        return super.view("product-create");
    }

    @PostMapping(route = "/products/create")
    public String createConfirm(ProductAddBindingModel product, HttpSoletRequest request) {
        if (!super.isLoggedIn(request)) {
            super.redirect("login");
        } else if (super.getRole(request).equals(Role.USER)) {
            return super.redirect("home");
        }

        Product newProduct = new Product();

        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setType(ProductType.valueOf(product.getType()));

        this.productRepository.createProduct(newProduct);

        return super.redirect("home");
    }

    @GetMapping(route = "/products/edit/{id}")
    public String edit(@PathVariable(name = "id") String id, HttpSoletRequest request, Model model) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        } else if (super.getRole(request).equals(Role.USER)) {
            return super.redirect("home");
        }

        model.addAttribute("id", id);

        return super.view("product-edit");
    }

    @PostMapping(route = "/products/edit/{id}")
    public String editConfirm(@PathVariable(name = "id") String id, HttpSoletRequest request,
                              ProductAddBindingModel editedProduct) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        } else if (super.getRole(request).equals(Role.USER)) {
            return super.redirect("home");
        }

        Product product = this.productRepository.findById(id);

        if (product == null) {
            return super.redirect("home");
        }

        product.setType(ProductType.valueOf(editedProduct.getType()));
        product.setPrice(editedProduct.getPrice());
        product.setDescription(editedProduct.getDescription());
        product.setName(editedProduct.getName());

        this.productRepository.updateProduct(product);

        return super.redirect("products/details/" + id);
    }

    @GetMapping(route = "/products/delete/{id}")
    public String delete(@PathVariable(name = "id") String id, HttpSoletRequest request) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        } else if (super.getRole(request).equals(Role.USER)) {
            return super.redirect("home");
        }

        Product product = this.productRepository.findById(id);

        if (product == null) {
            return super.redirect("home");
        }

        this.productRepository.deleteProduct(product);

        return super.redirect("home");
    }
}
