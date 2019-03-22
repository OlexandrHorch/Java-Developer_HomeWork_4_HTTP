package menu;

import requests.StoreRequests;

import java.io.IOException;
import java.util.Scanner;

public class MenuStore {
    private StoreRequests storeRequests = new StoreRequests();
    private Scanner input = new Scanner(System.in);

    public MenuStore() throws IOException {
        show();
    }

    private void show() throws IOException {
        System.out.println("\n * Меню магазина * ");
        System.out.println("Виберіть дію з переліку:" +
                "\n 1 - GET/store/order/{orderId} - Find purchase order by ID" +
                "\n 2 - GET/store/inventory - Returns pet inventories by status" +
                "\n 3 - POST/store/order - Place an order for a pet" +
                "\n 4 - DELETE/store/order/{orderId} - Delete purchase order by ID" +
                "\n s - повернутися до головного меню" +
                "\n x - завершити роботу програми");

        System.out.print("\nВведіть символ вибраної дії: ");
        String numberObj = input.next();
        switch (numberObj) {
            case "1":
                findOrderByID();
                show();
                break;
            case "2":
                petInventoriesByStatus();
                show();
                break;
            case "3":
                placeOrder();
                show();
                break;
            case "4":
                deleteOrderByID();
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

    private void findOrderByID() {
        System.out.print("Введіть ID замовлення: ");
        Integer orderId = input.nextInt();

        System.out.println("-------------------------------------------");
        storeRequests.findOrderByID(orderId);
        System.out.println("-------------------------------------------");
    }

    private void petInventoriesByStatus() throws IOException {
        System.out.println("-------------------------------------------");
        storeRequests.petInventoriesByStatus();
        System.out.println("-------------------------------------------");
    }

    private void placeOrder() throws IOException {
        System.out.println("-------------------------------------------");
        storeRequests.placeOrder();
        System.out.println("-------------------------------------------");
    }

    private void deleteOrderByID() {
        System.out.print("Введіть ID замовлення: ");
        Integer orderId = input.nextInt();

        System.out.println("-------------------------------------------");
        storeRequests.deleteOrderByID(orderId);
        System.out.println("-------------------------------------------");
    }
}