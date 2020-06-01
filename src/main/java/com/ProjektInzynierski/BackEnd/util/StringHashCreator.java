package com.ProjektInzynierski.BackEnd.util;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

/**
 * This class is responsible for creating hash to identify particular answer
 */
public class StringHashCreator {

    public static String createSimpleHash(int[] ids, String email) {

        StringBuilder stringToHash = new StringBuilder();

        for (int id : ids) {
            stringToHash.append(id);
        }
        stringToHash.append(email);

        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest256();
        byte[] digest = digestSHA3.digest(stringToHash.toString().getBytes());

        return Hex.toHexString(digest);
    }

}
