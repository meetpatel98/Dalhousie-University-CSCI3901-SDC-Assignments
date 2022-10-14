import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// This class is used to valid the inputs entered by the user such startDate, endDate, outputFileName and creates the xml file*/
public class CreateSummaryReport {

    File file; // for stroing thr object

    // Validate the dates
    private boolean datesValidation(String startDate, String endDate){
        Date parseStartDate;
        Date parseEndDate;

        //Return false if startDate or endDate is null
        if (startDate == null || endDate == null){
            return false;
        }

        // It parse the text from a string to produce the date
        // Return false if it encountes any error
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            parseStartDate = simpleDateFormat.parse(startDate);
            parseEndDate = simpleDateFormat.parse(endDate);
        } catch (ParseException e) {
            // Return false if there is any error
            System.out.println("Invalid Date format");
            return false;
        }

        // It ensures that endDate is after the startDate
        if (!parseEndDate.after(parseStartDate)){
            //return false if endDate is after the startDate
            return false;
        }

        // return true if the date is valid
        return true;
    }

    // validate the output file name and also creates the output file
    private boolean outputFileNameValidaton(String outputFile) {

        // It will ensutre that outpur file name is not null
        // retruns false if file name is empty
        if (outputFile == null){
            return false;
        }

        //it will ensure that file name is not empty or has extension .xml
        // it will retrun false if file name is empty or don't have extension .xml
        if (outputFile.trim().isEmpty() || !outputFile.trim().endsWith(".xml")) {
            return false;
        }

        // creating file object
        file = new File(outputFile);

        try {
            // It will create a new file
            boolean createNewFile  = file.createNewFile();
            // if there is any error while creating file it will retrun false
            if(!createNewFile){
                return false;
            }
        } catch (Exception e) {
            // retrun null if any error occures
            System.out.println("Error Message - "+e.getMessage());
            return false;
        }

        // At last it will retrun true if output file name passes all the tests
        return true;
    }

    // create the summery doc over a given period of time
    private Document createSummaryDoc(String startDate, String endDate) throws ParserConfigurationException {

        // It will get product_line_list and manager_List elemets
        Element productLineListElement = new ProductLineInformation().getProductLineInformationElement(startDate, endDate);
        Element managerListElement = new ManagerInformation().getManagerInformationElement(startDate, endDate);

        // it will create a new document
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        // create element <year_end_report>. It is the root element
        Element yearEndReportElement =doc.createElement("year_end_report");

        // this will create <start_date> and <end_date> elements
        Element startDateElement = doc.createElement("start_date");
        Element endDateElement = doc.createElement("end_date");

        // Add the dates to that elements
        startDateElement.appendChild(doc.createTextNode(startDate));
        endDateElement.appendChild(doc.createTextNode(endDate));

        // create <year> element
        Element yearElement = doc.createElement("year");
        // add <start_date> and <end_date> element into <year> element
        yearElement.appendChild(startDateElement);
        yearElement.appendChild(endDateElement);

        // add <year> element into the <year_end_report> element
        yearEndReportElement.appendChild(yearElement);

        // add <amanget_list> into <year_end_report>
        if (managerListElement != null){
            doc.adoptNode(managerListElement);
            yearEndReportElement.appendChild(managerListElement);
        } else {
            yearEndReportElement.appendChild(doc.createTextNode("manager_list"));
        }

        // add <product_line_list> into <year_end_report>
        if (productLineListElement != null){
            doc.adoptNode(productLineListElement);
            yearEndReportElement.appendChild(productLineListElement);
        } else {
            yearEndReportElement.appendChild(doc.createTextNode("product_line_list"));
        }

        // It will finally add <year_end_report> into the document
        doc.appendChild(yearEndReportElement);

        // retrun summary doc
        return doc;
    }

    // it will generate the report and store it into the database
    public boolean generateReport (String startDate, String endDate, String outputFile){

        // It will checks if the startDate and endDate are valid
        // it will retrun false if dates are not valid
        if (!datesValidation(startDate, endDate)){
            return false;
        }

        // It will ckecks if the output file name is valid
        // it will retrun false if output file name is not valid or it is not able to create file object
        if (!outputFileNameValidaton(outputFile)){
            return false;
        }

        try{
            // create the summery documenation over a given period of time
            Document doc = createSummaryDoc(startDate.trim(), endDate.trim());

            // it will write to the output file
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(file)));
        }catch (Exception e){
            // if there is any error then it will return false
            System.out.println("Error Message - "+e.getMessage());
            return false;
        }
        // if every thing runs successfully then it will retrun true
        return true;
    }
}
