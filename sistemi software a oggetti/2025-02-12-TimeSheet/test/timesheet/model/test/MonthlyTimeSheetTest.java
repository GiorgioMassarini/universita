package timesheet.model.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Set;

import timesheet.model.MonthlyTimeSheet;


class MonthlyTimeSheetTest {
	
	@Test
	void testOK_Ctor() {
		var ts = new MonthlyTimeSheet(Month.APRIL, Year.of(2024));
		assertEquals(0, ts.getTotalWorkedTime());
		assertEquals(0, ts.getTotalWorkedTimePerProject("aaa"));
		assertEquals(Month.APRIL, ts.getMonth());
		assertEquals(2024, ts.getYear().getValue());
		for(LocalDate d=ts.getStartDate(); !ts.getEndDate().isBefore(d); d = d.plusDays(1)) {
			assertEquals(0, ts.getWorkedTime(d));
		}
		assertEquals(Set.of(), ts.activeProjects());
	}
	
	@Test
	void testOK_oneProject_OneDate() {
		var ts = new MonthlyTimeSheet(Month.APRIL, Year.of(2024));
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,2), 95, "task1");
		assertEquals(95, ts.getTotalWorkedTimePerProject("task1"));
		assertEquals(95, ts.getTotalWorkedTime());
		assertEquals(95, ts.getWorkedTime(LocalDate.of(2024,4,2)));
		assertEquals(Set.of("task1"), ts.activeProjects());
	}
	
	@Test
	void testOK_moreProjects_MoreDates() {
		var ts = new MonthlyTimeSheet(Month.APRIL, Year.of(2024));
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,2), 95, "task1");
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,2), 65, "task3");
		
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,3), 150, "task1");
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,3), 120, "task2");
		
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,20), 65, "task3");
		
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,22), 65, "task3");
		
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,30), 150, "task2");
		
		assertEquals( 95+150, ts.getTotalWorkedTimePerProject("task1"));
		assertEquals(120+150, ts.getTotalWorkedTimePerProject("task2"));
		assertEquals(65+65+65, ts.getTotalWorkedTimePerProject("task3"));
		assertEquals(Set.of("task1","task2","task3"), ts.activeProjects());
		assertEquals(0, ts.getTotalWorkedTimePerProject("aaaa"));
		assertEquals(95+150+120+150+65+65+65, ts.getTotalWorkedTime());
		
		// System.out.println(ts);
		// System.out.println(ts.toFullString());
		
	}
	
	void testOK_getWorkedTime() {
		var ts = new MonthlyTimeSheet(Month.APRIL, Year.of(2024));
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,2), 95, "task1");
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,2), 65, "task3");
		
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,3), 150, "task1");
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,3), 120, "task2");
		
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,20), 65, "task3");
		
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,22), 65, "task3");
		
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,30), 150, "task2");
				
		assertEquals(95+65,   ts.getWorkedTime(LocalDate.of(2024,4,2)));
		assertEquals(150+120, ts.getWorkedTime(LocalDate.of(2024,4,3)));
		assertEquals(65,      ts.getWorkedTime(LocalDate.of(2024,4,20)));
		assertEquals(65,      ts.getWorkedTime(LocalDate.of(2024,4,22)));
		assertEquals(150,     ts.getWorkedTime(LocalDate.of(2024,4,30)));
		
		// System.out.println(ts);
		// System.out.println(ts.toFullString());
		
	}
	
	@Test
	void testOK_getWorkedTimePerProject() {
		var ts = new MonthlyTimeSheet(Month.APRIL, Year.of(2024));
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,2), 95, "task1");
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,2), 65, "task3");
		
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,3), 150, "task1");
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,3), 120, "task2");
		
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,20), 65, "task3");
		
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,22), 65, "task3");
		
		ts.setWorkedTimePerProject(LocalDate.of(2024,4,30), 150, "task2");
		
		assertEquals( 95, ts.getWorkedTimePerProject(LocalDate.of(2024,4,2), "task1"));
		assertEquals(  0, ts.getWorkedTimePerProject(LocalDate.of(2024,4,2), "task2"));
		assertEquals( 65, ts.getWorkedTimePerProject(LocalDate.of(2024,4,2), "task3"));
		
		assertEquals(150, ts.getWorkedTimePerProject(LocalDate.of(2024,4,3), "task1"));
		assertEquals(120, ts.getWorkedTimePerProject(LocalDate.of(2024,4,3), "task2"));
		assertEquals(  0, ts.getWorkedTimePerProject(LocalDate.of(2024,4,3), "task3"));
		
		assertEquals(  0, ts.getWorkedTimePerProject(LocalDate.of(2024,4,4), "task1"));
		assertEquals(  0, ts.getWorkedTimePerProject(LocalDate.of(2024,4,4), "task2"));
		assertEquals(  0, ts.getWorkedTimePerProject(LocalDate.of(2024,4,4), "task3"));
		
		assertEquals(  0, ts.getWorkedTimePerProject(LocalDate.of(2024,4,20), "task1"));
		assertEquals(  0, ts.getWorkedTimePerProject(LocalDate.of(2024,4,20), "task2"));
		assertEquals( 65, ts.getWorkedTimePerProject(LocalDate.of(2024,4,20), "task3"));
		
		assertEquals(  0, ts.getWorkedTimePerProject(LocalDate.of(2024,4,22), "task1"));
		assertEquals(  0, ts.getWorkedTimePerProject(LocalDate.of(2024,4,22), "task2"));
		assertEquals( 65, ts.getWorkedTimePerProject(LocalDate.of(2024,4,22), "task3"));
				
		assertEquals(  0, ts.getWorkedTimePerProject(LocalDate.of(2024,4,30), "task1"));
		assertEquals(150, ts.getWorkedTimePerProject(LocalDate.of(2024,4,30), "task2"));
		assertEquals(  0, ts.getWorkedTimePerProject(LocalDate.of(2024,4,30), "task3"));
	}
	
	
	@Test
	void testKO_getWorkedTime() {
		var ts = new MonthlyTimeSheet(Month.APRIL, Year.of(2024));
		assertThrows(NullPointerException.class, () -> ts.getWorkedTime(null));
		assertThrows(IllegalArgumentException.class, () -> ts.getWorkedTime(LocalDate.of(2024, 5, 1)));
		assertThrows(IllegalArgumentException.class, () -> ts.getWorkedTime(LocalDate.of(2024, 3, 31)));
		assertThrows(DateTimeException.class, () -> ts.getWorkedTime(LocalDate.of(2024, 4, 31)));
	}
	
	@Test
	void testKO_setWorkedTime() {
		var ts = new MonthlyTimeSheet(Month.APRIL, Year.of(2024));
		assertThrows(NullPointerException.class, () -> ts.setWorkedTimePerProject(null, 95, "task1"));
		assertThrows(NullPointerException.class, () -> ts.setWorkedTimePerProject(LocalDate.of(2024,4,2), 95, null));
		assertThrows(IllegalArgumentException.class, () -> ts.setWorkedTimePerProject(LocalDate.of(2024,4,2), 95, ""));
		assertThrows(IllegalArgumentException.class, () -> ts.setWorkedTimePerProject(LocalDate.of(2024,4,2), 95, " \t"));
		assertThrows(IllegalArgumentException.class, () -> ts.setWorkedTimePerProject(LocalDate.of(2024,4,2), -1, "task1"));
		assertThrows(IllegalArgumentException.class, () -> ts.setWorkedTimePerProject(LocalDate.of(2024, 5, 1), 95, "task1"));
		assertThrows(IllegalArgumentException.class, () -> ts.setWorkedTimePerProject(LocalDate.of(2024, 3, 31),95, "task1"));

	}
	

}
	