package bancaore.controller.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bancaore.controller.Controller;
import bancaore.model.Cedolino;
import bancaore.model.SettimanaLavorativa;
import bancaore.model.VoceCedolino;
import bancaore.model.BancaOre;
import bancaore.model.Causale;


class ControllerTest {
	
	private BancaOre banca;
	private Cedolino cedolino;
	private Controller controller;
			
	@BeforeEach
	void setupBancaOre() {
		cedolino = new Cedolino("Giuseppe Verdi", LocalDate.of(2022,1,10), SettimanaLavorativa.STANDARD, Duration.ofHours(12).plusMinutes(7));
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
		cedolino.aggiungi(vm1);
		cedolino.aggiungi(vm2);
		cedolino.aggiungi(vm3);		// 7h22m lavorate vs. giornata da 6h -> diff +1h22 -> saldo = 13h29m
		cedolino.aggiungi(vm4);
		cedolino.aggiungi(vm5);
		cedolino.aggiungi(vm6);
		cedolino.aggiungi(vm7);
		cedolino.aggiungi(vm8);		// 8h lavorate vs. giornata da 9h -> diff -1h -> saldo = 12h29m
		banca = new BancaOre(cedolino);
		controller = new Controller(banca);
	}
	
	@Test
	void testOK() {
		assertEquals(cedolino.getVoci(), controller.getVociCedolino());
		assertEquals(banca.getVoci(), controller.getVociBancaOre());
		assertEquals(cedolino.getNomeDipendente(), controller.getNomeDipendente());
		assertEquals("GENNAIO 2022", controller.getMeseAnno());
		assertEquals("12:07", 	controller.getSaldoOreIniziale());
		assertEquals("-122:31", controller.getSaldoOreFinale());
		assertEquals("15:22", 	controller.getTotaleOreLavorate());
		assertEquals("150:00", 	controller.getTotaleOrePreviste());
		assertEquals("01:00", 	controller.getTotalePermessiAOre());
		assertEquals("06:00", 	controller.getTotalePermessiGiornalieri());
	}

}
