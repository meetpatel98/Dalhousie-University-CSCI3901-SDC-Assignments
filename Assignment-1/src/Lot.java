public class Lot {
    int currentBid;
    int minimumBidIncrement;
    int rememberedBid;
    int nextValidBid;
    int lotId;
    int bidderId;
    int bid;

    public Lot(){

    }

    //constructor
    public Lot(int currentBid, int minimumBidIncrement, int rememberedBid, int nextValidBid, int lotId, int bid, int bidderId) {
        this.currentBid = currentBid;
        this.minimumBidIncrement = minimumBidIncrement;
        this.rememberedBid = rememberedBid;
        this.nextValidBid = nextValidBid;
        this.lotId = lotId;
        this.bid = bid;
        this.bidderId = bidderId;
    }


    //constructor
    public Lot(int currentBid, int minimumBidIncrement, int rememberedBid, int nextValidBid) {
        this.currentBid = currentBid;
        this.minimumBidIncrement = minimumBidIncrement;
        this.rememberedBid = rememberedBid;
        this.nextValidBid = nextValidBid;

    }


    // Getter Setter method for all the variables


    public int getBidderId() {
        return bidderId;
    }

    public void setBidderId(int bidderId) {
        this.bidderId = bidderId;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(int currentBid) {
        this.currentBid = currentBid;
    }

    public int getMinimumBidIncrement() {
        return minimumBidIncrement;
    }

    public void setMinimumBidIncrement(int minimumBidIncrement) {
        this.minimumBidIncrement = minimumBidIncrement;
    }

    public int getRememberedBid() {
        return rememberedBid;
    }

    public void setRememberedBid(int rememberedBid) {
        this.rememberedBid = rememberedBid;
    }

    public int getNextValidBid() {
        return nextValidBid;
    }

    public void setNextValidBid(int nextValidBid) {
        this.nextValidBid = nextValidBid;
    }

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }
}
