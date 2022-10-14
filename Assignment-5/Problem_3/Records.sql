
/* Problem 2 (Records) */

/* An SQL file that, when run on the mysql database that has your changes, will populate 
the database with 3 sample shipper records and one tax record for each office except 
the Paris office, which will have two tax records*/

/*records for shippes table*/
insert into shippers(ShipperId, CompanyName, Percentage) values (0, 'SM Line', 0);
insert into shippers(ShipperId, CompanyName, Percentage) values (1, 'COSCO', 5);
insert into shippers(ShipperId, CompanyName, Percentage) values (2, 'HMM Co. Ltd.', 10);

/*records for taxes table*/
insert into taxes(Percentage) values(30);
insert into taxes(Percentage) values(5);
insert into taxes(Percentage) values(15);
insert into taxes(Percentage) values(12);
insert into taxes(Percentage) values(2);
insert into taxes(Percentage) values(4);
insert into taxes(Percentage) values(6);
insert into taxes(Percentage) values(8);

/*records for TaxOfficeRelation table*/
insert into TaxOfficeRelation(TaxId, officeCode) values(1, 1);
insert into TaxOfficeRelation(TaxId, officeCode) values(2, 2);
insert into TaxOfficeRelation(TaxId, officeCode) values(3, 3);
insert into TaxOfficeRelation(TaxId, officeCode) values(4, 4);
insert into TaxOfficeRelation(TaxId, officeCode) values(5, 5);
insert into TaxOfficeRelation(TaxId, officeCode) values(6, 6);
insert into TaxOfficeRelation(TaxId, officeCode) values(7, 7);
insert into TaxOfficeRelation(TaxId, officeCode) values(8, 4);

/*records for promotions table*/
insert into promotions values("PRO10",10);
insert into promotions values("PRO20",20);
insert into promotions values("PRO30",30);
insert into promotions values("PRO40",40);
insert into promotions values("PRO50",50);

/*records for promoofficerelation table*/
insert into promoofficerelation(officeCode, PromoCode) values(1, "PRO10");
insert into promoofficerelation(officeCode, PromoCode) values(2, "PRO20");
insert into promoofficerelation(officeCode, PromoCode) values(3, "PRO30");
insert into promoofficerelation(officeCode, PromoCode) values(4, "PRO40");
insert into promoofficerelation(officeCode, PromoCode) values(5, "PRO50");
insert into promoofficerelation(officeCode, PromoCode) values(6, "PRO10");
insert into promoofficerelation(officeCode, PromoCode) values(7, "PRO20");
