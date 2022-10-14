import org.w3c.dom.ls.LSOutput;

import javax.crypto.spec.PSource;
import javax.swing.*;
import javax.swing.plaf.IconUIResource;
import java.io.*;
import java.lang.reflect.AnnotatedArrayType;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarOutputStream;

public class OnlineAuctionSystem {

    public int bidderId = 0; // it is the bidder id
    public int auctionId = 0; // it is the auction id
    public int lotId = 0; // it is the lot id
    int totalBid =0; // total bid placed by the particulat bidder
    public String statusString = "new"; // Sets status of newly created as new
    boolean status; // stores the status in boolean variable

    Auction auctionObj; // used to get the auction object of particular lot number
    Lot lotobj; // used to get the lot object of particular lot number

    int currentBid;
    int rememberedBid;
    int nextValidBid;

    public static ArrayList<Auction> auctions = new ArrayList<Auction>(); //stores then info about the auction
    public static ArrayList<Bidder> bidders = new ArrayList<Bidder>(); // stores the info about the bidders
    public static ArrayList<Lot> lots = new ArrayList<Lot>(); // stores the info about lots
    public static ArrayList<Integer> lotsList = new ArrayList<Integer>(); // Contains the list of all the lots
    public static List<ArrayList<Integer>> auctionLotList = new ArrayList<>(); // containing the list of lots auction wise
    public static Map<Integer, Lot> lotmap = new HashMap<>(); // stores the lot Number as key and the information about that lot Number as value

    public OnlineAuctionSystem(){
    }

    // it will create the auction with following information
    public Auction createAuction(String auctionName, int firstLotNumber, int lastLotNumber, int minBidIncrement){

        try // Checks whether auctionName is Empty of null
        {
            if (auctionName.equalsIgnoreCase("empty")) {
                auctionName = "";
            } else if (auctionName.equalsIgnoreCase("null")){
                auctionName = null;
            }

        }catch (Exception e){
            return null;
        }

        //it will ensure that lot Number and mimBidIncrement  must be greater than 0 and firstlotNumber Must be greater then lastlot Number
        if (firstLotNumber>0 && lastLotNumber>0 && minBidIncrement>0 && lastLotNumber >= firstLotNumber){

            //stores the range of auction as an arraylist from firstLotNumber to LastLotNumber
            ArrayList<Integer> auctionLots = new ArrayList<Integer>();

            for (int i=firstLotNumber; i<=lastLotNumber; i++){
                auctionLots.add(i);
                lotmap.put(i, new Lot(currentBid, minBidIncrement, rememberedBid, nextValidBid));
            }

            auctionLotList.add(auctionLots); // adds th range of arraylist to auctionLotList
            lotsList.addAll(auctionLots);   // it will add all the lots in single arraylist
            this.auctions.add(new Auction(auctionName, firstLotNumber, lastLotNumber, minBidIncrement, statusString, auctionId, status, auctionLots, totalBid));

            this.auctions.get(auctionId).setStatusString(statusString); //set the status of newly created bid as "New"
            this.auctions.get(auctionId).rememberedBid = 0; // initiall the rememberedBid of the auction is 0
            this.auctions.get(auctionId).setTotalBid(totalBid); // set the total bid by the bidders
            auctionId++; // After creating the auction it will increment the auctionId
            return new Auction(auctionName, firstLotNumber, lastLotNumber, minBidIncrement, statusString, auctionId, status, auctionLots, totalBid);
            // return the Auction Object

        }else {
            System.out.println("Lot Numbers and Minimum Increment must be greater than 0 and firstLotNumber < LastLotNumber");
            return null;
        }
    }

    //it will ensure that the lot Number of one auction should not overlap with other
    public boolean overlapped(ArrayList<Integer> auctionLots){
        for (int i=0; i<auctions.size(); i++){
            if (auctionLots.removeAll(auctions.get(i).getAuctionLots())){
                return true;
            }
        }
        return false;
    }


    // it will create the bidder with bidderName
    public Bidder createBidder(String bidderName){

        //Checks whether bidderName is Empty of null
        try{
            if (bidderName.equalsIgnoreCase("empty")) {
                bidderName = "";
            } else if (bidderName.equalsIgnoreCase("null")) {
                bidderName = null;
            }
        }catch (Exception e){
            return null;
        }

        bidderId = bidderId + 1; //increment the bidderId
        this.bidders.add(new Bidder(bidderName, bidderId));
        return new Bidder(bidderName, bidderId); //return the bidder Object
    }


    // Stores the information about the particular lot
    public Lot lotInfo(int currentBid, int minimumBidIncrement, int rememberedBid, int nextValidBid, int bid, int bidderId){
        this.lots.add(new Lot(currentBid, minimumBidIncrement, rememberedBid, nextValidBid, bid, bidderId, lotId));
        lotId = lotId + 1; //it will increment the auction
        return new Lot(currentBid, minimumBidIncrement, rememberedBid, nextValidBid, bid, bidderId, lotId);
    }

    // return the status of all the auctions
    public String auctionStatus(){

        StringBuffer sb=new StringBuffer();

        for (int i=0; i<auctions.size(); i++){
            sb.append(auctions.get(i).getAuctionName());
            sb.append(" ");
            sb.append(auctions.get(i).getStatusString());
            sb.append(" ");
            sb.append(auctions.get(i).getTotalBid());
            sb.append("\n");
        }
        return sb.toString();
    }


    // it will load the bids from the text file
    public int loadBids(String filename){

        // Ensure that the file exists or not
        try{
            String path = Paths.get("").toAbsolutePath().toString();
            String pathString  = path + "\\" + filename;
            FileReader fr = new FileReader(pathString);
            BufferedReader br  = new BufferedReader(fr);
            String str;
            int count = 0;

            while ((str = br.readLine()) != null){

                System.out.println(str);
                count++;
            }

            //checks whether the file is empty or not
            if (count == 0){
                System.out.println("File is Empty");
                return 0;
            }


            br.close();
            System.out.println("Successfully read the file");
            return count;
        }catch (IOException e){

            System.out.println("An error Occurred. File Name Should not be Null or Empty");
            return 0;
        }
    }


    //the bidder will place the bid on a specific Lot
    public int placeBid(int lotNumber, int bidderId, int bid) {

        // Ensure that bidder id and lot Number must be greater than 0
        if (lotNumber>0 && bidderId>0 && bid>0 ){
        lotmap.get(lotNumber).setCurrentBid(currentBid);

        // used to get the auction object from lotNumber
        auctionObj = getAuctionObjforLotNumber(lotNumber);


        // It will check whether the lot exists or not
        boolean lotExists = lotNumberExists(lotNumber);

        lotmap.get(lotNumber).setBidderId(bidderId);
        lotmap.get(lotNumber).setBid(bid);

        lotmap.get(lotNumber).setCurrentBid(currentBid);
        lotmap.get(lotNumber).setRememberedBid(rememberedBid);

        lotmap.get(lotNumber).setNextValidBid((lotmap.get(lotNumber).getCurrentBid() + auctionObj.minBidIncrement));

        // Ensure that lot must exists
            if (lotExists == true && lotmap.get(lotNumber).getBid() >= lotmap.get(lotNumber).getNextValidBid()) {

                lotmap.get(lotNumber).setCurrentBid(lotmap.get(lotNumber).getNextValidBid());
                lotmap.get(lotNumber).setNextValidBid(lotmap.get(lotNumber).getCurrentBid() + auctionObj.minBidIncrement);

                if (lotmap.get(lotNumber).getBid() > lotmap.get(lotNumber).getNextValidBid()) {
                    lotmap.get(lotNumber).setRememberedBid(lotmap.get(lotNumber).getBid());
                    lotmap.get(lotNumber).setBidderId(bidderId);
                    lotmap.get(lotNumber).setBid(rememberedBid);
                }

                lotExists = false;

                totalBid = lotmap.get(lotNumber).getRememberedBid() + totalBid;

                return 4; // return 2 when the bid was accepted

            } else {
                System.out.println("Invalid Lot Number or Invalid Bid");
                return 0; // return 1 when bid was not accepted
            }
        }
        else {
            System.out.println("Lot Numbers, BidderId, and Bid must be greater than 0");
            return 0; // return 0 when other error s occurred
        }

    }

    public String feesOwed(){

        //checks weather bidder exists or not
        if (bidderId == 0){
            System.out.println("No Bidder Exists");
        }

        //it will notify user if only one bidder exists
        if (bidderId == 1){
            System.out.println("Only One Bidder Exists");
        }


        StringBuffer sb = new StringBuffer();

        for (int i=0; i<bidders.size(); i++) {
            sb.append(bidders.get(i).getBidderName());
            sb.append("\n");
        }

        return sb.toString();

    }


    // used to get the auctionObject for particular lotNumber
    public Auction getAuctionObjforLotNumber(int lotNumber){
        for (int i=0; i<lotsList.size(); i++){
            for (int j=0; j<auctionLotList.get(i).size(); j++){
                if (lotNumber == auctionLotList.get(i).get(j)){
                    return auctions.get(i);
                }
            }
        }
        return null;
    }


    // checks whether lot exists or Not
    public boolean lotNumberExists(int lotNumber){
        if(lotsList.contains(lotNumber)){
            return true;
        }
        else {
            return false;
        }
    }
}
