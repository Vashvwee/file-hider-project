package com.filehider;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to File Hider");
        System.out.println("1. Register");
        System.out.println("2. Login");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            AuthenticationService.register(email, password);
        } else if (choice == 2) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (AuthenticationService.login(email, password)) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid email or password.");
            }
        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}

