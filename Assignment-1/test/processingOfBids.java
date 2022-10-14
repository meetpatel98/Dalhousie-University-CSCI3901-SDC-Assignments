import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class processingOfBids {

        @Test
        void NoPriorNoReserveMinBidWins() {

            OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
            Auction newAuction = auctionSystem.createAuction("testAuction", 10, 15, 1);
            Bidder newBidder = auctionSystem.createBidder("person1");

            assertTrue(newAuction.openAuction());

            assertEquals(3, auctionSystem.placeBid(10, newBidder.getBidderId(), 1));

            assertEquals("10\t1\t1\n11\t0\t0\n12\t0\t0\n13\t0\t0\n14\t0\t0\n15\t0\t0\n", newAuction.winningBids(), "No reserved bid, minimum bid wins");
        }

        @Test
        void NoPriorNoReserveBelowMinBid() {

            OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
            Auction newAuction = auctionSystem.createAuction("testAuction", 10, 15, 5);
            Bidder newBidder = auctionSystem.createBidder("person1");

            assertTrue(newAuction.openAuction());

            assertEquals(2, auctionSystem.placeBid(10, newBidder.getBidderId(), 1));

            assertEquals("10\t0\t0\n11\t0\t0\n12\t0\t0\n13\t0\t0\n14\t0\t0\n15\t0\t0\n", newAuction.winningBids(), "Bid is below minimum bid");

        }

        @Test
        void NoPriorNoReserveOverbidWins() {

            OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
            Auction newAuction = auctionSystem.createAuction("testAuction", 10, 15, 1);
            Bidder newBidder = auctionSystem.createBidder("person1");

            assertTrue(newAuction.openAuction());

            assertEquals(4, auctionSystem.placeBid(10, newBidder.getBidderId(), 100));

            assertEquals("10\t1\t1\n11\t0\t0\n12\t0\t0\n13\t0\t0\n14\t0\t0\n15\t0\t0\n", newAuction.winningBids(), "No reserve bid, overbid wins");

        }


        @Test
        void PriorBidNoReserveBelowCurrentBid() {

            OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
            Auction newAuction = auctionSystem.createAuction("testAuction", 10, 15, 5);
            Bidder oldBidder = auctionSystem.createBidder("person1");
            Bidder middleBidder = auctionSystem.createBidder("person2");
            Bidder newBidder = auctionSystem.createBidder("person3");

            assertTrue(newAuction.openAuction());

            // Get in a first bidder
            assertEquals(4, auctionSystem.placeBid(10, oldBidder.getBidderId(), 10));
            // Bump up the current bid to the desired level
            assertEquals(2, auctionSystem.placeBid(10, middleBidder.getBidderId(), 10));

            // Now bring in the test bidder
            assertEquals(2, auctionSystem.placeBid(10, newBidder.getBidderId(), 5));

            assertEquals("10\t10\t1\n11\t0\t0\n12\t0\t0\n13\t0\t0\n14\t0\t0\n15\t0\t0\n", newAuction.winningBids(), "No reserve bid, current bid not high enough to win");
        }

        @Test
        void PriorBidNoReserveBeatMaxBid() {

            OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
            Auction newAuction = auctionSystem.createAuction("testAuction", 10, 15, 5);
            Bidder oldBidder = auctionSystem.createBidder("person1");
            Bidder middleBidder = auctionSystem.createBidder("person2");
            Bidder newBidder = auctionSystem.createBidder("person3");

            assertTrue(newAuction.openAuction());

            // Get in a first bidder
            assertEquals(4, auctionSystem.placeBid(10, oldBidder.getBidderId(), 10));
            // Bump up the current bid to the desired level
            assertEquals(2, auctionSystem.placeBid(10, middleBidder.getBidderId(), 10));

            // Now bring in the test bidder
            assertEquals(3, auctionSystem.placeBid(10, newBidder.getBidderId(), 15));

            assertEquals("10\t15\t3\n11\t0\t0\n12\t0\t0\n13\t0\t0\n14\t0\t0\n15\t0\t0\n", newAuction.winningBids());

        }

        @Test
        void PriorBidNoReserveBeatMaxBidLeaveReserve() {

            OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
            Auction newAuction = auctionSystem.createAuction("testAuction", 10, 15, 5);
            Bidder oldBidder = auctionSystem.createBidder("person1");
            Bidder middleBidder = auctionSystem.createBidder("person2");
            Bidder newBidder = auctionSystem.createBidder("person3");

            assertTrue(newAuction.openAuction());

            // Get in a first bidder
            assertEquals(4, auctionSystem.placeBid(10, oldBidder.getBidderId(), 10));
            // Bump up the current bid to the desired level
            assertEquals(2, auctionSystem.placeBid(10, middleBidder.getBidderId(), 10));

            // Now bring in the test bidder
            assertEquals(4, auctionSystem.placeBid(10, newBidder.getBidderId(), 20));

            assertEquals("10\t15\t3\n11\t0\t0\n12\t0\t0\n13\t0\t0\n14\t0\t0\n15\t0\t0\n", newAuction.winningBids());

        }

        @Test
        void PriorBidWithReserveBidBelowReserve() {

            OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
            Auction newAuction = auctionSystem.createAuction("testAuction", 10, 15, 5);
            Bidder oldBidder = auctionSystem.createBidder("person1");
            Bidder middleBidder = auctionSystem.createBidder("person2");
            Bidder newBidder = auctionSystem.createBidder("person3");

            assertTrue(newAuction.openAuction());

            // Get in a first bidder
            assertEquals(4, auctionSystem.placeBid(10, oldBidder.getBidderId(), 10));
            // Bump up the current bid to the desired level
            assertEquals(2, auctionSystem.placeBid(10, middleBidder.getBidderId(), 10));
            assertEquals(4, auctionSystem.placeBid(10, oldBidder.getBidderId(), 20));

            // Now bring in the test bidder
            assertEquals(2, auctionSystem.placeBid(10, newBidder.getBidderId(), 15));

            assertEquals("10\t20\t1\n11\t0\t0\n12\t0\t0\n13\t0\t0\n14\t0\t0\n15\t0\t0\n", newAuction.winningBids());

        }

        @Test
        void ReserveReplacementBids() {

            OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
            Auction newAuction = auctionSystem.createAuction("testAuction", 10, 15, 5);
            Bidder oldBidder = auctionSystem.createBidder("person1");
            Bidder middleBidder = auctionSystem.createBidder("person2");

            assertTrue(newAuction.openAuction());

            // Get in a first bidder
            assertEquals(4, auctionSystem.placeBid(10, oldBidder.getBidderId(), 10));
            // Bump up the current bid to the desired level
            assertEquals(2, auctionSystem.placeBid(10, middleBidder.getBidderId(), 10));

            // Underbid, which should fail
            assertEquals(2, auctionSystem.placeBid(10, oldBidder.getBidderId(), 5));

            // Rebid the current value
            assertEquals(3, auctionSystem.placeBid(10, oldBidder.getBidderId(), 10));

            // Increase the reserve bid
            assertEquals(4, auctionSystem.placeBid(10, oldBidder.getBidderId(), 15));

            assertEquals("10\t10\t1\n11\t0\t0\n12\t0\t0\n13\t0\t0\n14\t0\t0\n15\t0\t0\n", newAuction.winningBids());
        }


        @Test
        void PriorBidWithReserveBidAtReserve() {

            OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
            Auction newAuction = auctionSystem.createAuction("testAuction", 10, 15, 5);
            Bidder oldBidder = auctionSystem.createBidder("person1");
            Bidder middleBidder = auctionSystem.createBidder("person2");
            Bidder newBidder = auctionSystem.createBidder("person3");

            assertTrue(newAuction.openAuction());

            // Get in a first bidder
            assertEquals(4, auctionSystem.placeBid(10, oldBidder.getBidderId(), 10));
            // Bump up the current bid to the desired level
            assertEquals(2, auctionSystem.placeBid(10, middleBidder.getBidderId(), 10));
            assertEquals(4, auctionSystem.placeBid(10, oldBidder.getBidderId(), 20));

            // Now bring in the test bidder
            assertEquals(2, auctionSystem.placeBid(10, newBidder.getBidderId(), 20));

            assertEquals("10\t20\t1\n11\t0\t0\n12\t0\t0\n13\t0\t0\n14\t0\t0\n15\t0\t0\n", newAuction.winningBids());
        }

        @Test
        void PriorBidWithReserveBidAboveReserve() {

            OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
            Auction newAuction = auctionSystem.createAuction("testAuction", 10, 15, 5);
            Bidder oldBidder = auctionSystem.createBidder("person1");
            Bidder middleBidder = auctionSystem.createBidder("person2");
            Bidder newBidder = auctionSystem.createBidder("person3");

            assertTrue(newAuction.openAuction());

            // Get in a first bidder
            assertEquals(4, auctionSystem.placeBid(10, oldBidder.getBidderId(), 10));
            // Bump up the current bid to the desired level
            assertEquals(2, auctionSystem.placeBid(10, middleBidder.getBidderId(), 10));
            assertEquals(4, auctionSystem.placeBid(10, oldBidder.getBidderId(), 20));

            // Now bring in the test bidder
            assertEquals(3, auctionSystem.placeBid(10, newBidder.getBidderId(), 25));

            assertEquals("10\t25\t3\n11\t0\t0\n12\t0\t0\n13\t0\t0\n14\t0\t0\n15\t0\t0\n", newAuction.winningBids());

        }


        @Test
        void PriorBidWithReserveBidAboveReserveReplaceReserve() {

            OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
            Auction newAuction = auctionSystem.createAuction("testAuction", 10, 15, 5);
            Bidder oldBidder = auctionSystem.createBidder("person1");
            Bidder middleBidder = auctionSystem.createBidder("person2");
            Bidder newBidder = auctionSystem.createBidder("person3");

            assertTrue(newAuction.openAuction());

            // Get in a first bidder
            assertEquals(4, auctionSystem.placeBid(10, oldBidder.getBidderId(), 10));
            // Bump up the current bid to the desired level
            assertEquals(2, auctionSystem.placeBid(10, middleBidder.getBidderId(), 10));
            assertEquals(4, auctionSystem.placeBid(10, oldBidder.getBidderId(), 20));

            // Now bring in the test bidder
            assertEquals(4, auctionSystem.placeBid(10, newBidder.getBidderId(), 30));

            assertEquals("10\t25\t3\n11\t0\t0\n12\t0\t0\n13\t0\t0\n14\t0\t0\n15\t0\t0\n", newAuction.winningBids());

        }


        @Test
        void PriorBidNoReserveBidBelowMin() {

            OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
            Auction newAuction = auctionSystem.createAuction("testAuction", 10, 15, 5);
            Bidder oldBidder = auctionSystem.createBidder("person1");
            Bidder middleBidder = auctionSystem.createBidder("person2");
            Bidder newBidder = auctionSystem.createBidder("person3");

            assertTrue(newAuction.openAuction());

            // Get in a first bidder
            assertEquals(4, auctionSystem.placeBid(10, oldBidder.getBidderId(), 10));
            // Bump up the current bid to the desired level
            assertEquals(2, auctionSystem.placeBid(10, middleBidder.getBidderId(), 10));

            // Now bring in the test bidder
            assertEquals(2, auctionSystem.placeBid(10, newBidder.getBidderId(), 11));

            assertEquals("10\t10\t1\n11\t0\t0\n12\t0\t0\n13\t0\t0\n14\t0\t0\n15\t0\t0\n", newAuction.winningBids());

        }

        @Test
        void PriorBidWithReserveBidBelowMinWithIncrement() {

            OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
            Auction newAuction = auctionSystem.createAuction("testAuction", 10, 15, 5);
            Bidder oldBidder = auctionSystem.createBidder("person1");
            Bidder middleBidder = auctionSystem.createBidder("person2");
            Bidder newBidder = auctionSystem.createBidder("person3");

            assertTrue(newAuction.openAuction());

            // Get in a first bidder
            assertEquals(4, auctionSystem.placeBid(10, oldBidder.getBidderId(), 10));
            // Bump up the current bid to the desired level
            assertEquals(2, auctionSystem.placeBid(10, middleBidder.getBidderId(), 10));
            assertEquals(4, auctionSystem.placeBid(10, oldBidder.getBidderId(), 20));

            // Now bring in the test bidder
            assertEquals(2, auctionSystem.placeBid(10, newBidder.getBidderId(), 23));

            assertEquals("10\t20\t1\n11\t0\t0\n12\t0\t0\n13\t0\t0\n14\t0\t0\n15\t0\t0\n", newAuction.winningBids());

        }

        @Test
        void bidTest() {
            OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

            Auction auction1 = auctionSystem.createAuction("theAuction", 1, 10, 5);
            assertNotNull( auction1 );

            // Check with a bid on a single lot

            Bidder bidder1 = auctionSystem.createBidder("newBidder");
            assertNotNull(bidder1);

            auction1.openAuction();
            assertEquals(3, auctionSystem.placeBid(1, bidder1.getBidderId(), 5));
            assertEquals("theAuction\topen\t5\n", auctionSystem.auctionStatus());

            // Bid on the other lots and check the outcome of all lots bid upon

            assertEquals(4, auctionSystem.placeBid(2, bidder1.getBidderId(), 10));
            assertEquals("theAuction\topen\t10\n", auctionSystem.auctionStatus());
            assertEquals(4, auctionSystem.placeBid(3, bidder1.getBidderId(), 55));
            assertEquals("theAuction\topen\t15\n", auctionSystem.auctionStatus());

        }

        @Test
        void bidInTwoAuctions() {
            OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();

            Auction auction1 = auctionSystem.createAuction("theAuction", 1, 10, 5);
            assertNotNull( auction1 );
            Auction auction2 = auctionSystem.createAuction("theSecondAuction", 11, 20, 5);
            assertNotNull( auction2 );

            // Check with a bid on a single lot

            Bidder bidder1 = auctionSystem.createBidder("newBidder");
            assertNotNull(bidder1);

            auction1.openAuction();
            auction2.openAuction();

            assertEquals(3, auctionSystem.placeBid(1, bidder1.getBidderId(), 5));
            assertEquals(3, auctionSystem.placeBid(12, bidder1.getBidderId(), 5));
            assertEquals("theAuction\topen\t5\ntheSecondAuction\topen\t5\n", auctionSystem.auctionStatus());

            auction1.closeAuction();
            auction2.closeAuction();

            assertEquals("newBidder\t2\t10\n", auctionSystem.feesOwed());

        }

    @Test
    void placeBidBadLotTests() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
        Bidder bidder1 = auctionSystem.createBidder( "Alice" );

        // Bid with no auction

        assertEquals( 0, auctionSystem.placeBid( 10, bidder1.getBidderId(), 5 ));

        // Invalid bid with a single lot defined

        Auction auction1 = auctionSystem.createAuction( "FirstAuction", 2, 2, 1 );
        auction1.openAuction();
        assertEquals( 0, auctionSystem.placeBid( 1, bidder1.getBidderId(), 5 ));

        // Invalid bid with many lots defined

        Auction auction2 = auctionSystem.createAuction( "SecondAuction", 4, 6, 1 );
        auction2.openAuction();
        assertEquals( 0, auctionSystem.placeBid( 10, bidder1.getBidderId(), 5 ));

    }

    @Test
    void placeBidBadBidderTests() {
        OnlineAuctionSystem auctionSystem = new OnlineAuctionSystem();
        Auction auction1 = auctionSystem.createAuction( "FirstAuction", 2, 6, 1 );
        auction1.openAuction();

        // Make a bid when no bidders exist

        assertEquals( 0, auctionSystem.placeBid( 3, 1, 5 ));

        // Make a bid with a bad bidder number when there is one bidder

        Bidder bidder1 = auctionSystem.createBidder( "Alice" );
        assertEquals( 0, auctionSystem.placeBid( 4, 3, 5 ));

        // Make a bid with a bad bidder number when there is more than one bidder

        Bidder bidder2 = auctionSystem.createBidder( "Bob" );
        assertEquals( 0, auctionSystem.placeBid( 5, 7, 5 ));

        // Show that valid bids are still possible
        assertEquals( 3, auctionSystem.placeBid( 2, bidder1.getBidderId(), 1 ));
        assertEquals( 3, auctionSystem.placeBid( 6, bidder2.getBidderId(), 1 ));
    }
}
