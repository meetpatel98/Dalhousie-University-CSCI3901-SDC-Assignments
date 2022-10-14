import java.sql.Time;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String createCityCommand = "city";
        String createFlightCommand = "flight";
        String createTrainCommand = "train";
        String createPlanCommand = "travelPlan";
        String quitCommand = "quit";

        String userCommand = "";
        Scanner userInput = new Scanner(System.in);

        TravelAssistant travelAssistant = new TravelAssistant();

        System.out.println("\nCommands available");
        System.out.println(createCityCommand+ " <City Name> <Test Required> <Time To Test (days)> <Night Hotel Stay Cost (CAD)>");
        System.out.println(createFlightCommand+ " <Start City> <Destination City> <Flight Time (min)> <Flight Cost (CAD)>");
        System.out.println(createTrainCommand+ " <Start City> <Destination City> <Train Time (min)> <Train Cost (CAD)>"  );
        System.out.println(createPlanCommand+ " <Start City> <Destination City> <Is Vaccinated> <costImportance> <travelTimeImportance> <travelHopImportance>");
        System.out.println(quitCommand);

        System.out.println();
            do{
                userCommand = userInput.next();

                if (userCommand.equalsIgnoreCase(createCityCommand)){
                    String cityName = userInput.next();
                    boolean testRequired = userInput.nextBoolean();
                    int timeToTest = userInput.nextInt();
                    int nightlyHotelCost = userInput.nextInt();

                    travelAssistant.addCity(cityName, testRequired, timeToTest, nightlyHotelCost);

                    System.out.println();


                }
                else if (userCommand.equalsIgnoreCase(createFlightCommand)){
                    String startCity = userInput.next();
                    String destinationCity = userInput.next();
                    int flightTime = userInput.nextInt();
                    int flightCost = userInput.nextInt();

                    travelAssistant.addFlight(startCity, destinationCity, flightTime, flightCost);

                    System.out.println();
                }

                else if (userCommand.equalsIgnoreCase(createTrainCommand)){
                    String startCity = userInput.next();
                    String destinationCity = userInput.next();
                    int trainTime = userInput.nextInt();
                    int trainCost = userInput.nextInt();

                    travelAssistant.addTrain(startCity, destinationCity, trainTime, trainCost);
                    System.out.println();
                }

                else if (userCommand.equalsIgnoreCase(createPlanCommand)){
                    String startCity = userInput.next();
                    String destinationCity = userInput.next();
                    boolean isVaccinated = userInput.nextBoolean();
                    int costImportance = userInput.nextInt();
                    int travelTimeImportance = userInput.nextInt();
                    int travelHopImportance = userInput.nextInt();

                    System.out.println(travelAssistant.planTrip(startCity, destinationCity, isVaccinated, costImportance, travelTimeImportance, travelHopImportance));
                }else if (userCommand.equalsIgnoreCase(quitCommand)) {
                    System.out.println(userCommand);
                } else {
                    System.out.println("Bad command: " + userCommand);
                }

            } while (!userCommand.equalsIgnoreCase("quit"));
        }
}