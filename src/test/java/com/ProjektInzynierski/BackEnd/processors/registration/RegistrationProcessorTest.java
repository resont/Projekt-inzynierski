package com.ProjektInzynierski.BackEnd.processors.registration;

import com.ProjektInzynierski.BackEnd.data.entity.UserEntity;
import com.ProjektInzynierski.BackEnd.enums.RegistrationMsg;
import com.ProjektInzynierski.BackEnd.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationProcessorTest {

    private static final String TEST_EMAIL = "test_email";
    private static final String TEST_PASSWORD = "test_password";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String CANNOT_BE_EMPTY = "Email cannot be empty.";
    private static final String ERROR = "error";
    private static final String RESULT = "result";
    private static final String EMPTY = "";

    private RegistrationProcessor registrationProcessor;

    @Mock
    private UsersRepository usersRepository;

    @BeforeEach
    void init() {
        registrationProcessor = new RegistrationProcessor(usersRepository);
    }

    @Test
    void shouldProcessRegistration() {
        //given
        Map<String, String> map = getStringStringMap();
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(TEST_EMAIL);
        when(usersRepository.save(Mockito.any())).thenReturn(userEntity);

        //when
        Map<String, String> result = registrationProcessor.process(map);

        //then
        assertEquals(RegistrationMsg.REGISTRY_SUCCESSFUL.getErrorMsg(), result.get(RESULT));
        assertEquals(TEST_EMAIL, result.get(EMAIL));

    }

    @Test
    void shouldNotProcessRegistrationWhenEmailOrPasswordEmpty() {
        //given
        Map<String, String> map = new HashMap<>();
        map.put(EMAIL, EMPTY);
        map.put(PASSWORD, EMPTY);

        //when
        Map<String, String> result = registrationProcessor.process(map);

        //then
        assertEquals(CANNOT_BE_EMPTY, result.get(ERROR));

    }

    private Map<String, String> getStringStringMap() {
        Map<String, String> map = new HashMap<>();
        map.put(EMAIL, TEST_EMAIL);
        map.put(PASSWORD, TEST_PASSWORD);
        return map;
    }

}