package com.app.project.controllers;

import com.app.project.domain.dtos.UserDtos.UserDto;
import com.app.project.domain.dtos.UserDtos.UserLoginDto;
import com.app.project.domain.dtos.UserDtos.UserOrdersDto;
import com.app.project.domain.entities.*;
import com.app.project.repositories.OrderRepository;
import com.app.project.repositories.ProductRepository;
import com.app.project.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Controller
public class AppController implements CommandLineRunner {

   static  Scanner scanner = new Scanner(System.in);
   private UserService userService;
   private PaymentService paymentService;
   private AddressService addressService;
   private ProductService productService;
   private CategoryService categoryService;
   private OrderService orderService;
   private OrderRepository orderRepository;


    @Autowired
    public AppController(UserService userService, PaymentService paymentService, AddressService addressService, ProductService productService, CategoryService categoryService, OrderService orderService, OrderRepository orderRepository) {
        this.userService = userService;
        this.paymentService = paymentService;
        this.addressService = addressService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.orderService = orderService;
        this.orderRepository = orderRepository;

    }


    @Override
    public void run(String... args) throws Exception {



        this.categoryService.seedCategories();
        this.addressService.seedAddresses();
        this.paymentService.seedPayments();
        this.userService.seedUsers();
        this.productService.seedProducts();

        menu();

    }



    private void loginUser() throws IOException {

        String email;
        String password;

        System.out.println("Enter your email: ");
        email=scanner.nextLine();
        System.out.println("Enter your password: ");
        password=scanner.nextLine();

        UserLoginDto userLoginDto = new UserLoginDto(email,password);

        if(this.userService.loginUser(userLoginDto)){

            this.paymentService.setLoggedInUser(email);
            this.productService.setLoggedInUser(email);
            loggedUserMenu();
        }

    }


    private void loggedUserMenu() throws IOException {

        outerloop:
        while(true){

            System.out.println("-----------------------------------------");
            System.out.println("Enter option: ");
            System.out.println("Press (1) to purchase product.");
            System.out.println("Press (2) to check balance.");
            System.out.println("Press (3) to check ULTIMATE BLACK FRIDAY DISCOUNTS.");
            System.out.println("Press (4) to check your shopping cart.");
            System.out.println("Press (5) to logout.");
            System.out.println("Press (6) for account settings or write a support.");
            System.out.println("Press (7) to read check email.");
            System.out.println("------------------------------------------");
            System.out.println("Press (8) to use special commands(ADMIN ONLY).");


            switch(scanner.nextLine()){

                case "1": purchaseProduct(); break;
                case "2": checkBalance(); break;
                case "3": checkDiscount(); break;
                case "4": checkShoppingCart(); break;
                case "5": break outerloop;
                case "6": accountSettings(); break;
                case "7": this.userService.readMessage(); break;
                case "8": if(this.userService.checkIfAdmin()) adminMenu();
                else System.out.println("You don't have permission to open ADMIN menu");break;

                default: System.out.println("Invalid operation"); break;
            }

        }

    }



    private void accountSettings() {

        outerLoop:
        while (true){

            System.out.println("(1) Write a ticket do admin.");
            System.out.println("(2) Change password.");
            System.out.println("(3) Go back to user menu.");


            switch (scanner.nextLine()){

                case "1" :
                    System.out.println("Enter message to admin: ");
                    this.userService.sendMessage(scanner.nextLine());
                    break;

                case "2" :
                    System.out.println("Enter old password: ");
                    String oldPassword = scanner.nextLine();
                    System.out.println("Enter new password: ");
                    String newPassword = scanner.nextLine();

                    System.out.println(this.userService.changePassword(oldPassword, newPassword));
                    break;


                case "3" :   break outerLoop;


            }
        }


    }

    private void adminMenu() throws IOException {

        outerloop:
        while(true){

            System.out.println("Special commands for :");
            System.out.println("(1) products");
            System.out.println("(2) users");
            System.out.println("(3) payments");
            System.out.println("(4) shop");
            System.out.println("(5) tickets");
            System.out.println("(6) random to JSON functions");
            System.out.println("(7) to go back");


            switch (scanner.nextLine()){

                case "1":  adminProducts(); break;
                case "2":  adminUsers();  break;
                case "3":  adminPayments(); break;
                case "4":  adminShop(); break;
                case "5":  if(!adminTickets()) break;
                    System.out.println("Press:");
                    System.out.println("(1) To response ticket.");
                    System.out.println("(2) Go back to admin menu.");
                    String option=scanner.nextLine();
                    if(option.equals("1")) {

                        System.out.println("Enter user id: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.println("Enter response: ");
                        String response = scanner.nextLine();
                        System.out.println(this.userService.responseTicket(id, response));
                        break;
                    }
                    else break;

                case "6": adminGson(); break;
                case "7":  break outerloop;

                default:
                    System.out.println("Wrong operation"); break;

            }

        }

    }

    private void adminGson() throws IOException {

        outerLoop:
        while(true){

            System.out.println("(1) User with his orders TO GSON");
            System.out.println("(2) User TO GSON ");
            System.out.println("(3) To go back.");

            switch(scanner.nextLine()){

                case "1" : this.userService.toGson("src/main/resources/JsonExport/UserOrders.json", UserOrdersDto.class); break;
                case "2" : this.userService.toGson("src/main/resources/JsonExport/UserFullInformation.json", UserDto.class);break;
                case "3" : break outerLoop;
                default:
                    System.out.println("Invalid operation"); break;
            }

        }

    }

    private void adminShop() {

        LocalDate localDate;
        String[] time;

        outerLoop:
        while(true){

            System.out.println("---------------------------------------------------------------------");
            System.out.println("(1) Check Profit after custom period of time. ");
            System.out.println("(2) Check Profit before custom period of time. ");
            System.out.println("(3) Check how much orders were purchased and products were bought after custom period of time.");
            System.out.println("(4) Go back to admin menu.");

            switch (scanner.nextLine()){


                case "1":

                    System.out.println("Please write date and time (format yyyy/m/d)");
                    time = scanner.nextLine().split("/");

                    try{
                        localDate=LocalDate.of(Integer.parseInt(time[0]),Integer.parseInt(time[1]),Integer.parseInt(time[2]));
                        System.out.println(this.orderService.profitAfterDate(localDate));
                    }catch (Exception e){
                        System.out.println("Invalid date input.");
                    }

                    break;

                case "2":

                    System.out.println("Please write date and time (format yyyy/m/d)");
                    time = scanner.nextLine().split("/");

                    try{
                        localDate=LocalDate.of(Integer.parseInt(time[0]),Integer.parseInt(time[1]),Integer.parseInt(time[2]));
                        System.out.println(this.orderService.profitBeforeDate(localDate));
                    }catch (Exception e){
                        System.out.println("Invalid date input.");
                    }

                    break;



                case "3":
                    System.out.println(this.orderService.getPurchaseOrdersProfit());
                    break;

                case "4": break outerLoop;
                default:
                    System.out.println("Wrong operation"); break;
            }



        }


    }


    private boolean adminTickets() {

        try {
            this.userService.getAllUsers().forEach(user -> {
                if (user.getChat() != null) {
                    if (user.getChat().getMessage().length() > 0) {
                        System.out.println(user.getId() + " -> " + user.getFirstName() + " " + user.getLastName());
                        System.out.println("Email: " + user.getEmail());
                        System.out.println("Message:  " + user.getChat().getMessage());
                    }
                }
            });
        }catch (NullPointerException e){
            System.out.println("No tickets found");
            return false;
        }

        return true;

    }

    private void adminPayments() {

        int id;

        outerloop:
        while(true){



            System.out.println("(1) Get users with most transactions");
            System.out.println("(2) Get users with most money spend in orders");
            System.out.println("(3) Get users with specific card type");
            System.out.println("(4) Return user order and retrieve money");
            System.out.println("(5) To go back to admin menu");


            switch (scanner.nextLine()){


                case "1": this.userService.findTopByBalance().forEach(u-> System.out.println(u.toString()));
                    break;

                case "2": this.userService.findUserWithMostSpendedMoney().forEach(u-> System.out.println(u.toString()));
                   break;

                case "3": System.out.println("Write specific payment: ");

                Arrays.stream(PaymentType.values()).forEach(System.out::println);
                String[] stringTypes =  scanner.nextLine().toUpperCase().split(" ");

                this.userService.findAllBySpecificPaymentType(stringTypes)
                        .forEach(u-> System.out.println(u.toString()));
                break;


                case "4":
                    System.out.println("Write down user id ");
                    id=Integer.parseInt(scanner.nextLine());
                    System.out.println(this.userService.getShoppingCart(id));
                    System.out.println("Select order id to return order ");

                    System.out.println(this.orderService.deleteOrder(Integer.parseInt(scanner.nextLine()), id));

                    break;


                case "5": break outerloop;

                default:
                    System.out.println("Wrong operation"); break;


            }



        }
    }

    private void adminUsers() {

        int id;
        String bigDecimal;

        outerLoop:
        while(true){


        System.out.println("(1) print all users");
        System.out.println("(2) print all MALE users");
        System.out.println("(3) print all FEMALE users");
        System.out.println("(4) add balance to specific user ");
        System.out.println("(5) check user shopping cart history");
        System.out.println("(6) go back to admin menu");


        switch(scanner.nextLine()){

            case "1": this.userService.returnAllUser().forEach(u-> System.out.println(u.toString())); break;
            case "2": this.userService.returnAllUserWithGender(Gender.valueOf("MALE")).forEach(u-> System.out.println(u.toString())); break;
            case "3": this.userService.returnAllUserWithGender(Gender.valueOf("FEMALE")).forEach(u-> System.out.println(u.toString())); break;

            case "4":
                System.out.println("Enter id: ");
                id=Integer.parseInt(scanner.nextLine());
                System.out.println("Enter balance you want to add");
                 bigDecimal = scanner.nextLine();
                System.out.println(this.userService.addBalanceToUser(id, new BigDecimal(bigDecimal)));
                break;


            case "5":
                System.out.println("Enter user id: ");
                id=Integer.parseInt(scanner.nextLine());

                System.out.println(this.userService.getShoppingCart(id));

                break;


            case "6": break outerLoop;


            default:
                System.out.println("Wrong operation"); break;
            }
        }

    }

    private void adminProducts() {

        String id;


        outerLoop:
        while(true) {

            System.out.println("(1) Check quantity");
            System.out.println("(2) Change quantity");
            System.out.println("(3) Check discount");
            System.out.println("(4) Change discount");
            System.out.println("(5) Check products out of stock");
            System.out.println("(6) Add new product");
            System.out.println("(7) To delete product by id.");
            System.out.println("(8) Go back");


            switch (scanner.nextLine()) {

                case "1": this.productService.checkQuantity().forEach(p->
                        System.out.println(p.toString())); break;

                case "2":
                    System.out.println("Select product id: ");

                    try{
                        id = scanner.nextLine();
                        System.out.println(this.productService.changeProductQuantity(Integer.parseInt(id)));
                        break;

                    }catch (NumberFormatException e){
                        System.out.println("Wrong input");
                        break;
                    }


                case "3": this.productService.checkDiscount().forEach(p->
                        System.out.println(p.toString())); break;

                case "4":  System.out.println("Select product id: ");

                  try{
                      id = scanner.nextLine();
                      System.out.println(this.productService.changeProductDiscount(Integer.parseInt(id)));
                      break;
                  }catch (NumberFormatException e){
                      System.out.println("Wrong input");
                      break;
                  }

                case "5": this.productService.checkOutOfStock().forEach(p->{
                    System.out.println(p.toString());
                }); break;

                case "6": System.out.println(this.productService.addNewProduct());
                    break;


                case "7":
                    this.productService.showAllProducts().forEach(p-> System.out.println(p.toString()));
                    System.out.println("---------------------------");
                    System.out.println("Select product id to delete");
                    System.out.println(this.productService.deleteProduct(Integer.parseInt(scanner.nextLine())));
                    break;



                case "8": break outerLoop;

                default:
                    System.out.println("Wrong operation"); break;

            }
        }

    }

    private void checkShoppingCart() {

        this.userService.getShoppingCart();
    }

    private void checkDiscount() {

        this.productService.showDiscount();

    }

    private void checkBalance() {

        this.paymentService.printBalanceAndCardType();
    }

    private void purchaseProduct() {


        this.productService.showAllProducts().forEach(product -> System.out.println(product.toString()));

        System.out.println("----------------------------------");
        System.out.println("Press (1) to add filters.");
        System.out.println("Press (2) to skip and purchase.");
        System.out.println("Press (3) to cancel transaction.");

        switch(scanner.nextLine()){
            case "1": addFilters().forEach(product -> System.out.println(product.toString()));

            case "2":
                System.out.println("Type all product id's with space in a single line or (Press (0) to cancel transaction)");

                try{
                    int[] products  =  Arrays.stream(scanner.nextLine().split(" "))
                            .mapToInt(Integer::parseInt)
                            .toArray();

                    if(products[0] == 0) break;

                    System.out.println(this.userService.purchaseProduct(products));

                }catch (NumberFormatException e){
                    System.out.println("Please type only numbers");
                    break;
                }

                break;

            default:
                System.out.println("Wrong operation."); break;
        }

//        scanner.nextLine();
//        System.out.println("Type all product Id's with space in a single line");
//
//        int[] products  =  Arrays.stream(scanner.nextLine().split(" "))
//                .mapToInt(Integer::parseInt)
//                .toArray();
//
//        System.out.println(this.userService.purchaseProduct(products));


    }
    private List<Product> addFilters(){



        System.out.println("Filters Available: ");
        System.out.println("1. Order By Price Asc");
        System.out.println("2. Order By Price Desc");
        System.out.println("3. Order By Discount Asc");
        System.out.println("4. Order By Discount Desc");
        System.out.println("5. Type specific product category");
        System.out.println("6. Products in price range");
        System.out.println("7. Show only products with BLACK FRIDAY discount");
        System.out.println("8. Continue");


        try{
            int[] filters  =  Arrays.stream(scanner.nextLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            return  this.productService.filterProducts(filters);
        }
        catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }


       return this.productService.filterProducts(null);

    }


    void menu() throws IOException {

        System.out.println("Welcome to AliExpress!");

        outerloop:
        while(true){

            System.out.println("Please press one of the options below. ");
            System.out.println("Press (1) to login into your account.");
            System.out.println("Press (2) to create new account.");
            System.out.println("Press (3) to exit.");


            switch (scanner.nextLine()){

                case "1": loginUser();  break;
                case "3": break outerloop;
                default: System.out.println("invalid operation"); break;

            }


        }

    }



}