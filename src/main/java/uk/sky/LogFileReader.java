package uk.sky;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class LogFileReader implements DataFilterer<LogData>{
	
	private final int REQUEST_TIMESTAMP = 0;
	private final int COUNTRY_CODE = 1;
	private final int RESPONSE_TIME = 2;

	public Collection<LogData> filterByCountry(Reader source, String country) {
		return this.readCsvFile(source)
				.stream()
				.filter(row -> row.getCountryCode().equals(country))
				.collect(Collectors.toList());
	}
	
	public Collection<LogData> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {
		Collection<LogData> data = this.filterByCountry(source, country);
		if(data.isEmpty()) {
			return Collections.emptyList();
		}

		return data.stream()
				.filter(row -> row.getResponseTime() > limit)
				.collect(Collectors.toList());
	}
	
	public Collection<LogData> filterByResponseTimeAboveAverage(Reader source) {
		List<LogData> records = this.readCsvFile(source);
		long average = records.stream().mapToLong(row -> row.getResponseTime()).sum() / records.size();

		return records.stream()
				.filter(row -> row.getResponseTime() > average)
				.collect(Collectors.toList());
	}
	
	protected List<LogData> readCsvFile(Reader fileReader) {
		CSVParser csvParser = null;
		CSVFormat csvFormat = CSVFormat.DEFAULT
				.withFirstRecordAsHeader()
				.withIgnoreHeaderCase()
				.withTrim();
		
		List<LogData> rowSet = new ArrayList<LogData>();
		try {
			csvParser = new CSVParser(fileReader, csvFormat);
			List<CSVRecord> csvRecords = csvParser.getRecords();
			for(CSVRecord record : csvRecords) {
				LogData data = new LogData();
				data.setRequestTimestamp(Long.valueOf(record.get(REQUEST_TIMESTAMP)));
				data.setCountryCode(record.get(COUNTRY_CODE));
				data.setResponseTime(Long.valueOf(record.get(RESPONSE_TIME)));
				rowSet.add(data);
			}
		}
		catch(Exception ex) {
			System.out.println("Error in LogFileReader!!!");
			ex.printStackTrace();
		}
		finally {
		    try {
				fileReader.close();
				csvParser.close();
			}
		    catch(IOException ioex) {
		    	System.out.println("Error in closing resources!");
		    	ioex.printStackTrace();
			}
		}
		
		return rowSet;
	}
}
