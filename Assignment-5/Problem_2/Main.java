import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try{
            String startDate; //String for storing Start Date
            String endDate; // String for Storing End Date

            // Scanner to take input from the user
            Scanner scanner = new Scanner(System.in);

            // Date must be in following format
            System.out.println("Dates must in YYYY-MM-DD format\n");

            // Start date for the XML report
            System.out.println("Enter the starting date for the period to summarize");
            startDate = scanner.nextLine();

            // End date for the XML report
            System.out.println("Enter the ending date for the period to summarize");
            endDate = scanner.next();

            // Name of the output file
            System.out.println("Enter the output file name");
            String outputFile = scanner.next();

            // craeting object for SummeryReport
            CreateSummaryReport createSummaryReport = new CreateSummaryReport();

            if (createSummaryReport.generateReport(startDate, endDate, outputFile)){
                System.out.println("Report is ready to use");
            }else {
                System.out.println("Error occurred while creating report");
            }
        }
        catch (Exception e){
            // if there is any error then it will throw exception
            System.out.println("Report Generation Failed - "+e.getMessage());
        }
    }
}
