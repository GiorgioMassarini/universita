package frutta.model.test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import frutta.model.Formatters;


class FormattersTest {

	@Test
	void testOK_format() {
		assertEquals("29/10/25", Formatters.itDateFormatter.format(LocalDate.of(2025,10,29)));
		assertEquals("02/03/25", Formatters.itDateFormatter.format(LocalDate.of(2025, 3, 2)));
		
		assertEquals("€ 10,40", Formatters.itPriceFormatter.format(10.40));
		assertEquals("€ 9,99",  Formatters.itPriceFormatter.format( 9.99));
	}

	@Test
	void testOK_parse() {
		assertEquals(LocalDate.of(2025,10,29), LocalDate.parse("29/10/25", Formatters.itDateFormatter));
		assertEquals(LocalDate.of(2025, 3, 2), LocalDate.parse("02/03/25", Formatters.itDateFormatter));
		
		assertEquals(10.40, Formatters.itPriceFormatter.parse("€ 10,40"));
		assertEquals( 9.99, Formatters.itPriceFormatter.parse("€ 9,99"));
	}

}
