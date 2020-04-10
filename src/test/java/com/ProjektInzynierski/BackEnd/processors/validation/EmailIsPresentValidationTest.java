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
class EmailIsPresentValidationTest {

    private static final String TEST_EMAIL = "test@email";
    private static final String EMAIL = "email";
    private static final String ERROR = "error";
    private static final String EMPTY = "";

    private LoginValidationProcessor emailIsPresentValidation;

    @Mock
    LoginValidationProcessor loginValidationProcessor;

    EmailIsPresentValidationTest() {
        emailIsPresentValidation = new EmailIsPresentValidation(loginValidationProcessor);
    }

    @Test
    void shouldProcessValidationWithEmail() {
        //given
        Map<String, String> map = new HashMap<>();
        map.put(EMAIL, TEST_EMAIL);

        //when
        Map<String, String> resultMap = emailIsPresentValidation.process(map);

        //then
        assertEquals(TEST_EMAIL, resultMap.get(EMAIL));
    }

    @Test
    void shouldNotProcessValidationWithemptyEmail() {
        //given
        Map<String, String> map = new HashMap<>();
        map.put(EMAIL, EMPTY);

        //when
        Map<String, String> resultMap = emailIsPresentValidation.process(map);

        //then
        assertEquals(LoginMsg.EMPTY_EMAIL.getErrorMsg(), resultMap.get(ERROR));
    }

}