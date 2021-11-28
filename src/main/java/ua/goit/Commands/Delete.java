package ua.goit.Commands;

import ua.goit.Console;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.stream.Stream;

public class Delete implements CommandHandler {

    private Scanner scanner;

    public Delete(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void handle() {
            while (true) {
                Console.printMethodInstruction("DELETE");

                String input = scanner.nextLine().toLowerCase();

                if (input.equals("exit")) {
                    System.out.println("You will be redirected to main console menu");
                    break;
                }

                if (!Console.entities.contains(input)) {
                    Console.printErrorMessage("Your input could not be processed, try again.");
                }

                switch (input) {
                    case "pet":
                        try {
                            pet();
                        } catch (URISyntaxException e) {
                            Console.printErrorMessage("Your request could not be processed, try again.");
                        }
                        break;
                    case "store":
                        try {
                            store();
                        } catch (URISyntaxException e) {
                            Console.printErrorMessage("Your request could not be processed, try again.");
                        }
                        break;
                    case "user":
                        try {
                            user();
                        } catch (URISyntaxException e) {
                            Console.printErrorMessage("Your request could not be processed, try again.");
                        }
                        break;
                }
            }
        }


    private void pet() throws URISyntaxException {
        String url = "https://petstore.swagger.io/v2/pet/";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true){
            System.out.println("You can delete a pet by id - enter pet's \"id\"");

            long id;
            try {
                id = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e){
                Console.printErrorMessage("You entered an invalid id, try again.");
                continue;
            }

            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + id)).DELETE()
                    .header("api_key", "special-key").build();
            try {
                HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                System.out.println(response.statusCode());
                response.body().forEach(System.out::println);
            } catch (Exception e) {
                Console.printErrorMessage("HTTP DELETE request was unsuccessful, try again.");
                continue;
            }
            break;
        }
    }

    private void store() throws URISyntaxException {
        String url = "https://petstore.swagger.io/v2/store/";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true){
            System.out.println("You can delete an order by id - enter the \"id\"");

            long id;
            try {
                id = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e){
                Console.printErrorMessage("You entered an invalid id, try again.");
                continue;
            }

            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + "order/" + id)).DELETE().build();
            try {
                HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                System.out.println(response.statusCode());
                response.body().forEach(System.out::println);
            } catch (Exception e) {
                Console.printErrorMessage("HTTP DELETE request was unsuccessful, try again.");
                continue;
            }
            break;
        }
    }

    private void user() throws URISyntaxException {
        String url = "https://petstore.swagger.io/v2/user/";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true){
            System.out.println("You can delete a user by the username - enter \"username\"");

            String username = scanner.nextLine();
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + username)).DELETE().build();
            try {
                HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                System.out.println(response.statusCode());
                response.body().forEach(System.out::println);
            } catch (Exception e) {
                Console.printErrorMessage("HTTP DELETE request was unsuccessful, try again.");
                continue;
            }
            break;
        }
    }
}
