package com.ProjektInzynierski.BackEnd.data.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

@Setter
@Getter
public class UserData {

    @Setter(AccessLevel.NONE)
    private String email;

    @Setter(AccessLevel.NONE)
    private String password;

    private String token;

    public UserData(String email, String password) {
        this.email = createEmail(email);
        this.password = this.hashPassword(password);
    }

    public UserData(String email) {
        this.email = createEmail(email);
    }

    void setEmail(String email) {
        this.email = createEmail(email);
    }

    void setPassword(String password) {
        this.password = hashPassword(password);
    }

    private String createEmail(String email) {
        return email.toLowerCase();
    }

    private String hashPassword(String password) {
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        byte[] digest = digestSHA3.digest(password.getBytes());

        return Hex.toHexString(digest);
    }
}
