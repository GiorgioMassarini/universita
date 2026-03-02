package tram.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import tram.model.Formatters;

class FormattersTest {

	@Test
	void testOK_format() {
		assertEquals("12:32", Formatters.timeFormatter.format(LocalTime.of(12,32)));
		assertEquals("05:32", Formatters.timeFormatter.format(LocalTime.of( 5,32)));
		assertEquals("07:01", Formatters.timeFormatter.format(LocalTime.of( 7, 1)));
	}
	
	@Test
	void testOK_parse() {
		assertEquals(LocalTime.of(12,32), LocalTime.parse("12:32", Formatters.timeFormatter));
		assertEquals(LocalTime.of( 5,32), LocalTime.parse("05:32", Formatters.timeFormatter));
		assertEquals(LocalTime.of( 7, 1), LocalTime.parse("07:01", Formatters.timeFormatter));
	}
	
	@Test
	void testKO_parse() {
		assertThrows(DateTimeParseException.class, () -> LocalTime.parse("7:01", Formatters.timeFormatter));
		assertThrows(DateTimeParseException.class, () -> LocalTime.parse("07:1", Formatters.timeFormatter));
	}
	
}
