package bancaore.model.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import bancaore.model.VoceCedolino;
import bancaore.model.Causale;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

class VoceCedolinoTest {

	@Test
	void testOK_SenzaCausale() {
		// 10 Lunedì	07:30	13:42	(senza causale)
		var vm = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(7,30), LocalTime.of(13,42), Optional.empty());
		assertEquals(LocalDate.of(2022,1,10), vm.getData());
		assertEquals(LocalTime.of(7,30), vm.getOraEntrata());
		assertEquals(LocalTime.of(13,42), vm.getOraUscita());
		assertEquals(Optional.empty(), vm.getCausale());
	}

	@Test
	void testOK_ConCausale() {
		// 10 Lunedì	13:42	13:53	Pausa Pranzo
		var vm = new VoceCedolino(LocalDate.of(2022,1,10),LocalTime.of(13,42), LocalTime.of(13,53), Optional.of(Causale.PAUSA_PRANZO));
		assertEquals(LocalDate.of(2022,1,10), vm.getData());
		assertEquals(LocalTime.of(13,42), vm.getOraEntrata());
		assertEquals(LocalTime.of(13,53), vm.getOraUscita());
		assertEquals(Optional.of(Causale.PAUSA_PRANZO), vm.getCausale());
	}

	@Test
	void testKO_Arg1Null() {
		assertThrows(IllegalArgumentException.class, () -> new VoceCedolino(
				null,LocalTime.of(13,42), LocalTime.of(13,53), Optional.of(Causale.PAUSA_PRANZO)));
	}
	@Test
	void testKO_Arg2Null() {
		assertThrows(IllegalArgumentException.class, () -> new VoceCedolino(
				LocalDate.of(2022,1,10),null, LocalTime.of(13,53), Optional.of(Causale.PAUSA_PRANZO)));
	}
	@Test
	void testKO_Arg3Null() {
		assertThrows(IllegalArgumentException.class, () -> new VoceCedolino(
				LocalDate.of(2022,1,10),LocalTime.of(13,42), null, Optional.of(Causale.PAUSA_PRANZO)));
	}
	@Test
	void testKO_Arg4Null() {
		assertThrows(IllegalArgumentException.class, () -> new VoceCedolino(
				LocalDate.of(2022,1,10),LocalTime.of(13,42), LocalTime.of(13,53), null));
	}
	@Test
	void testKO_OraEntrataSuccessivaAOraUscita() {
		assertThrows(IllegalArgumentException.class, () -> new VoceCedolino(
				LocalDate.of(2022,1,10),LocalTime.of(13,54), LocalTime.of(13,53), Optional.of(Causale.PAUSA_PRANZO)));
	}
	
}
