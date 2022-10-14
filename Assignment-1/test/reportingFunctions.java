import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class reportingFunctions {
    @Test
    void auctionStatus() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
        Auction auction1 = null;
        Bidder bidder1 = null;

        // Nothing in the system.  Shouldn't break.
        assertEquals( "", auctionSystem.auctionStatus() );

        // Add an auction and see it there.

        auction1 = auctionSystem.createAuction( "theAuction", 1, 3, 5 );
        assertNotNull( auction1 );
        assertEquals( "theAuction\tnew\t0\n", auctionSystem.auctionStatus() );
    }

    @Test
    void feesOwedSingleBidderNoBids() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
        Bidder bidder1 = null;

        // Nothing in the system.  Shouldn't break.
        assertEquals( "", auctionSystem.feesOwed() );

        // Check with a single bidder
        bidder1 = auctionSystem.createBidder( "newBidder" );
        assertNotNull( bidder1 );
        assertEquals( "newBidder\t0\t0\n", auctionSystem.feesOwed() );
    }
    @Test
    void winningBidsSingleAuctionNoBids() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
        Auction auction1 = null;
        Bidder bidder1 = null;

        auction1 = auctionSystem.createAuction( "alpha", 1, 1, 5 );
        assertNotNull( auction1 );
        assertEquals( "1\t0\t0\n", auction1.winningBids() );
    }

    @Test
    void auctionStatusThreeAuctionsOpen() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        Auction auction1 = auctionSystem.createAuction( "First", 2, 3, 1 );
        Auction auction2 = auctionSystem.createAuction( "Second", 4, 5, 1 );
        Auction auction3 = auctionSystem.createAuction( "Third", 6, 7, 1 );

        if (auction2.openAuction() && auction3.openAuction() && auction3.closeAuction()) {
            assertEquals("First\tnew\t0\nSecond\topen\t0\nThird\tclosed\t0\n", auctionSystem.auctionStatus());
        }
    }

    @Test
    void winningBidsAuctionWithBids() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
        Auction theAuction = auctionSystem.createAuction( "FirstAuction", 10, 15, 2 );
        assertNotNull( theAuction );

        theAuction.openAuction();

        // Make some bidders to work with

        Bidder bidder1 = auctionSystem.createBidder( "Alice" );
        Bidder bidder2 = auctionSystem.createBidder( "Bob" );
        Bidder bidder3 = auctionSystem.createBidder( "Charlie" );

        // Set up some bids on lots.  Leave lot 11 without a bid.

        assertEquals( 3, auctionSystem.placeBid( 12, bidder1.getBidderId(), 2 ));

        assertEquals( 4, auctionSystem.placeBid( 13, bidder2.getBidderId(), 4 ));
        assertEquals( 3, auctionSystem.placeBid( 13, bidder3.getBidderId(), 6 ));

        assertEquals( 4, auctionSystem.placeBid( 14, bidder2.getBidderId(), 10 ));
        assertEquals( 2, auctionSystem.placeBid( 14, bidder3.getBidderId(), 6 ));

        // Check out the outcomes

        assertEquals( "10\t0\t0\n11\t0\t0\n12\t2\t1\n13\t6\t3\n14\t8\t2\n15\t0\t0\n", theAuction.winningBids() );

    }

    @Test
    void feesOwedWithSeveralAuctions() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

        // Get a mix of auctions to receive bids

        Auction auction1 = auctionSystem.createAuction( "FirstAuction", 10, 19, 2 );
        Auction auction2 = auctionSystem.createAuction( "SecondAuction", 20, 29, 2 );
        Auction auction3 = auctionSystem.createAuction( "ThirdAuction", 30, 39, 2 );
        Auction auction4 = auctionSystem.createAuction( "FourthAuction", 40, 49, 2 );
        Auction auction5 = auctionSystem.createAuction( "NewAuction", 50, 59, 2 );

        assertNotNull( auction1 );
        assertNotNull( auction2 );
        assertNotNull( auction3 );
        assertNotNull( auction4 );
        assertNotNull( auction5 );

        assertTrue( auction1.openAuction());
        assertTrue( auction2.openAuction());
        assertTrue( auction3.openAuction());
        assertTrue( auction4.openAuction());

        // Set up some bidders too

        Bidder bidder1 = auctionSystem.createBidder( "Alice" ); // win 0 closed, 0 open
        Bidder bidder2 = auctionSystem.createBidder( "Bob" ); // win 0 closed, 1 open
        Bidder bidder3 = auctionSystem.createBidder( "Charlie" ); // win 1 closed, 0 open
        Bidder bidder4 = auctionSystem.createBidder( "Denise" ); // win 1 closed, 1 open
        Bidder bidder5 = auctionSystem.createBidder( "Edna" ); // win many closed, 0 open
        Bidder bidder6 = auctionSystem.createBidder( "Frank" ); // win many closed, many open

        assertNotNull( bidder1 );
        assertNotNull( bidder2 );
        assertNotNull( bidder3 );
        assertNotNull( bidder4 );
        assertNotNull( bidder5 );
        assertNotNull( bidder6 );

        // Set the bids as we'll expect.  Auctions 2 and 3 will get closed.  Auction 5 is never opened.

        // Win 0 closed, 1 open
        assertEquals( 3, auctionSystem.placeBid( 15, bidder2.getBidderId(), 2 ));

        // Win 1 closed, 0 open

        assertEquals( 3, auctionSystem.placeBid( 24, bidder3.getBidderId(), 2 ));

        // Win 1 closed, 1 open

        assertEquals( 3, auctionSystem.placeBid( 26, bidder4.getBidderId(), 2 ));
        assertEquals( 3, auctionSystem.placeBid( 16, bidder4.getBidderId(), 2 ));

        // Win many closed, 0 open

        assertEquals( 3, auctionSystem.placeBid( 30, bidder5.getBidderId(), 2 ));
        assertEquals( 3, auctionSystem.placeBid( 31, bidder5.getBidderId(), 2 ));
        assertEquals( 3, auctionSystem.placeBid( 32, bidder5.getBidderId(), 2 ));
        assertEquals( 3, auctionSystem.placeBid( 33, bidder5.getBidderId(), 2 ));
        assertEquals( 3, auctionSystem.placeBid( 34, bidder5.getBidderId(), 2 ));

        // Win many closed, many open

        assertEquals( 3, auctionSystem.placeBid( 35, bidder6.getBidderId(), 2 ));
        assertEquals( 3, auctionSystem.placeBid( 36, bidder6.getBidderId(), 2 ));
        assertEquals( 3, auctionSystem.placeBid( 37, bidder6.getBidderId(), 2 ));
        assertEquals( 3, auctionSystem.placeBid( 38, bidder6.getBidderId(), 2 ));
        assertEquals( 3, auctionSystem.placeBid( 42, bidder6.getBidderId(), 2 ));
        assertEquals( 3, auctionSystem.placeBid( 43, bidder6.getBidderId(), 2 ));
        assertEquals( 3, auctionSystem.placeBid( 44, bidder6.getBidderId(), 2 ));
        assertEquals( 3, auctionSystem.placeBid( 45, bidder6.getBidderId(), 2 ));
        assertEquals( 3, auctionSystem.placeBid( 46, bidder6.getBidderId(), 2 ));

        // Close off the relevant auctions

        assertTrue( auction2.closeAuction() );
        assertTrue( auction3.closeAuction() );

        // Check out the outcomes

        assertEquals( "Alice\t0\t0\nBob\t0\t0\nCharlie\t1\t2\nDenise\t1\t2\nEdna\t5\t10\nFrank\t4\t8\n", auctionSystem.feesOwed() );

    }
}
