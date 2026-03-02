package timesheet.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import timesheet.model.Formatters;

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
	
	@Test
	void testKO_getTimeAsString1() {
		assertThrows(IllegalArgumentException.class, () -> Formatters.getTimeAsString(-1));
		assertThrows(IllegalArgumentException.class, () -> Formatters.getTimeAsString(-37));
	}
	
	@Test
	void testOK_getTimeAsString1() {
		assertEquals("00:00", Formatters.getTimeAsString(0));
		assertEquals("00:37", Formatters.getTimeAsString(37));
		assertEquals("01:07", Formatters.getTimeAsString(67));
		assertEquals("02:00", Formatters.getTimeAsString(120));
		assertEquals("07:01", Formatters.getTimeAsString(421));
	}
	
	@Test
	void testKO_getTimeAsString2() {
		assertThrows(IllegalArgumentException.class, () -> Formatters.getTimeAsString(-1, 0));
		assertThrows(IllegalArgumentException.class, () -> Formatters.getTimeAsString(0, -37));
		assertThrows(IllegalArgumentException.class, () -> Formatters.getTimeAsString(1, 60));
		assertThrows(IllegalArgumentException.class, () -> Formatters.getTimeAsString(24, 0));
	}
	
	@Test
	void testOK_getTimeAsString2() {
		assertEquals("00:00", Formatters.getTimeAsString(0,0));
		assertEquals("00:37", Formatters.getTimeAsString(0,37));
		assertEquals("01:07", Formatters.getTimeAsString(1,7));
		assertEquals("02:00", Formatters.getTimeAsString(2,0));
		assertEquals("07:01", Formatters.getTimeAsString(7,1));
	}
	
}
