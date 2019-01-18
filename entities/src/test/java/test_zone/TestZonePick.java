package test_zone;

import static org.junit.Assert.*;

import org.junit.Test;

import zone.ZonePick;

public class TestZonePick
{

	@Test
	public final void testToString()
	{
		String[] expected = new String[]
				{
						"choice",
						"default",
						"bottom",
				};
		
		String[] result = new String[]
				{
						ZonePick.CHOICE.toString(),
						ZonePick.DEFAULT.toString(),
						ZonePick.BOTTOM.toString(),
				};
		
		for(int i = 0; i < expected.length; i++)
		{
			assertEquals(expected[i], result[i]);
		}
	}

}