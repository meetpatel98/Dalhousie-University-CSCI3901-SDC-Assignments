import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class auctionAndBidderManagement {
    @Test
    void createAuction() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
        Auction auction1 = null;
        Auction auction2 = null;
        Auction auction3 = null;
        Auction auction4 = null;

        // Single letter auction name
        auction1 = auctionSystem.createAuction("a", 10, 15, 5);
        assertNotNull( auction1 );

        // Longer auction names
        // Range of lots
        auction2 = auctionSystem.createAuction("test1", 1, 2, 5);
        assertNotNull( auction2 );

        // Single lot
        auction3 = auctionSystem.createAuction("test2", 3, 3, 5);
        assertNotNull( auction3 );

        // Minimum bid increment
        auction4 = auctionSystem.createAuction("test3", 5, 8, 1);
        assertNotNull( auction4 );

    }

    @Test
    void createBidder() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
        Bidder bidder1 = null;

        bidder1 = auctionSystem.createBidder("z" );
        assertNotNull( bidder1 );
    }

    @Test
    void bidInNewAuction() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        Auction theAuction = auctionSystem.createAuction( "First", 2, 3, 1 );
        Bidder theBidder = auctionSystem.createBidder("someone" );

        assertNotNull( theAuction );
        assertNotNull( theBidder );

        // Try to bid on a new auction.  Should fail.

        assertEquals( 0, auctionSystem.placeBid( 2, theBidder.getBidderId(), 5 ) );
    }

    @Test
    void bidInOpenAuction() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        Auction theAuction = auctionSystem.createAuction( "First", 2, 3, 1 );
        Bidder theBidder = auctionSystem.createBidder("someone" );

        assertNotNull( theAuction );
        assertNotNull( theBidder );

        // Open the auction then try a bid then that should succeed.

        assertTrue( theAuction.openAuction() );
        assertEquals( 4, auctionSystem.placeBid( 2, theBidder.getBidderId(), 10 ) );
    }

    @Test
    void bidInClosedAuction() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        Auction theAuction = auctionSystem.createAuction( "First", 2, 3, 1 );
        Bidder theBidder = auctionSystem.createBidder("someone" );

        assertNotNull( theAuction );
        assertNotNull( theBidder );

        assertTrue( theAuction.openAuction() );

        // Close the auction then try a bid then that should fail.

        assertTrue( theAuction.closeAuction() );
        assertEquals( 0, auctionSystem.placeBid( 3, theBidder.getBidderId(), 10 ) );

    }

    @Test
    void openAndCloseOnClosedAuction() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        Auction theAuction = auctionSystem.createAuction( "First", 2, 3, 1 );

        assertNotNull( theAuction );

        assertTrue( theAuction.openAuction() );
        assertTrue( theAuction.closeAuction() );

        // Try the options on an auction that is now closed

        assertFalse( theAuction.openAuction() );
        assertFalse( theAuction.closeAuction() );
    }

    @Test
    void openAndCloseOnNewAuction() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        Auction theAuction = auctionSystem.createAuction( "First", 2, 3, 1 );

        assertNotNull( theAuction );

        //  Try the options on an auction that is new

        assertFalse( theAuction.closeAuction() );
        assertTrue( theAuction.openAuction() );
    }

    @Test
    void openAndCloseOnOpenAuction() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        Auction theAuction = auctionSystem.createAuction( "First", 2, 3, 1 );

        assertNotNull( theAuction );

        assertTrue( theAuction.openAuction() );

        // Try the options on an auction that is open

        assertFalse( theAuction.openAuction() );
        assertTrue( theAuction.closeAuction() );
    }

    @Test
    void createAuctionTests() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        Auction auction1 = auctionSystem.createAuction( "First", 2, 3, 1 );
        Auction auction2 = auctionSystem.createAuction( "Second", 4, 5, 1 );
        Auction auction3 = auctionSystem.createAuction( "Third", 6, 7, 1 );

        assertNotNull( auction1 );
        assertNotNull( auction2 );
        assertNotNull( auction3 );
    }

    @Test
    void createAuctionLotRangeTests() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Check on lot range overlaps
        Auction auction1 = auctionSystem.createAuction( "First", 10, 15, 1 );
        Auction auction2 = auctionSystem.createAuction( "Second", 5, 12, 1 );
        Auction auction3 = auctionSystem.createAuction( "Third", 13, 20, 1 );
        Auction auction4 = auctionSystem.createAuction( "Fourth", 2, 25, 1 );
        Auction auction5 = auctionSystem.createAuction( "Fifth", 30, 35, 1 );

        assertNotNull( auction1 );
        assertNull( auction2 );
        assertNull( auction3 );
        assertNull( auction4 );
        assertNotNull( auction5 );

        // Check on lot number interactions
        Auction auction6 = auctionSystem.createAuction( "Sixth", 100, 100, 1 );
        Auction auction7 = auctionSystem.createAuction( "Seventh", 150, 140, 1 );

        assertNotNull( auction6 );
        assertNull( auction7 );
    }

    @Test
    void createBidderTests() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        Bidder bidder1 = auctionSystem.createBidder( "Alice" );
        Bidder bidder2 = auctionSystem.createBidder( "Bob" );
        Bidder bidder3 = auctionSystem.createBidder( "Charlene" );

        assertNotNull( bidder1 );
        assertNotNull( bidder2 );
        assertNotNull( bidder3 );
    }
}
