package com.app.project.services.impl;

import com.app.project.domain.dtos.UserDtos.*;
import com.app.project.domain.entities.*;
import com.app.project.exceptions.NoChatException;
import com.app.project.exceptions.UserNotFoundException;
import com.app.project.repositories.*;
import com.app.project.services.UserService;
import com.app.project.util.FileUtil;
import com.app.project.util.ValidatorUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {


    private final String USER_FILE_PATH="src/main/resources/JsonImport/userSeed.json";
    private UserRepository userRepository;
    private PaymentRepository paymentRepository;
    private AddressRepository addressRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private Set<Integer> uniqueRandomAddresses;
    private Set<Integer> uniqueRandomPayments;
    private Random random;
    private ChatRepository chatRepository;
    private ValidatorUtil validatorUtil;
    private ModelMapper modelMapper;
    private FileUtil fileUtil;
    private Gson gson;
    private String loggedInUser = "";

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, Gson gson, FileUtil fileUtil, PaymentRepository paymentRepository, AddressRepository addressRepository, OrderRepository orderRepository, ProductRepository productRepository, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.modelMapper=modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.paymentRepository = paymentRepository;
        this.addressRepository = addressRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.chatRepository = chatRepository;
        this.random = new Random();
        this.uniqueRandomPayments = new HashSet<>();
        this.uniqueRandomAddresses =new HashSet<>();
    }




    @Override
    @Transactional
    public void seedUsers() throws IOException {

        String information= this.fileUtil.fileContent(USER_FILE_PATH);

        UserSeedDto[] userSeedDtos = this.gson.fromJson(information,UserSeedDto[].class);


        for(UserSeedDto userSeedDto: userSeedDtos){


            if(!validatorUtil.isValid(userSeedDto)){

                validatorUtil.vioations(userSeedDto)
                        .forEach(violation -> System.out.println(violation.getMessage()));

                continue;
            }

            User user = this.modelMapper.map(userSeedDto,User.class);
            user.setAddress(getRandomAddress());
            user.setPayment(getRandomPayment());
            this.userRepository.saveAndFlush(user);

        }

        this.userRepository.findAll().forEach(user -> {

            user.setFriends(getRandomFriends(user.getId()));
            this.userRepository.saveAndFlush(user);

        });


    }

    private Set<User> getRandomFriends(int userId) {

        int usersCount = random.nextInt((int) this.userRepository.count() - 1) + 1;
        int randomNumber;

        Set<User> friends = new HashSet<>();

        while(usersCount > 0){

            randomNumber=random.nextInt((int) this.userRepository.count() - 1) + 1;

            if(randomNumber==userId) continue;

            User user = this.userRepository.findById(userId).orElse(null);

            friends.add(user);

            usersCount--;
        }

        return friends;
    }

    @Override
    public <E> void toGson(String path,Class<E> type) throws IOException {

        List<User> users = this.userRepository.findAll();

        List<E> dtos = new ArrayList<>();

        users.forEach(u->{
            E dto = this.modelMapper.map(u,type);
            dtos.add(dto);
        });

        String content = this.gson.toJson(dtos);

        this.fileUtil.exportJson(path,content);

    }

    @Override
    public boolean checkIfAdmin() {

        User user = this.userRepository.findFirstByEmail(loggedInUser).orElse(null);

        if(user.getRole().equals(Role.ADMIN)) return true;

        return false;
    }

    @Override
    public String getShoppingCart(int... id) {

        StringBuilder sb = new StringBuilder();
        User user;

        if(id.length > 0){
            user = this.userRepository.findById(id[0]).orElse(null);
            if(user==null) return sb.append("no user with such id found").toString();
        }

        else {
            user = this.userRepository.findFirstByEmail(loggedInUser).orElse(null);
        }

        user.getOrders().forEach(order -> {

            System.out.println("Order ID: " + order.getId() + "     Purchased on: "+order.getPurchaseDate());
            order.getProducts().forEach(product ->{
                System.out.println(product.toString());
            });

            System.out.println("--------------");

        });

        return "Shopping cart details";

    }

    @Override
    public User getUser(String email) throws UserNotFoundException {

        User user = this.userRepository.getByEmailLike(email);

        if(user==null){

            throw new UserNotFoundException(email);
        }

        return user;
    }

    @Override
    public List<UserPaymentsCountDto> findTopByBalance() {
        return this.userRepository.findTopByOrdersCount();
    }


    @Override
    public List<User> findAllBySpecificPaymentType(String... paymentType) {

        List<PaymentType> paymentTypes = new ArrayList<>();

        for(String s : paymentType){
            paymentTypes.add(PaymentType.valueOf(s));
        }

        return this.userRepository.findAllByPaymentPaymentTypeIn(paymentTypes);
    }

    @Override
    public List<UserWithMostSpendedMoneyDto> findUserWithMostSpendedMoney() {
        return this.userRepository.findUserWithMostSpendedMoney();
    }

    @Override
    @Transactional
    public String purchaseProduct(int... ids) {

        StringBuilder sb = new StringBuilder();

        Set<Product> products = this.productRepository.findAllByIdIn(ids);

        double sum = products
                .stream()
                .map(product -> product.getPrice().doubleValue() - product.getPrice().doubleValue() * product.getDiscount())
                .reduce(0.0,Double::sum);

        if(products.isEmpty()) return sb.append("No products with such id's exist").toString();

        User user = this.userRepository.findFirstByEmail(loggedInUser).orElse(null);

        if(user.getPayment().getBalance().compareTo(BigDecimal.valueOf(sum)) < 0){
            return sb.append("You dont have enough money").toString();
        }

        user.getPayment().setBalance(user.getPayment().getBalance().subtract(BigDecimal.valueOf(sum)));

        Order order = new Order();
        order.setPurchaseDate(LocalDate.now());
        order.setProducts(products);
        order.setUser(user);

        this.orderRepository.saveAndFlush(order);


        return sb.append("Successfully purchased products.").toString();

    }

    private Address getRandomAddress() {


        int randomNumber = random.nextInt((int) this.addressRepository.count() - 1) + 1;

        while (this.uniqueRandomAddresses.contains(randomNumber)) {
            randomNumber = random.nextInt((int) this.addressRepository.count() - 1) + 1;
        }
        this.uniqueRandomAddresses.add(randomNumber);


        return this.addressRepository.findById(randomNumber).orElse(null);
    }


    private Payment getRandomPayment() {

        int randomNumber = random.nextInt((int) this.paymentRepository.count() - 1) + 1;

        while (this.uniqueRandomPayments.contains(randomNumber)) {
            randomNumber = random.nextInt((int) this.paymentRepository.count() - 1) + 1;
        }
        this.uniqueRandomPayments.add(randomNumber);

        return this.paymentRepository.findById(randomNumber).orElse(null);
    }

    @Override
    public boolean loginUser(UserLoginDto userLoginDto) {

        User user  = this.userRepository.findFirstByEmail(userLoginDto.getEmail()).orElse(null);

        if(user==null){
            System.out.println("Invalid email.");
            return false;
        }

        if(!user.getPassword().equals(userLoginDto.getPassword())){
            System.out.println("Invalid password.");
            return false;
        }

        this.loggedInUser=user.getEmail();

        System.out.println("Welcome back "+user.getFirstName() + " " + user.getLastName() + " ! " +
                checkMail() );

        return true;
    }

    @Override
    public String checkMail() {
        try{
            Chat chat = getLoggedInUser().getChat();
            if(chat==null) throw new NoChatException();

            else if(getLoggedInUser().getChat().getResponse()!=null){
                return " (You have message from admin)";
            }
            else if (getLoggedInUser().getChat().getMessage()!=null){

               return " (Your ticket is still pending on response)";
            }
            else {
               return " (0 notifications)";
            }

        }catch (NoChatException e){
            return " ("+e.getMessage()+")";
        }
    }

    @Override
    public List<User> returnAllUser() {
        return this.userRepository.findAll();
    }

    public void readMessage(){

        try{
            User user  = this.userRepository.findFirstByEmail(loggedInUser).orElse(null);
            Chat chat = user.getChat();
            if(chat==null) throw new NoChatException();

            if(chat.getResponse()!= null){
                System.out.println("Message from ADMIN : " + chat.getResponse());
            }
            else {
                System.out.println("No notifications");
            }

            chat.setMessage(null);
            chat.setResponse(null);
            this.chatRepository.saveAndFlush(chat);



        }catch (NoChatException e){
            e.getMessage();
        }

    }

    @Override
    public List<User> returnAllUserWithGender(Gender gender) {
        return this.userRepository.findAllByGenderLike(gender);
    }

    @Override
    public String addBalanceToUser(int id,BigDecimal bigDecimal) {

        StringBuilder sb = new StringBuilder();

        User user = this.userRepository.findById(id).orElse(null);

        if(user==null) return sb.append("User with such id does not exist").toString();

        BigDecimal oldBalance = user.getPayment().getBalance();

        oldBalance=oldBalance.add(bigDecimal);

        user.getPayment().setBalance(oldBalance);

        this.userRepository.saveAndFlush(user);

        return sb.append(String.format("%f $ were added to %s %s"
                ,bigDecimal,user.getFirstName(),user.getLastName())).toString();

    }

    @Override
    public User getLoggedInUser() {
        return this.userRepository.findFirstByEmail(loggedInUser).orElse(null);
    }

    @Override
    public String changePassword(String oldPassword, String newPassword) {

        User user = this.userRepository.findFirstByEmail(loggedInUser).orElse(null);

        if(!user.getPassword().equals(oldPassword)) return "Incorrect password.";

        user.setPassword(newPassword);

        this.userRepository.saveAndFlush(user);

        return "Password successfully changed. ";
    }

    @Override
    public void sendMessage(String message) {

        User user = this.userRepository.findFirstByEmail(loggedInUser).orElse(null);
        Chat chat = new Chat();

        if(user.getChat()==null){
            chat.setMessage(message);
            chat.setUser(user);
            user.setChat(chat);
        }
        else {
            chat = user.getChat();
            chat.setUser(user);
            chat.setMessage(message);
        }
        this.userRepository.saveAndFlush(user);

    }

    @Override
    public String responseTicket(int id,String response) {

        User user = this.userRepository.findById(id).orElse(null);
        if(user==null){
            return "No user with id "+ id +" exist.";
        }
        System.out.println(user.getChat().getMessage());

        user.getChat().setResponse(response);

        this.userRepository.saveAndFlush(user);

        return "Response sent.";

    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
