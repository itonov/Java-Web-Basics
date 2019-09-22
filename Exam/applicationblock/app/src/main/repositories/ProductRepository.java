package main.repositories;

import main.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository extends BaseRepository {
    public void createProduct(Product product) {
        this.execute(actionResult -> {
            this.entityManager.persist(product);
        });
    }

    public void updateProduct(Product product) {
        this.execute(actionResult -> {
            this.entityManager.merge(product);
        });
    }

    public void deleteProduct(Product product) {
        this.execute(actionResult -> {
            this.entityManager.createNativeQuery(
                    "DELETE FROM products WHERE id = \'" + product.getId() + "\'")
                    .executeUpdate();
        });
    }

    public List<Product> findAll() {
        List<Product> result = new ArrayList<Product>();

        this.execute(actionResult -> {
            result.addAll(this.entityManager.createNativeQuery(
                    "SELECT * FROM products", Product.class
            ).getResultList());
        });

        return result;
    }

    public Product findById(String id) {
        Product result = (Product) this.execute(actionResult -> {
            actionResult.setResult(
                    this.entityManager
                            .createNativeQuery(
                                    "SELECT * FROM products AS p WHERE p.id = \'" + id + "\'", Product.class)
                            .getResultList()
                            .stream()
                            .findFirst()
                            .orElse(null));
        }).getResult();

        return result;
    }
}
