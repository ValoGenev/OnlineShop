package com.app.project.services.impl;

import com.app.project.domain.comparators.ProductDiscountComparator;
import com.app.project.domain.comparators.ProductPriceComparator;
import com.app.project.domain.dtos.ProductDtos.*;
import com.app.project.domain.entities.Category;
import com.app.project.domain.entities.Product;
import com.app.project.domain.entities.ProductInShop;
import com.app.project.exceptions.NoFiltesAddedException;
import com.app.project.repositories.CategoryRepository;
import com.app.project.repositories.ProductRepository;
import com.app.project.services.ProductService;
import com.app.project.util.FileUtil;
import com.app.project.util.ValidatorUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


    private final String PRODUCT_FILE_PATH = "src/main/resources/JsonImport/ProductSeed.json";
    private FileUtil fileUtil;
    private Gson gson;
    private ProductRepository productRepository;
    private ModelMapper modelMapper;
    private ValidatorUtil validatorUtil;
    private String loggedInUser = "";
    private Random random;
    private CategoryRepository categoryRepository;
    private Scanner scanner;


    @Autowired
    public ProductServiceImpl(FileUtil fileUtil, Gson gson, ProductRepository productRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, CategoryRepository categoryRepository) {
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.categoryRepository = categoryRepository;
        this.scanner = new Scanner(System.in);
        this.random=new Random();
    }

    @Transactional
    @Override
    public void seedProducts() throws IOException {

        String information= this.fileUtil.fileContent(PRODUCT_FILE_PATH);

        ProductSeedDto[] productSeedDtos = this.gson.fromJson(information,ProductSeedDto[].class);


        for(ProductSeedDto productSeedDto: productSeedDtos){


            if(!validatorUtil.isValid(productSeedDto)){

                validatorUtil.vioations(productSeedDto)
                        .forEach(violation -> System.out.println(violation.getMessage()));

                continue;
            }

            Product product = this.modelMapper.map(productSeedDto,Product.class);
            product.setProductInShop(new ProductInShop(0,500,BigDecimal.valueOf(0)));
            product.setCategories(getRandomCategories());
            this.productRepository.saveAndFlush(product);

        }
    }

    private Set<Category> getRandomCategories() {

        int categoriesCount = random.nextInt((int) this.categoryRepository.count() - 1) + 1;
        int randomNumber;

        Set<Category> categories = new HashSet<>();


        while(categoriesCount > 0){

            randomNumber=random.nextInt((int) this.categoryRepository.count() - 1) + 1;

            Category category = this.categoryRepository.getOne(randomNumber);

            if(categories.contains(category)) categoriesCount++;

            categories.add(category);


            categoriesCount--;
        }

        return categories;

    }

    @Override
    public List<Product> filterProducts(int[] filters) {


        List<Product> productsToReturn = this.productRepository.findAll();

        try{
            if(filters==null) throw new NoFiltesAddedException();
        }
        catch (NoFiltesAddedException e){
            System.out.println(e.getMessage());
            return productsToReturn;
        }

        for(int filter : filters){

            switch (filter){

                case 1: productsToReturn.sort(new ProductPriceComparator()); break;

                case 2: productsToReturn.sort(new ProductPriceComparator().reversed()); break;

                case 3: productsToReturn.sort(new ProductDiscountComparator()); break;

                case 4: productsToReturn.sort(new ProductDiscountComparator().reversed()); break;

                case 5:
                    System.out.println("1-> sport");
                    System.out.println("2-> electric devices");
                    System.out.println("3-> for kids");
                    System.out.println("4-> family");
                    System.out.println("5-> for the house");
                    System.out.println("6-> gaming");
                    System.out.println("7-> animals");
                    System.out.println("-----------------------");
                    System.out.println("type category: ");
                    String category = scanner.nextLine();

                    productsToReturn = productsToReturn.stream()
                            .filter(product -> {

                                for (Category cat: product.getCategories()){

                                    if(cat.getName().equals(category)) return true;
                                }
                                return false;
                            })
                            .collect(Collectors.toList());
                    break;


                case 6:
                    String lowestPrice;
                    String highestPrice;

                    System.out.println("enter lowest price");
                    lowestPrice=scanner.nextLine();

                    System.out.println("enter highest price");
                    highestPrice=scanner.nextLine();

                    productsToReturn = productsToReturn.stream().filter(product -> {
                        return product.getPrice().compareTo(new BigDecimal(lowestPrice)) >= 0
                                && product.getPrice().compareTo(new BigDecimal(highestPrice)) <= 0;

                    }).collect(Collectors.toList());

                    break;


            }


        }


        return productsToReturn;

    }

    @Override
    public List<Product> showAllProducts() {

        return this.productRepository.findAll();
    }

    @Override
    public void showDiscount() {
        this.productRepository.findAllByOrderByDiscountDesc()
                .forEach(product -> {

                    System.out.println(String.format("DISCOUNT %.2f : Product %s price is lowered to %.2f from %.2f",
                            product.getDiscount(),
                            product.getName(),
                            (product.getPrice().subtract(product.getPrice().multiply(BigDecimal.valueOf(product.getDiscount())))),
                            product.getPrice()));

                });
    }

    @Override
    public void setLoggedInUser(String email) {
        this.loggedInUser=email;
    }

    @Override
    public void setLoggedOutUser() {

    }

    @Override
    public void printShoppingCart() {

    }

    @Override
    public List<Product> checkOutOfStock() {

        return this.productRepository.getOutOfStockProducts();
    }

    @Override
    public List<ProductQuantityDto> checkQuantity() {

        return this.productRepository.getProductQuantity();

    }

    @Override
    public List<ProductDiscountDto> checkDiscount() {
        return this.productRepository.getProductDiscount();
    }

    @Override
    public String changeProductQuantity(int id) {

        StringBuilder sb = new StringBuilder();

        Product product = this.productRepository.findById(id).orElse(null);

        if(product==null) return sb.append("Product with such id does not exist ").toString();


        System.out.println("Enter new quantity: ");

        int quantity = scanner.nextInt();

        product.getProductInShop().setQuantity(quantity);

        this.productRepository.saveAndFlush(product);


        return sb.append(String.format("Product %s quantity was changed to %d",product.getName(),quantity)).toString();
    }


    @Override
    @Transactional
    public String addNewProduct() {

         StringBuilder sb = new StringBuilder();
         String name;
         String description;
         BigDecimal price;
         double discount;
         int quantity;
         scanner.nextLine();

        System.out.println("Enter name ");
        name=scanner.nextLine();
        System.out.println("Enter description ");
        description=scanner.nextLine();
        System.out.println("Enter price");
        price = new BigDecimal(scanner.nextLine());
        System.out.println("Enter discount ");
        discount=scanner.nextDouble();
        System.out.println("Enter quantity");
        quantity=scanner.nextInt();

        System.out.println("Set Category: ");
        this.categoryRepository.findAll().forEach(category -> {
            System.out.println(category.getId() + " -- " + category.getName());
        });

        scanner.nextLine();
        System.out.println("write category id");
        String categories = scanner.nextLine();
        ;
        int[] categoriesId  =  Arrays.stream(categories.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        ProductInShopAddDto productInShopAddDto = new ProductInShopAddDto(quantity);
        AddNewProductDto addNewProductDto = new AddNewProductDto(name,description,price,discount,productInShopAddDto);

        if(!this.validatorUtil.isValid(addNewProductDto)) {
            this.validatorUtil.vioations(addNewProductDto).forEach(vailation -> {
                sb.append(vailation.getMessage()).append(System.lineSeparator());
            });

            return sb.toString();
        }

        Product product = this.modelMapper.map(addNewProductDto,Product.class);
        product.setCategories(getCategoriesById(categoriesId));
        this.productRepository.saveAndFlush(product);


        return sb.append("Product successfully added").toString();
    }


    public Set<Category> getCategoriesById(int... id){

        return this.categoryRepository.findAllByIdIn(id);
    }

    @Override
    @Transactional
    public String deleteProduct(int id) {

        Product product = this.productRepository.findById(id).orElse(null);
        if(product==null) return "Product with such id does not exist.";

        this.productRepository.deleteProductBy(id);
        return "Product  "+ product.getName() +" was deleted. ";


    }

    @Override
    public String changeProductDiscount(int id) {

        StringBuilder sb = new StringBuilder();

        Product product = this.productRepository.findById(id).orElse(null);

        if(product==null) return sb.append("Product with such id does not exist ").toString();


        System.out.println("Enter new discount: ");

        double discount = scanner.nextDouble();

        product.setDiscount(discount);

        this.productRepository.saveAndFlush(product);


        return sb.append(String.format("Product %s discount was changed to %.2f",product.getName(),discount)).toString();
    }
}
