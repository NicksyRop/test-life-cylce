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
    String createdUserId=""; //todo create this as a member variable so we can use it to store and retrieve stores ids

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
         createdUserId = userService.createUser(user);
        //assert
        Assertions.assertNotNull(createdUserId,"User id should not be null");
    }


    @Test
    @Order(2)
    @DisplayName("Update user works")
    void testUpdateUser_whenProvidedWithValidDetails_returnsUpdatedUserDetails() {
        //arrange
        Map<String, String> newUserDetails = new HashMap<>();
        newUserDetails.put("firstName", "Nickson");
        newUserDetails.put("lastName", "Doe");

        //act
        Map updatedUserDetails = userService.updateUser(createdUserId, newUserDetails);
        //Assert to compare if the two maps are equak
        Assertions.assertEquals(newUserDetails.get("firstName"), updatedUserDetails.get("firstName") ,"Update user did not produce correct map");
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
