import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class bidsFromFiles {

    private String basepath = "/Users/mcallist/IdeaProjects/online auction/testfiles";

    @Test
    void noFile() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Make some auctions

        Auction auction1 = auctionSystem.createAuction( "first", 1, 9, 2);
        Auction auction2 = auctionSystem.createAuction( "second", 10, 19, 2);
        Auction auction3 = auctionSystem.createAuction( "third", 20, 29, 2);

        assertNotNull( auction1 );
        assertNotNull( auction2 );
        assertNotNull( auction3 );

        assertTrue( auction1.openAuction() );
        assertTrue( auction2.openAuction() );
        assertTrue( auction3.openAuction() );

        // Make some bidders
        Bidder bidder1 = auctionSystem.createBidder( "Alice" );
        Bidder bidder2 = auctionSystem.createBidder( "Bob" );
        Bidder bidder3 = auctionSystem.createBidder( "Candace" );

        assertNotNull( bidder1 );
        assertNotNull( bidder2 );
        assertNotNull( bidder3 );

        // Read in a file that doesn't exist

        assertEquals( 0, auctionSystem.loadBids( basepath + "/nofile.txt" ) );
    }

    @Test
    void emptyFile() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Make some auctions

        Auction auction1 = auctionSystem.createAuction( "first", 1, 9, 2);
        Auction auction2 = auctionSystem.createAuction( "second", 10, 19, 2);
        Auction auction3 = auctionSystem.createAuction( "third", 20, 29, 2);

        assertNotNull( auction1 );
        assertNotNull( auction2 );
        assertNotNull( auction3 );

        assertTrue( auction1.openAuction() );
        assertTrue( auction2.openAuction() );
        assertTrue( auction3.openAuction() );

        // Make some bidders
        Bidder bidder1 = auctionSystem.createBidder( "Alice" );
        Bidder bidder2 = auctionSystem.createBidder( "Bob" );
        Bidder bidder3 = auctionSystem.createBidder( "Candace" );

        assertNotNull( bidder1 );
        assertNotNull( bidder2 );
        assertNotNull( bidder3 );

        // Read in a file

        assertEquals( 0, auctionSystem.loadBids( basepath + "/empty.txt" ) );
    }


    @Test
    void singleLineFile() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Make some auctions

        Auction auction1 = auctionSystem.createAuction( "first", 1, 9, 2);
        Auction auction2 = auctionSystem.createAuction( "second", 10, 19, 2);
        Auction auction3 = auctionSystem.createAuction( "third", 20, 29, 2);

        assertNotNull( auction1 );
        assertNotNull( auction2 );
        assertNotNull( auction3 );

        assertTrue( auction1.openAuction() );
        assertTrue( auction2.openAuction() );
        assertTrue( auction3.openAuction() );

        // Make some bidders
        Bidder bidder1 = auctionSystem.createBidder( "Alice" );
        Bidder bidder2 = auctionSystem.createBidder( "Bob" );
        Bidder bidder3 = auctionSystem.createBidder( "Candace" );

        assertNotNull( bidder1 );
        assertNotNull( bidder2 );
        assertNotNull( bidder3 );

        // Read in a file

        assertEquals( 1, auctionSystem.loadBids( basepath + "/oneLine.txt" ) );
    }


    @Test
    void manyLinesOneAuctionOneAccept() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Make some auctions

        Auction auction1 = auctionSystem.createAuction( "first", 1, 9, 2);
        Auction auction2 = auctionSystem.createAuction( "second", 10, 19, 2);
        Auction auction3 = auctionSystem.createAuction( "third", 20, 29, 2);

        assertNotNull( auction1 );
        assertNotNull( auction2 );
        assertNotNull( auction3 );

        assertTrue( auction1.openAuction() );
        assertTrue( auction2.openAuction() );
        assertTrue( auction3.openAuction() );

        // Make some bidders
        Bidder bidder1 = auctionSystem.createBidder( "Alice" );
        Bidder bidder2 = auctionSystem.createBidder( "Bob" );
        Bidder bidder3 = auctionSystem.createBidder( "Candace" );

        assertNotNull( bidder1 );
        assertNotNull( bidder2 );
        assertNotNull( bidder3 );

        // Read in a file

        assertEquals( 1, auctionSystem.loadBids( basepath + "/manySameAuctionOneAccept.txt" ) );
    }


    @Test
    void allAcceptOneAuction() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Make some auctions

        Auction auction1 = auctionSystem.createAuction( "first", 1, 9, 2);
        Auction auction2 = auctionSystem.createAuction( "second", 10, 19, 2);
        Auction auction3 = auctionSystem.createAuction( "third", 20, 29, 2);

        assertNotNull( auction1 );
        assertNotNull( auction2 );
        assertNotNull( auction3 );

        assertTrue( auction1.openAuction() );
        assertTrue( auction2.openAuction() );
        assertTrue( auction3.openAuction() );

        // Make some bidders
        Bidder bidder1 = auctionSystem.createBidder( "Alice" );
        Bidder bidder2 = auctionSystem.createBidder( "Bob" );
        Bidder bidder3 = auctionSystem.createBidder( "Candace" );

        assertNotNull( bidder1 );
        assertNotNull( bidder2 );
        assertNotNull( bidder3 );

        // Read in a file

        assertEquals( 4, auctionSystem.loadBids( basepath + "/manySameAuctionAllAccept.txt" ) );
    }


    @Test
    void sameAuctionSomeAccept() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Make some auctions

        Auction auction1 = auctionSystem.createAuction( "first", 1, 9, 2);
        Auction auction2 = auctionSystem.createAuction( "second", 10, 19, 2);
        Auction auction3 = auctionSystem.createAuction( "third", 20, 29, 2);

        assertNotNull( auction1 );
        assertNotNull( auction2 );
        assertNotNull( auction3 );

        assertTrue( auction1.openAuction() );
        assertTrue( auction2.openAuction() );
        assertTrue( auction3.openAuction() );

        // Make some bidders
        Bidder bidder1 = auctionSystem.createBidder( "Alice" );
        Bidder bidder2 = auctionSystem.createBidder( "Bob" );
        Bidder bidder3 = auctionSystem.createBidder( "Candace" );

        assertNotNull( bidder1 );
        assertNotNull( bidder2 );
        assertNotNull( bidder3 );

        // Read in a file

        assertEquals( 4, auctionSystem.loadBids( basepath + "/manySameAuctionSomeAccept.txt" ) );
    }


    @Test
    void differentAuction() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Make some auctions

        Auction auction1 = auctionSystem.createAuction( "first", 1, 9, 2);
        Auction auction2 = auctionSystem.createAuction( "second", 10, 19, 2);
        Auction auction3 = auctionSystem.createAuction( "third", 20, 29, 2);

        assertNotNull( auction1 );
        assertNotNull( auction2 );
        assertNotNull( auction3 );

        assertTrue( auction1.openAuction() );
        assertTrue( auction2.openAuction() );
        assertTrue( auction3.openAuction() );

        // Make some bidders
        Bidder bidder1 = auctionSystem.createBidder( "Alice" );
        Bidder bidder2 = auctionSystem.createBidder( "Bob" );
        Bidder bidder3 = auctionSystem.createBidder( "Candace" );

        assertNotNull( bidder1 );
        assertNotNull( bidder2 );
        assertNotNull( bidder3 );

        // Read in a file

        assertEquals( 5, auctionSystem.loadBids( basepath + "/manyDifferentAuctions.txt" ) );
    }

}