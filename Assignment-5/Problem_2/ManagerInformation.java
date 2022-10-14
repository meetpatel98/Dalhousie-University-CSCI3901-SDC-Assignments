import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

// this class is used to get manager information from the database
public class ManagerInformation {

    private Connection conn; // it is the database connection object

    // it is used to open the connection with the database
    public void open(){

        Properties properties = new Properties();
        try{
            // it will load the login.properties file
            properties.load(new FileInputStream("src/login.properties"));
        }catch (IOException e){
            System.out.println("Error Message - "+e.getMessage());
        }
        String USER = properties.getProperty("USERNAME") ; // username of the database
        String PASSWORD = properties.getProperty("PASSWORD"); // password for the database
        String DATABASE_NAME = properties.getProperty("DATABASE_NAME"); // database name
        String CONNECTION_STRING = "jdbc:mysql://db.cs.dal.ca:3306/"+DATABASE_NAME+"?serverTimezone=UTC"; // connection string to connect to the database
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING,USER,PASSWORD);
        } catch (SQLException e){
            // prints the error message if unable to connect with the database
            System.out.println("Couldn't connect to database; "+e.getMessage());
        }
    }

    // it is used to close the connection with the database
    public void close(){
        try{
            if (conn!=null){
                conn.close();
            }
        } catch (SQLException e){
            // prints the error message if unable to close the connection with the database
            System.out.println("Couldn't close connection: "+e.getMessage());
        }
    }

    // default constructor used to open the connection with the database
    public ManagerInformation() {
        open();
    }

    //this will return the information about manager
    public Element getManagerInformationElement(String startDate, String endDate) {

        // Queryt to extract information about manager from the database over a period of time
        String QUERY_MANAGER_LIST = "SELECT Q10.Manager AS Manager, Q10.city AS Manager_City, Q10.staff AS Staff, sum(Q11.Customer_Served) AS Customers, sum(Q11.Total_Sales) AS Sales_Values FROM " +
                "(SELECT Q5.Manager, Q5.employeeNumber, Q5.staff, Q5.officeCode, OFC.city FROM offices AS OFC INNER JOIN " +
                "(SELECT CONCAT(E.firstname, ' ', E.lastname) AS Manager, E.employeeNumber, Q4.staff, E.officeCode FROM employees AS E INNER JOIN " +
                "(SELECT Q3.manager, count(Q3.staff) AS staff FROM " +
                "(SELECT Q2.manager, Q2.staff FROM " +
                "(SELECT Q1.manager, Q1.staff, C.customerNumber FROM " +
                "(SELECT M.employeeNumber AS manager, E.employeeNumber AS staff FROM employees E LEFT JOIN employees M ON M.employeeNumber = E.reportsto) AS Q1 INNER JOIN customers AS C " +
                "ON Q1.staff = C.salesRepEmployeeNumber) Q2 INNER JOIN orders AS O " +
                "ON O.customerNumber = Q2.customerNumber and O.orderDate between \"" + startDate + "\" AND \"" + endDate + "\" group by Q2.staff) AS Q3 group by Q3.manager) AS Q4 " +
                "ON E.employeeNumber = Q4.manager) AS Q5 " +
                "ON Q5.officeCode = OFC.officeCode) AS Q10 INNER JOIN " +
                "(SELECT Q9.manager, Q8.Total_Sales, Q8.Customer_Served FROM " +
                "(SELECT Q6.employeeNumber, SUM(Q7.Total_Orders_Value) AS Total_Sales, COUNT(Q7.TOTAL_CUSTOMERS) AS Customer_Served FROM " +
                "(SELECT E.employeeNumber, C.customerNumber FROM employees E, customers C WHERE E.employeeNumber = C.salesRepEmployeeNumber) AS Q6 INNER JOIN " +
                "(SELECT C.customerName, C.customerNumber, SUM(OD.priceEach * OD.quantityOrdered) AS Total_Orders_Value, COUNT(*) AS TOTAL_CUSTOMERS " +
                "FROM customers AS C, orders AS O, orderdetails AS OD WHERE C.customerNumber = O.customerNumber AND O.orderNumber = " +
                "OD.orderNumber AND O.orderDate BETWEEN \"" + startDate + "\" AND \"" + endDate + "\" GROUP BY O.customerNumber) as Q7 " +
                "ON Q6.customerNumber = Q7.customerNumber GROUP BY Q6.employeeNumber) AS Q8 INNER JOIN " +
                "(SELECT E.reportsTo AS manager, E.employeeNumber FROM employees E GROUP BY E.employeeNumber) AS Q9 " +
                "ON Q8.employeeNumber = Q9.employeeNumber) AS Q11 " +
                "ON Q10.employeeNumber = Q11.manager GROUP BY Q10.employeeNumber;";

        try (Statement statement = conn.createStatement();
             // exectuting the query and storing the result in Result Set
             ResultSet managerListResultSet = statement.executeQuery(QUERY_MANAGER_LIST)) {

            // create a new document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // create element <manager_list>. It is the root element
            Element managerListElement = doc.createElement("manager_list");

            //It will iterate managerListResultSet to extract information from the database
            while (managerListResultSet.next()){

                // create element <manager_name> and add Manager
                Element managerNameElement= doc.createElement("manager_name");
                managerNameElement.appendChild(doc.createTextNode(
                        managerListResultSet.getString("Manager")));

                // create element <manager_city> and add City
                Element managerCityElement = doc.createElement("manager_city");
                managerCityElement.appendChild(doc.createTextNode(
                        managerListResultSet.getString("Manager_City")));

                // create element <staff> adn add Staff
                Element staffElement = doc.createElement("staff");
                staffElement.appendChild(doc.createTextNode(
                        managerListResultSet.getString("Staff")));

                // create element <customers> and add customers
                Element customersElement = doc.createElement("customers");
                customersElement.appendChild(doc.createTextNode(
                        managerListResultSet.getString("Customers")));

                // create element <sales_value> and add Sales_Values
                Element salesValueElement = doc.createElement("sales_value");
                salesValueElement.appendChild(doc.createTextNode(
                        managerListResultSet.getString("Sales_Values")));

                // create element <manager>
                Element managerElement = doc.createElement("manager");

                // Add <manager_name>, <manager_city>, <staff>, <customers>, and <sales_value> into <manager>
                managerElement.appendChild(managerNameElement);
                managerElement.appendChild(managerCityElement);
                managerElement.appendChild(staffElement);
                managerElement.appendChild(customersElement);
                managerElement.appendChild(salesValueElement);

                // Add <manager> into <manager_List>.
                managerListElement.appendChild(managerElement);

            }
            // retrun <manager_list>
            return managerListElement;
        }
        catch (Exception e){
            System.out.println("Error MEssage - "+e.getMessage());
            // if error occure then retrun null
            return null;
        }
    }
}
