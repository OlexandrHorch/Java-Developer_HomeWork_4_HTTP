package requests;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Order;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import service.ConnectionService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StoreRequests {
    private HttpURLConnection connection;
    private ConnectionService connectionService = new ConnectionService();
    private Scanner input = new Scanner(System.in);
    private Order order = new Order();


    public void findOrderByID(int orderId) {
        String query = "http://petstore.swagger.io/v2/store/order/" + orderId;

        try {
            connection = connectionService.makeConnection(null, query, "GET");
            readResponse(connection);

        } catch (Throwable cause) {
            cause.printStackTrace();

        } finally {
            connectionService.disconnectConnection(connection);
        }
    }


    public void petInventoriesByStatus() throws IOException {
        String jsonString = Jsoup.connect("https://petstore.swagger.io/v2/store/inventory")
                .ignoreContentType(true)
                .get()
                .text();

        Map<String, Integer> petQuantityByStatus = new ObjectMapper().readValue(jsonString, HashMap.class);

        System.out.println("|--------------------|---------------|");
        System.out.println("|       Статус       |   Кількість   |");
        System.out.println("|--------------------|---------------|");
        for (Map.Entry<String, Integer> item : petQuantityByStatus.entrySet()) {
            System.out.printf("%s%-19s%s%9d%s\n", "| ", item.getKey(), "|", item.getValue(), "      |");
            System.out.println("|--------------------|---------------|");
        }
    }


    public void placeOrder() throws IOException {
        Order order = fillOrder();
        String jsonOrder = serializeObject(order);
        Document document = Jsoup
                .connect("https://petstore.swagger.io/v2/store/order")
                .header("Content-Type", "application/json")
                .ignoreContentType(true)
                .requestBody(jsonOrder).post();
        System.out.println("Iнформація про замовлення додана.");
        findOrderByID(order.getId());
    }


    private Order fillOrder() {
        System.out.print("Введіть ID замовлення: ");
        order.setId(input.nextInt());

        System.out.print("Введіть ID тварини: ");
        order.setPetId(input.nextInt());

        System.out.print("Введіть кількість: ");
        order.setQuantity(input.nextInt());

        order.setShipDate(String.valueOf(LocalDateTime.now()));

        order.setStatus(chooseOrderStatus());

        order.setComplete(false);

        return order;
    }

    private Order.OrderStatus chooseOrderStatus() {
        Order.OrderStatus status = null;
        String numberObj;

        System.out.println("Виберіть статус замовлення:" +
                "\n 1 - розміщене (placed)" +
                "\n 2 - затверджено (approved)" +
                "\n 3 - доставлено (delivered)");

        System.out.print("\nВведіть символ вибраного статусу: ");
        numberObj = input.next();

        switch (numberObj) {
            case "1":
                status = Order.OrderStatus.PLACED;
                break;
            case "2":
                status = Order.OrderStatus.APPROVED;
                break;
            case "3":
                status = Order.OrderStatus.DELIVERED;
                break;
            default:
                System.out.println("* * * * * * * * * * * * * * * * * * * *\n" +
                        "* Невідома команда! Спробуйте ще раз. *\n" +
                        "* * * * * * * * * * * * * * * * * * * *\n");
                chooseOrderStatus();
                break;
        }
        return status;
    }


    public void deleteOrderByID(int orderId) {
        String query = "https://petstore.swagger.io/v2/store/order/" + orderId;

        try {
            connection = connectionService.makeConnection(null, query, "DELETE");
            readResponseForDelete(connection, orderId);

        } catch (Throwable cause) {
            cause.printStackTrace();

        } finally {
            connectionService.disconnectConnection(connection);
        }
    }


    private void printOrder(Order order) {
        System.out.println("id - " + order.getId()
                + "\npetId - " + order.getPetId()
                + "\nquantity - " + order.getQuantity()
                + "\nshipDate - " + order.getShipDate()
                + "\nstatus - " + order.getStatus().getTranslation()
                + "\ncomplete - " + order.isComplete());
    }


    private void readResponse(HttpURLConnection connection) throws IOException {
        String line;
        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while (( line = in.readLine() ) != null) {
                order = (Order) parseFromJson(line);
                System.out.println("Iнформація про замовлення:");
                printOrder(order);
            }
        } else {
            System.out.println("Iнформація про замовлення не знайдена.");
        }
    }


    private void readResponseForDelete(HttpURLConnection connection, int orderId) throws IOException {
        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            System.out.println("Iнформація про замовлення з ID " + orderId + " видалена.");
        } else {
            System.out.println("Iнформація про замовлення з ID " + orderId + " не знайдена.");
        }
    }


    private static String serializeObject(Object object) {
        String json = JSON.toJSONString(object);
        return json;
    }

    private static Object parseFromJson(String json) {
        Object object = JSON.parseObject(json, Order.class);
        return object;
    }
}