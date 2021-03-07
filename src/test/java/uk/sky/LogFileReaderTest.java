package uk.sky;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class LogFileReaderTest {

	public LogFileReader logFileReader;
	public FileReader fileReader;

	@BeforeEach
	public void setUp() {
		try {
			fileReader = new FileReader("src/test/resources/data.csv");
			logFileReader = new LogFileReader();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	@AfterEach
	public void tearDown() {
		try {
			fileReader.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testFilterByCountry() {
		Collection<LogData> result = logFileReader.filterByCountry(fileReader, "US");
		Iterator<LogData> iterator = result.iterator();

		LogData row = null;
		if(iterator.hasNext())
			row = iterator.next();
		else
			row = new LogData();

		assertEquals(1433190845, row.getRequestTimestamp());
		assertEquals("US", row.getCountryCode());
		assertEquals(539, row.getResponseTime());

		assertEquals("LogData [requestTimestamp=1433190845, countryCode=US, responseTime=539]"
				, row.toString());
	}

	@Test
	public void testFilterByCountry_FileNotFound() {
		assertThrows(FileNotFoundException.class
				, () -> {Collection<LogData> result = logFileReader.filterByCountry(new FileReader("src/test/resources/notfound"), "US");});

	}

	@Test
	public void testFilterByCountry_GB() {
		Collection<LogData> result = logFileReader.filterByCountry(fileReader, "GB");
		Iterator<LogData> iterator = result.iterator();

		LogData row = null;
		if(iterator.hasNext())
			row = iterator.next();
		else
			row = new LogData();

		assertEquals(1432917066, row.getRequestTimestamp());
		assertEquals("GB", row.getCountryCode());
		assertEquals(37, row.getResponseTime());

	}

	@Test
	public void testFilterByCountry_RecordNotFound() {
		Collection<LogData> result = logFileReader.filterByCountry(fileReader, "FR");
		Iterator<LogData> iterator = result.iterator();

		LogData row = null;
		if(iterator.hasNext())
			row = iterator.next();
		else
			row = new LogData();

		assertEquals(0, row.getRequestTimestamp());
		assertNull(row.getCountryCode());
		assertEquals(0, row.getResponseTime());
	}

	@Test
	public void testFilterByCountryWithResponseTimeAboveLimit(){
		Collection<LogData> result = logFileReader.filterByCountryWithResponseTimeAboveLimit(
				fileReader, "GB", 36);
		Iterator<LogData> iterator = result.iterator();

		LogData row = null;
		if(iterator.hasNext())
			row = iterator.next();
		else
			row = new LogData();

		assertEquals(1432917066, row.getRequestTimestamp());
		assertEquals("GB", row.getCountryCode());
		assertEquals(37, row.getResponseTime());
	}

	@Test
	public void testFilterByCountryWithResponseTimeAboveLimit_RecordNotFound() {
		Collection<LogData> result = logFileReader.filterByCountryWithResponseTimeAboveLimit(
				fileReader, "GB", 47);
		Iterator<LogData> iterator = result.iterator();

		LogData row = null;
		if(iterator.hasNext())
			row = iterator.next();
		else
			row = new LogData();

		assertEquals(0, row.getRequestTimestamp());
		assertNull(row.getCountryCode());
		assertEquals(0, row.getResponseTime());
	}

	@Test
	public void testFilterByCountryWithResponseTimeAboveLimit_EmptyFile() throws IOException{
		Collection<LogData> result = logFileReader.filterByCountryWithResponseTimeAboveLimit(
				new FileReader("src/test/resources/empty.csv"), "FR", 36);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testFilterByResponseTimeAboveAverage() {
		Collection<LogData> result
				= logFileReader.filterByResponseTimeAboveAverage(fileReader);
		Iterator<LogData> iterator = result.iterator();

		LogData row = null;
		if(iterator.hasNext())
			row = iterator.next();
		else
			row = new LogData();

		assertEquals(1433190845, row.getRequestTimestamp());
		assertEquals("US", row.getCountryCode());
		assertEquals(539, row.getResponseTime());
	}

	@Test
	public void testReadCsvFile() throws IOException  {
		List<LogData> resultSet = logFileReader.readCsvFile(fileReader);
		Iterator<LogData> iterator = resultSet.iterator();

		LogData row = iterator.next();
		assertEquals(1433190845, row.getRequestTimestamp());
		assertEquals("US", row.getCountryCode());
		assertEquals(539, row.getResponseTime());
	}

	@Test
	public void testReadCsvFile_IOException() {
		assertThrows(IOException.class
				, () ->
					(new LogFileReader()).readCsvFile(
							new FileReader("src/test/resources/notfound"))
		);
	}
}
