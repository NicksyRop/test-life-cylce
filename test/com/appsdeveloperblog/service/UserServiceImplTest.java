package com.appsdeveloperblog.service;

import com.appsdeveloperblog.io.UsersDatabase;
import com.appsdeveloperblog.io.UsersDatabaseMapImpl;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {

    /**
     * @BeforeAll and  @AfterAll do not need to be static  when we use TestInstance.Lifecycle.PER_CLASS
     * default method TestInstance.Lifecycle.PER_METHOD will require the methods static
     */

   // private UserServiceImpl userService;
    UsersDatabase usersDatabase;
    UserService userService;

    @BeforeAll
    void setup() {
        // Create & initialize database
        usersDatabase = new UsersDatabaseMapImpl();
        usersDatabase.init();
        userService = new UserServiceImpl(usersDatabase);
    }

    @AfterAll
    void cleanup() {
        // Close connection
        // Delete database
        usersDatabase.close();
    }

    @Test
    @Order(1)
    @DisplayName("Create User works")
    void testCreateUser_whenProvidedWithValidDetails_returnsUserId() {
        //arrange - prepare user details to be stored
        Map<String, String> user = new HashMap<>();
        user.put("firstName", "John");
        user.put("lastName", "Doe");

        //act  -  invoke method under test 
        String createdUserId = userService.createUser(user);

        //assert
        Assertions.assertNotNull(createdUserId,"User id should not be null");
    }


    @Test
    @Order(2)
    @DisplayName("Update user works")
    void testUpdateUser_whenProvidedWithValidDetails_returnsUpdatedUserDetails() {

    }

    @Test
    @Order(3)
    @DisplayName("Find user works")
    void testGetUserDetails_whenProvidedWithValidUserId_returnsUserDetails() {

    }

    @Test
    @Order(4)
    @DisplayName("Delete user works")
    void testDeleteUser_whenProvidedWithValidUserId_returnsUserDetails() {

    }

}
