package uk.sky;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class LogDataTest {

	public LogFileReader logFileReader;
	public FileReader fileReader;

	@BeforeEach
	public void setUp() {
		try {
			fileReader = new FileReader("src/test/resources/data.csv");
			logFileReader = new LogFileReader();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@AfterEach
	public void tearDown() {
		try {
			fileReader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testToString() {
		Collection<LogData> result = logFileReader.filterByCountry(fileReader, "US");
		Iterator<LogData> iterator = result.iterator();

		LogData row = null;
		if (iterator.hasNext())
			row = iterator.next();
		else
			row = new LogData();

		assertEquals(1433190845, row.getRequestTimestamp());
		assertEquals("US", row.getCountryCode());
		assertEquals(539, row.getResponseTime());

		assertEquals("LogData [requestTimestamp=1433190845, countryCode=US, responseTime=539]"
				, row.toString());

	}
}
