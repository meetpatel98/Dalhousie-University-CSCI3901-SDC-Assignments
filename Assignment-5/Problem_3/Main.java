import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String checkout = "checkout";
        String orderValue = "orderValue";
        String quitCommand = "quit";

        String userCommand = "";
        Scanner userInput = new Scanner(System.in);

        OrderManager orderManager = new OrderManager();

        System.out.println("\nCommands available");
        System.out.println(checkout + " <orderNumber> <shipperId>");
        System.out.println(orderValue + " <orderNumber>");

        System.out.println(quitCommand);

        System.out.println();
        do {
            userCommand = userInput.next();

            if (userCommand.equalsIgnoreCase(checkout)) {
                int orderNumber = userInput.nextInt();
                int shipperId = userInput.nextInt();
                orderManager.checkout(orderNumber,shipperId);
                System.out.println();
            } else if (userCommand.equalsIgnoreCase(orderValue)) {
                int orderNumber = userInput.nextInt();
                System.out.println("Total Cost of Order is "+orderManager.orderValue(orderNumber));
                System.out.println();
            }
            else if (userCommand.equalsIgnoreCase(quitCommand)) {
                System.out.println(userCommand);
            } else {
                System.out.println("Bad command: " + userCommand);
            }
        } while (!userCommand.equalsIgnoreCase("Bye..."));
    }
}
