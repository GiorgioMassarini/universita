package timesheet.model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;
import timesheet.model.DailyTimeSheet;


class DailyTimeSheetTest {
	
	@Test
	void testOK() {
		var wtpp = new DailyTimeSheet();
		wtpp.setWorkedTime("task1", 95);
		wtpp.setWorkedTime("task2", 60);
		wtpp.setWorkedTime("task3", 45);
		assertEquals(Set.of("task1","task2","task3"), wtpp.activeProjects());
		assertEquals(95, wtpp.getWorkedTime("task1"));
		assertEquals(60, wtpp.getWorkedTime("task2"));
		assertEquals(45, wtpp.getWorkedTime("task3"));
		assertEquals(95+60+45, wtpp.getTotalWorkedTime());
		assertEquals("01:35", wtpp.getWorkedTimeAsString("task1"));
		assertEquals("01:00", wtpp.getWorkedTimeAsString("task2"));
		assertEquals("00:45", wtpp.getWorkedTimeAsString("task3"));
		wtpp.setWorkedTime("task3", 180);
		assertEquals(180, wtpp.getWorkedTime("task3"));
		assertEquals("03:00", wtpp.getWorkedTimeAsString("task3"));		
		assertEquals(95+60+180, wtpp.getTotalWorkedTime());
		wtpp.setWorkedTime("task4", 0);
		assertEquals(0, wtpp.getWorkedTime("task4"));
		assertEquals("00:00", wtpp.getWorkedTimeAsString("task4"));
		assertEquals(95+60+180+0, wtpp.getTotalWorkedTime());
		assertEquals(Set.of("task1","task2","task3","task4"), wtpp.activeProjects());
	}
	
	
	@Test
	void testKO_setWorkedTime() {
		var wtpp = new DailyTimeSheet();
		assertThrows(NullPointerException.class, () -> wtpp.setWorkedTime(null, 95));
		assertThrows(IllegalArgumentException.class, () -> wtpp.setWorkedTime("", 95));
		assertThrows(IllegalArgumentException.class, () -> wtpp.setWorkedTime("  ", 95));
		assertThrows(IllegalArgumentException.class, () -> wtpp.setWorkedTime("aaa", -1));
	}
	
	@Test
	void testKO_getWorkedTime() {
		var wtpp = new DailyTimeSheet();
		assertThrows(NullPointerException.class, () -> wtpp.getWorkedTime(null));
		assertThrows(IllegalArgumentException.class, () -> wtpp.getWorkedTime(""));
		assertThrows(IllegalArgumentException.class, () -> wtpp.getWorkedTime("  "));
	}
	
	@Test
	void testKO_getWorkedTimeAsString() {
		var wtpp = new DailyTimeSheet();
		assertThrows(NullPointerException.class, () -> wtpp.getWorkedTimeAsString(null));
		assertThrows(IllegalArgumentException.class, () -> wtpp.getWorkedTimeAsString(""));
		assertThrows(IllegalArgumentException.class, () -> wtpp.getWorkedTimeAsString("  "));
	}

}
	