package com.ProjektInzynierski.BackEnd.util;

import org.bouncycastle.jcajce.provider.digest.SHA3;

public class StringHashCreator {

    public static String createSimpleHash(String uuid) {
        String stringToHash = String.valueOf(CurrentDateProvider.getCurrentDate().getTime() + uuid);

        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest256();
        byte[] digest = digestSHA3.digest();

        return digest.toString();
    }

}
