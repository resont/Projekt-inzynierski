package com.ProjektInzynierski.BackEnd.util;

import org.bouncycastle.jcajce.provider.digest.SHA3;

public class StringHashCreator {

    public static String createSimpleHash(int[] ids, String email) {

        StringBuilder stringToHash = new StringBuilder();

        for (int i = 0; i < ids.length; i++) {
            stringToHash.append(ids[i]);
        }
        stringToHash.append(email);

        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest256();
        byte[] digest = digestSHA3.digest(stringToHash.toString().getBytes());

        return digest.toString();
    }

}
