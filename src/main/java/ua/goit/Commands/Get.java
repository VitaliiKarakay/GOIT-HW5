package ua.goit.Commands;

import ua.goit.Console;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLOutput;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

public class Get implements CommandHandler{

    private Scanner scanner;

    public Get(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void handle() {
        while (true) {
            Console.printMethodInstruction("GET");

            String input = scanner.nextLine().toLowerCase();

            if (input.equals("exit")) {
                System.out.println("You will be returned to main menu");
                break;
            }
            if (!Console.entities.contains(input)) {
                Console.printErrorMessage("Unknown request");
            }

            switch (input) {
                case "pet":
                    try {
                        pet();
                    } catch (Exception e) {
                        Console.printErrorMessage("Unknown request");
                    } break;
                case "store":
                    try {
                        store();
                    } catch (Exception e) {
                        Console.printErrorMessage("Unknown request");
                    } break;
                case "user":
                    try {
                        user();
                    } catch (Exception e) {
                        Console.printErrorMessage("Unknown request");
                    } break;
            }
        }
    }

    private void pet() throws URISyntaxException {
        String url = "https://petstore.swagger.io/v2/pet/";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true) {
            System.out.println("You can get pet by id or status\n" + "To get a pet by id - print id\n"
            + "Otherwise print status");

            String input = scanner.nextLine().toLowerCase();

            if (!(input.equals("id") || input.equals("status"))) {
                Console.printErrorMessage("Please enter \"id\" or \"status\"");
                continue;
            }

            if (input.equals("id")) {
                System.out.println("Please enter the id of a pet");

                long id = 0;
                try {
                    id = Long.parseLong(scanner.nextLine());
                } catch (NumberFormatException e) {
                    Console.printErrorMessage("Please enter correct id");
                    continue;
                }

                HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + id)).build();
                try {
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(System.out::println);
                } catch (Exception e) {
                    Console.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }
            } else {
                System.out.println("Please enter one of the following statuses: \n" + Console.statusList);

                String status = scanner.nextLine().toLowerCase();

                if (!Console.statusList.contains(status)) {
                    Console.printErrorMessage("You entered an invalid status");
                    continue;
                }

                HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + "findByStatus?status=" + status)).build();
                try {
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(System.out::println);
                } catch (IOException | InterruptedException e) {
                    Console.printErrorMessage("GET request was unsuccessful, try again.");
                    continue;
                }
            } break;
        }
    }

    private void store() throws URISyntaxException {
        String url = "https://petstore.swagger.io/v2/store/";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true) {
            System.out.println("You can get inventories by status - enter \"inventory\"\n" +
                            "You can find order by id - enter \"order\"\n");

            String input = scanner.nextLine().toLowerCase();

            if (!(input.equals("inventory") || input.equals("order"))) {
                Console.printErrorMessage("Please enter \"inventory\" or \"order\"");
                continue;
            }

            if (input.equals("inventory")) {
                HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + "inventory")).build();
                try {
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(System.out::println);
                } catch (Exception e) {
                    Console.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }
            } else {
                System.out.println("Please enter the id of an order");

                long id = 0;

                try {
                    id = Long.parseLong(scanner.nextLine());
                } catch (NumberFormatException e){
                    Console.printErrorMessage("You entered an invalid id");
                    continue;
                }

                HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + "order/=" + id)).build();
                try {
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(System.out::println);
                } catch (IOException | InterruptedException e) {
                    Console.printErrorMessage("GET request was unsuccessful, try again.");
                    continue;
                }
            } break;
        }
    }

    private void user() throws URISyntaxException {
        String url = "https://petstore.swagger.io/v2/user/";

        HttpClient httpClient = HttpClient.newBuilder().build();



        while (true) {
            System.out.println("You can get user by the username - enter \"get\"\n"
                    + "You can log in into the system with username and password - enter \"login\"\n"
                    + "You can log out from the system - enter \"logout\"\n");

            String input = scanner.nextLine().toLowerCase();

            if (!(input.equals("get") || input.equals("login") || input.equals("logout"))) {
                Console.printErrorMessage("Please enter \"get\" or \"login\" or \"logout\"");
                continue;
            }

            if (input.equals("get")) {
                System.out.println("Please enter the username of a user");

                String username = scanner.nextLine();

                HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + username)).build();
                try {
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(System.out::println);
                } catch (Exception e) {
                    Console.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }
            } else if (input.equals("login")){
                System.out.println("Please enter username");
                String username = scanner.nextLine();
                System.out.println("Please enter password");
                String password = scanner.nextLine();

                HttpRequest request = HttpRequest.newBuilder().uri(new URI(url
                        + "login?username=" + username
                        + "&password=" + password)).build();
                try {
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(System.out::println);
                } catch (IOException | InterruptedException e) {
                    Console.printErrorMessage("GET request was unsuccessful, try again.");
                    continue;
                }
            } else {
                HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + "logout")).build();
                try {
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(System.out::println);
                } catch (IOException | InterruptedException e) {
                    Console.printErrorMessage("GET request was unsuccessful, try again.");
                    continue;
                }
            } break;
        }
    }
}
