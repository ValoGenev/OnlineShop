package com.app.project.usertest;

import com.app.project.domain.entities.User;
import com.app.project.exceptions.UserNotFoundException;
import com.app.project.repositories.UserRepository;
import com.app.project.services.impl.UserServiceImpl;
import javafx.beans.binding.When;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    public void setup() throws Exception{


    }

    @Test
    public void deletByIdShouldDeleteUserFromEntity() throws UserNotFoundException {

        User user = new User();
        user.setId(1);
        user.setFirstName("valo");
        user.setLastName("genev");
        user.setEmail("valo_genev@abv.bg");
        user.setPassword("1234");


        when(this.userRepository.getByEmailLike(anyString())).thenReturn(user);

        User returnUser = userService.getUser("test@gmail.com");

        assertNotNull(returnUser);
        assertEquals("valo",returnUser.getFirstName());
    }


    @Test()
    public void testIfGetUserThrowsUserNotFoundException() throws UserNotFoundException {

        when(userRepository.getByEmailLike(anyString())).thenReturn(null);

        Assertions.assertThrows(UserNotFoundException.class,() -> userService.getUser("email"));

    }


}
