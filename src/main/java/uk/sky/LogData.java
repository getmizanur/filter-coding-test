package uk.sky;

public class LogData {
	
	private long requestTimestamp;
	private String countryCode;
	private long responseTime;

	public long getRequestTimestamp() {
		return requestTimestamp;
	}
	public void setRequestTimestamp(long requestTimestamp) {
		this.requestTimestamp = requestTimestamp;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public long getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	@Override
	public String toString() {
		return "LogData [requestTimestamp=" + requestTimestamp + ", countryCode=" + countryCode + ", responseTime="
				+ responseTime + "]";
	}
	
}
