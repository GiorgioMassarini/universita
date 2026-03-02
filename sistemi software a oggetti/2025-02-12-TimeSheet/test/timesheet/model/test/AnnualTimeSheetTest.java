package timesheet.model.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Set;

import timesheet.model.AnnualTimeSheet;


class AnnualTimeSheetTest {
	
	@Test
	void testOK_Ctor() {
		var as = new AnnualTimeSheet(Year.of(2024));
		assertEquals(0, as.getTotalWorkedTime());
		assertEquals(0, as.getTotalWorkedTimePerProject("aaa"));
		assertEquals(2024, as.getYear().getValue());
		for(Month m : Month.values()) {
			assertEquals(0, as.getWorkedTime(m));
		}
		assertEquals(Set.of(), as.activeProjects());
	}
	
	@Test
	void testOK() {
		var as = new AnnualTimeSheet(Year.of(2024));
		
		var ts1 = as.getMonthlyTimeSheet(Month.JANUARY);
		ts1.setWorkedTimePerProject(LocalDate.of(2024,1,16), 95, "task1");
		ts1.setWorkedTimePerProject(LocalDate.of(2024,1,17), 95, "task1");
		ts1.setWorkedTimePerProject(LocalDate.of(2024,1,17), 45, "task2");

		var ts2 = as.getMonthlyTimeSheet(Month.FEBRUARY);
		ts2.setWorkedTimePerProject(LocalDate.of(2024,2,22), 95, "task1");
		ts2.setWorkedTimePerProject(LocalDate.of(2024,2,17), 55, "task2");
		ts2.setWorkedTimePerProject(LocalDate.of(2024,2,10), 50, "task3");

		var ts4 = as.getMonthlyTimeSheet(Month.APRIL);
		ts4.setWorkedTimePerProject(LocalDate.of(2024,4, 2), 95, "task1");
		ts4.setWorkedTimePerProject(LocalDate.of(2024,4,20), 75, "task3");
		ts4.setWorkedTimePerProject(LocalDate.of(2024,4,12), 60, "task4");
		
		assertEquals(95+95+95+95, 	as.getTotalWorkedTimePerProject("task1"));
		assertEquals(45+55, 		as.getTotalWorkedTimePerProject("task2"));
		assertEquals(50+75, 		as.getTotalWorkedTimePerProject("task3"));
		assertEquals(60, 			as.getTotalWorkedTimePerProject("task4"));
		
		assertEquals(95*4+100+125+60, as.getTotalWorkedTime());
		
		assertEquals(95+95+45, 	as.getWorkedTime(Month.JANUARY));
		assertEquals(95+55+50, 	as.getWorkedTime(Month.FEBRUARY));
		assertEquals(0,  		as.getWorkedTime(Month.MARCH));
		assertEquals(95+75+60, 	as.getWorkedTime(Month.APRIL));
		assertEquals(0,  		as.getWorkedTime(Month.JUNE));
		
		assertEquals(Set.of("task4", "task1" , "task3" ,"task2"), as.activeProjects());
	}
	
	@Test
	void testOK_getWorkedTimePerProjectByMonth() {
		var as = new AnnualTimeSheet(Year.of(2024));
		
		var ts1 = as.getMonthlyTimeSheet(Month.JANUARY);
		ts1.setWorkedTimePerProject(LocalDate.of(2024,1,16), 95, "task1");
		ts1.setWorkedTimePerProject(LocalDate.of(2024,1,17), 95, "task1");
		ts1.setWorkedTimePerProject(LocalDate.of(2024,1,17), 45, "task2");

		var ts2 = as.getMonthlyTimeSheet(Month.FEBRUARY);
		ts2.setWorkedTimePerProject(LocalDate.of(2024,2,22), 95, "task1");
		ts2.setWorkedTimePerProject(LocalDate.of(2024,2,17), 55, "task2");
		ts2.setWorkedTimePerProject(LocalDate.of(2024,2,10), 50, "task3");

		var ts4 = as.getMonthlyTimeSheet(Month.APRIL);
		ts4.setWorkedTimePerProject(LocalDate.of(2024,4, 2), 95, "task1");
		ts4.setWorkedTimePerProject(LocalDate.of(2024,4,20), 75, "task3");
		ts4.setWorkedTimePerProject(LocalDate.of(2024,4,12), 60, "task4");
		
		assertEquals(190,  as.getWorkedTimePerProject(Month.JANUARY,"task1"));
		assertEquals( 45,  as.getWorkedTimePerProject(Month.JANUARY,"task2"));
		assertEquals(  0,  as.getWorkedTimePerProject(Month.JANUARY,"task3"));
		assertEquals(  0,  as.getWorkedTimePerProject(Month.JANUARY,"task4"));

		assertEquals(95,  as.getWorkedTimePerProject(Month.FEBRUARY,"task1"));
		assertEquals(55,  as.getWorkedTimePerProject(Month.FEBRUARY,"task2"));
		assertEquals(50,  as.getWorkedTimePerProject(Month.FEBRUARY,"task3"));
		assertEquals( 0,  as.getWorkedTimePerProject(Month.FEBRUARY,"task4"));

		assertEquals(95,  as.getWorkedTimePerProject(Month.APRIL,"task1"));
		assertEquals( 0,  as.getWorkedTimePerProject(Month.APRIL,"task2"));
		assertEquals(75,  as.getWorkedTimePerProject(Month.APRIL,"task3"));
		assertEquals(60,  as.getWorkedTimePerProject(Month.APRIL,"task4"));
	}
	
	
	@Test
	void testOK_getWorkedTimePerProjectByDate() {
		var as = new AnnualTimeSheet(Year.of(2024));
		
		var ts1 = as.getMonthlyTimeSheet(Month.JANUARY);
		ts1.setWorkedTimePerProject(LocalDate.of(2024,1,16), 95, "task1");
		ts1.setWorkedTimePerProject(LocalDate.of(2024,1,17), 95, "task1");
		ts1.setWorkedTimePerProject(LocalDate.of(2024,1,17), 45, "task2");

		var ts2 = as.getMonthlyTimeSheet(Month.FEBRUARY);
		ts2.setWorkedTimePerProject(LocalDate.of(2024,2,22), 95, "task1");
		ts2.setWorkedTimePerProject(LocalDate.of(2024,2,17), 55, "task2");
		ts2.setWorkedTimePerProject(LocalDate.of(2024,2,10), 50, "task3");

		var ts4 = as.getMonthlyTimeSheet(Month.APRIL);
		ts4.setWorkedTimePerProject(LocalDate.of(2024,4, 2), 95, "task1");
		ts4.setWorkedTimePerProject(LocalDate.of(2024,4,20), 75, "task3");
		ts4.setWorkedTimePerProject(LocalDate.of(2024,4,12), 60, "task4");
		
		assertEquals(95,  as.getWorkedTimePerProject(LocalDate.of(2024,1,16),"task1"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,1,16),"task2"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,1,16),"task3"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,1,16),"task4"));
		
		assertEquals(95,  as.getWorkedTimePerProject(LocalDate.of(2024,1,17),"task1"));
		assertEquals(45,  as.getWorkedTimePerProject(LocalDate.of(2024,1,17),"task2"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,1,17),"task3"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,1,17),"task4"));

		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,2,10),"task1"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,2,10),"task2"));
		assertEquals(50,  as.getWorkedTimePerProject(LocalDate.of(2024,2,10),"task3"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,2,10),"task4"));
		
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,2,17),"task1"));
		assertEquals(55,  as.getWorkedTimePerProject(LocalDate.of(2024,2,17),"task2"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,2,17),"task3"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,2,17),"task4"));
		
		assertEquals(95,  as.getWorkedTimePerProject(LocalDate.of(2024,2,22),"task1"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,2,22),"task2"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,2,22),"task3"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,2,22),"task4"));

		assertEquals(95,  as.getWorkedTimePerProject(LocalDate.of(2024,4, 2),"task1"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,4, 2),"task2"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,4, 2),"task3"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,4, 2),"task4"));
		
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,4,12),"task1"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,4,12),"task2"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,4,12),"task3"));
		assertEquals(60,  as.getWorkedTimePerProject(LocalDate.of(2024,4,12),"task4"));
		
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,4,20),"task1"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,4,20),"task2"));
		assertEquals(75,  as.getWorkedTimePerProject(LocalDate.of(2024,4,20),"task3"));
		assertEquals( 0,  as.getWorkedTimePerProject(LocalDate.of(2024,4,20),"task4"));
		
	}
	
	@Test
	void testKO_getTotalWorkedTimePerProject() {
		var as = new AnnualTimeSheet(Year.of(2024));
		assertThrows(NullPointerException.class, () -> as.getTotalWorkedTimePerProject(null));
		assertThrows(IllegalArgumentException.class, () -> as.getTotalWorkedTimePerProject("  "));
	}
	
	@Test
	void testKO_getWorkedTimePerProjectByMonth() {
		var as = new AnnualTimeSheet(Year.of(2024));
		assertThrows(NullPointerException.class, () -> as.getWorkedTimePerProject((Month)null, "project"));
		assertThrows(NullPointerException.class, () -> as.getWorkedTimePerProject(Month.JULY, null));
		assertThrows(IllegalArgumentException.class, () -> as.getWorkedTimePerProject(Month.JULY,"  "));
	}

	@Test
	void testKO_getWorkedTimePerProjectByDate() {
		var as = new AnnualTimeSheet(Year.of(2024));
		assertThrows(NullPointerException.class, () -> as.getWorkedTimePerProject((LocalDate)null, "project"));
		assertThrows(NullPointerException.class, () -> as.getWorkedTimePerProject(Month.JULY, null));
		assertThrows(IllegalArgumentException.class, () -> as.getWorkedTimePerProject(Month.JULY,"  "));
	}

	
	@Test
	void testKO_getWorkedTime_Month() {
		var as = new AnnualTimeSheet(Year.of(2024));
		assertThrows(NullPointerException.class, () -> as.getWorkedTime((Month)null));
	}
	
	@Test
	void testKO_getWorkedTime_LocalDate() {
		var as = new AnnualTimeSheet(Year.of(2024));
		assertThrows(NullPointerException.class, () -> as.getWorkedTime((LocalDate)null));
	}

}
	