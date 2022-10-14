import javax.management.ObjectName;
import javax.sound.midi.Soundbank;
import java.util.ArrayList;

public class Auction extends OnlineAuctionSystem{

    private String auctionName;
    private int firstLotNumber;
    private int lastLotNumber;
    public int minBidIncrement;
    public String statusString;
    public boolean status;
    private int auctionId;
    private int bidderId;
    private Lot lots;
    private int totalBid;
    Auction auctionObj;

    private ArrayList<Integer> auctionLots = new ArrayList<Integer>(); // stores the list of lots


    //constructor
    public Auction(String auctionName, int firstLotNumber, int lastLotNumber, int minBidIncrement, String statusString, int auctionId, boolean status, ArrayList<Integer> auctionLots, int totalBid) {

        this.auctionName = auctionName;
        this.firstLotNumber = firstLotNumber;
        this.lastLotNumber = lastLotNumber;
        this.minBidIncrement = minBidIncrement;
        this.statusString = statusString;
        this.auctionId = auctionId;
        this.status = status;
        this.auctionLots = auctionLots;
        this.totalBid = totalBid;
    }
    public Auction(){

    }

    // it will open the auction and set the status as Open
    public Boolean openAuction(){

        OnlineAuctionSystem.auctions.get(auctionId - 1).setStatusString("open");
        OnlineAuctionSystem.auctions.get(auctionId - 1).setStatus(true);
        System.out.println("Auction "+ auctionName +" Opened Successfully");
        return OnlineAuctionSystem.auctions.get(auctionId - 1).isStatus();
    }


    // it will close the auction and set the status as close
    public Boolean closeAuction(){

        OnlineAuctionSystem.auctions.get(auctionId - 1).setStatusString("close");
        OnlineAuctionSystem.auctions.get(auctionId - 1).setStatus(false);
        System.out.println("Auction "+ auctionName +" Closed Successfully");
        return OnlineAuctionSystem.auctions.get(auctionId - 1).isStatus();
    }


    // return the winningBids
    public String winningBids(){

        StringBuffer sb = new StringBuffer();

        for (int i=auctions.get(auctionId - 1).firstLotNumber; i<=auctions.get(auctionId - 1).lastLotNumber; i++)
        {
            sb.append(i);
            sb.append(" ");
            sb.append(OnlineAuctionSystem.lotmap.get(i).getCurrentBid());
            sb.append(" ");
            sb.append(OnlineAuctionSystem.lotmap.get(i).getBidderId());
            sb.append("\n");

        }
        return sb.toString(); // return the lotNumbers and, bidderId and the bid who won the particular lot
    }

    // Setter and Getter of all the variables

    public ArrayList<Integer> getAuctionLots() {
        return auctionLots;
    }

    public Lot getLots() {
        return lots;
    }

    public void setLots(Lot lots) {
        this.lots = lots;
    }

    public void setAuctionLots(ArrayList<Integer> auctionLots) {
        this.auctionLots = auctionLots;
    }

    public int getBidderId() {
        return bidderId;
    }

    public void setBidderId(int bidderId) {
        this.bidderId = bidderId;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotalBid() {
        return totalBid;
    }

    public void setTotalBid(int totalBid) {
        this.totalBid = totalBid;
    }

    public Auction(Auction auctionObj){
        this.auctionObj = auctionObj;
    }

    public Auction(int auctionId){
        this.auctionId = auctionId;
    }
}
