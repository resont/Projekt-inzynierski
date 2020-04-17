package com.ProjektInzynierski.BackEnd.processors.login;

import com.ProjektInzynierski.BackEnd.data.entity.UserEntity;
import com.ProjektInzynierski.BackEnd.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@Disabled
@ExtendWith(MockitoExtension.class)
class LoginProcessorTest {

    private static final String TEST_PASSWORD = "test_password";
    private static final String TEST_EMAIL = "test_email";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String RESULT = "result";
    private static final String ERROR = "error";

    private LoginProcessor loginProcessor;

    @Mock
    private UsersRepository usersRepository;

    @BeforeEach
    void init() {
        loginProcessor = new LoginProcessor(usersRepository);
    }

    @Test
    void shouldProcessLogin() {
        //given
        Map<String, String> map = new HashMap<>();
        map.put(EMAIL, TEST_EMAIL);
        map.put(PASSWORD, TEST_PASSWORD);
        UserEntity userEntity = new UserEntity();
        when(usersRepository.findByEmailAndPassword(Mockito.any(), Mockito.any())).thenReturn(userEntity);

        //when
        Map<String, String> result = loginProcessor.process(map);

        //then
        assertNotNull(result.get(RESULT));
    }

    @Test
    void shouldNotProcessLoginWithWrongValues() {
        //given
        Map<String, String> map = new HashMap<>();
        map.put(EMAIL, TEST_EMAIL);
        UserEntity userEntity = new UserEntity();

        //when
        Map<String, String> result = loginProcessor.process(map);

        //then
        assertNotNull(result.get(ERROR));
    }
}