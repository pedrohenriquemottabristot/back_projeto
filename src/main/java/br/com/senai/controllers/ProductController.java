package br.com.senai.controllers;

import br.com.senai.models.Product;
import br.com.senai.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/product") //rota pra salvar /product/createproduct
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    @PostMapping(value="/createProduct",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Product createProduct(@RequestBody Product product){
        //Cria um novo objeto Product
        Product newProduct = new Product();
        //Seta as propriedades do Product
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        //Chama o método save para salvar o objeto no banco de dados
        return productRepository.save(newProduct);
    }
    //api.put(`product/updateProduct/${id}
    @PutMapping(value="/updateProduct/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Product updateProduct(@RequestBody Product product,
                                 @PathVariable Long id){
        Product getProduct = productRepository.findById(id).orElseThrow();
        Product updateProduct = new Product();
        System.out.println("preço: " +product.getPrice());
        updateProduct.setId(id);
        updateProduct.setName(getProduct.getName());
        updateProduct.setDescription(getProduct.getDescription());
        updateProduct.setPrice(product.getPrice());

        return productRepository.save(updateProduct);
    }
    //Método deletar product
    @DeleteMapping(value="/deleteProduct/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@PathVariable pega um valor passado junto a barra de endereço
    public Product deleteProduct(@PathVariable Long id){
        //Verificamos se existe o café no banco de dados procurando o id
        Product getProduct = productRepository.findById(id).orElseThrow();
        //chamamos o método .delete e passamos o café a ser deletado
        productRepository.delete(getProduct);
        return getProduct;
    }

    // método filtrar produtos de manutenção de produtos
    @GetMapping(value = "/filtro/{palavra}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> filtrarLista(@PathVariable String palavra) {
        // Search for products using the provided 'palavra'
        List<Product> filteredProducts = productRepository.findByNameContainingIgnoreCase(palavra);

        // Check if any products were found
        if (filteredProducts.isEmpty()) {
            // No products found, return an empty list
            return Collections.emptyList();
        }

        // Products found, return the filtered list
        return filteredProducts;
    }
}