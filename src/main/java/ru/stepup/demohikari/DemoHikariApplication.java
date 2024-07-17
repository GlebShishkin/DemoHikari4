package ru.stepup.demohikari;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.stepup.demohikari.Entity.Product;
import ru.stepup.demohikari.Entity.User;
import ru.stepup.demohikari.Service.ProductService;
import ru.stepup.demohikari.Service.UserService;
import ru.stepup.demohikari.enumerator.ProdType;

import java.math.BigDecimal;
import java.util.List;

@ComponentScan("ru.stepup.demohikari")
public class DemoHikariApplication {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {

        applicationContext =
                new AnnotationConfigApplicationContext(DemoHikariApplication.class);

//        for (String beanName : applicationContext.getBeanDefinitionNames()) {
//            System.out.println(beanName);
//        }

        UserService userService = applicationContext.getBean("userService", UserService.class);
        ProductService productService = applicationContext.getBean("productService", ProductService.class);

        productService.deleteAll();
        userService.deleteAll();

        User user1 = new User("Ivan Ivanov");
        userService.save(user1);

        User user2 = new User("Petr Petrov");
        long user2_id = userService.save(user2);

        System.out.println("user1.getId() = " + user1.getId() + "; user2.getId() = " + user1.getId());

        List<User> userList = userService.getUsers();
        System.out.println("User list:");
        for (User usr:userList) {
            System.out.println(usr.getUsername());
        }

        // поменяем имя пользователся
        userService.updateUser(user2_id, "Sergey Sergeyev");
        System.out.println(userService.getUser(user2_id).getUsername());

        // добавляем продукты к первому юзеру
        long product1 = productService.save(new Product(user1.getId(), "42301810503434285787", new BigDecimal(1000.02), ProdType.счет));
        long product2 = productService.save(new Product(user1.getId(), "40817810306724176375", new BigDecimal(5000.05), ProdType.карта));

        // распечатаем продукты
        productService.getProducts(user1.getId()).stream().forEach(x -> System.out.println(x.getAccount()));

        // получим продукт по id
        Product product = productService.getProduct(product1);
        if (product != null)
            System.out.println("Номер счета продукта с id = " + product1 + ": " + product.getAccount());

        // попытаемся получить продукт по несуществующему id
        long idx_15 = 15;
        Product product15 = productService.getProduct(idx_15);
        if (product15 != null)
            System.out.println("Номер счета продукта с id = " + product15 + ": " + product15.getAccount());
    }
}
