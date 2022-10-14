import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.RecursiveTask;

public class OrderManager {
    public static final String TABLE_ORDER_DETAILS = "OrderShipperRelation"; // Table name
    public static final String COLUMN_SHIPPER_ID = "ShipperId"; // ShipperId column
    public static final String COLUMN_ORDER_NUMBER = "OrderNumber"; // OrderNumber column

    private Connection conn; // it is the database connection object

    // it is used to open the connection with the database
    public void open() {

        // it is used to store project configuration data or settings
        Properties properties = new Properties();

        try {
            // it will load the login.properties file
            properties.load(new FileInputStream("src/login.properties"));
        } catch (IOException e) {
            System.out.println("Error Message - " + e.getMessage());
        }
        String USER = properties.getProperty("USERNAME"); // username of the database
        String PASSWORD = properties.getProperty("PASSWORD"); // password for the database
        String DATABASE_NAME = properties.getProperty("DATABASE_NAME");
        String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?useSSL=false"; // connection string to connect to the database
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
        } catch (SQLException e) {
            // prints the error message if unable to connect with the database
            System.out.println("Couldn't connect to database; " + e.getMessage());
        }
    }

    // default constructor used to open the connection with the database
    public OrderManager() {
        open();
    }

    //Note: Order Number must be correct and from the order table and Shipper must be correct and from shippers table
    // Thie will take orderNumber and shipper id as parameter and store it in OrderShipperRelation table where initially the ordervalue is null
    // this will then apply the shipping cost to the order value of the given order number
    // Then it will update the order value in OrderShipperRelation table of given order number
    public void checkout(int orderNumber, int shipperId) {

        try (Statement statement_Shipper_Percentage = conn.createStatement();
             Statement statement_Order_Value = conn.createStatement();
             Statement statement_Insert_Shipper_Id = conn.createStatement();) {

            // Query to insert orderNumber and shipperId in the OrderShipperRelation table with Order_Value as null
            String INSERT_SHIPPER_ID = "INSERT INTO " + TABLE_ORDER_DETAILS + " (" + COLUMN_ORDER_NUMBER + "," + COLUMN_SHIPPER_ID + ") " + "VALUES('" + orderNumber + "','" + shipperId + "');";

            // this will insert orderNumber and shipperId in the OrderShipperRelation table
            statement_Insert_Shipper_Id.executeUpdate(INSERT_SHIPPER_ID);

            // Query to get Order Value of all the Order Numbers on which promotions and taxes are already applied
            String ORDER_VALUE = "select q5.orderNumber, q5.officeCode, q5.Order_Value-((q5.Order_Value*promotions.Percentage)/100) as Order_Value from\n" +
                    "(select q4.orderNumber, q4.officeCode, q4.Tax_Percentage, promoofficerelation.PromoCode, q4.Order_Value from\n" +
                    "(select q3.orderNumber, q3.officeCode, q3.Total_Orders_Value as beforTax, sum(taxes.Percentage) as Tax_Percentage,  (((q3.Total_Orders_Value*sum(taxes.Percentage))/100)+q3.Total_Orders_Value) as Order_Value from \n" +
                    "(select q2.orderNumber, q2.officeCode, taxofficerelation.TaxId, q2.Total_Orders_Value from\n" +
                    "(select q1.orderNumber,  q1.Total_Orders_Value, employees.officeCode from \n" +
                    "(select orders.orderNumber, customers.salesRepEmployeeNumber, sum(orderdetails.priceEach * orderdetails.quantityOrdered) as Total_Orders_Value\n" +
                    "from customers, orders, orderdetails where customers.customerNumber = orders.customerNumber and orders.orderNumber = \n" +
                    "orderdetails.orderNumber group by orders.orderNumber) as q1 inner join employees on\n" +
                    "q1.salesRepEmployeeNumber = employees.employeeNumber) as q2 inner join taxofficerelation on \n" +
                    "q2.officeCode = taxofficerelation.officeCode) as q3 inner join taxes on \n" +
                    "q3.TaxId = taxes.TaxId group by q3.orderNumber) as q4 inner join promoofficerelation on q4.officeCode = promoofficerelation.officeCode) as q5\n" +
                    "inner join promotions on q5.PromoCode = promotions.PromoCode where q5.orderNumber = '" + orderNumber + "';";

            // Result set will store all the Order Values
            ResultSet resultSet_OrderValue = statement_Order_Value.executeQuery(ORDER_VALUE);
            resultSet_OrderValue.next();

            //Stores the Order_Value of passed order number in a variable
            double Order_Value = resultSet_OrderValue.getInt("Order_Value");

            //Return a record containing shipper code of given Order Number
            String Shipper_Percentage = "SELECT shippers.Percentage FROM shippers INNER JOIN " +
                    "OrderShipperRelation ON shippers.ShipperId = OrderShipperRelation.ShipperId WHERE OrderShipperRelation.OrderNumber = '" + orderNumber + "';";

            // Result set will store one record of given order number
            ResultSet resultSet_Shipper_Percentage = statement_Shipper_Percentage.executeQuery(Shipper_Percentage);
            resultSet_Shipper_Percentage.next();

            //store the shipping cost of given order number
            int orderPercentage = resultSet_Shipper_Percentage.getInt(1);

            // Add shipping cost to the ordervalue
            Order_Value = (Order_Value * orderPercentage) / 100 + Order_Value;

            // update the order value in the table
            String update_ordervalue = "UPDATE OrderShipperRelation SET " +
                    "Final_Order_Value= '" + Order_Value + "'WHERE OrderShipperRelation.OrderNumber = '" + orderNumber + "';";

            //this will update the final order value in the OrderShipperRelation table
            statement_Order_Value.executeUpdate(update_ordervalue);

        } catch (Exception e) {
            //if entered ordernumber or shipper id is not correct
            System.out.println("Check whether the orderNumber or shipperId is correct ");
            System.out.println("Error Message - " + e.getMessage());
        }
    }

    //Note: The passed Order Number must be chceked out
    //Return the total order value of passed order number in which the promotions, tax and shipping cost is applied
    // It will only accept the order numbers that have been checked out
    // For the order numbers which haven't checked out it will retrun exception
    public double orderValue(int orderNumber) {
        String FINAL_ORDER_VALUE = "SELECT OrderShipperRelation.Final_Order_Value from OrderShipperRelation " +
                "WHERE OrderShipperRelation.OrderNumber = '" + orderNumber + "';";
        try (Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(FINAL_ORDER_VALUE);) {
            rs.next();
            // Retrun the final order value on which promotions, taxes and shipping cost is applied
            return rs.getInt("Final_Order_Value");
        } catch (SQLException e) {
            System.out.println("Error Message - " + e.getMessage());
            // retrun 0 if any error
            return 0;
        }
    }
}
