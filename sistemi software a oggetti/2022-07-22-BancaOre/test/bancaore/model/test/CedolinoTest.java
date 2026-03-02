package bancaore.model.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import bancaore.model.Cedolino;
import bancaore.model.SettimanaLavorativa;
import bancaore.model.VoceCedolino;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collections;
import java.util.Optional;


class CedolinoTest {

	@Test
	void testOK_ConfigurazioneIniziale() {
		// Marcatempo(String nome, LocalDate data, SettimanaLavorativa settimana, Duration saldoPrecedente)
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		assertEquals(Month.JANUARY, m.getData().getMonth());
		assertEquals(2022, m.getData().getYear());
		assertEquals("Giuseppe Verdi", m.getNomeDipendente());
		assertEquals(Duration.ofHours(12).plusMinutes(7), m.getSaldoOrePrecedente());
		assertEquals(SettimanaLavorativa.STANDARD, m.getSettimanaLavorativa());
		assertEquals(Collections.emptyList(), m.getVoci());
	}

	@Test
	void testKO_Arg1Null() {
		assertThrows(IllegalArgumentException.class, () -> new Cedolino(
				null, LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7)));
	}
	@Test
	void testKO_Arg2Null() {
		assertThrows(IllegalArgumentException.class, () -> new Cedolino(
				"Giuseppe Verdi", null, SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7)));
	}
	@Test
	void testKO_Arg3Null() {
		assertThrows(IllegalArgumentException.class, () -> new Cedolino(
				"Giuseppe Verdi", LocalDate.of(2022,1,10), null, Duration.ofHours(12).plusMinutes(7)));
	}
	@Test
	void testKO_Arg4Null() {
		assertThrows(IllegalArgumentException.class, () -> new Cedolino(
				"Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, null));
	}
	
	@Test
	void testOK_AggiungiVoce() {
		// Marcatempo(String nome, LocalDate data, SettimanaLavorativa settimana, Duration saldoPrecedente)
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		var vm = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(7,30), LocalTime.of(13,42), Optional.empty());
		m.aggiungi(vm);
		assertEquals(vm, m.getVoci().get(0));
	}

	@Test
	void testOK_AggiungiPiuVoci_StessoGiorno_CasoA() {
		// Marcatempo(String nome, LocalDate data, SettimanaLavorativa settimana, Duration saldoPrecedente)
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		var vm1 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(7,30), LocalTime.of(13,42), Optional.empty());
		var vm2 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(13,42), LocalTime.of(14,30), Optional.empty());
		var vm3 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(14,30), LocalTime.of(15,40), Optional.empty());
		m.aggiungi(vm1);
		m.aggiungi(vm3);
		m.aggiungi(vm2);
		assertEquals(vm1, m.getVoci().get(0));
		assertEquals(vm2, m.getVoci().get(1));
		assertEquals(vm3, m.getVoci().get(2));
	}

	@Test
	void testOK_AggiungiPiuVoci_StessoGiorno_CasoB() {
		// Marcatempo(String nome, LocalDate data, SettimanaLavorativa settimana, Duration saldoPrecedente)
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		var vm1 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(7,30), LocalTime.of(13,42), Optional.empty());
		var vm2 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(13,42), LocalTime.of(14,30), Optional.empty());
		var vm3 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(14,30), LocalTime.of(15,40), Optional.empty());
		m.aggiungi(vm3);
		m.aggiungi(vm1);
		m.aggiungi(vm2);
		assertEquals(vm1, m.getVoci().get(0));
		assertEquals(vm2, m.getVoci().get(1));
		assertEquals(vm3, m.getVoci().get(2));
	}
	
	@Test
	void testOK_AggiungiPiuVoci_StessoGiorno_CasoC() {
		// Marcatempo(String nome, LocalDate data, SettimanaLavorativa settimana, Duration saldoPrecedente)
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		var vm1 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(7,30), LocalTime.of(13,42), Optional.empty());
		var vm2 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(13,42), LocalTime.of(14,30), Optional.empty());
		var vm3 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(14,30), LocalTime.of(15,40), Optional.empty());
		m.aggiungi(vm3);
		m.aggiungi(vm2);
		m.aggiungi(vm1);
		assertEquals(vm1, m.getVoci().get(0));
		assertEquals(vm2, m.getVoci().get(1));
		assertEquals(vm3, m.getVoci().get(2));
	}
	
	@Test
	void testOK_AggiungiPiuVoci_PiuGiorni_CasoA() {
		// Marcatempo(String nome, LocalDate data, SettimanaLavorativa settimana, Duration saldoPrecedente)
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		var vm1 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(7,30), LocalTime.of(13,40), Optional.empty());
		var vm2 = new VoceCedolino(LocalDate.of(2022,1,11),LocalTime.of(6,42), LocalTime.of(7,30), Optional.empty());
		var vm3 = new VoceCedolino(LocalDate.of(2022,1,14),LocalTime.of(13,40), LocalTime.of(15,40), Optional.empty());
		m.aggiungi(vm3);
		m.aggiungi(vm2);
		m.aggiungi(vm1);
		assertEquals(vm1, m.getVoci().get(0));
		assertEquals(vm2, m.getVoci().get(1));
		assertEquals(vm3, m.getVoci().get(2));
	}
	
	@Test
	void testOK_AggiungiPiuVoci_PiuGiorni_CasoB() {
		// Marcatempo(String nome, LocalDate data, SettimanaLavorativa settimana, Duration saldoPrecedente)
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		var vm1 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(7,30), LocalTime.of(13,40), Optional.empty());
		var vm2 = new VoceCedolino(LocalDate.of(2022,1,11),LocalTime.of(6,42), LocalTime.of(7,30), Optional.empty());
		var vm3 = new VoceCedolino(LocalDate.of(2022,1,14),LocalTime.of(13,40), LocalTime.of(15,40), Optional.empty());
		m.aggiungi(vm1);
		m.aggiungi(vm3);
		m.aggiungi(vm2);
		assertEquals(vm1, m.getVoci().get(0));
		assertEquals(vm2, m.getVoci().get(1));
		assertEquals(vm3, m.getVoci().get(2));
	}
	
	@Test
	void testOK_AggiungiPiuVoci_PiuGiorni_CasoC() {
		// Marcatempo(String nome, LocalDate data, SettimanaLavorativa settimana, Duration saldoPrecedente)
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		var vm1 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(7,30), LocalTime.of(13,40), Optional.empty());
		var vm2 = new VoceCedolino(LocalDate.of(2022,1,11),LocalTime.of(6,42), LocalTime.of(7,30), Optional.empty());
		var vm3 = new VoceCedolino(LocalDate.of(2022,1,14),LocalTime.of(13,40), LocalTime.of(15,40), Optional.empty());
		m.aggiungi(vm2);
		m.aggiungi(vm3);
		m.aggiungi(vm1);
		assertEquals(vm1, m.getVoci().get(0));
		assertEquals(vm2, m.getVoci().get(1));
		assertEquals(vm3, m.getVoci().get(2));
	}

	@Test
	void testKO_Aggiungi_ArgNull() {
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		assertThrows(IllegalArgumentException.class, () -> m.aggiungi(null));
	}
}
