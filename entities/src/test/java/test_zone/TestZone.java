package test_zone;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import event.CardArrayRequestEvent;
import exception.NotInitialisedContextException;
import listener.ICardArrayRequestListener;
import spell.Card;
import zone.Zone;
import zone.ZonePick;
import zone.ZoneType;

final class TOPCardArrayRequestListener implements ICardArrayRequestListener
{
	public TOPCardArrayRequestListener() {}
	
	public Card[] getCardArray(CardArrayRequestEvent e)
	{
		List<Card> choosenCards = new LinkedList<Card>();
		
		int i = 0;
		while(i<e.getNbCard()&&i<e.getCards().length)
		{
			choosenCards.add(e.getCards()[e.getCards().length-1 - i]);
			i++;
		}
		
		return choosenCards.toArray(new Card[0]);
	}
	
}

public class TestZone
{
	
	private Zone zone;
	
	private Card[] cards;
	private Card card1;
	private Card card2;
	private Card card3;
	private Card card4;
	private Card card5;
	
	private Card[] addedCards;
	private Card card6;
	private Card card7;
	private Card card8;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Zone.setCardArrayRequestListener(new TOPCardArrayRequestListener());
		
		addedCards = new Card[]
				{
						card6 = mock(Card.class),
						card7 =	mock(Card.class),
						card8 =	mock(Card.class)
				};
		
		cards = new Card[]
				{
						card1 = mock(Card.class), //BOTTOM
						card2 = mock(Card.class),
						card3 = mock(Card.class),
						card4 = mock(Card.class),
						card5 = mock(Card.class) //TOP
				};
		
		zone = new Zone(cards, ZoneType.BURN, ZonePick.TOP);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	

	@Test
	public final void testZoneCardsEmpty() {
		int expected = 0;
		
		zone = new Zone(null, ZoneType.BURN, ZonePick.TOP);
		int result = zone.getCards().length;
		assertEquals(expected, result);

		zone = new Zone(new Card[0], ZoneType.BURN, ZonePick.TOP);
		result = zone.getCards().length;
		assertEquals(expected, result);
	}
	
	@Test (expected = NotInitialisedContextException.class)
	public final void testZoneException1() {
		//cardArrayRequestListener n'a pas �t� initialis�
		Zone.setCardArrayRequestListener(null);
		zone = new Zone(cards, ZoneType.BURN, ZonePick.TOP);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testZoneException2() {
		//zoneType ne peut pas �tre null
		zone = new Zone(cards, null, ZonePick.TOP);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testZoneException3() {
		//zonePick ne peut pas �tre null
		zone = new Zone(cards, ZoneType.BURN, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testZoneException4() {
		//zonePick par default ne peut pas �tre choix
		zone = new Zone(cards, ZoneType.BURN, ZonePick.CHOICE);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testZoneException5() {
		//zonePick par default ne peut pas �tre default, il faut en choisir un vrai par default !!
		zone = new Zone(cards, ZoneType.BURN, ZonePick.DEFAULT);
	}

	@Test
	public final void testAdd() {
		zone.add(addedCards);

		//Ici, default est TOP
		Card[] expected = new Card[]
				{
					card1,
					card2,
					card3,
					card4,
					card5,
					card6,
					card7,
					card8,	
				};
		Card[] result = zone.getCards();
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}

	@Test
	public final void testAddTOP() {
		zone.add(addedCards, ZonePick.TOP);
		
		Card[] expected = new Card[]
				{
					card1,
					card2,
					card3,
					card4,
					card5,
					card6,
					card7,
					card8,	
				};
		Card[] result = zone.getCards();
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}
	
	@Test
	public final void testAddDEFAULT() {
		zone.add(addedCards, ZonePick.DEFAULT);
		
		//Ici, default est TOP
		Card[] expected = new Card[]
				{
					card1,
					card2,
					card3,
					card4,
					card5,
					card6,
					card7,
					card8,	
				};
		Card[] result = zone.getCards();
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}
	
	@Test
	public final void testAddBOTTOM() {
		zone.add(addedCards, ZonePick.BOTTOM);
		
		Card[] expected = new Card[]
				{
					card8,
					card7,
					card6,
					card1,
					card2,
					card3,
					card4,
					card5,	
				};
		Card[] result = zone.getCards();
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}
	
	@Test
	public final void testAddRANDOM() {
		zone.add(addedCards, ZonePick.RANDOM);
		
		int expected = 8;
		int result = zone.getCards().length;
		
		assertEquals(expected, result);
		for(Card c : cards)
		{
			assertTrue(Arrays.asList(zone.getCards()).contains(c));
		}
		for(Card c : addedCards)
		{
			assertTrue(Arrays.asList(zone.getCards()).contains(c));
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddException1()
	{
		//le tableau de card ne peut pas etre null
		zone.add(null, ZonePick.RANDOM);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddException2()
	{
		//zonePick ne peut pas etre null
		zone.add(new Card[0], null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testAddException3()
	{
		//zonePick ne peut pas �tre choix lors d'un ajout
		zone.add(new Card[0], ZonePick.CHOICE);
	}

	@Test
	public final void testRemove() {

		//Ici, default est TOP
		Card[] expected = new Card[]
				{
					card5,
					card4,
				};
		Card[] result = zone.remove(2);
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
		
		expected = new Card[]
				{
					card1,
					card2,
					card3,
				};
		result = zone.getCards();
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}

	@Test
	public final void testRemoveTOP() {

		Card[] expected = new Card[]
				{
					card5,
					card4,
				};
		Card[] result = zone.remove(2, ZonePick.TOP);
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
		
		expected = new Card[]
				{
					card1,
					card2,
					card3,
				};
		result = zone.getCards();
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}
	
	@Test
	public final void testRemoveDEFAULT() {
		//Ici, default est TOP
		Card[] expected = new Card[]
				{
					card5,
					card4,
				};
		Card[] result = zone.remove(2, ZonePick.DEFAULT);
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
				
		expected = new Card[]
				{
					card1,
					card2,
					card3,
				};
		result = zone.getCards();
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}
	
	@Test
	public final void testRemoveBOTTOM() {
		Card[] expected = new Card[]
				{
					card1,
					card2,
				};
		Card[] result = zone.remove(2, ZonePick.BOTTOM);
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
		
		
		expected = new Card[]
				{
					card3,
					card4,
					card5,
				};
		result = zone.getCards();
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}
	
	@Test
	public final void testRemoveRANDOM() {
		Card[] removedCards = zone.remove(2, ZonePick.RANDOM);
		
		int expected = 3;
		int result = zone.getCards().length;
		assertEquals(expected, result);
		
		for(Card c : removedCards)
		{
			Arrays.asList(cards).contains(c);
		}
		for(Card c : zone.getCards())
		{
			Arrays.asList(cards).contains(c);
		}
		
	}
	
	@Test
	public final void testRemoveCHOICE() {
		
		//Ici, CardArrayRequestListener choisi les cartes comme un "TOP"
		Card[] expected = new Card[]
				{
					card5,
					card4,
				};
		Card[] result = zone.remove(2, ZonePick.CHOICE);
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
		
		expected = new Card[]
				{
					card1,
					card2,
					card3,
				};
		result = zone.getCards();
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testRemoveException1()
	{
		//on ne peut pas enlever 0 cartes
		zone.remove(0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testRemoveException2()
	{
		//on ne peut pas enlever moins de 0 cartes
		zone.remove(-3);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testRemoveException3()
	{
		//on ne peut pas enlever 0 cartes
		zone.remove(0, ZonePick.RANDOM);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testRemoveException4()
	{
		//on ne peut pas enlever moins de 0 cartes
		zone.remove(-3, ZonePick.RANDOM);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public final void testRemoveException5()
	{
		//zonePick ne peut pas �tre null
		zone.remove(7, null);
	}
	
	@Test
	public final void testRemoveAll()
	{
		Card[] expected = cards;
		Card[] result = zone.removeAll();
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
		
		int expected2 = 0;
		int result2 = zone.getCards().length;
		assertEquals(expected2, result2);
	}

	@Test
	public final void testGetZoneType() {
		ZoneType expected = ZoneType.BURN;
		ZoneType result = zone.getZoneType();
		assertEquals(expected, result);
	}

	@Test
	public final void testShuffle() {
		
		zone.shuffle();
		
		int expected = 5;
		int result = zone.getCards().length;
		assertEquals(expected, result);
		
		for(Card c : zone.getCards())
		{
			Arrays.asList(cards).contains(c);
		}
	}

	@Test
	public final void testGetCards() {
		Card[] expected = cards;
		Card[] result = zone.getCards();
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}

	@Test
	public final void testHideCards() {
		fail("Not yet implemented");
	}

	@Test
	public final void testRevealCards() {
		fail("Not yet implemented");
	}

	@Test
	public final void testMoveCardToIndex() {
		zone.moveCardToIndex(1, 3);
		
		Card[] expected = new Card[]
				{
						card1,
						card3,
						card4,
						card2,
						card5
				};
		Card[] result = zone.getCards();
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}

}
