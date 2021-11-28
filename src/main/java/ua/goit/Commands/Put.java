package ua.goit.Commands;

import ua.goit.Console;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;

public class Put implements CommandHandler{

    private Scanner scanner;

    public Put(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void handle() {
        while (true){
            Console.printMethodInstruction("PUT");

            String input = scanner.nextLine().toLowerCase();

            if(input.equals("exit")){
                System.out.println("You will be redirected to main console menu");
                break;
            }

            if(!Console.entities.contains(input)){
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
                    store();
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

    private void store() {
        Console.printErrorMessage("Not supported");
    }


    private void pet() throws URISyntaxException{
        String url = "https://petstore.swagger.io/v2/pet/";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true){
            System.out.println("You can update your pet");
            System.out.println("Please provide the request body in Json format in a text file and specify the path");
            System.out.println("Please enter the path");

            String path = scanner.nextLine();

            try {
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                        .PUT(HttpRequest.BodyPublishers.ofFile(Path.of(path)))
                        .header("Content-Type","application/json").build();

                HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                System.out.println(response.statusCode());
                response.body().forEach(System.out::println);
            } catch (FileNotFoundException e) {
                Console.printErrorMessage("The file could not be found, try again.");
                continue;
            } catch (Exception e) {
                Console.printErrorMessage("HTTP PUT request was unsuccessful, try again.");
                continue;
            }

            break;
        }
    }

    private void user() throws URISyntaxException{
        String url = "https://petstore.swagger.io/v2/user/";

        HttpClient httpClient = HttpClient.newBuilder().build();

        while (true){
            System.out.println("You can update a user by the username - enter \"username\"");
            String username = scanner.nextLine();

            System.out.println("Please provide the request body in Json format in a text file and specify the path");
            System.out.println("Please enter the path");

            String path = scanner.nextLine();

            try {
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + username))
                        .PUT(HttpRequest.BodyPublishers.ofFile(Path.of(path)))
                        .header("Content-Type","application/json").build();

                HttpResponse<Stream<String>> response = httpClient.send(request, HttpResponse.BodyHandlers.ofLines());
                System.out.println(response.statusCode());
                response.body().forEach(System.out::println);
            } catch (FileNotFoundException e) {
                Console.printErrorMessage("The file could not be found, try again.");
                continue;
            } catch (Exception e) {
                Console.printErrorMessage("HTTP PUT request was unsuccessful, try again.");
                continue;
            }
            break;
        }
    }
}
