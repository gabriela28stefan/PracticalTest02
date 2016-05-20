package ro.pub.cs.systems.eim.practicaltest02;

public class TimeInformation {
	
	
	private String hour;
	private String minute;
	private String seconds;
	
	private String time;
	
	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getSeconds() {
		return seconds;
	}

	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public TimeInformation(
			String hour,
			String minute,
			String seconds){
		this.hour = hour;
		this.minute = minute;
		this.seconds = seconds;
	}
	

	public TimeInformation(
			String time){
		this.time = time;
	}
	
	
    @Override
    public String toString() {
        return Constants.TEMPERATURE + ": " + hour + "\n\r" +
                Constants.WIND_SPEED + ": " + minute + "\n\r" +
                Constants.HUMIDITY + ": " + seconds;
    }
	
	
}
