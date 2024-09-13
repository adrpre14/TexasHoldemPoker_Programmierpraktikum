package tag2;

import tag2.tools.CardDeck52;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TexasHoldemCombination implements Comparable<TexasHoldemCombination> {
    public enum CombinationType {
        HighCard(1),
        OnePair(2),
        TwoPair(3),
        ThreeOfAKind(4),
        Straight(5),
        Flush(6),
        FullHouse(7),
        FourOfAKind(8),
        StraightFlush(9),
        RoyalFlush(10);

        private final int rank;

        // Constructor to assign the rank value
        CombinationType(int rank) {
            this.rank = rank;
        }

        // Getter method to retrieve the rank value
        public int getRank() {
            return this.rank;
        }
    }

    CombinationType combinationType;
    List<CardDeck52.Card> combinationCards;

    TexasHoldemCombination(List<CardDeck52.Card> tableCards, TexasHoldemHand hand) {
        List<CardDeck52.Card> cards = new ArrayList<>(tableCards);
        cards.addAll(List.of(hand.get()));
        cards.sort(CardDeck52.Card::compareTo);

        combinationType = CombinationType.HighCard;

        Map<CardDeck52.Card.Sign, Integer> signCounts = new HashMap<>(){
            {
                put(CardDeck52.Card.Sign.Hearts, 0);
                put(CardDeck52.Card.Sign.Diamonds, 0);
                put(CardDeck52.Card.Sign.Spades, 0);
                put(CardDeck52.Card.Sign.Clubs, 0);
            }
        };

        List<CardDeck52.Card> straight = new ArrayList<>();
        straight.add(cards.getFirst());
        signCounts.put(cards.getFirst().sign, (signCounts.get(cards.getFirst().sign))  + 1);

        for (int i = 0; i < cards.size() - 1; i++) {
            CardDeck52.Card card = cards.get(i);
            CardDeck52.Card secondCard = cards.get(i + 1);

            signCounts.put(secondCard.sign, (signCounts.get(secondCard.sign))  + 1);

            if (card.value + 1 == secondCard.value) {
                straight.add(secondCard);
            }
            else if (card.value != secondCard.value && straight.size() < 5) {
                straight.clear();
                straight.add(cards.get(i + 1));
            }
        }

        if (straight.size() >= 5) {
            straight = straight.stream().sorted(Comparator.reverseOrder()).limit(5).collect(Collectors.toList());
            combinationType = CombinationType.Straight;
            combinationCards = straight;
        }

        CardDeck52.Card.Sign flushSign = signCounts.entrySet().stream()
                .filter(entry -> entry.getValue() >= 5)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        // is Flush, StraightFlush or RoyalFlush
        if (flushSign != null) {
            if (combinationType == CombinationType.Straight && straight.stream().allMatch(card -> card.sign == flushSign)) {
                combinationType = straight.getFirst().value == 14 ? CombinationType.RoyalFlush : CombinationType.StraightFlush;
                combinationCards = straight;
            }
            else {
                combinationCards = cards.stream()
                        .filter(card -> card.sign == flushSign)
                        .sorted(Comparator.reverseOrder())
                        .limit(5)
                        .toList();
                combinationType = CombinationType.Flush;
            }
        }
        // don't need to calculate further if we have StraightFlush or RoyalFlush
        if (combinationType.ordinal() >= CombinationType.StraightFlush.ordinal()) {
            return;
        }

        Map<Integer, Long> cardValues = cards.stream().collect(Collectors.groupingBy(e -> e.value, Collectors.counting()));
        // is FourOfAKind
        if (cardValues.containsValue(4L)) {
            List<CardDeck52.Card> fourOfAKind = cardValues.entrySet().stream()
                    .filter(entry -> entry.getValue() == 4)
                    .flatMap(entry -> cards.stream().filter(card -> card.value == entry.getKey()))
                    .toList();
            combinationCards = new ArrayList<>(fourOfAKind);
            combinationCards.add(cards.stream()
                    .filter(card -> card.value != fourOfAKind.getFirst().value)
                    .sorted(Comparator.reverseOrder())
                    .toList()
                    .getFirst());
            combinationType = CombinationType.FourOfAKind;
        }
        // is FullHouse
        else if (cardValues.containsValue(3L) && cardValues.containsValue(2L)) {
            List<CardDeck52.Card> threeOfAKind = cardValues.entrySet().stream()
                    .filter(entry -> entry.getValue() == 3)
                    .flatMap(entry -> cards.stream().filter(card -> card.value == entry.getKey()))
                    .sorted(Comparator.reverseOrder())
                    .limit(3)
                    .toList();
            List<CardDeck52.Card> twoOfAKind = cardValues.entrySet().stream()
                    .filter(entry -> entry.getValue() == 2)
                    .flatMap(entry -> cards.stream().filter(card -> card.value == entry.getKey()))
                    .sorted(Comparator.reverseOrder())
                    .limit(2)
                    .toList();
            // combinationCards = threeOfAKind + twoOfAKind
            combinationCards = new ArrayList<>(threeOfAKind);
            combinationCards.addAll(twoOfAKind);
            combinationType = CombinationType.FullHouse;
        }
        // don't need to calculate further if we have Straight or higher
        if (combinationType.ordinal() >= CombinationType.Straight.ordinal()) {
            return;
        }
        // is ThreeOfAKind
        if (cardValues.containsValue(3L)) {
            combinationCards = new ArrayList<>(
                    cardValues.entrySet().stream()
                            .filter(entry -> entry.getValue() == 3)
                            .flatMap(entry -> cards.stream().filter(card -> card.value == entry.getKey()))
                            .sorted(Comparator.reverseOrder())
                            .limit(3)
                            .toList()
            );
            combinationCards.addAll(cards.stream()
                    .filter(card -> card.value != combinationCards.getFirst().value)
                    .sorted(Comparator.reverseOrder())
                    .limit(2)
                    .toList());
            combinationType = CombinationType.ThreeOfAKind;
        }
        // is TwoPair or OnePair
        else if (cardValues.containsValue(2L)) {
            List<CardDeck52.Card> twoOfAKind = cardValues.entrySet().stream()
                    .filter(entry -> entry.getValue() == 2)
                    .flatMap(entry -> cards.stream().filter(card -> card.value == entry.getKey()))
                    .toList();
            if (twoOfAKind.size() == 4) {
                combinationCards = new ArrayList<>(twoOfAKind);
                combinationCards.add(cards.stream()
                        .filter(c -> !twoOfAKind.contains(c))
                        .sorted(Comparator.reverseOrder())
                        .toList()
                        .getFirst());
                combinationType = CombinationType.TwoPair;
            }
            else {
                combinationCards = new ArrayList<>(twoOfAKind);
                combinationCards.addAll(cards.stream()
                    .filter(card -> card.value != twoOfAKind.getFirst().value)
                    .sorted(Comparator.reverseOrder())
                    .limit(3)
                    .toList());
                combinationType = CombinationType.OnePair;
            }
        }
        else {
            // get the highest card in the hand and then the 4 highest on the table
            combinationCards = new ArrayList<>();
            combinationCards.add(hand.get()[1].compareTo(hand.get()[0]) > 0 ? hand.get()[1] : hand.get()[0]);
            combinationCards.addAll(cards.stream()
                    .filter(card -> card.value != combinationCards.getFirst().value)
                    .sorted(Comparator.reverseOrder())
                    .limit(4)
                    .toList());
        }
    }

    public CombinationType getCombinationType() {
        return combinationType;
    }

    // b)
    //return 1 if this > that, 0 if that = this, return -1 if this < that
    @Override
    public final int compareTo(TexasHoldemCombination that) {
        // TODO

        //combi type and cards of that
        CombinationType thatCombiType = that.combinationType;
        List<CardDeck52.Card> thatCards = that.combinationCards;

        //combi type and cards of this
        CombinationType thisCombitype = this.combinationType;
        List<CardDeck52.Card> thisCard = this.combinationCards;

        // case this worse than that
        if (thatCombiType.rank > thisCombitype.rank) {
            return -1;
        }
        //case this better than that
        if (thatCombiType.rank < thisCombitype.rank) {
            return 1;
        }
        // case same combi type
        else {
            switch (thisCombitype) {
                case RoyalFlush, Straight, StraightFlush, Flush, HighCard -> {
                    int comparisonResult = getHighestCard(thisCard).compareTo(getHighestCard(thatCards));
                    return comparisonResult > 0 ? 1 : (comparisonResult < 0 ? -1 : 0);
                }

                case OnePair -> {
                    CardDeck52.Card pairOfThis = findPair(thisCard);
                    CardDeck52.Card pairOfThat = findPair(thatCards);
                    int comparisonResult = pairOfThis.compareTo(pairOfThat);

                    if (comparisonResult > 0) {
                        return 1;
                    } else if (comparisonResult < 0) {
                        return -1;
                    }
                    else {
                        return compareKicker(thisCard,thatCards,thatCombiType);
                    }


                }
                case TwoPair, FourOfAKind -> {
                    List<CardDeck52.Card> thisTwoPairs = findTwoPairs(thisCard);
                    List<CardDeck52.Card> thatTwoPairs = findTwoPairs(thatCards);

                    CardDeck52.Card thisFirstRank = thisTwoPairs.getFirst();
                    CardDeck52.Card thatFirstRank = thatTwoPairs.getFirst();

                    CardDeck52.Card thisSecondRank = thisTwoPairs.getLast();
                    CardDeck52.Card thatSecondRank = thatTwoPairs.getLast();

                    if (thisFirstRank.getValue() > thatFirstRank.getValue()) {
                        return 1;
                    }
                    if (thisFirstRank.getValue() < thatFirstRank.getValue()) {
                        return -1;
                    }
                    if (thisSecondRank.getValue() > thatSecondRank.getValue()){
                        return 1;
                    }
                    if (thisSecondRank.getValue() < thatSecondRank.getValue()) {
                        return -1;
                    } else {

                        return compareKicker(thisCard,thatCards,thatCombiType);
                    }
                }
                case ThreeOfAKind -> {
                    Integer thisTriplet = findTriplet(thisCard);
                    Integer thatTriplet = findTriplet(thatCards);

                    if (thisTriplet > thatTriplet) {
                        return 1;
                    }
                    if (thisTriplet < thatTriplet) {
                        return -1;
                    }
                    else {
                        return compareKicker(thisCard,thatCards,thatCombiType);
                    }
                }
                case FullHouse -> {
                    Integer thisTriplet = findTriplet(thisCard);
                    CardDeck52.Card thisPair = findPair(thisCard);

                    Integer thatTriplet = findTriplet(thatCards);
                    CardDeck52.Card thatPair = findPair(thatCards);

                    if (thisTriplet > thatTriplet) {
                        return 1;
                    }
                    if (thisTriplet < thatTriplet) {
                        return -1;
                    }
                    if (thisPair.getValue() > thatPair.getValue()) {
                        return 1;
                    }
                    else if (thisPair.getValue() < thatPair.getValue()) return -1;
                    else return 0;
                }
            }

        }

        return 0;
    }
    //return a highest card in a hand
    public static CardDeck52.Card getHighestCard(List<CardDeck52.Card> hand) {
        return hand.stream().max(Comparator.naturalOrder()).orElseThrow();
    }

    // Method to identify a single pair in a hand
    public static CardDeck52.Card findPair(List<CardDeck52.Card> hand) {
        Map<CardDeck52.Card, Integer> rankCountMap = new HashMap<>();

        // Count the occurrences of each rank
        for (CardDeck52.Card card : hand) {
            CardDeck52.Card rank = card;
            rankCountMap.put(rank, rankCountMap.getOrDefault(rank, 0) + 1);
        }

        // Identify the pair
        for (Map.Entry<CardDeck52.Card, Integer> entry : rankCountMap.entrySet()) {
            if (entry.getValue() == 2) {
                return entry.getKey(); // Return the rank of the pair
            }
        }

        return null; // Return null if no pair is found
    }

    // Method to identify two pairs in a hand
    public static List<CardDeck52.Card> findTwoPairs(List<CardDeck52.Card> hand) {
        Map<CardDeck52.Card, Integer> rankCountMap = new HashMap<>();

        // Count the occurrences of each rank
        for (CardDeck52.Card card : hand) {
            CardDeck52.Card rank = card;
            rankCountMap.put(rank, rankCountMap.getOrDefault(rank, 0) + 1);
        }

        List<CardDeck52.Card> pairs = new ArrayList<>();

        // Identify the pairs
        for (Map.Entry<CardDeck52.Card, Integer> entry : rankCountMap.entrySet()) {
            if (entry.getValue() == 2) {
                pairs.add(entry.getKey());
            }
        }

        // Check if there are exactly two pairs
        if (pairs.size() == 2) {
            //Sort
            pairs.sort(Comparator.naturalOrder());
            return pairs;
        }

        return Collections.emptyList();
    }

    // Method to identify a triplet in a hand
    public static Integer findTriplet(List<CardDeck52.Card> hand) {
        Map<Integer, Integer> rankCountMap = new HashMap<>();

        // Count the occurrences of each rank
        for (CardDeck52.Card card : hand) {
            Integer rank = card.value;
            rankCountMap.put(rank, rankCountMap.getOrDefault(rank, 0) + 1);
        }

        // Identify the triplet
        for (Map.Entry<Integer, Integer> entry : rankCountMap.entrySet()) {
            if (entry.getValue() == 3) {
                return entry.getKey();
            }
        }

        return null; // Return null if no triplet is found
    }

    public static int compareKicker(List<CardDeck52.Card> thisCards, List<CardDeck52.Card> thatCards, CombinationType type) {
        // Sort both lists
        thisCards.sort(Comparator.naturalOrder());
        thatCards.sort(Comparator.naturalOrder());

        for (int i = 0; i < thisCards.size(); i++) {
            int comparisonResult = thisCards.get(i).compareTo(thatCards.get(i));
            if (comparisonResult > 0) {
                return 1;
            } else if (comparisonResult < 0) {
                return -1;
            }
        }
        // If all kickers are the same, return 0
        return 0;
    }

    // c)
    public static Stream<TexasHoldemCombination> generate() {
        return Stream.generate(() -> {
            CardDeck52 deck = new CardDeck52();
            deck.shuffle();  // Shuffle

            // Define the possible table card counts
            int[] possibleCardCounts = {0,3,4,5};

            // Randomly select one of the possible card counts
            int numberOfCards = possibleCardCounts[ThreadLocalRandom.current().nextInt(possibleCardCounts.length)];

            // Deal the cards from the deck first for the player then the table
            List<CardDeck52.Card> dealtHandCard = deck.deal(2);
            List<CardDeck52.Card> dealTableCards = deck.deal(numberOfCards);


            // Create a random TexasHoldemHand (representing the player's hand)
            TexasHoldemHand hand = new TexasHoldemHand();
            for (CardDeck52.Card c : dealtHandCard) {
                hand.takeDeal(c);
            }

            // Create the TexasHoldemCombination object
            return new TexasHoldemCombination(dealTableCards, hand);
        });
    }

    public static void main(String[] args) {
//        CardDeck52 deck = new CardDeck52();
//        boolean hasTable = Math.random() >= 0.5;
//        List<CardDeck52.Card> tableCards = hasTable ?
//                deck.deal(ThreadLocalRandom.current().nextInt(3, 5 + 1)) :
//                Collections.emptyList();
//
//        TexasHoldemHand hand = new TexasHoldemHand();
//        TexasHoldemHand hand2 = new TexasHoldemHand();
//        hand.takeDeal(deck.deal());
//        hand2.takeDeal(deck.deal());
//        hand.takeDeal(deck.deal());
//        hand2.takeDeal(deck.deal());
//
//        System.out.println("Table Cards (" + tableCards.size() + "): " + tableCards);
//        System.out.println("Hand1: " + Arrays.toString(hand.get()));
//        System.out.println("Hand2: " + Arrays.toString(hand2.get()));
//        Stream.of(hand.eval(tableCards), hand2.eval(tableCards))
//                .sorted(Comparator.reverseOrder())
//                .forEach(combination -> System.out.println(
//                        "CombinationType: " + combination.combinationType +
//                        ", CombinationCards: " + combination.combinationCards));

//        TexasHoldemCombination combination = hand.eval(tableCards);
//        System.out.println("Combination (" + combination.combinationCards.size() + "): " + combination.combinationType + " -> " + combination.combinationCards);

        TexasHoldemCombination.generate()
            .limit(1000000)
            .sorted((a, b) -> a.combinationType.rank - b.combinationType.rank)
            .collect(Collectors.groupingBy(TexasHoldemCombination::getCombinationType, TreeMap::new, Collectors.counting()))
            .forEach((type, count) -> {
                BigDecimal bd = BigDecimal.valueOf((double) count / 10000).stripTrailingZeros();
                System.out.println(type + ": " + bd.toPlainString() + "%");
            });
    }
}
