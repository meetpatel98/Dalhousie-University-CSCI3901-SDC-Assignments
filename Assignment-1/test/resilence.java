import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class resilience {

    @Test
    void createAuctionNullName() {

        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        assertNull( auctionSystem.createAuction(null, 10, 15, 1) );
    }

    @Test
    void createAuctionEmptyName() {

        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        assertNull( auctionSystem.createAuction("", 10, 15, 1) );
    }

    @Test
    void createAuctionNegativeFirstLot() {

        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        assertNull( auctionSystem.createAuction("test1", -1, 15, 1) );
    }

    @Test
    void createAuctionZeroFirstLot() {

        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        assertNull( auctionSystem.createAuction("test2", 0, 15, 1) );
    }

    @Test
    void createAuctionNegativeEndLot() {

        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        assertNull( auctionSystem.createAuction("test3", 10, -1, 1) );
    }

    @Test
    void createAuctionZeroEndLot() {

        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        assertNull( auctionSystem.createAuction("test4", 10, 0, 1) );
    }

    @Test
    void createAuctionNegativeIncrement() {

        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        assertNull( auctionSystem.createAuction("test5", 10, 15, -1) );
    }

    @Test
    void createAuctionZeroIncrement() {

        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        assertNull( auctionSystem.createAuction("test6", 10, 15, 0) );
    }

    @Test
    void createBidderNullName() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        assertNull( auctionSystem.createBidder(null ) );
    }

    @Test
    void createBidderEmptyName() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        assertNull( auctionSystem.createBidder( "" ) );
    }

    @Test
    void loadBidsNullFilename() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        assertEquals( 0, auctionSystem.loadBids(null ) );
    }

    @Test
    void loadBidsEmptyFilename() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        assertEquals( 0, auctionSystem.loadBids("" ) );
    }

    @Test
    void placeBidNegativeLot() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Load a basic auction to ensure the failure isn't from having no auction data

        Auction auction1 = auctionSystem.createAuction( "first", 1, 3, 10 );
        Auction auction2 = auctionSystem.createAuction( "second", 4, 7, 20 );

        Bidder bidder1 = auctionSystem.createBidder( "Alice " );

        // Make bids with bad parameters.  Only one parameter is bad at a time.

        assertEquals( 0, auctionSystem.placeBid( -1, bidder1.getBidderId(), 100 ) );
    }

    @Test
    void placeBidZeroLot() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Load a basic auction to ensure the failure isn't from having no auction data

        Auction auction1 = auctionSystem.createAuction( "first", 1, 3, 10 );
        Auction auction2 = auctionSystem.createAuction( "second", 4, 7, 20 );

        Bidder bidder1 = auctionSystem.createBidder( "Alice " );

        // Make bids with bad parameters.  Only one parameter is bad at a time.

        assertEquals( 0, auctionSystem.placeBid( 0, bidder1.getBidderId(), 100 ) );
    }

    @Test
    void placeBidLotNumberTooHigh() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Load a basic auction to ensure the failure isn't from having no auction data

        Auction auction1 = auctionSystem.createAuction( "first", 1, 3, 10 );
        Auction auction2 = auctionSystem.createAuction( "second", 4, 7, 20 );

        Bidder bidder1 = auctionSystem.createBidder( "Alice " );

        // Make bids with bad parameters.  Only one parameter is bad at a time.

        assertEquals( 0, auctionSystem.placeBid( 8, bidder1.getBidderId(), 100 ) );
    }

    @Test
    void placeBidNegativeBidderId() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Load a basic auction to ensure the failure isn't from having no auction data

        Auction auction1 = auctionSystem.createAuction( "first", 1, 3, 10 );
        Auction auction2 = auctionSystem.createAuction( "second", 4, 7, 20 );

        Bidder bidder1 = auctionSystem.createBidder( "Alice " );

        // Make bids with bad parameters.  Only one parameter is bad at a time.

        assertEquals( 0, auctionSystem.placeBid( 1, -1, 100 ) );
    }

    @Test
    void placeBidZeroBidderId() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Load a basic auction to ensure the failure isn't from having no auction data

        Auction auction1 = auctionSystem.createAuction( "first", 1, 3, 10 );
        Auction auction2 = auctionSystem.createAuction( "second", 4, 7, 20 );

        Bidder bidder1 = auctionSystem.createBidder( "Alice " );

        // Make bids with bad parameters.  Only one parameter is bad at a time.

        assertEquals( 0, auctionSystem.placeBid( 1, 0, 100 ) );
    }

    @Test
    void placeBidBidderIdTooHigh() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Load a basic auction to ensure the failure isn't from having no auction data

        Auction auction1 = auctionSystem.createAuction( "first", 1, 3, 10 );
        Auction auction2 = auctionSystem.createAuction( "second", 4, 7, 20 );

        Bidder bidder1 = auctionSystem.createBidder( "Alice " );

        // Make bids with bad parameters.  Only one parameter is bad at a time.

        assertEquals( 0, auctionSystem.placeBid( 1, 3, 100 ) );
    }

    @Test
    void placeBidNegativeBid() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Load a basic auction to ensure the failure isn't from having no auction data

        Auction auction1 = auctionSystem.createAuction( "first", 1, 3, 10 );
        Auction auction2 = auctionSystem.createAuction( "second", 4, 7, 20 );

        Bidder bidder1 = auctionSystem.createBidder( "Alice " );

        // Make bids with bad parameters.  Only one parameter is bad at a time.

        int bidOutcome = auctionSystem.placeBid( 1, bidder1.getBidderId(), -1 );
        if (bidOutcome == 1) {
            // We will consider, for the class, that claiming a bad bid is also ok
            bidOutcome = 0;
        }
        assertEquals( 0, bidOutcome );
    }

    @Test
    void placeBidZeroBid() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Load a basic auction to ensure the failure isn't from having no auction data

        Auction auction1 = auctionSystem.createAuction( "first", 1, 3, 10 );
        Auction auction2 = auctionSystem.createAuction( "second", 4, 7, 20 );

        Bidder bidder1 = auctionSystem.createBidder( "Alice " );

        // Make bids with bad parameters.  Only one parameter is bad at a time.

        int bidOutcome = auctionSystem.placeBid( 1, bidder1.getBidderId(), -1 );
        if (bidOutcome == 1) {
            // We will consider, for the class, that claiming a bad bid is also ok
            bidOutcome = 0;
        }

        assertEquals( 0, bidOutcome );
    }
}