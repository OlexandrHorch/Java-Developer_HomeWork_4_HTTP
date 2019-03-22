package requests;

import com.alibaba.fastjson.JSON;
import menu.MenuPet;
import models.Category;
import models.Pet;
import models.Tag;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import service.ConnectionService;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PetRequests extends Component {
    private HttpURLConnection connection;
    private ConnectionService connectionService = new ConnectionService();
    private Scanner input = new Scanner(System.in);
    private Pet pet;
    private Category category = new Category();
    private Tag tag = new Tag();
    private ArrayList<String> petPhotoURL = new ArrayList<>();
    private ArrayList<Tag> tags = new ArrayList<>();


    public Pet getPetByID(Integer petId) {
        String query = "https://petstore.swagger.io/v2/pet/" + petId;
        pet = null;

        try {
            connection = connectionService.makeConnection(null, query, "GET");
            pet = readResponse(connection);

        } catch (Throwable cause) {
            cause.printStackTrace();

        } finally {
            connectionService.disconnectConnection(connection);
        }
        return pet;
    }


    public void findsPetsByStatus(Pet.PetStatus petStatus) {
        String query = "https://petstore.swagger.io/v2/pet/findByStatus?status=" + petStatus.getTranslation();

        try {
            connection = connectionService.makeConnection(null, query, "GET");
            System.out.println("Iнформація про тварин зі статусом " + petStatus.getTranslation() + ":");
            readResponseForByStatus(connection);

        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            connectionService.disconnectConnection(connection);
        }
    }


    public void addPet() throws IOException {
        pet = null;
        pet = fillPet();
        String jsonPet = serializeObject(pet);
        Jsoup.connect("https://petstore.swagger.io/v2/pet")
                .header("Content-Type", "application/json")
                .ignoreContentType(true)
                .requestBody(jsonPet)
                .post();

        System.out.println("Iнформація про тварину додана.");
    }


    public void updatesPetWithFormData(Integer petId) throws IOException {
        String url = "https://petstore.swagger.io/v2/pet/";
        pet = null;
        pet = getPetByID(petId);
        if (pet != null) {
            fillPetName();
            fillPetStatus();

            Jsoup.connect(url + petId)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .data("name", pet.getName())
                    .data("status", pet.getStatus().getTranslation())
                    .ignoreContentType(true)
                    .post();
        }
    }


    public void uploadImage(Integer petId) throws IOException {
        String url = "https://petstore.swagger.io/v2/pet/" + petId + "/uploadImage";
        pet = null;
        pet = getPetByID(petId);
        if (pet != null) {
            System.out.print("Введіть коментар до фото: ");
            String message = input.next();

            File file = new File(getPathToFile());
            FileInputStream fileInputStream = new FileInputStream(file);

            Jsoup.connect(url)
                    .data("additionalMetadata", message)
                    .data("file", file.getName(), fileInputStream)
                    .ignoreContentType(true)
                    .method(Connection.Method.POST)
                    .execute()
                    .parse();

            System.out.println("Id тварини: " + pet.getId() + ". "
                    + "\nКоментар до фото: " + message + ". "
                    + "\nШляш до фото: " + file.getPath());
        }
    }

    private String getPathToFile() {
        System.out.println("Виберіть файл в вікні \"Вибір файла\"");
        String startPath = "C:\\"; // Стартовий шлях вибору файла
        File file = new File(startPath);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setDialogTitle("Вибір файла"); // ім'я вікна
        fileChooser.setCurrentDirectory(file);
        String pathToFile = null;
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            pathToFile = file.getPath();
        }

        return pathToFile;
    }


    public void updateExistingPet(Integer petId) throws IOException {
        pet = null;
        pet = getPetByID(petId);
        if (pet != null) {
            updateExistingPetChooseWhatUpdate();
        }
    }

    private void updateExistingPetChooseWhatUpdate() throws IOException {
        System.out.println("Виберіть дію з переліку:" +
                "\n 1 - змінити category" +
                "\n 2 - змінити name" +
                "\n 3 - змінити photoUrls" +
                "\n 4 - змінити tags" +
                "\n f - застосувати зміни" +
                "\n p - повернутися до попереднього меню без застосування змін");

        System.out.print("\nВведіть символ вибраної дії: ");
        String numberObj = input.next();
        switch (numberObj) {
            case "1":
                fillNewPetCategory();
                updateExistingPetChooseWhatUpdate();
                break;
            case "2":
                fillNewPetName();
                updateExistingPetChooseWhatUpdate();
                break;
            case "3":
                fillNewPetPhotoURL();
                updateExistingPetChooseWhatUpdate();
                break;
            case "4":
                fillNewPetTag();
                updateExistingPetChooseWhatUpdate();
                break;
            case "f":
                putPet(pet);
                break;
            case "p":
                new MenuPet();
                pet = null;
                break;
            default:
                System.out.println("* * * * * * * * * * * * * * * * * * * *\n" +
                        "* Невідома команда! Спробуйте ще раз. *\n" +
                        "* * * * * * * * * * * * * * * * * * * *");
                updateExistingPetChooseWhatUpdate();
        }
    }

    private void putPet(Pet pet) throws IOException {
        String jsonPet = serializeObject(pet);
        Jsoup.connect("https://petstore.swagger.io/v2/pet")
                .header("Content-Type", "application/json")
                .ignoreContentType(true)
                .requestBody(jsonPet)
                .method(Connection.Method.PUT)
                .execute()
                .parse();
        System.out.println("Iнформація про тварину оновлена.");
    }


    public void deletePetByID(Integer petId) {
        String query = "https://petstore.swagger.io/v2/pet/" + petId;

        try {
            connection = connectionService.makeConnection(null, query, "DELETE");
            readResponseForDelete(connection, petId);

        } catch (Throwable cause) {
            cause.printStackTrace();

        } finally {
            connectionService.disconnectConnection(connection);
        }
    }


    private Pet readResponse(HttpURLConnection connection) throws IOException {
        pet = null;
        String line;
        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while (( line = in.readLine() ) != null) {
                pet = (Pet) parseFromJson(line);
                System.out.println("Iнформація про тварину:");
                printPet(pet);
            }
        } else {
            System.out.println("Iнформація про тварину не знайдена.");
        }
        return pet;
    }


    private void readResponseForByStatus(HttpURLConnection connection) throws IOException {
        String line;
        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while (( line = in.readLine() ) != null) {
                System.out.println(line);
            }
        } else {
            System.out.println("Iнформація про тварину не знайдена.");
        }
    }


    private void readResponseForDelete(HttpURLConnection connection, Integer petId) throws IOException {
        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            System.out.println("Iнформація про тварину з ID " + petId + " видалена.");
        } else {
            System.out.println("Iнформація про тварину з ID " + petId + " не знайдена.");
        }
    }


    private void printPet(Pet pet) {
        System.out.println("id - " + pet.getId()
                + "\ncategory - " + pet.getCategory()
                + "\nname - " + pet.getName()
                + "\nphotoUrls - " + pet.getPhotoUrls()
                + "\ntags - " + pet.getTags()
                + "\nstatus - " + pet.getStatus().getTranslation());
    }


    private Pet fillPet() {
        pet = new Pet(fillPetId(),
                fillPetCategory(),
                fillPetName(),
                fillPetPhotoURL(),
                fillPetTag(),
                fillPetStatus());
        return pet;
    }

    private Integer fillPetId() {
        System.out.print("Введіть ID тварини: ");
        return input.nextInt();
    }

    private String fillPetName() {
        System.out.print("Введіть ім'я тварини: ");
        return input.next();
    }

    private void fillNewPetName() {
        System.out.print("Введіть нове ім'я тварини: ");
        pet.setName(input.next());
    }

    private Category fillPetCategory() {
        System.out.print("Введіть ID категорії: ");
        category.setId(input.nextInt());
        System.out.print("Введіть назву категорії: ");
        category.setName(input.next());
        return category;
    }

    private void fillNewPetCategory() {
        System.out.print("Введіть новий ID категорії: ");
        category.setId(input.nextInt());
        System.out.print("Введіть нову назву категорії: ");
        category.setName(input.next());
    }

    private List<String> fillPetPhotoURL() {
        System.out.print("Введіть URL фотографії тварини: ");
        petPhotoURL.add(input.next());
        return petPhotoURL;
    }

    private void fillNewPetPhotoURL() {
        petPhotoURL.clear();
        System.out.print("Введіть новий URL фотографії тварини: ");
        petPhotoURL.add(input.next());
        pet.setPhotoUrls(petPhotoURL);
    }

    private List<Tag> fillPetTag() {
        System.out.print("Введіть ID тега: ");
        tag.setId(input.nextInt());
        System.out.print("Введіть назву тега: ");
        tag.setName(input.next());
        tags.add(tag);
        return tags;
    }

    private void fillNewPetTag() {
        tags.clear();
        System.out.print("Введіть новий ID тега: ");
        tag.setId(input.nextInt());
        System.out.print("Введіть нову назву тега: ");
        tag.setName(input.next());
        tags.add(tag);
        pet.setTags(tags);
    }

    private Pet.PetStatus fillPetStatus() {
        return choosePetStatus();
    }

    public Pet.PetStatus choosePetStatus() {
        Pet.PetStatus status = null;
        String numberObj;

        System.out.println("Виберіть статус тварин:" +
                "\n 1 - доступні (available)" +
                "\n 2 - очікуються (pending)" +
                "\n 3 - продані (sold)");

        System.out.print("\nВведіть символ вибраного статусу: ");
        numberObj = input.next();

        switch (numberObj) {
            case "1":
                status = Pet.PetStatus.AVAILABLE;
                break;
            case "2":
                status = Pet.PetStatus.PENDING;
                break;
            case "3":
                status = Pet.PetStatus.SOLD;
                break;
            default:
                System.out.println("* * * * * * * * * * * * * * * * * * * *\n" +
                        "* Невідома команда! Спробуйте ще раз. *\n" +
                        "* * * * * * * * * * * * * * * * * * * *\n");
                choosePetStatus();
                break;
        }
        return status;
    }


    // Additional task - "Delete from start id to finish id"
    public void Delete() {
        System.out.print("Введіть id з якого почати видалення: ");
        Integer startId = input.nextInt();
        System.out.print("Введіть id яким завершити видалення: ");
        Integer finishId = input.nextInt();

        for (Integer petId = startId; petId <= finishId; petId++) {
            deletePetByID(petId);
        }
    }


    private static String serializeObject(Object object) {
        String json = JSON.toJSONString(object);
        return json;
    }

    private static Object parseFromJson(String json) {
        Object object = JSON.parseObject(json, Pet.class);
        return object;
    }
}