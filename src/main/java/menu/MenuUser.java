package menu;

import requests.UserRequests;

import java.io.IOException;
import java.util.Scanner;

public class MenuUser {
    private UserRequests userRequests = new UserRequests();
    private Scanner input = new Scanner(System.in);

    public MenuUser() throws IOException {
        show();
    }

    public void show() throws IOException {
        System.out.println("\n * Меню користувача * ");
        System.out.println("Виберіть дію з переліку:" +
                "\n 1 - GET/user/{username} - Get user by user name" +
                "\n 2 - GET/user/login - Logs user into the system" +
                "\n 3 - GET/user/logout - Logs out current logged in user session" +
                "\n 4 - POST/user - Create user" +
                "\n 5 - POST/user/createWithArray - Creates list of users with given input array" +
                "\n 6 - POST/user/createWithList - Creates list of users with given input array" +
                "\n 7 - PUT/user/{username} - Updated user" +
                "\n 8 - DELETE/user/{username} - Delete user" +
                "\n s - повернутися до головного меню" +
                "\n x - завершити роботу програми");

        System.out.print("\nВведіть символ вибраної дії: ");
        String numberObj = input.next();
        switch (numberObj) {
            case "1":
                getUserByUserName();
                show();
                break;
            case "2":
                logsUserIntoSystem();
                show();
                break;
            case "3":
                logsOutCurrentLoggedInUserSession();
                show();
                break;
            case "4":
                createUser();
                show();
                break;
            case "5":
                createUsersWithArray();
                show();
                break;
            case "6":
                createUsersWithList();
                show();
                break;
            case "7":
                updateUser();
                show();
                break;
            case "8":
                deleteUser();
                show();
                break;
            case "s":
                new StartMenu();
                break;
            case "x":
                System.out.print("\nПрограму завершено");
                break;
            default:
                System.out.println("* * * * * * * * * * * * * * * * * * * *\n" +
                        "* Невідома команда! Спробуйте ще раз. *\n" +
                        "* * * * * * * * * * * * * * * * * * * *");
                show();
        }
    }


    private void getUserByUserName() {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть ім'я користувача: ");
        String userName = input.next();
        userRequests.getUserByUserName(userName);
        System.out.println("-------------------------------------------");
    }


    private void logsUserIntoSystem() {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть ім'я користувача: ");
        String userName = input.next();
        System.out.print("Введіть пароль: ");
        String password = input.next();
        System.out.println("Iнформація про вхід в сеанс користувача з ім'ям - " + userName + ":");
        userRequests.logsUserIntoSystem(userName, password);
        System.out.println("-------------------------------------------");
    }


    private void logsOutCurrentLoggedInUserSession() {
        System.out.println("-------------------------------------------");
        System.out.println("Інформація про вихід з поточного сеансу користувача:");
        userRequests.logsOutCurrentLoggedInUserSession();
        System.out.println("-------------------------------------------");
    }


    private void createUser() throws IOException {
        System.out.println("-------------------------------------------");
        userRequests.createUser();
        System.out.println("-------------------------------------------");
    }


    private void createUsersWithArray() throws IOException {
        System.out.println("-------------------------------------------");
        userRequests.createUsersWithArray();
        System.out.println("-------------------------------------------");
    }


    private void createUsersWithList() throws IOException {
        System.out.println("-------------------------------------------");
        userRequests.createUsersWithList();
        System.out.println("-------------------------------------------");
    }


    private void updateUser() throws IOException {
        System.out.println("-------------------------------------------");
        userRequests.updateUser();
        System.out.println("-------------------------------------------");
    }


    private void deleteUser() {
        System.out.print("Введіть ім'я користувача: ");
        String userName = input.next();

        System.out.println("-------------------------------------------");
        userRequests.deleteUser(userName);
        System.out.println("-------------------------------------------");
    }
}