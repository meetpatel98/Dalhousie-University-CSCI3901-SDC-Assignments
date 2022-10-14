use csci3901;

/*Problem 1*/

/*A) Which customers are in a different city than their sales representative?*/

SELECT c.customerName AS Customers, c.city AS Customer_City, 
o.city AS Rep_City FROM customers AS c, employees AS e, offices AS o WHERE 
c.salesRepEmployeeNumber=e.employeeNumber AND 
e.officeCode=o.officeCode AND 
c.city != o.city;


/*B) Which orders included sales that are below the manufacturer’s suggested retail price 
(MSRP)?*/

SELECT od.orderNumber AS Order_Number
FROM orderdetails As od JOIN products AS p 
WHERE od.productCode = p.productCode AND 
od.priceEach < p.MSRP group by Order_Number;  


/*C) Which product line has the highest profit margin in 2003. Include the profit margin. 
The profit of an item is the sales of the item less the cost of the item. The profit margin 
is the profit divided by the total base cost.*/

SELECT p.productline AS Product_Line, (SUM(subQuery.quantityOrdered*subQuery.priceEach)-SUM(subQuery.quantityOrdered*p.buyPrice))/SUM(subQuery.quantityOrdered*p.buyPrice) 
AS Profit_Margin FROM products AS p INNER JOIN 
(SELECT productCode,quantityOrdered,priceEach FROM orders INNER JOIN orderdetails ON
orders.orderNumber = orderdetails.orderNumber AND YEAR(orderDate)='2003') AS subQuery ON 
p.productCode = subQuery.productCode GROUP BY productline ORDER BY Profit_Margin DESC LIMIT 1;


/*D) List the 5 employees with the highest sales in 2004. Include their total sales values and 
ensure that the top seller is first in the list.*/

SELECT CONCAT(e.firstname, ' ', e.lastname) AS Employee_Name, e.employeeNumber as Employee_Number, q1.totalSales AS Total_Sales FROM employees e INNER JOIN  
(SELECT  subQuery.salesRepEmployeeNumber , SUM(orderdetails.quantityOrdered*orderdetails.priceEach) AS totalSales FROM orderdetails INNER JOIN
(SELECT orders.customerNumber, orders.orderNumber, customers.salesRepEmployeeNumber
FROM customers INNER JOIN orders ON customers.customerNumber = orders.customerNumber
WHERE YEAR(orderDate) = '2004') 
subQuery ON orderdetails.orderNumber = subQuery.orderNumber GROUP BY subQuery.salesRepEmployeeNumber) AS q1 ON 
q1.salesRepEmployeeNumber = e.employeeNumber ORDER BY q1.totalSales DESC LIMIT 5;


/*E) Which employees had the value of their 2004 orders exceed the value of their 2003 
orders?*/

SELECT q1.Employee_Number, q1.Employee_Name , q1.Total_Sales_2004, q2.Total_Sales_2003 from
(SELECT CONCAT(e.firstname, ' ', e.lastname) AS Employee_Name, e.employeeNumber as Employee_Number, q1.totalSales AS Total_Sales_2004 FROM employees e INNER JOIN  
(SELECT  subQuery.salesRepEmployeeNumber , SUM(orderdetails.quantityOrdered*orderdetails.priceEach) AS totalSales FROM orderdetails INNER JOIN
(SELECT orders.customerNumber, orders.orderNumber, customers.salesRepEmployeeNumber
FROM customers INNER JOIN orders ON customers.customerNumber = orders.customerNumber
WHERE YEAR(orderDate) = '2004') 
subQuery ON orderdetails.orderNumber = subQuery.orderNumber GROUP BY subQuery.salesRepEmployeeNumber) AS q1 ON 
q1.salesRepEmployeeNumber = e.employeeNumber) AS q1 INNER JOIN 
(SELECT CONCAT(e.firstname, ' ', e.lastname) AS Employee_Name, e.employeeNumber as Employee_Number, q1.totalSales AS Total_Sales_2003 FROM employees e INNER JOIN  
(SELECT  subQuery.salesRepEmployeeNumber , SUM(orderdetails.quantityOrdered*orderdetails.priceEach) AS totalSales FROM orderdetails INNER JOIN
(SELECT orders.customerNumber, orders.orderNumber, customers.salesRepEmployeeNumber
FROM customers INNER JOIN orders ON customers.customerNumber = orders.customerNumber
WHERE YEAR(orderDate) = '2003') 
subQuery ON orderdetails.orderNumber = subQuery.orderNumber GROUP BY subQuery.salesRepEmployeeNumber) AS q1 ON 
q1.salesRepEmployeeNumber = e.employeeNumber ) AS q2 ON q1.Employee_Number = q2.Employee_Number WHERE q1.Total_Sales_2004>q2.Total_Sales_2003 ORDER BY q1.Employee_Number ASC;