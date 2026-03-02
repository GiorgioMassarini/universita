package bancaore.model.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import bancaore.model.BancaOre;
import bancaore.model.Causale;
import bancaore.model.Cedolino;
import bancaore.model.SettimanaLavorativa;
import bancaore.model.VoceCedolino;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


class BancaOreTest {

 	@Test
	void testOK_ConfigurazioneIniziale() {
		var b = new BancaOre(new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(0).plusMinutes(0)));
		assertEquals(LocalDate.of(2022,1,10), b.getCedolino().getData());
		assertEquals("Giuseppe Verdi", b.getCedolino().getNomeDipendente());
		assertEquals(Duration.ofHours(0).plusMinutes(0), b.getCedolino().getSaldoOrePrecedente());
		assertEquals(SettimanaLavorativa.STANDARD, b.getCedolino().getSettimanaLavorativa());
		assertEquals(Collections.emptyList(), b.getCedolino().getVoci());
		assertEquals(Duration.ofHours(-150).plusMinutes(0), b.getSaldoOrePrecedente());
		// 4 settimane complete da 36h + 31 gennaio da 6h = 150h da lavorare nel mese
		assertEquals(Duration.ofHours(0), b.getTotalePermessi());
		assertEquals(Duration.ofHours(0), b.getTotalePermessiAOre());
	}

	@Test
	void testKO_CtorArgNull() {
		assertThrows(IllegalArgumentException.class, () -> new BancaOre(null));
	}
	
	@Test
	void testOK_AggiungiVoce() {
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		var vm = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(7,30), LocalTime.of(13,42), Optional.empty());
		m.aggiungi(vm); // 6h12 lavorate, vs giornata da 6h -> diff = +12m -> saldo banca ore = 12h19m
		var b = new BancaOre(m);
		assertEquals(LocalDate.of(2022,1,10), b.getCedolino().getData());
		assertEquals("Giuseppe Verdi", b.getCedolino().getNomeDipendente());
		assertEquals(Duration.ofHours(12).plusMinutes(7), b.getCedolino().getSaldoOrePrecedente());
		assertEquals(SettimanaLavorativa.STANDARD, b.getCedolino().getSettimanaLavorativa());
		assertEquals(List.of(vm), b.getCedolino().getVoci());
		assertEquals(Duration.ofHours(0), b.getTotalePermessi());
		assertEquals(Duration.ofHours(0), b.getTotalePermessiAOre());
		// 150 ore da lavorare in gennaio (incluso giovedì 6)
		// -6h lavorate il giorno 10 = 144 ore da lavorare
		// -12h19 da banca ore = -131h41m saldo banca ore (ovviamente, molto negativo, perché non ha svolto tanti gg lavorativi)
		assertEquals(Duration.ofHours(12).plusMinutes(19).minus(Duration.ofHours(144)), b.getSaldoOrePrecedente());
		var vb = b.getVoci().get(9); // quella del 10 gennaio
		assertEquals(Duration.ofHours(6).plusMinutes(12), vb.getOreEffettuate());
		assertEquals(Duration.ofHours(6), vb.getOrePreviste());
		assertEquals(Duration.ofMinutes(12), vb.getDifferenzaOre()); // diff = +12m
		assertEquals(Duration.ofHours(-131).plusMinutes(-41), b.getSaldoOrePrecedente()); // saldo = -131h41m
	}

	@Test
	void testOK_AggiungiPiuVoci_StessoGiorno_SenzaPausaPranzo() {
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		var vm1 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(7,30), LocalTime.of(13,42), Optional.empty());
		var vm2 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(13,42), LocalTime.of(14,30), Optional.empty());
		var vm3 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(14,30), LocalTime.of(15,40), Optional.empty());
		m.aggiungi(vm1);
		m.aggiungi(vm2);
		m.aggiungi(vm3);
		var b = new BancaOre(m); // 8h10 lavorate vs. giornata da 6h -> diff +2h10 -> saldo = 14h17m
		assertEquals(LocalDate.of(2022,1,10), b.getCedolino().getData());
		assertEquals("Giuseppe Verdi", b.getCedolino().getNomeDipendente());
		assertEquals(Duration.ofHours(12).plusMinutes(7), b.getCedolino().getSaldoOrePrecedente());
		assertEquals(SettimanaLavorativa.STANDARD, b.getCedolino().getSettimanaLavorativa());
		assertEquals(List.of(vm1,vm2,vm3), b.getCedolino().getVoci());
		assertEquals(Duration.ofHours(0), b.getTotalePermessi());
		assertEquals(Duration.ofHours(0), b.getTotalePermessiAOre());
		// 150 ore da lavorare in gennaio (incluso giovedì 6)
		// -6h lavorate il giorno 10 = 144 ore da lavorare
		// -14h17m da banca ore = -129h43m
		var vb1 = b.getVoci().get(9); // il 10 gennaio
		assertEquals(Duration.ofHours(8).plusMinutes(10), vb1.getOreEffettuate());
		assertEquals(Duration.ofHours(6), vb1.getOrePreviste());		
		assertEquals(Duration.ofHours(-129).plusMinutes(-43), b.getSaldoOrePrecedente()); // saldo = -129h43m
	}
	
	@Test
	void testOK_AggiungiPiuVoci_StessoGiorno_ConPausaPranzo() {
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		var vm1 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(7,30), LocalTime.of(13,42), Optional.empty());
		var vm2 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(13,42), LocalTime.of(14,30), Optional.of(Causale.PAUSA_PRANZO));
		var vm3 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(14,30), LocalTime.of(15,40), Optional.empty());
		m.aggiungi(vm1);
		m.aggiungi(vm2);
		m.aggiungi(vm3);
		var b = new BancaOre(m); // 6h12+1h10=7h22 lavorate vs. giornata da 6h -> diff +1h22 -> saldo = 13h29m
		assertEquals(LocalDate.of(2022,1,10), b.getCedolino().getData());
		assertEquals("Giuseppe Verdi", b.getCedolino().getNomeDipendente());
		assertEquals(Duration.ofHours(12).plusMinutes(7), b.getCedolino().getSaldoOrePrecedente());
		assertEquals(SettimanaLavorativa.STANDARD, b.getCedolino().getSettimanaLavorativa());
		assertEquals(List.of(vm1,vm2,vm3), b.getCedolino().getVoci());
		assertEquals(Duration.ofHours(0), b.getTotalePermessi());
		assertEquals(Duration.ofHours(0), b.getTotalePermessiAOre());
		// 150 ore da lavorare in gennaio (incluso giovedì 6)
		// -6h lavorate il giorno 10 = 144 ore da lavorare
		// -13h29m da banca ore = -130h31m
		var vb1 = b.getVoci().get(9); // il 10 gennaio
		assertEquals(Duration.ofHours(7).plusMinutes(22), vb1.getOreEffettuate());
		assertEquals(Duration.ofHours(6), vb1.getOrePreviste());		
		assertEquals(Duration.ofHours(-130).plusMinutes(-31), b.getSaldoOrePrecedente()); // saldo = -130h31m
	}
	
	@Test
	void testOK_AggiungiPiuVoci_PiuGiorni_CasoA() {
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		var vm1 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(7,30), LocalTime.of(13,42), Optional.empty());
		var vm2 = new VoceCedolino(LocalDate.of(2022,1,11),LocalTime.of(6,42), LocalTime.of(7,30), Optional.empty());
		var vm3 = new VoceCedolino(LocalDate.of(2022,1,14),LocalTime.of(13,40), LocalTime.of(15,40), Optional.empty());
		m.aggiungi(vm3);
		m.aggiungi(vm2);
		m.aggiungi(vm1);
		var b = new BancaOre(m); // 6h12+0h48+2h=9h lavorate vs. 3 giornata da 6h+9h+6h=21h -> diff -12h -> saldo = 0h7m
		assertEquals(LocalDate.of(2022,1,10), b.getCedolino().getData());
		assertEquals("Giuseppe Verdi", b.getCedolino().getNomeDipendente());
		assertEquals(Duration.ofHours(12).plusMinutes(7), b.getCedolino().getSaldoOrePrecedente());
		assertEquals(SettimanaLavorativa.STANDARD, b.getCedolino().getSettimanaLavorativa());
		assertEquals(List.of(vm1,vm2,vm3), b.getCedolino().getVoci());
		assertEquals(Duration.ofHours(0), b.getTotalePermessi());
		assertEquals(Duration.ofHours(0), b.getTotalePermessiAOre());
		// 150 ore da lavorare in gennaio (incluso giovedì 6)
		// -6h lavorate il giorno 10, 9h lavorate il giorno 11, 6h lavorate il giorno 14 = 21h totali 
		//	= 129 ore da lavorare
		// -7m da banca ore = -128h53m
		var vb1 = b.getVoci().get(9); // il 10 gennaio
		assertEquals(Duration.ofHours(6).plusMinutes(12), vb1.getOreEffettuate());
		assertEquals(Duration.ofHours(6), vb1.getOrePreviste());		
		var vb2 = b.getVoci().get(10); // l'11 gennaio
		assertEquals(Duration.ofHours(0).plusMinutes(48), vb2.getOreEffettuate());
		assertEquals(Duration.ofHours(9), vb2.getOrePreviste());		
		var vb3 = b.getVoci().get(13); // il 14 gennaio
		assertEquals(Duration.ofHours(2).plusMinutes(0), vb3.getOreEffettuate());
		assertEquals(Duration.ofHours(6), vb3.getOrePreviste());
		assertEquals(Duration.ofHours(-128).plusMinutes(-53), b.getSaldoOrePrecedente()); // saldo = -128h53m
	}
	
	@Test
	void testOK_AggiungiPiuVoci_StessoGiorno_ConPermessoAOre() {
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		var vm3 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(7,30), LocalTime.of(8,30), Optional.of(Causale.RIPOSO_COMPENSATIVO_A_ORE));
		var vm1 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(8,30), LocalTime.of(13,30), Optional.empty());
		var vm2 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(13,30), LocalTime.of(14,30), Optional.of(Causale.PAUSA_PRANZO));
		m.aggiungi(vm1);
		m.aggiungi(vm2);
		m.aggiungi(vm3);
		var b = new BancaOre(m); // 5h ore lavorate vs. giornata da 6h, ora mancante è permesso, già considerato cmq dopo
		assertEquals(LocalDate.of(2022,1,10), b.getCedolino().getData());
		assertEquals("Giuseppe Verdi", b.getCedolino().getNomeDipendente());
		assertEquals(Duration.ofHours(12).plusMinutes(7), b.getCedolino().getSaldoOrePrecedente());
		assertEquals(SettimanaLavorativa.STANDARD, b.getCedolino().getSettimanaLavorativa());
		assertEquals(List.of(vm3,vm1,vm2), b.getCedolino().getVoci());
		assertEquals(Duration.ofHours(0), b.getTotalePermessi());
		assertEquals(Duration.ofHours(1), b.getTotalePermessiAOre());
		// 150 ore da lavorare in gennaio (incluso giovedì 6)
		// -5h lavorate il giorno 10 = 145 ore da lavorare (sarebbero state 6 e dunque 144, ma ne tiene conto dopo)
		// 12h7m da banca ore -145h = -132h53m (nel fare questo scomputa già 1h di permesso)
		var vb1 = b.getVoci().get(9); // il 10 gennaio
		assertEquals(Duration.ofHours(5), vb1.getOreEffettuate());
		assertEquals(Duration.ofHours(6), vb1.getOrePreviste());		
		assertEquals(Duration.ofHours(-132).plusMinutes(-53), b.getSaldoOrePrecedente()); // saldo = -132h53m
	}
	
	@Test
	void testOK_AggiungiPiuVoci_PiuGiorni_ConPermessiVari() {
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		var vm1 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(7,30), LocalTime.of(13,42), Optional.empty());
		var vm2 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(13,42), LocalTime.of(14,30), Optional.of(Causale.PAUSA_PRANZO));
		var vm3 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(14,30), LocalTime.of(15,40), Optional.of(Causale.RIPOSO_COMPENSATIVO_A_ORE));
		var vm4 = new VoceCedolino(LocalDate.of(2022,1,12),LocalTime.of(8,30), LocalTime.of(14,30), Optional.of(Causale.RIPOSO_COMPENSATIVO));
		m.aggiungi(vm1);
		m.aggiungi(vm2);
		m.aggiungi(vm3);
		m.aggiungi(vm4);
		var b = new BancaOre(m); // 6h12 lavorate vs. giornata da 6h -> diff +12m -> saldo = 12h19m
		assertEquals(LocalDate.of(2022,1,10), b.getCedolino().getData());
		assertEquals("Giuseppe Verdi", b.getCedolino().getNomeDipendente());
		assertEquals(Duration.ofHours(12).plusMinutes(7), b.getCedolino().getSaldoOrePrecedente());
		assertEquals(SettimanaLavorativa.STANDARD, b.getCedolino().getSettimanaLavorativa());
		assertEquals(List.of(vm1,vm2,vm3,vm4), b.getCedolino().getVoci());
		assertEquals(Duration.ofHours(6), b.getTotalePermessi());
		assertEquals(Duration.ofHours(1).plusMinutes(10), b.getTotalePermessiAOre());
		// 150 ore da lavorare in gennaio (incluso giovedì 6)
		// -6h lavorate il giorno 10 = 144 ore da lavorare
		// 12h19m da banca ore -144h da lavorare = -131h41m
		var vb1 = b.getVoci().get(9); // il 10 gennaio
		assertEquals(Duration.ofHours(6).plusMinutes(12), vb1.getOreEffettuate()); // c'è 1h12 di permesso a ore
		assertEquals(Duration.ofHours(6), vb1.getOrePreviste());		
		assertEquals(Duration.ofHours(-131).plusMinutes(-41), b.getSaldoOrePrecedente()); // saldo = -130h31m-1h10 di permesso
		var vb2 = b.getVoci().get(11); // il 12 gennaio
		assertEquals(Duration.ofHours(0), vb2.getOreEffettuate()); // c'è 1h12 di permesso a ore
		assertEquals(Duration.ofHours(6), vb2.getOrePreviste());		
		assertEquals(Duration.ofHours(-131).plusMinutes(-41), b.getSaldoOrePrecedente()); // saldo = -130h31m-1h10 di permesso
	}
	
	@Test
	void testOK_UnGiornoDiPermesso() {
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		var vm4 = new VoceCedolino(LocalDate.of(2022,1,12),LocalTime.of(8,30), LocalTime.of(14,30), Optional.of(Causale.RIPOSO_COMPENSATIVO));
		m.aggiungi(vm4);
		var b = new BancaOre(m); // 0h lavorate vs. giornata da 6h (ha preso un permesso giornaliero)
		assertEquals(LocalDate.of(2022,1,10), b.getCedolino().getData());
		assertEquals("Giuseppe Verdi", b.getCedolino().getNomeDipendente());
		assertEquals(Duration.ofHours(12).plusMinutes(7), b.getCedolino().getSaldoOrePrecedente());
		assertEquals(SettimanaLavorativa.STANDARD, b.getCedolino().getSettimanaLavorativa());
		assertEquals(List.of(vm4), b.getCedolino().getVoci());
		assertEquals(Duration.ofHours(6), b.getTotalePermessi());
		assertEquals(Duration.ofHours(0), b.getTotalePermessiAOre());
		// 150 ore da lavorare in gennaio (incluso giovedì 6)
		// Non ne svolge nessuna, perché il giorno 12 si prende un permesso
		// 12h7m da banca ore -150 = -137h53m
		var vb1 = b.getVoci().get(9); // il 10 gennaio
		assertEquals(Duration.ofHours(0), vb1.getOreEffettuate()); // c'è 1h12 di permesso a ore
		assertEquals(Duration.ofHours(6), vb1.getOrePreviste());		
		assertEquals(Duration.ofHours(-137).plusMinutes(-53), b.getSaldoOrePrecedente()); // saldo = -130h31m-1h10 di permesso
	}

	@Test
	void testOK_TreGiorni_ConPermessiVari() {
		var m = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
		var vm1 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(7,30), LocalTime.of(13,42), Optional.empty());
		var vm2 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(13,42), LocalTime.of(14,30), Optional.of(Causale.PAUSA_PRANZO));
		var vm3 = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(14,30), LocalTime.of(15,40), Optional.empty());
		//
		var vm4 = new VoceCedolino(LocalDate.of(2022,1,12),LocalTime.of(8,30), LocalTime.of(14,30), Optional.of(Causale.RIPOSO_COMPENSATIVO));
		//
		var vm5 = new VoceCedolino(LocalDate.of(2022,1,13),LocalTime.of(7,30), LocalTime.of(13,00), Optional.empty());
		var vm6 = new VoceCedolino(LocalDate.of(2022,1,13),LocalTime.of(13,00), LocalTime.of(13,30), Optional.of(Causale.PAUSA_PRANZO));
		var vm7 = new VoceCedolino(LocalDate.of(2022,1,13),LocalTime.of(13,30), LocalTime.of(16,00), Optional.empty());
		var vm8 = new VoceCedolino(LocalDate.of(2022,1,13),LocalTime.of(16,00), LocalTime.of(17,00), Optional.of(Causale.RIPOSO_COMPENSATIVO_A_ORE));
		m.aggiungi(vm1);
		m.aggiungi(vm2);
		m.aggiungi(vm3);		// 7h22m lavorate vs. giornata da 6h -> diff +1h22 -> saldo = 13h29m
		m.aggiungi(vm4);
		m.aggiungi(vm5);
		m.aggiungi(vm6);
		m.aggiungi(vm7);
		m.aggiungi(vm8);		// 8h lavorate vs. giornata da 9h -> diff -1h -> saldo = 12h29m
		var b = new BancaOre(m);
		
		assertEquals(LocalDate.of(2022,1,10), b.getCedolino().getData());
		assertEquals("Giuseppe Verdi", b.getCedolino().getNomeDipendente());
		assertEquals(Duration.ofHours(12).plusMinutes(7), b.getCedolino().getSaldoOrePrecedente());
		assertEquals(SettimanaLavorativa.STANDARD, b.getCedolino().getSettimanaLavorativa());
		assertEquals(List.of(vm1,vm2,vm3,vm4,vm5,vm6,vm7,vm8), b.getCedolino().getVoci());
		assertEquals(Duration.ofHours(6), b.getTotalePermessi());
		assertEquals(Duration.ofHours(1), b.getTotalePermessiAOre());
		// 150 ore da lavorare in gennaio (incluso giovedì 6)
		// -7h22 lavorate il giorno 10 = 142h38 ore da lavorare (oppure: 6h lavorate ma banca ore +1h22)
		// -8h00 lavorate il giorno 13 = 134h38 ore da lavorare (oppure: 9h lavorate ma banca ore -1h)
		// 12h7m da banca ore -134h38 da lavorare = -122h31m (oppure: 12h7+1h22-1h=12h29 in banca ore vs. 135h lavorate virtuali)
		var vb1 = b.getVoci().get(9); // il 10 gennaio
		assertEquals(Duration.ofHours(7).plusMinutes(22), vb1.getOreEffettuate()); // 6h12 mattina + 1h10 pom = 7h22
		assertEquals(Duration.ofHours(6), vb1.getOrePreviste());		
		var vb2 = b.getVoci().get(11); // il 12 gennaio
		assertEquals(Duration.ofHours(0), vb2.getOreEffettuate()); // permesso giornaliero
		assertEquals(Duration.ofHours(6), vb2.getOrePreviste());		
		var vb3 = b.getVoci().get(12); // il 13 gennaio
		assertEquals(Duration.ofHours(8), vb3.getOreEffettuate()); // c'è 1/2h di permesso a ore
		assertEquals(Duration.ofHours(9), vb3.getOrePreviste());
		//
		assertEquals(Duration.ofHours(-122).plusMinutes(-31), b.getSaldoOrePrecedente()); // saldo = -122h31
	}
	
}
