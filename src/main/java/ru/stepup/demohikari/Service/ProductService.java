package ru.stepup.demohikari.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepup.demohikari.Dao.ProductDao;
import ru.stepup.demohikari.Entity.Product;

import java.util.List;

@Service
public class ProductService {

    private ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public long save (Product product) {
        return productDao.save(product);
    }

    public List<Product> getProducts(Long userId) {
        return productDao.getProducts(userId);
    }

    public Product getProduct(long id) {
        return productDao.getProduct(id);
    }
    public void deleteAll() {
        productDao.deleteAll();
    }
}
