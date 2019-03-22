package menu;

import models.Pet;
import requests.PetRequests;

import java.io.IOException;
import java.util.Scanner;

public class MenuPet {
    private PetRequests petRequests = new PetRequests();
    private Scanner input = new Scanner(System.in);

    public MenuPet() throws IOException {
        show();
    }

    private void show() throws IOException {
        System.out.println("\n * Меню тварин * ");
        System.out.println("Виберіть дію з переліку:" +
                "\n 1 - GET/pet/{petId} - Find pet by ID" +
                "\n 2 - GET/pet/findByStatus - Finds Pets by status" +
                "\n 3 - POST/pet - Add a new pet to the store" +
                "\n 4 - PUT/pet - Update an existing pet" +
                "\n 5 - POST/pet/{petId} - Updates a pet in the store with form data" +
                "\n 6 - POST/pet/{petId}/uploadImage - uploads an image" +
                "\n 7 - DELETE/pet/{petId} - Deletes a pet" +
                "\n del - Видаляє Pets в діапазоні id" + // Additional function
                "\n s - повернутися до головного меню" +
                "\n x - завершити роботу програми");

        System.out.print("\nВведіть символ вибраної дії: ");
        String numberObj = input.next();
        switch (numberObj) {
            case "1":
                findPetByID();
                show();
                break;
            case "2":
                findsPetsByStatus();
                show();
                break;
            case "3":
                addNewPet();
                show();
                break;
            case "4":
                updateExistingPet();
                show();
                break;
            case "5":
                updatesPetWithFormData();
                show();
                break;
            case "6":
                uploadImage();
                show();
                break;
            case "7":
                deletePetByID();
                show();
                break;
            case "del": // Additional function - "Delete in diapason of id"
                petRequests.Delete();
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


    private void findPetByID() {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть ID тварини: ");
        Integer petId = input.nextInt();
        petRequests.getPetByID(petId);
        System.out.println("-------------------------------------------");
    }


    private void findsPetsByStatus() {
        System.out.println("-------------------------------------------");
        Pet.PetStatus petStatus = petRequests.choosePetStatus();
        petRequests.findsPetsByStatus(petStatus);
        System.out.println("-------------------------------------------");
    }


    private void addNewPet() throws IOException {
        System.out.println("-------------------------------------------");
        petRequests.addPet();
        System.out.println("-------------------------------------------");
    }


    private void updateExistingPet() throws IOException {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть ID тварини: ");
        Integer petId = input.nextInt();
        petRequests.updateExistingPet(petId);
        System.out.println("-------------------------------------------");
    }


    private void updatesPetWithFormData() throws IOException {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть ID тварини: ");
        Integer petId = input.nextInt();
        petRequests.updatesPetWithFormData(petId);
        System.out.println("-------------------------------------------");
    }


    private void uploadImage() throws IOException {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть ID тварини: ");
        Integer petId = input.nextInt();
        petRequests.uploadImage(petId);
        System.out.println("-------------------------------------------");
    }


    private void deletePetByID() {
        System.out.println("-------------------------------------------");
        System.out.print("Введіть ID тварини: ");
        Integer petId = input.nextInt();
        petRequests.deletePetByID(petId);
        System.out.println("-------------------------------------------");
    }
}