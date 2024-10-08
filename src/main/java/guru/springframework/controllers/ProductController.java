package guru.springframework.controllers;

import guru.springframework.commands.ProductForm;
import guru.springframework.converters.ProductToProductForm;
import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jt on 1/10/17.
 */
@Controller
public class ProductController {
    public static final String PRODUCT_PRODUCT_FORM = "product/productform";
    private ProductService productService;

    private ProductToProductForm productToProductForm;

    @Autowired
    public void setProductToProductForm(ProductToProductForm productToProductForm) {
        this.productToProductForm = productToProductForm;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/")
    public String redirToList() {
        return "redirect:/product/list";
    }

    @RequestMapping({"/product/list", "/product"})
    public String listProducts(Model model) {
        model.addAttribute("products", productService.listAll());
        return "product/list";
    }

    @RequestMapping("/product/show/{id}")
    public String getProduct(@PathVariable String id, Model model) {
        model.addAttribute("product", productService.getById(id));
        return "product/show";
    }

    @RequestMapping("product/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Product product = productService.getById(id);
        ProductForm productForm = productToProductForm.convert(product);

        model.addAttribute("productForm", productForm);
        return PRODUCT_PRODUCT_FORM;
    }

    @RequestMapping("/product/new")
    public String newProduct(Model model) {
        model.addAttribute("productForm", new ProductForm());
        return PRODUCT_PRODUCT_FORM;
    }

    @PostMapping(value = "/product")
    public String saveOrUpdateProduct(@Valid ProductForm productForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return PRODUCT_PRODUCT_FORM;
        }

        Product savedProduct = productService.saveOrUpdateProductForm(productForm);

        return "redirect:/product/show/" + savedProduct.getId();
    }

    @RequestMapping("/product/delete/{id}")
    public String delete(@PathVariable String id) {
        productService.delete(id);
        return "redirect:/product/list";
    }
}
