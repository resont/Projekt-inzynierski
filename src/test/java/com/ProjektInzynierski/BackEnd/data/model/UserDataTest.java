package com.ProjektInzynierski.BackEnd.data.model;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDataTest {

    private static final String TEST_EMAIL = "TeSt_EmAiL";
    private static final String TEST_EMAIL_LC = "test_email";
    private static final String TEST_PASSWORD = "test_password";

    @Test
    void shouldSetLoginToLowerCaseAndHashPassword() {
        //given
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        byte[] digest = digestSHA3.digest(TEST_PASSWORD.getBytes());

        // when
        UserData userData = new UserData(TEST_EMAIL, TEST_PASSWORD);

        //then
        assertEquals(TEST_EMAIL_LC, userData.getEmail());
        assertEquals(Hex.toHexString(digest), userData.getPassword());

    }
}