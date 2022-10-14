import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Properties;

// This class is used to get product informaton from the database
public class ProductLineInformation {

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
    public ProductLineInformation() {
        open();
    }

    //this will retrun the information about product line
    public Element getProductLineInformationElement(String startDate, String endDate){
        // Query to extract information about product line list from the database over a period of time
        String QUERY_PRODUCT_LINE_LIST = "SELECT subQuery2.productLine AS ProductLine, productlines.textDescription AS TextDescription from " +
                "productlines INNER JOIN (SELECT products.productLine FROM " +
                "products INNER JOIN (SELECT subQuery.customerNumber, subQuery.orderNumber,  " +
                "orderdetails.productCode FROM orderdetails INNER JOIN (select customers.customerNumber,  " +
                "orders.orderNumber FROM customers INNER JOIN orders ON  " +
                "customers.customerNumber = orders.customerNumber WHERE orders.orderDate  " +
                "BETWEEN \"" + startDate + "\" AND \"" + endDate + "\") AS subQuery ON  " +
                "orderdetails.orderNumber = subQuery.orderNumber) AS subQuery1 ON  " +
                "products.productCode = subQuery1.productCode GROUP BY products.productLine) AS subQuery2 " +
                "ON subQuery2.productLine = productlines.productLine ORDER BY subQuery2.productLine ASC;";

        // query to extract information about customers from the database overe a period of time
        String QUERY_CUSTOMER_LIST = "select products.productLine as ProductLine, productlines.textDescription as textDesc ,customers.customerName as customerName, sum(orderdetails.priceEach* orderdetails.quantityOrdered) as" +
                " order_value from orders" +
                " inner join orderdetails on orderdetails.orderNumber=orders.orderNumber inner join" +
                " customers on customers.customerNumber = orders.customerNumber inner join products on" +
                " products.productCode= orderdetails.productCode  inner join productlines on" +
                " products.productLine = productlines.productLine  where orders.orderDate between \"" + startDate + "\" AND \"" + endDate + "\"  group by productLine,customerName ORDER BY ProductLine;";

        try(Statement customerStatement = conn.createStatement(); Statement productStatement = conn.createStatement();
            // exectuting the query and storing the result in Result Set
            ResultSet customerListResultSet = customerStatement.executeQuery(QUERY_CUSTOMER_LIST);
            ResultSet productLineListResultSet = productStatement.executeQuery(QUERY_PRODUCT_LINE_LIST)) {

            customerListResultSet.next();

            // Create a new document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // create element <product_line_list. it is the root element
            Element productLineListElement = doc.createElement("product_line_list");

            //It will iterate productLineListResultSet to extract information from the database
            while (productLineListResultSet.next()) {

                // create element <product_line_name> and add ProductLine;
                Element productLineNameElement = doc.createElement("product_line_name");
                productLineNameElement.appendChild(doc.createTextNode(
                        productLineListResultSet.getString("ProductLine")));

                // create element <product_line_description> and add TextDescription
                Element productLineDescriptionElement = doc.createElement("product_line_description");
                productLineDescriptionElement.appendChild(doc.createTextNode(
                        productLineListResultSet.getString("TextDescription")));

                // create element <product_line>
                Element productLineElement = doc.createElement("product_line");
                productLineElement.appendChild(productLineNameElement);
                productLineElement.appendChild(productLineDescriptionElement);
                    do {
                        // create an element <customer_name> and add the CustomerName
                        Element customerNamElement = doc.createElement("customer_name");
                        customerNamElement.appendChild(doc.createTextNode(
                                customerListResultSet.getString("customerName")));

                        // create an element <order_value> and add the OrdersValue
                        Element orderValueElement = doc.createElement("order_value");
                        orderValueElement.appendChild(doc.createTextNode(
                                customerListResultSet.getString("order_value")));

                        // create an element <customer> and add the customer
                        Element customerElement = doc.createElement("customer");

                        // Add <order_value>,<customer_name> into <customer>
                        customerElement.appendChild(customerNamElement);
                        customerElement.appendChild(orderValueElement);

                        // Add <product_line> into <product_line>
                        productLineElement.appendChild(customerElement);
                    } while (customerListResultSet.next() && productLineListResultSet.getString("ProductLine").equals(customerListResultSet.getString("ProductLine")));
                productLineListElement.appendChild(productLineElement);
            }
            // closing the connection
            close();
            return productLineListElement;
        }
        catch (Exception e){
            System.out.println("Error Message - "+e.getMessage());
            // if error occure then retrun null
            return null;
        }
    }
}
