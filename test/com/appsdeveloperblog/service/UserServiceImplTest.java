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
     *
     *
     * ----- all this method should run in order , hence a single instance of a test class is required otherwise it will fail
     * "@TestInstance(TestInstance.Lifecycle.PER_CLASS)" configures JUnit 5 to create a single instance of the test class for all test methods.
     * This allows you to share the setup and teardown process for multiple test methods within the same test class,
     * which can be beneficial in situations where initializing or cleaning up resources is time-consuming or complex.
     * By using a single instance for all test methods, you can maintain state and resources between test methods,
     * making this approach more efficient in some cases.
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
        //act
        Map userDetails = userService.getUserDetails(createdUserId);

        //Assert object not null
        Assertions.assertNotNull(userDetails,"Find by User id should return user details");
        //Assert that object is of correct id
        Assertions.assertEquals(createdUserId, userDetails.get("userId"), "User id did not produce correct user id");
    }

    @Test
    @Order(4)
    @DisplayName("Delete user works")
    void testDeleteUser_whenProvidedWithValidUserId_returnsUserDetails() {

        //act - return type is void
        userService.deleteUser(createdUserId);

        //assert that if we try fetching user of createdUserId , will be null
        Assertions.assertNull(userService.getUserDetails(createdUserId), "User should not be found.");

    }

}
