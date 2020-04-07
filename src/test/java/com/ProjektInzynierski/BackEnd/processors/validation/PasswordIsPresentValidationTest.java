package com.ProjektInzynierski.BackEnd.processors.validation;

import com.ProjektInzynierski.BackEnd.enums.LoginMsg;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PasswordIsPresentValidationTest {

    private static final String TEST_PASSWORD = "testpassword";
    private static final String PASSWORD = "password";
    private static final String ERROR = "error";
    private static final String EMPTY = "";

    private LoginValidationProcessor passwordIsPresentValidation;

    @Mock
    LoginValidationProcessor loginValidationProcessor;

    PasswordIsPresentValidationTest() {
        passwordIsPresentValidation = new PasswordIsPresentValidation(loginValidationProcessor);
    }

    @Test
    void shouldProcessValidationWithEmail() {
        //given
        Map<String, String> map = new HashMap<>();
        map.put(PASSWORD, TEST_PASSWORD);

        //when
        Map<String, String> resultMap = passwordIsPresentValidation.process(map);

        //then
        assertEquals(TEST_PASSWORD, resultMap.get(PASSWORD));
    }

    @Test
    void shouldNotProcessValidationWithemptyEmail() {
        //given
        Map<String, String> map = new HashMap<>();
        map.put(PASSWORD, EMPTY);

        //when
        Map<String, String> resultMap = passwordIsPresentValidation.process(map);

        //then
        assertEquals(LoginMsg.EMPTY_PASSWORD.getErrorMsg(), resultMap.get(ERROR));
    }

}