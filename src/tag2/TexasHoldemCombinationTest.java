package tag2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tag2.tools.CardDeck52;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TexasHoldemCombinationTest {
    static TexasHoldemHand hand = new TexasHoldemHand();
    static List<CardDeck52.Card> mainTable;

    static final CardDeck52.Card twoOfHearts = new CardDeck52.Card(2, CardDeck52.Card.Sign.Hearts);
    static final CardDeck52.Card fourOfDiamonds = new CardDeck52.Card(4, CardDeck52.Card.Sign.Diamonds);
    static final CardDeck52.Card sixOfSpades = new CardDeck52.Card(6, CardDeck52.Card.Sign.Spades);
    static final CardDeck52.Card eightOfClubs = new CardDeck52.Card(8, CardDeck52.Card.Sign.Clubs);
    static final CardDeck52.Card tenOfHearts = new CardDeck52.Card(10, CardDeck52.Card.Sign.Hearts);
    static TexasHoldemCombination highCard;
    static TexasHoldemCombination onePair;
    static TexasHoldemCombination twoPair;
    static TexasHoldemCombination threeOfAKind;
    static TexasHoldemCombination straight;
    static TexasHoldemCombination flush;
    static TexasHoldemCombination fullHouse;
    static TexasHoldemCombination fourOfAKind;
    static TexasHoldemCombination straightFlush;
    static TexasHoldemCombination royalFlush;
    static TexasHoldemCombination highCardAlt;
    static TexasHoldemCombination highCardAlt2;
    static TexasHoldemCombination onePairAlt;
    static TexasHoldemCombination twoPairAlt;
    static TexasHoldemCombination threeOfAKindAlt;
    static TexasHoldemCombination straightAlt;
    static TexasHoldemCombination flushAlt;
    static TexasHoldemCombination fullHouseAlt;
    static TexasHoldemCombination fourOfAKindAlt;
    static TexasHoldemCombination straightFlushAlt;
    static TexasHoldemCombination royalFlushAlt;


    @BeforeAll
    static void setUp() {
        mainTable = List.of(twoOfHearts, fourOfDiamonds, sixOfSpades, eightOfClubs, tenOfHearts);

        // Card declarations (hands and community cards)

// Hand cards
        CardDeck52.Card card1 = new CardDeck52.Card(2, CardDeck52.Card.Sign.Hearts);
        CardDeck52.Card card2 = new CardDeck52.Card(3, CardDeck52.Card.Sign.Spades);
        CardDeck52.Card card3 = new CardDeck52.Card(4, CardDeck52.Card.Sign.Diamonds);
        CardDeck52.Card card4 = new CardDeck52.Card(5, CardDeck52.Card.Sign.Hearts);
        CardDeck52.Card card5 = new CardDeck52.Card(6, CardDeck52.Card.Sign.Hearts);
        CardDeck52.Card card6 = new CardDeck52.Card(7, CardDeck52.Card.Sign.Spades);
        CardDeck52.Card card7 = new CardDeck52.Card(8, CardDeck52.Card.Sign.Diamonds);
        CardDeck52.Card card8 = new CardDeck52.Card(9, CardDeck52.Card.Sign.Clubs);
        CardDeck52.Card card9 = new CardDeck52.Card(10, CardDeck52.Card.Sign.Hearts);
        CardDeck52.Card card10 = new CardDeck52.Card(14, CardDeck52.Card.Sign.Spades); // Ace
        CardDeck52.Card card11 = new CardDeck52.Card(6, CardDeck52.Card.Sign.Clubs);
        CardDeck52.Card card12 = new CardDeck52.Card(7, CardDeck52.Card.Sign.Diamonds);
        CardDeck52.Card card13 = new CardDeck52.Card(6, CardDeck52.Card.Sign.Spades);
        CardDeck52.Card card14 = new CardDeck52.Card(7, CardDeck52.Card.Sign.Hearts);
        CardDeck52.Card card15 = new CardDeck52.Card(5, CardDeck52.Card.Sign.Diamonds);
        CardDeck52.Card card16 = new CardDeck52.Card(11, CardDeck52.Card.Sign.Hearts); // Jack of Hearts
        CardDeck52.Card card17 = new CardDeck52.Card(12, CardDeck52.Card.Sign.Hearts); // Queen of Hearts
        CardDeck52.Card card18 = new CardDeck52.Card(13, CardDeck52.Card.Sign.Hearts); // King of Hearts
        // Additional Cards
        CardDeck52.Card card19 = new CardDeck52.Card(2, CardDeck52.Card.Sign.Spades);
        CardDeck52.Card card20 = new CardDeck52.Card(3, CardDeck52.Card.Sign.Clubs);
        CardDeck52.Card card21 = new CardDeck52.Card(4, CardDeck52.Card.Sign.Hearts);
        CardDeck52.Card card22 = new CardDeck52.Card(5, CardDeck52.Card.Sign.Clubs);
        CardDeck52.Card card23 = new CardDeck52.Card(6, CardDeck52.Card.Sign.Diamonds);
        CardDeck52.Card card24 = new CardDeck52.Card(7, CardDeck52.Card.Sign.Clubs);
        CardDeck52.Card card25 = new CardDeck52.Card(8, CardDeck52.Card.Sign.Clubs);
        CardDeck52.Card card26 = new CardDeck52.Card(9, CardDeck52.Card.Sign.Spades);
        CardDeck52.Card card27 = new CardDeck52.Card(10, CardDeck52.Card.Sign.Clubs);
        CardDeck52.Card card28 = new CardDeck52.Card(14, CardDeck52.Card.Sign.Clubs); // Ace of Clubs
        CardDeck52.Card card29 = new CardDeck52.Card(11, CardDeck52.Card.Sign.Spades); // Jack of Spades
        CardDeck52.Card card30 = new CardDeck52.Card(12, CardDeck52.Card.Sign.Spades); // Queen of Spades
        CardDeck52.Card card31 = new CardDeck52.Card(13, CardDeck52.Card.Sign.Spades); // King of Spades


// 1. High Card (Ace High)
        TexasHoldemHand hand1 = new TexasHoldemHand();
        hand1.takeDeal(card1);  // 2 of Hearts
        hand1.takeDeal(card6);  // 7 of Spades
        highCard = new TexasHoldemCombination(Arrays.asList(card2, card3, card4, card9, card10), hand1);

// 2. One Pair (Pair of Sevens)
        TexasHoldemHand hand2 = new TexasHoldemHand();
        hand2.takeDeal(card6);  // 7 of Spades
        hand2.takeDeal(card12); // 7 of Diamonds
        onePair = new TexasHoldemCombination(Arrays.asList(card2, card3, card4, card9, card1), hand2);

// 3. Two Pair (Sevens and Sixes)
        TexasHoldemHand hand3 = new TexasHoldemHand();
        hand3.takeDeal(card5);  // 6 of Hearts
        hand3.takeDeal(card6);  // 7 of Spades
        twoPair = new TexasHoldemCombination(Arrays.asList(card11, card12, card3, card9, card10), hand3);

// 4. Three of a Kind (Three Sevens)
        TexasHoldemHand hand4 = new TexasHoldemHand();
        hand4.takeDeal(card6);  // 7 of Spades
        hand4.takeDeal(card12); // 7 of Diamonds
        threeOfAKind = new TexasHoldemCombination(Arrays.asList(card14, card3, card4, card9, card10), hand4);

// 5. Straight (5 to 9)
        TexasHoldemHand hand5 = new TexasHoldemHand();
        hand5.takeDeal(card4);  // 5 of Hearts
        hand5.takeDeal(card8);  // 9 of Clubs
        straight = new TexasHoldemCombination(Arrays.asList(card5, card6, card7, card14, card15), hand5);

// 6. Flush (Hearts)
        TexasHoldemHand hand6 = new TexasHoldemHand();
        hand6.takeDeal(card1);  // 2 of Hearts
        hand6.takeDeal(card4);  // 5 of Hearts
        flush = new TexasHoldemCombination(Arrays.asList(card9, card5, card4, card1, card16), hand6);

// 7. Full House (Sevens over Sixes)
        TexasHoldemHand hand7 = new TexasHoldemHand();
        hand7.takeDeal(card6);  // 7 of Spades
        hand7.takeDeal(card12); // 7 of Diamonds
        fullHouse = new TexasHoldemCombination(Arrays.asList(card11, card5, card13, card6, card14), hand7);

// 8. Four of a Kind (Four Sevens)
        TexasHoldemHand hand8 = new TexasHoldemHand();
        hand8.takeDeal(card6);  // 7 of Spades
        hand8.takeDeal(card12); // 7 of Diamonds
        fourOfAKind = new TexasHoldemCombination(Arrays.asList(card24, card14, card2, card9, card10), hand8);

// 9. Straight Flush (5 to 9 of Hearts)
        TexasHoldemHand hand9 = new TexasHoldemHand();
        hand9.takeDeal(card4);  // 5 of Hearts
        hand9.takeDeal(card9);  // 10 of Hearts
        straightFlush = new TexasHoldemCombination(Arrays.asList(card5, card1, card16, card17, card18), hand9);

// 10. Royal Flush (Hearts)
        TexasHoldemHand hand10 = new TexasHoldemHand();
        hand10.takeDeal(card9);  // 10 of Hearts
        hand10.takeDeal(card16); // Jack of Hearts
        royalFlush = new TexasHoldemCombination(Arrays.asList(card17, card18, card9, card10, card16), hand10);

        // Create Hands
        TexasHoldemHand hand11 = new TexasHoldemHand();
        hand11.takeDeal(card19);  // 2 of Spades
        hand11.takeDeal(card24);  // 7 of Clubs

        TexasHoldemHand hand12 = new TexasHoldemHand();
        hand12.takeDeal(card24);  // 7 of Clubs
        hand12.takeDeal(card12);  // 7 of Diamonds

        TexasHoldemHand hand13 = new TexasHoldemHand();
        hand13.takeDeal(card23);  // 6 of Diamonds
        hand13.takeDeal(card24);  // 7 of Clubs

        TexasHoldemHand hand14 = new TexasHoldemHand();
        hand14.takeDeal(card24);  // 7 of Clubs
        hand14.takeDeal(card12);  // 7 of Diamonds

        TexasHoldemHand hand15 = new TexasHoldemHand();
        hand15.takeDeal(card21);  // 5 of Hearts
        hand15.takeDeal(card26);  // 9 of Spades

        TexasHoldemHand hand16 = new TexasHoldemHand();
        hand16.takeDeal(card19);  // 2 of Spades
        hand16.takeDeal(card26);  // 9 of Spades

        TexasHoldemHand hand17 = new TexasHoldemHand();
        hand17.takeDeal(card24);  // 7 of Clubs
        hand17.takeDeal(card12);  // 7 of Diamonds

        TexasHoldemHand hand18 = new TexasHoldemHand();
        hand18.takeDeal(card24);  // 7 of Clubs
        hand18.takeDeal(card12);  // 7 of Diamonds

        TexasHoldemHand hand19 = new TexasHoldemHand();
        hand19.takeDeal(card21);  // 5 of Hearts
        hand19.takeDeal(card26);  // 9 of Spades

        TexasHoldemHand hand20 = new TexasHoldemHand();
        hand20.takeDeal(card29);  // Jack of Spades
        hand20.takeDeal(card31);  // King of Spades


        // 1. High Card (Ace High) - Alternate
        highCardAlt = new TexasHoldemCombination(Arrays.asList(card20, card21, card22, card27, card28), hand11);

// 2. One Pair (Pair of Sevens) - Alternate
        onePairAlt = new TexasHoldemCombination(Arrays.asList(card19, card20, card21, card27, card19), hand12);

// 3. Two Pair (Sevens and Sixes) - Alternate
        twoPairAlt = new TexasHoldemCombination(Arrays.asList(card11, card23, card21, card27, card28), hand13);

// 4. Three of a Kind (Three Sevens) - Alternate
        threeOfAKindAlt = new TexasHoldemCombination(Arrays.asList(card14, card21, card22, card27, card28), hand14);

// 5. Straight (5 to 9) - Alternate
        straightAlt = new TexasHoldemCombination(Arrays.asList(card22, card23, card24, card14, card25), hand15);

// 6. Flush (Spades) - Alternate
        flushAlt = new TexasHoldemCombination(Arrays.asList(card27, card26, card19, card30, card29), hand16);

// 7. Full House (Sevens over Sixes) - Alternate
        fullHouseAlt = new TexasHoldemCombination(Arrays.asList(card23, card22, card24, card11, card14), hand17);

// 8. Four of a Kind (Four Sevens) - Alternate
        fourOfAKindAlt = new TexasHoldemCombination(Arrays.asList(card14, card24, card19, card27, card28), hand18);

// 9. Straight Flush (5 to 9 of Spades) - Alternate
        straightFlushAlt = new TexasHoldemCombination(Arrays.asList(card22, card19, card30, card31, card26), hand19);

// 10. Royal Flush (Spades) - Alternate
        royalFlushAlt = new TexasHoldemCombination(Arrays.asList(card30, card31, card29, card27, card28), hand20);




    }

    @AfterEach
    void tearDown() {
        hand.clearCards();
    }

    @Test
    void testHighCard() {
        hand.takeDeal(new CardDeck52.Card(3, CardDeck52.Card.Sign.Hearts));
        hand.takeDeal(new CardDeck52.Card(7, CardDeck52.Card.Sign.Diamonds));
        TexasHoldemCombination combination = hand.eval(mainTable);
        assertEquals(TexasHoldemCombination.CombinationType.HighCard, combination.combinationType);
        assertEquals(5, combination.combinationCards.size());
        assertFalse(combination.combinationCards.contains(hand.card1));
        assertFalse(combination.combinationCards.contains(twoOfHearts));

        combination = hand.eval(List.of());
        assertEquals(TexasHoldemCombination.CombinationType.HighCard, combination.combinationType);
        assertEquals(2, combination.combinationCards.size());
        assertTrue(combination.combinationCards.contains(hand.card1));
        assertTrue(combination.combinationCards.contains(hand.card2));
    }

    @Test
    void testOnePair() {
        hand.takeDeal(new CardDeck52.Card(2, CardDeck52.Card.Sign.Diamonds));
        hand.takeDeal(new CardDeck52.Card(7, CardDeck52.Card.Sign.Spades));
        TexasHoldemCombination combination = hand.eval(mainTable);
        assertEquals(TexasHoldemCombination.CombinationType.OnePair, combination.combinationType);
        assertEquals(5, combination.combinationCards.size());
        assertTrue(combination.combinationCards.contains(hand.card1));
        assertTrue(combination.combinationCards.contains(twoOfHearts));
        assertFalse(combination.combinationCards.contains(fourOfDiamonds));
        assertFalse(combination.combinationCards.contains(sixOfSpades));
    }

    @Test
    void testTwoPair() {
        hand.takeDeal(new CardDeck52.Card(2, CardDeck52.Card.Sign.Diamonds));
        hand.takeDeal(new CardDeck52.Card(4, CardDeck52.Card.Sign.Spades));
        TexasHoldemCombination combination = hand.eval(mainTable);
        assertEquals(TexasHoldemCombination.CombinationType.TwoPair, combination.combinationType);
        assertEquals(5, combination.combinationCards.size());
        assertTrue(combination.combinationCards.contains(hand.card1));
        assertTrue(combination.combinationCards.contains(hand.card2));
        assertTrue(combination.combinationCards.contains(twoOfHearts));
        assertTrue(combination.combinationCards.contains(fourOfDiamonds));
        assertTrue(combination.combinationCards.contains(tenOfHearts));
    }

    @Test
    void testThreeOfAKind() {
        hand.takeDeal(new CardDeck52.Card(2, CardDeck52.Card.Sign.Diamonds));
        hand.takeDeal(new CardDeck52.Card(2, CardDeck52.Card.Sign.Spades));
        TexasHoldemCombination combination = hand.eval(mainTable);
        assertEquals(TexasHoldemCombination.CombinationType.ThreeOfAKind, combination.combinationType);
        assertEquals(5, combination.combinationCards.size());
        assertTrue(combination.combinationCards.contains(hand.card1));
        assertTrue(combination.combinationCards.contains(hand.card2));
        assertTrue(combination.combinationCards.contains(twoOfHearts));
        assertTrue(combination.combinationCards.contains(tenOfHearts));
        assertTrue(combination.combinationCards.contains(eightOfClubs));
    }

    @Test
    void testStraight() {
        hand.takeDeal(new CardDeck52.Card(5, CardDeck52.Card.Sign.Diamonds));
        hand.takeDeal(new CardDeck52.Card(7, CardDeck52.Card.Sign.Spades));
        TexasHoldemCombination combination = hand.eval(mainTable);
        assertEquals(TexasHoldemCombination.CombinationType.Straight, combination.combinationType);
        assertEquals(5, combination.combinationCards.size());
        assertTrue(combination.combinationCards.contains(hand.card1));
        assertTrue(combination.combinationCards.contains(hand.card2));
        assertTrue(combination.combinationCards.contains(fourOfDiamonds));
        assertTrue(combination.combinationCards.contains(sixOfSpades));
        assertTrue(combination.combinationCards.contains(eightOfClubs));
    }

    @Test
    void testFlush() {
        CardDeck52.Card threeOfHearts = new CardDeck52.Card(3, CardDeck52.Card.Sign.Hearts);
        List<CardDeck52.Card> flushTable = List.of(
                twoOfHearts,
                tenOfHearts,
                threeOfHearts
        );
        hand.takeDeal(new CardDeck52.Card(9, CardDeck52.Card.Sign.Hearts));
        hand.takeDeal(new CardDeck52.Card(12, CardDeck52.Card.Sign.Hearts));
        TexasHoldemCombination combination = hand.eval(flushTable);
        assertEquals(TexasHoldemCombination.CombinationType.Flush, combination.combinationType);
        assertEquals(5, combination.combinationCards.size());
        assertTrue(combination.combinationCards.contains(hand.card1));
        assertTrue(combination.combinationCards.contains(hand.card2));
        assertTrue(combination.combinationCards.contains(twoOfHearts));
        assertTrue(combination.combinationCards.contains(tenOfHearts));
        assertTrue(combination.combinationCards.contains(threeOfHearts));
    }

    @Test
    void testFullHouse() {
        CardDeck52.Card twoOfSpades = new CardDeck52.Card(2, CardDeck52.Card.Sign.Spades);
        CardDeck52.Card threeOfSpades = new CardDeck52.Card(3, CardDeck52.Card.Sign.Spades);
        List<CardDeck52.Card> fullHouseTable = List.of(
                twoOfHearts,
                twoOfSpades,
                threeOfSpades
        );
        hand.takeDeal(new CardDeck52.Card(2, CardDeck52.Card.Sign.Diamonds));
        hand.takeDeal(new CardDeck52.Card(3, CardDeck52.Card.Sign.Clubs));
        TexasHoldemCombination combination = hand.eval(fullHouseTable);
        assertEquals(TexasHoldemCombination.CombinationType.FullHouse, combination.combinationType);
        assertEquals(5, combination.combinationCards.size());
        assertTrue(combination.combinationCards.contains(hand.card1));
        assertTrue(combination.combinationCards.contains(hand.card2));
        assertTrue(combination.combinationCards.contains(twoOfHearts));
        assertTrue(combination.combinationCards.contains(twoOfSpades));
        assertTrue(combination.combinationCards.contains(threeOfSpades));
    }

    @Test
    void testFourOfAKind() {
        CardDeck52.Card twoOfSpades = new CardDeck52.Card(2, CardDeck52.Card.Sign.Spades);
        CardDeck52.Card tenOfHearts = new CardDeck52.Card(10, CardDeck52.Card.Sign.Hearts);
        List<CardDeck52.Card> fourOfKindTable = List.of(
                twoOfHearts,
                twoOfSpades,
                tenOfHearts
        );
        hand.takeDeal(new CardDeck52.Card(2, CardDeck52.Card.Sign.Diamonds));
        hand.takeDeal(new CardDeck52.Card(2, CardDeck52.Card.Sign.Clubs));
        TexasHoldemCombination combination = hand.eval(fourOfKindTable);
        assertEquals(TexasHoldemCombination.CombinationType.FourOfAKind, combination.combinationType);
        assertEquals(5, combination.combinationCards.size());
        assertTrue(combination.combinationCards.contains(hand.card1));
        assertTrue(combination.combinationCards.contains(hand.card2));
        assertTrue(combination.combinationCards.contains(twoOfHearts));
        assertTrue(combination.combinationCards.contains(twoOfSpades));
        assertTrue(combination.combinationCards.contains(tenOfHearts));
    }

    @Test
    void testStraightFlush() {
        CardDeck52.Card threeOfHearts = new CardDeck52.Card(3, CardDeck52.Card.Sign.Hearts);
        CardDeck52.Card fourOfHearts = new CardDeck52.Card(4, CardDeck52.Card.Sign.Hearts);
        List<CardDeck52.Card> straightFlushTable = List.of(
                twoOfHearts,
                fourOfHearts,
                threeOfHearts
        );
        hand.takeDeal(new CardDeck52.Card(5, CardDeck52.Card.Sign.Hearts));
        hand.takeDeal(new CardDeck52.Card(6, CardDeck52.Card.Sign.Hearts));
        TexasHoldemCombination combination = hand.eval(straightFlushTable);
        assertEquals(TexasHoldemCombination.CombinationType.StraightFlush, combination.combinationType);
        assertEquals(5, combination.combinationCards.size());
        assertTrue(combination.combinationCards.contains(hand.card1));
        assertTrue(combination.combinationCards.contains(hand.card2));
        assertTrue(combination.combinationCards.contains(twoOfHearts));
        assertTrue(combination.combinationCards.contains(threeOfHearts));
        assertTrue(combination.combinationCards.contains(fourOfHearts));
    }

    @Test
    void testRoyalFlush() {
        CardDeck52.Card tenOfHearts = new CardDeck52.Card(10, CardDeck52.Card.Sign.Hearts);
        CardDeck52.Card jackOfHearts = new CardDeck52.Card(11, CardDeck52.Card.Sign.Hearts);
        CardDeck52.Card queenOfHearts = new CardDeck52.Card(12, CardDeck52.Card.Sign.Hearts);
        List<CardDeck52.Card> royalFlushTable = List.of(
                tenOfHearts,
                jackOfHearts,
                queenOfHearts
        );
        hand.takeDeal(new CardDeck52.Card(13, CardDeck52.Card.Sign.Hearts));
        hand.takeDeal(new CardDeck52.Card(14, CardDeck52.Card.Sign.Hearts));
        TexasHoldemCombination combination = hand.eval(royalFlushTable);
        assertEquals(TexasHoldemCombination.CombinationType.RoyalFlush, combination.combinationType);
        assertEquals(5, combination.combinationCards.size());
        assertTrue(combination.combinationCards.contains(hand.card1));
        assertTrue(combination.combinationCards.contains(hand.card2));
        assertTrue(combination.combinationCards.contains(tenOfHearts));
        assertTrue(combination.combinationCards.contains(jackOfHearts));
        assertTrue(combination.combinationCards.contains(queenOfHearts));
    }
    @Test
    void differntType () {
        assertEquals(1, onePair.compareTo(highCard));
        assertEquals(-1, onePair.compareTo(twoPair));
        assertEquals(-1, highCard.compareTo(flush));
        assertEquals(-1,straight.compareTo(straightFlush));
        assertEquals(1, flush.compareTo(straight));
        assertEquals(1, threeOfAKind.compareTo(twoPair));
        assertEquals(-1, threeOfAKind.compareTo(fourOfAKind));
    }

    @Test
    void sameType () {
        assertEquals(0, highCard.compareTo(highCardAlt));
        assertEquals(1, onePair.compareTo(onePairAlt));
        assertEquals(-1, flush.compareTo(flushAlt));
        assertEquals(0, royalFlush.compareTo(royalFlushAlt));
        assertEquals(0, straightFlush.compareTo(straightFlushAlt));
    }

}