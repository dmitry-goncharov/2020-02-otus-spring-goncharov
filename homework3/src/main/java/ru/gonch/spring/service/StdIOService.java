package ru.gonch.spring.service;

import java.util.Scanner;

public class StdIOService implements IOService<String, String> {
    private static final String CHARSET_NAME = "UTF-8";

    private final Scanner in = new Scanner(System.in, CHARSET_NAME);

    @Override
    public String receiveMessage() {
        String line = null;
        if (in.hasNextLine()) {
            line = in.nextLine();
        }
        return line;
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }
}
