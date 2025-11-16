package com.pruebapaula.pruebapaula.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHashGenerator {

    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        String adminRaw = "Adm1nT3st!";
        String externoRaw = "Ext3rn0T3st!";

        System.out.println("=== ADMIN ===");
        System.out.println("raw: " + adminRaw);
        System.out.println("hash: " + encoder.encode(adminRaw));

        System.out.println("\n=== EXTERNO ===");
        System.out.println("raw: " + externoRaw);
        System.out.println("hash: " + encoder.encode(externoRaw));
    }
}

