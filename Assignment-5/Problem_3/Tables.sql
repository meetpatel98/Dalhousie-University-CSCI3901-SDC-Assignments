
/* Problem 2 (Tables)*/

/* An SQL file that, when run on the mysql database, would make whatever changes are
needed to the database to realize your design modifications. */

/*shippes table*/
CREATE TABLE shippers (
    ShipperId int NOT NULL,
    CompanyName varchar(255) NOT NULL,
    Percentage int NOT NULL,
    PRIMARY KEY (ShipperId)
);

/*taxes table*/
CREATE TABLE taxes (
    TaxId int NOT NULL AUTO_INCREMENT,
    Percentage int,
    PRIMARY KEY (TaxId)
);

/*promotions table*/
CREATE TABLE promotions (
    PromoCode varchar(255) NOT NULL,
    Percentage int,
    PRIMARY KEY (PromoCode)
);

/*TaxOfficeRelation table*/
CREATE TABLE TaxOfficeRelation (
    TaxId int NOT NULL,
    officeCode varchar(255) NOT NULL,
    FOREIGN KEY (officeCode) REFERENCES offices(officeCode),
    FOREIGN KEY (TaxId) REFERENCES taxes(TaxId)
);

/*OrderShipperRelation table*/
CREATE TABLE OrderShipperRelation (
	OrderNumber int NOT NULL,
    ShipperId int NOT NULL,
    Final_Order_Value int,
    FOREIGN KEY (OrderNumber) REFERENCES orderdetails(orderNumber),
    FOREIGN KEY (ShipperId) REFERENCES shippers(ShipperId),
    PRIMARY KEY (OrderNumber)
);


/*PromoOfficeRelation table*/
CREATE TABLE PromoOfficeRelation (
	officeCode varchar(255) NOT NULL,
    PromoCode varchar(255) NOT NULL,
    FOREIGN KEY (PromoCode) REFERENCES promotions(PromoCode),
    FOREIGN KEY (officeCode) REFERENCES offices(officeCode),
    PRIMARY KEY (officeCode)
);
