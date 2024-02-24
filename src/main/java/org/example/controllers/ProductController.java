package org.example.controllers;
//
//import org.example.repo.PhoenixRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@CrossOrigin(origins = "http://localhost:8081")
////@RestController
//@Controller
//@RequestMapping("/")
//public class PhoenixController {
//
//    @Autowired
//    PhoenixRepository phoenixRepository;
//
//    List<Phoenix> phoenixList = new ArrayList<Phoenix>();
//
//    @GetMapping("/")
//    public String getPhoenix(Model model) {
//        Iterable<Phoenix> phoenixes = phoenixRepository.findAll();
//        model.addAttribute("phoenixes", phoenixes);
//
//        return "home";
//    }
//    @GetMapping("/api/")
//    public ResponseEntity<List<Phoenix>> getAllPhoenix(@RequestParam(required = false)String genre) {
//        try {
//            List<Phoenix> phoenixes = new ArrayList<Phoenix>();
//            if (genre == null)
//                phoenixRepository.findAll().forEach(phoenixes::add);
//            else
//                phoenixRepository.findByGenreContaining("genre").forEach(phoenixes::add);
//            if (phoenixes.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//
//            return new ResponseEntity<>(phoenixes, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @PostMapping("/api")
//    public ResponseEntity<Phoenix> createPhoenix(@RequestBody Phoenix phoenix) {
//        try {
//            Phoenix _phoenix = phoenixRepository
//                    .save(new Phoenix(phoenix.getName(), phoenix.getGenre(), phoenix.getRelease_date()));
//            return new ResponseEntity<>(_phoenix, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//
//        }
//    }
//}

import lombok.RequiredArgsConstructor;
import org.example.models.Product;
import org.example.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
//@RequiredArgsConstructor

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String products(@RequestParam(name = "title", required = false) String title, Principal principal, Model model) {
    model.addAttribute("products", productService.listProducts(title));
    model.addAttribute("user", productService.getUserByPrincipal(principal));
    return "products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        return "product-info";
    }

    @PostMapping("product/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3, Product product, Principal principal) throws IOException {
        productService.saveProduct(principal, product, file1, file2, file3);
        return "redirect:/";

    }
    @PostMapping("product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }

}
