import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

public class Bidder {

    private String bidderName;
    private int bidderId;

    public Bidder () {

    }

    //Constructor
    public Bidder(String bidderName) {
        this.bidderName = bidderName;
    }

    //Constructor
    public Bidder(int bidderId){
        this.bidderId = bidderId;
    }

    //Constructor
    public Bidder(String bidderName, int bidderId){
        this.bidderId = bidderId;
        this.bidderName = bidderName;
    }

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public int getBidderId() {
        return bidderId;
    }

    public void setBidderId(int bidderId) {
        this.bidderId = bidderId;
    }
}
