package timesheet.controller.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import timesheet.controller.Controller;
import timesheet.controller.SiNo;


class ControllerTest {
	
	private static Map<String,Integer> projects;

	@BeforeAll
	static void setup() {
		projects = Map.of("ProgettoA", 900, "ProgettoB", 600, "ProgettoC", 400);
	}
	
	@Test 
	void testOK_Ctor2() {
		var controller = new Controller(projects, Year.of(2024));
		assertSame(projects, controller.getProjects());
		assertEquals(List.of("ProgettoA", "ProgettoB", "ProgettoC"), controller.getProjectNames());
		assertEquals(SiNo.NO, controller.isSundayWorkingDay());
		assertEquals(SiNo.NO, controller.isSaturdayWorkingDay());
		assertFalse(controller.isSundayWorkingDay().getValue());
		assertFalse(controller.isSaturdayWorkingDay().getValue());
		assertEquals(8, controller.getMaxHoursPerDay());
		assertEquals(0, controller.getTotalWorkedTimePerProject("ProgettoA"));
		assertEquals(0, controller.getTotalWorkedTimePerProject("ProgettoB"));
		assertEquals(0, controller.getTotalWorkedTimePerProject("ProgettoC"));
	}
	
	@Test 
	void testOK_Ctor5() {
		var controller = new Controller(projects, true, true, 6, Year.of(2024));
		assertSame(projects, controller.getProjects());
		assertEquals(List.of("ProgettoA", "ProgettoB", "ProgettoC"), controller.getProjectNames());
		assertEquals(SiNo.SI, controller.isSundayWorkingDay());
		assertEquals(SiNo.SI, controller.isSaturdayWorkingDay());
		assertTrue(controller.isSundayWorkingDay().getValue());
		assertTrue(controller.isSaturdayWorkingDay().getValue());
		assertEquals(6, controller.getMaxHoursPerDay());
		assertEquals(0, controller.getTotalWorkedTimePerProject("ProgettoA"));
		assertEquals(0, controller.getTotalWorkedTimePerProject("ProgettoB"));
		assertEquals(0, controller.getTotalWorkedTimePerProject("ProgettoC"));
	}
	
	@Test 
	void testOK_Getters() {
		var controller = new Controller(projects, Year.of(2024));
		assertSame(projects, controller.getProjects());
		assertEquals(List.of("ProgettoA", "ProgettoB", "ProgettoC"), controller.getProjectNames());
		for (Month month : Month.values()) {
			assertEquals(0, controller.getWorkedTime(month));
			assertEquals(0, controller.getWorkedTimePerProject(month, "ProgettoA"));
			assertEquals(0, controller.getWorkedTimePerProject(month, "ProgettoB"));
			assertEquals(0, controller.getWorkedTimePerProject(month, "ProgettoC"));
		}
		assertEquals(2024, controller.getYear().getValue());
	}
	
	@Test 
	void testOK() {
		var controller = new Controller(projects, Year.of(2024));
		assertSame(projects, controller.getProjects());
		assertEquals(List.of("ProgettoA", "ProgettoB", "ProgettoC"), controller.getProjectNames());
		
		controller.setWorkedTimePerProject(LocalDate.of(2024,12,10), 120, "ProgettoA");
		controller.setWorkedTimePerProject(LocalDate.of(2024,12,11), 140, "ProgettoA");
		controller.setWorkedTimePerProject(LocalDate.of(2024,12,12),  90, "ProgettoB");
		controller.setWorkedTimePerProject(LocalDate.of(2024,12,12), 150, "ProgettoC");
		controller.setWorkedTimePerProject(LocalDate.of(2024,12,10), 120, "ProgettoC");
		controller.setWorkedTimePerProject(LocalDate.of(2024,12,22), 120, "ProgettoB");
		
		for (Month month : Month.values())
			if (month!=Month.DECEMBER) {
				assertEquals(0, controller.getWorkedTime(month));
				assertEquals(0, controller.getWorkedTimePerProject(month, "ProgettoA"));
				assertEquals(0, controller.getWorkedTimePerProject(month, "ProgettoB"));
				assertEquals(0, controller.getWorkedTimePerProject(month, "ProgettoC"));
			}
		assertEquals(740, controller.getWorkedTime(Month.DECEMBER));
		assertEquals(260, controller.getWorkedTimePerProject(Month.DECEMBER, "ProgettoA"));
		assertEquals(210, controller.getWorkedTimePerProject(Month.DECEMBER, "ProgettoB"));
		assertEquals(270, controller.getWorkedTimePerProject(Month.DECEMBER, "ProgettoC"));
	}
	
	
	// ------ test KO

	@Test
	void testKO_CtorArg1Null() {
		assertThrows(NullPointerException.class, () -> new Controller(null, Year.of(2024)));
	}
	
	@Test
	void testKO_CtorArg5Null() {
		assertThrows(NullPointerException.class, () -> new Controller(projects, null));
	}
	
	@Test
	void testKO_CtorArg4Wrong_Zero() {
		assertThrows(IllegalArgumentException.class, () -> new Controller(projects, true, true, 0, Year.of(2024)));
	}
	
	@Test
	void testKO_CtorArg4Wrong_Neg() {
		assertThrows(IllegalArgumentException.class, () -> new Controller(projects, true, true, -2, Year.of(2024)));
	}
	
	@Test
	void testKO_getMaxHoursPerProject_Arg1Null() {
		assertThrows(NullPointerException.class, () -> new Controller(projects, Year.of(2024)).getMaxHoursPerProject(null));
	}
	
	@Test
	void testKO_getMaxHoursPerProject_Arg1Blank() {
		assertThrows(IllegalArgumentException.class, () -> new Controller(projects, Year.of(2024)).getMaxHoursPerProject(" "));
	}

	@Test
	void testKO_getTotalWorkedTimePerProject_Arg1Null() {
		assertThrows(NullPointerException.class, () -> new Controller(projects, Year.of(2024)).getTotalWorkedTimePerProject(null));
	}
	
	@Test
	void testKO_getTotalWorkedTimePerProject_Arg1Blank() {
		assertThrows(IllegalArgumentException.class, () -> new Controller(projects, Year.of(2024)).getTotalWorkedTimePerProject(" "));
	}
	
	@Test
	void testKO_getWorkedTimePerProject_Arg2Blank() {
		assertThrows(IllegalArgumentException.class, () -> new Controller(projects, Year.of(2024)).getWorkedTimePerProject(Month.JUNE, " "));
	}
	
	@Test
	void testKO_getWorkedTimePerProject_Arg2Null() {
		assertThrows(NullPointerException.class, () -> new Controller(projects, Year.of(2024)).getWorkedTimePerProject(Month.JUNE, null));
	}

	@Test
	void testKO_getWorkedTimePerProjectByMonth_Arg1Null() {
		assertThrows(NullPointerException.class, () -> new Controller(projects, Year.of(2024)).getWorkedTimePerProject((Month)null, "ProgettoA"));
	}
	
	@Test
	void testKO_getWorkedTimePerProjectByDate_Arg1Null() {
		assertThrows(NullPointerException.class, () -> new Controller(projects, Year.of(2024)).getWorkedTimePerProject((LocalDate)null, "ProgettoA"));
	}
	
	@Test
	void testKO_setWorkedTimePerProject_Arg1Null() {
		assertThrows(NullPointerException.class, () -> new Controller(projects, Year.of(2024)).setWorkedTimePerProject(null, 60, "ProgettoB"));
	}
	
	@Test
	void testKO_setWorkedTimePerProject_Arg2Neg() {
		assertThrows(IllegalArgumentException.class, () -> new Controller(projects, Year.of(2024)).setWorkedTimePerProject(LocalDate.of(2024,12,12), -1, "ProgettoB"));
	}

	@Test
	void testKO_setWorkedTimePerProject_Arg3Nullo() {
		assertThrows(NullPointerException.class, () -> new Controller(projects, Year.of(2024)).setWorkedTimePerProject(LocalDate.of(2024,12,12), 60, null));
	}
	
	@Test
	void testKO_setWorkedTimePerProject_Arg3Vuoto() {
		assertThrows(IllegalArgumentException.class, () -> new Controller(projects, Year.of(2024)).setWorkedTimePerProject(LocalDate.of(2024,12,12), 60, " "));
	}
	
}
