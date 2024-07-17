package ru.stepup.demohikari.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.stepup.demohikari.Entity.Product;
import ru.stepup.demohikari.enumerator.ProdType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDao {
    private Connection connection;
    @Autowired
    public ProductDao(Connection jdbcConnection) {
        this.connection = jdbcConnection;
    }

    public long save (Product product) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO products(userid, account, saldo, typ) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, product.getUserid());
            preparedStatement.setString(2, product.getAccount());
            preparedStatement.setBigDecimal(3, product.getSaldo());
            preparedStatement.setString(4, product.getTyp().name());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                var rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    product.setId(rs.getLong(1));
                    return rs.getLong(1);
                }
            }
        }
        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Product> getProducts(Long userId) {

        List<Product> listProduct = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM products t where t.userid = " + userId;
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setUserid(resultSet.getLong("userid"));
                product.setAccount(resultSet.getString("account"));
                product.setSaldo(resultSet.getBigDecimal("saldo"));
                product.setTyp(ProdType.valueOf(resultSet.getString("typ")));
                listProduct.add(product);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return listProduct;
    }

    public Product getProduct(long id) {
        Product product = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM products WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setUserid(resultSet.getLong("userid"));
                product.setAccount(resultSet.getString("account"));
                product.setSaldo(resultSet.getBigDecimal("saldo"));
                product.setTyp(ProdType.valueOf(resultSet.getString("typ")));
                return product;
            }
            else
            {
                System.out.println("По id = " + id + " продукт не найден");
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void deleteAll() {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM products");
            preparedStatement.executeQuery();
        }
        catch (SQLException e) {
        }
    }
}
