package ua.goit;

import ua.goit.Commands.*;

import java.util.*;

public class Console {
    public static final List<String> requestList = Arrays.asList("GET", "POST", "PUT", "DELETE");
    public static final List<String> entities = Arrays.asList("pet", "store", "user");
    public static final List<String> statusList = Arrays.asList("available", "pending", "sold");
    public static final Map<String, CommandHandler> commandMap = new HashMap<>();

    public static void run() {
        Scanner scanner = new Scanner(System.in);

        commandMap.put("GET", new Get(scanner));
        commandMap.put("POST", new Post(scanner));
        commandMap.put("PUT", new Put(scanner));
        commandMap.put("DELETE", new Delete(scanner));

        printWelcomeMessage();

        while (true) {
            printInstructionForRequests();
            String input = scanner.nextLine().toUpperCase();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting from app...");
                break;
            }

            if (!requestList.contains(input)) {
                printErrorMessage("Your input is not correct");
                continue;
            }

            commandMap.get(input).handle();
        }
    }


    public static void printWelcomeMessage(){
        System.out.println("Welcome to the app\n" + "You can send requests to petstore.swagger.io\n");
    }

    public static void printInstructionForRequests() {
        System.out.println("Following requests are supported:\n" + requestList + "\nTo exit enter: exit");
    }

    public static void printErrorMessage(String msg) {
        System.err.println(msg);
    }

    public static void printMethodInstruction(String method) {
        System.out.println("You are sending " + method + " request\n" + "Supported entities are: "
                + entities + "\n To exit enter: exit");
    }
}
