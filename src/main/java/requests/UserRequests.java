package requests;

import com.alibaba.fastjson.JSON;
import menu.MenuUser;
import models.User;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import service.ConnectionService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class UserRequests {
    private HttpURLConnection connection;
    private ConnectionService connectionService = new ConnectionService();
    private Scanner input = new Scanner(System.in);
    private User user;
    private ArrayList<User> usersList = new ArrayList<>();


    public User getUserByUserName(String userName) {
        String query = "https://petstore.swagger.io/v2/user/" + userName;

        try {
            connection = connectionService.makeConnection(null, query, "GET");
            readResponse(connection);

        } catch (Throwable cause) {
            cause.printStackTrace();

        } finally {
            connectionService.disconnectConnection(connection);
        }

        return user;
    }


    public void logsUserIntoSystem(String userName, String password) {
        String query = "https://petstore.swagger.io/v2/user/login?username=" + userName + "&password=" + password;

        try {
            connection = connectionService.makeConnection(null, query, "GET");
            readResponseForLoginSession(connection);

        } catch (Throwable cause) {
            cause.printStackTrace();

        } finally {
            connectionService.disconnectConnection(connection);
        }
    }


    public void logsOutCurrentLoggedInUserSession() {
        String query = "https://petstore.swagger.io/v2/user/logout";

        try {
            connection = connectionService.makeConnection(null, query, "GET");
            readResponseForLogoutSession(connection);

        } catch (Throwable cause) {
            cause.printStackTrace();

        } finally {
            connectionService.disconnectConnection(connection);
        }
    }


    public void createUser() throws IOException {
        user = fillUser();
        String jsonUser = serializeObject(user);

        Connection.Response response = Jsoup
                .connect("https://petstore.swagger.io/v2/user")
                .header("Content-Type", "application/json")
                .ignoreContentType(true)
                .requestBody(jsonUser)
                .method(Connection.Method.POST)
                .execute();

        System.out.println("statusCode = " + response.statusCode());
        if (response.statusCode() == 200) {
            System.out.println("Iнформація про користувача додана.");
        } else {
            System.out.println("Iнформація про користувача не додана.");
        }
    }


    public void createUsersWithArray() throws IOException {
        User[] usersArray = createUsersArray();
        String jsonUsersArray = serializeObject(usersArray);
        System.out.println(jsonUsersArray);
        Connection.Response response = Jsoup
                .connect("https://petstore.swagger.io/v2/user/createWithArray")
                .header("Content-Type", "application/json")
                .ignoreContentType(true)
                .requestBody(jsonUsersArray)
                .method(Connection.Method.POST)
                .execute();

        System.out.println("statusCode = " + response.statusCode());
        if (response.statusCode() == 200) {
            System.out.println("Iнформація про користувачів додана.");
        } else {
            System.out.println("Iнформація про користувачів не додана.");
        }
    }

    private User[] createUsersArray() {
        User[] usersArray;
        System.out.print("Введіть кількість користувачів для створення: ");
        int quantityUsers = input.nextInt();

        usersArray = new User[quantityUsers];
        for (int u = 0; u < usersArray.length; u++) {
            usersArray[u] = fillUser();
            System.out.println("USER " + u + " = " + usersArray[u].toString());
        }
        return usersArray;
    }


    public void createUsersWithList() throws IOException {
        addUserToList();
        String jsonUsersArray = serializeObject(usersList);
        System.out.println(jsonUsersArray);
        Connection.Response response = Jsoup
                .connect("https://petstore.swagger.io/v2/user/createWithArray")
                .header("Content-Type", "application/json")
                .ignoreContentType(true)
                .requestBody(jsonUsersArray)
                .method(Connection.Method.POST)
                .execute();

        System.out.println("statusCode = " + response.statusCode());
        if (response.statusCode() == 200) {
            System.out.println("Iнформація про користувачів додана.");
        } else {
            System.out.println("Iнформація про користувачів не додана.");
        }
    }

    private void addUserToList() {
        System.out.println("Додати користувача?" +
                "\n Так - введіть 1" +
                "\n Ні  - введіть 2");

        System.out.print("\nВведіть символ вибраної дії: ");
        String numberObj = input.next();
        switch (numberObj) {
            case "1":
                usersList.add(fillUser());
                addUserToList();
                break;
            case "2":
                break;
            default:
                System.out.println("* * * * * * * * * * * * * * * * * * * *\n" +
                        "* Невідома команда! Спробуйте ще раз. *\n" +
                        "* * * * * * * * * * * * * * * * * * * *");
                addUserToList();
        }
    }


    public void updateUser() throws IOException {
        System.out.print("Введіть userName: ");
        String userName = input.next();
        user = getUserByUserName(userName);
        updateExistingUserChooseWhatUpdate();
    }

    private void updateExistingUserChooseWhatUpdate() throws IOException {
        System.out.println("\nВиберіть дію з переліку:" +
                "\n 1 - змінити id" +
                "\n 2 - змінити userName" +
                "\n 3 - змінити firstName" +
                "\n 4 - змінити lastName" +
                "\n 5 - змінити email" +
                "\n 6 - змінити password" +
                "\n 7 - змінити phone" +
                "\n 8 - змінити userStatus" +
                "\n f - застосувати зміни" +
                "\n p - повернутися до попереднього меню без застосування змін");

        System.out.print("\nВведіть символ вибраної дії: ");
        String numberObj = input.next();
        switch (numberObj) {
            case "1":
                fillUserId();
                updateExistingUserChooseWhatUpdate();
                break;
            case "2":
                fillUserName();
                updateExistingUserChooseWhatUpdate();
                break;
            case "3":
                fillUserFirstName();
                updateExistingUserChooseWhatUpdate();
                break;
            case "4":
                fillUserLastName();
                updateExistingUserChooseWhatUpdate();
                break;
            case "5":
                fillUserEmail();
                updateExistingUserChooseWhatUpdate();
                break;
            case "6":
                fillUserPassword();
                updateExistingUserChooseWhatUpdate();
                break;
            case "7":
                fillUserPhone();
                updateExistingUserChooseWhatUpdate();
                break;
            case "8":
                fillUserStatus();
                updateExistingUserChooseWhatUpdate();
                break;
            case "f":
                putUser(user);
                break;
            case "p":
                new MenuUser();
                user = null;
                break;
            default:
                System.out.println("* * * * * * * * * * * * * * * * * * * *\n" +
                        "* Невідома команда! Спробуйте ще раз. *\n" +
                        "* * * * * * * * * * * * * * * * * * * *");
                updateExistingUserChooseWhatUpdate();
        }
    }

    private void putUser(User user) throws IOException {
        String jsonUser = serializeObject(user);

        Connection.Response response = Jsoup
                .connect("https://petstore.swagger.io/v2/user/" + user.getUserName())
                .header("Content-Type", "application/json")
                .data("name", "test")
                .data("password", "abc123")
                .ignoreContentType(true)
                .requestBody(jsonUser)
                .method(Connection.Method.PUT)
                .execute();

        System.out.println("statusCode = " + response.statusCode());
        if (response.statusCode() == 200) {
            System.out.println("Iнформація про користувача оновлена.");
        } else {
            System.out.println("Iнформація про користувача не оновлена.");
        }
    }


    public void deleteUser(String userName) {
        String query = "https://petstore.swagger.io/v2/user/" + userName;

        try {
            connection = connectionService.makeConnection(null, query, "DELETE");
            readResponseForDelete(connection, userName);

        } catch (Throwable cause) {
            cause.printStackTrace();

        } finally {
            connectionService.disconnectConnection(connection);
        }
    }


    private User fillUser() {
        user = new User(fillUserId(),
                fillUserName(),
                fillUserFirstName(),
                fillUserLastName(),
                fillUserEmail(),
                fillUserPassword(),
                fillUserPhone(),
                fillUserStatus());
        return user;
    }

    private Integer fillUserId() {
        System.out.print("Введіть ID користувача: ");
        return input.nextInt();
    }

    private String fillUserName() {
        System.out.print("Введіть user name користувача: ");
        return input.next();
    }

    private String fillUserFirstName() {
        System.out.print("Введіть first name користувача: ");
        return input.next();
    }

    private String fillUserLastName() {
        System.out.print("Введіть last name користувача: ");
        return input.next();
    }

    private String fillUserEmail() {
        System.out.print("Введіть email користувача: ");
        return input.next();
    }

    private String fillUserPassword() {
        System.out.print("Введіть password користувача: ");
        return input.next();
    }

    private String fillUserPhone() {
        System.out.print("Введіть phone користувача: ");
        return input.next();
    }

    private Integer fillUserStatus() {
        System.out.print("Введіть status користувача: ");
        return input.nextInt();
    }


    private void printUser(User user) {
        System.out.println("id - " + user.getId()
                + "\nuser name - " + user.getUserName()
                + "\nfirst name - " + user.getFirstName()
                + "\nlast name - " + user.getLastName()
                + "\nemail - " + user.getEmail()
                + "\npassword - " + user.getPassword()
                + "\nphone - " + user.getPhone()
                + "\nuser status - " + user.getUserStatus());
    }


    private void readResponse(HttpURLConnection connection) throws IOException {
        String line;
        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while (( line = in.readLine() ) != null) {
                user = (User) parseFromJson(line);
                System.out.println("Iнформація про користувача:");
                printUser(user);
            }

        } else {
            System.out.println("Iнформація про користувача не знайдена.");
            System.out.println("-------------------------------------------");
            MenuUser menuUser = new MenuUser();
            menuUser.show();
        }
    }


    private void readResponseForDelete(HttpURLConnection connection, String userName) throws IOException {
        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            System.out.println("Iнформація про користувача з ім'ям " + userName + " видалена.");
        } else {
            System.out.println("Iнформація про користувача з ім'ям " + userName + " не знайдена.");
        }
    }


    private void readResponseForLoginSession(HttpURLConnection connection) throws IOException {
        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            System.out.println(in.readLine());
        } else {
            System.out.println("fail: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
        }
    }


    private void readResponseForLogoutSession(HttpURLConnection connection) throws IOException {
        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            System.out.println("Сеанс завершено.");
        } else {
            System.out.println("fail: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
        }
    }


    private static String serializeObject(Object object) {
        String json = JSON.toJSONString(object);
        return json;
    }

    private static Object parseFromJson(String json) {
        Object object = JSON.parseObject(json, User.class);
        return object;
    }
}