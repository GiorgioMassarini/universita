package bancaore.model.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import bancaore.model.SettimanaLavorativa;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.Map;


class SettimanaLavorativaTest {

	@Test
	void testOK_ConfigurazioneIniziale_CtorDaStringArray() {		
		var m = new SettimanaLavorativa(new String[] {"9H","6H","9H","6H","9H","0H","0H"} );
		assertEquals(Duration.ofHours(9), m.getOreLavorative(DayOfWeek.MONDAY));
		assertEquals(Duration.ofHours(6), m.getOreLavorative(DayOfWeek.TUESDAY));
		assertEquals(Duration.ofHours(9), m.getOreLavorative(DayOfWeek.WEDNESDAY));
		assertEquals(Duration.ofHours(6), m.getOreLavorative(DayOfWeek.THURSDAY));
		assertEquals(Duration.ofHours(9), m.getOreLavorative(DayOfWeek.FRIDAY));
		assertEquals(Duration.ofHours(0), m.getOreLavorative(DayOfWeek.SATURDAY));
		assertEquals(Duration.ofHours(0), m.getOreLavorative(DayOfWeek.SUNDAY));
		assertEquals(Duration.ofHours(39), m.getOreSettimanali());		
	}
	
	@Test
	void testOK_ConfigurazioneIniziale_CtorDaOreSettimanaliTotali() {		
		var m = new SettimanaLavorativa(Duration.ofHours(36));
		assertEquals(Duration.ofHours(7).plusMinutes(12), m.getOreLavorative(DayOfWeek.MONDAY));
		assertEquals(Duration.ofHours(7).plusMinutes(12), m.getOreLavorative(DayOfWeek.TUESDAY));
		assertEquals(Duration.ofHours(7).plusMinutes(12), m.getOreLavorative(DayOfWeek.WEDNESDAY));
		assertEquals(Duration.ofHours(7).plusMinutes(12), m.getOreLavorative(DayOfWeek.THURSDAY));
		assertEquals(Duration.ofHours(7).plusMinutes(12), m.getOreLavorative(DayOfWeek.FRIDAY));
		assertEquals(Duration.ofHours(0), m.getOreLavorative(DayOfWeek.SATURDAY));
		assertEquals(Duration.ofHours(0), m.getOreLavorative(DayOfWeek.SUNDAY));
		assertEquals(Duration.ofHours(36), m.getOreSettimanali());		
	}

	@Test
	void testOK_ConfigurazioneIniziale_CtorDaMappa() {		
		var m = new SettimanaLavorativa(Map.of(
				DayOfWeek.SUNDAY,   Duration.ofHours(0),
				DayOfWeek.MONDAY,   Duration.ofHours(6),
				DayOfWeek.TUESDAY,  Duration.ofHours(6),
				DayOfWeek.SATURDAY, Duration.ofHours(3),
				DayOfWeek.WEDNESDAY,Duration.ofHours(9),
				DayOfWeek.THURSDAY, Duration.ofHours(6),
				DayOfWeek.FRIDAY,   Duration.ofHours(6)				
				));
		assertEquals(Duration.ofHours(6), m.getOreLavorative(DayOfWeek.MONDAY));
		assertEquals(Duration.ofHours(6), m.getOreLavorative(DayOfWeek.TUESDAY));
		assertEquals(Duration.ofHours(9), m.getOreLavorative(DayOfWeek.WEDNESDAY));
		assertEquals(Duration.ofHours(6), m.getOreLavorative(DayOfWeek.THURSDAY));
		assertEquals(Duration.ofHours(6), m.getOreLavorative(DayOfWeek.FRIDAY));
		assertEquals(Duration.ofHours(3), m.getOreLavorative(DayOfWeek.SATURDAY));
		assertEquals(Duration.ofHours(0), m.getOreLavorative(DayOfWeek.SUNDAY));
		assertEquals(Duration.ofHours(36), m.getOreSettimanali());		
	}
	
	@Test
	void testKO_CtorDaDuration_ArgNull() {
		assertThrows(IllegalArgumentException.class, () -> new SettimanaLavorativa((Duration)null));
	}

	@Test
	void testKO_CtorDaMappa_ArgNull() {
		assertThrows(IllegalArgumentException.class, () -> new SettimanaLavorativa((Map<DayOfWeek,Duration>)null));
	}
	
	@Test
	void testKO_CtorDaArray_ArgNull() {
		assertThrows(IllegalArgumentException.class, () -> new SettimanaLavorativa((String[])null));
	}
	
	@Test
	void testOK_ConfigurazioneIniziale_CtorDaMappa_MenoDi7Righe() {	
		assertThrows(IllegalArgumentException.class, () -> new SettimanaLavorativa(Map.of(
				DayOfWeek.SUNDAY,   Duration.ofHours(0),
				DayOfWeek.MONDAY,   Duration.ofHours(6),
				// manca il martedì DayOfWeek.TUESDAY,  Duration.ofHours(6),
				DayOfWeek.SATURDAY, Duration.ofHours(3),
				DayOfWeek.WEDNESDAY,Duration.ofHours(9),
				DayOfWeek.THURSDAY, Duration.ofHours(6),
				DayOfWeek.FRIDAY,   Duration.ofHours(6)				
				)));
	}
	
	@Test
	void testOK_ConfigurazioneIniziale_CtorDaMappa_MenoDi7Righe_GiornoDuplicato() {	
		assertThrows(IllegalArgumentException.class, () -> new SettimanaLavorativa(Map.of(
				DayOfWeek.SUNDAY,   Duration.ofHours(0),
				DayOfWeek.MONDAY,   Duration.ofHours(6),
				DayOfWeek.TUESDAY,  Duration.ofHours(6),
				DayOfWeek.TUESDAY,  Duration.ofHours(3), // doppio, ma poi manca il sabato
				DayOfWeek.WEDNESDAY,Duration.ofHours(9),
				DayOfWeek.THURSDAY, Duration.ofHours(6),
				DayOfWeek.FRIDAY,   Duration.ofHours(6)				
				)));
	}
	
	@Test
	void testKO_ConfigurazioneIniziale_CtorDaStringArray_MenoDi7Durate() {		
		assertThrows(IllegalArgumentException.class, () -> new SettimanaLavorativa(
				new String[] {"9H","6H","9H",/* "6H",*/"9H","0H","0H"} ));		
	}
	
	@Test
	void testKO_ConfigurazioneIniziale_CtorDaStringArray_PiùDi7Durate() {		
		assertThrows(IllegalArgumentException.class, () -> new SettimanaLavorativa(
				new String[] {"9H","6H","9H","6H","9H","0H","0H", "99M"} ));		
	}
	

	@Test
	void testKO_ConfigurazioneIniziale_CtorDaDurata_Oltre24hAlGiornoPer7gg() {		
		assertThrows(IllegalArgumentException.class, () -> new SettimanaLavorativa(
				Duration.ofHours(24*7).plusMinutes(1) ));		
	}
	
}
