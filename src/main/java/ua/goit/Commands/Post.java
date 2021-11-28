package ua.goit.Commands;

import ua.goit.Console;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.LinkPermission;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

public class Post implements CommandHandler {

    private Scanner scanner;

    public Post(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void handle() {
        while (true) {
            Console.printMethodInstruction("POST");

            String input = scanner.nextLine().toLowerCase().trim();
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
                    }
                    break;
                case "store":
                    try {
                        store();
                    } catch (Exception e) {
                        Console.printErrorMessage("Unknown request");
                    }
                    break;
                case "user":
                    try {
                        user();
                    } catch (Exception e) {
                        Console.printErrorMessage("Unknown request");
                    }
                    break;
            }
        }
    }

    private void store() {
        String url = "https://petstore.swagger.io/v2/store/order";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true){
            System.out.println("You can create order by specifying the order json file path\n" + "Please enter the path");
            String path = scanner.nextLine().toLowerCase();

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofFile(Path.of(path))).build();

                HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                System.out.println(response.statusCode());
                response.body().forEach(System.out::println);
            }catch (FileNotFoundException e) {
                Console.printErrorMessage("The specified file could not be found, try again.");
                continue;
            } catch (Exception e) {
                Console.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                continue;
            }
            break;
        }
    }

    private void user() {
        String url = "https://petstore.swagger.io/v2/user/";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true) {
            System.out.println("You can create one or multiple users by specifying the path to a json file\n"
                    + "To create one user - enter \"one\"\n"
                    + "To create multiple users -enter \"multiple\"");

            String input = scanner.nextLine().toLowerCase().trim();

            if (!(input.equals("one") || (input.equals("multiple")))) {
                Console.printErrorMessage("Please enter \"one\" or \"multiple\"");
            }

            if (input.equals("one")) {
                System.out.println("Please print the path to the json file");
                String path = scanner.nextLine();

                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI(url))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofFile(Path.of(path))).build();

                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(System.out::println);
                }catch (FileNotFoundException e) {
                    Console.printErrorMessage("The specified file could not be found, try again.");
                    continue;
                } catch (Exception e) {
                    Console.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }
            } else {
                url += "createWithArray";
                System.out.println("Please print the path to the json file");
                String path = scanner.nextLine();

                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI(url))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofFile(Path.of(path))).build();

                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(System.out::println);
                }catch (FileNotFoundException e) {
                    Console.printErrorMessage("The specified file could not be found, try again.");
                    continue;
                } catch (Exception e) {
                    Console.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }
            }
        }
    }

    private void pet() throws URISyntaxException {
        String url = "https://petstore.swagger.io/v2/pet/";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true) {
            System.out.println("You can upload a picture of pet - enter \"picture\"\n"
                    + "You can create a pet by specifying a path to a Json file - enter \"create\"\n"
                    + "You can update an existing pet by specifying its id new name and new status - enter \"update\"");

            String input = scanner.nextLine().toLowerCase().trim();

            if (!(input.equals("picture") || input.equals("create") || input.equals("update"))) {
                Console.printErrorMessage("Please enter \"picture\" or \"create\" or \"update\"");
                continue;
            }

            if (input.equals("update")) {
                System.out.println("Enter the id of your pet");
                long id;
                try {
                    id = Long.parseLong(scanner.nextLine());
                } catch (NumberFormatException e) {
                    Console.printErrorMessage("Please enter correct id");
                    continue;
                }

                System.out.println("Enter the new name of your pet");
                String name = scanner.nextLine().trim();
                System.out.println("Enter the new status of your pet");
                String status = scanner.nextLine().trim();

                if (!Console.statusList.contains(status)) {
                    Console.printErrorMessage("Invalid status! Try again.");
                    continue;
                }
                HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + id))
                        .POST(HttpRequest.BodyPublishers.ofString("name=" + name + "&status=" + status))
                        .header("Content-Type", "application/x-www-form-urlencoded").build();
                try {
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(System.out::println);
                } catch (Exception e) {
                    Console.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }

            } else if (input.equals("picture")) {
                System.out.println("Please enter the id of a pet");

                long id;
                try {
                    id = Long.parseLong(scanner.nextLine());
                } catch (NumberFormatException e) {
                    Console.printErrorMessage("You entered an invalid id");
                    continue;
                }

                System.out.println("Please specify the path to a picture");
                String path = scanner.nextLine().toLowerCase();
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI(url + id + "/uploadImage"))
                            .header("Content-Type", "image/png")
                            .POST(HttpRequest.BodyPublishers.ofFile(Path.of(path))).build();
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(System.out::println);
                } catch (FileNotFoundException e) {
                    Console.printErrorMessage("File not found");
                    continue;
                } catch (Exception e) {
                    Console.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }
            } else {
                System.out.println("Please specify the path to a user json file");
                String path = scanner.nextLine().toLowerCase();

                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI(url))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofFile(Path.of(path))).build();
                    HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                    System.out.println(response.statusCode());
                    response.body().forEach(System.out::println);
                } catch (FileNotFoundException e) {
                    Console.printErrorMessage("File not found");
                    continue;
                } catch (Exception e) {
                    Console.printErrorMessage("HTTP GET request was unsuccessful, try again.");
                    continue;
                }

            }
            break;
        }
    }
}
