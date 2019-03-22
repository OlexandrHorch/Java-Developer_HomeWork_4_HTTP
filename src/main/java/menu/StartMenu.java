package menu;

import java.io.IOException;
import java.util.Scanner;

public class StartMenu {
    private Scanner input = new Scanner(System.in);

    public StartMenu() throws IOException {
        show();
    }

    private void show() throws IOException {
        System.out.println("\n\n * ГОЛОВНЕ МЕНЮ * ");
        System.out.println("Виберіть дію з переліку:" +
                "\n 1 - перейти до тварин" +
                "\n 2 - перейти до магазину" +
                "\n 3 - перейти до користувачів" +
                "\n x - завершити роботу програми");

        System.out.print("\nВведіть символ вибраного об'єкта: ");
        String numberObj = input.next();
        switch (numberObj) {
            case "1":
                new MenuPet();
                break;
            case "2":
                new MenuStore();
                break;
            case "3":
                new MenuUser();
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
}