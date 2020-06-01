package com.ProjektInzynierski.BackEnd.data;

import com.ProjektInzynierski.BackEnd.data.model.UserData;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

/**
 * This class is responsible for creating new userData object with hashed password and lower case email
 */
public class UserDataFactory {

    public static UserData create(String email, String password) {

        String lowerCaseEmail;

        if (email != null && !email.isEmpty()) {
            lowerCaseEmail = createEmail(email);
        } else {
            lowerCaseEmail = null;
        }
        String hashedPassword = hashPassword(password);

        return new UserData(lowerCaseEmail, hashedPassword);
    }

    public static UserData create(String email) {

        String lowerCaseEmail = createEmail(email);

        return new UserData(lowerCaseEmail);
    }

    private static String createEmail(String email) {
        return email.toLowerCase();
    }

    private static String hashPassword(String password) {
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        byte[] digest = digestSHA3.digest(password.getBytes());

        return Hex.toHexString(digest);
    }
}
