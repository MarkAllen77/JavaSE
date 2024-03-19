package SourcePages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import org.apache.commons.lang3.time.StopWatch;

import JavaMain.JavaSelenium;

public class TimersPage {
	StopWatch watch = new StopWatch();
	
	public void TimersPageStart() {
		watch.start();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
		LocalDateTime now = LocalDateTime.now();  			
		JavaSelenium.logger.info("Start Execution: " + dtf.format(now));
	}
	
	public void TimersPageStop() {
	    watch.stop();
	    DateFormat df = new SimpleDateFormat("HH 'hours', mm 'mins', ss 'seconds', SSS 'ms'");
		df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		String elapsedTime = df.format(watch.getTime());			
		JavaSelenium.logger.info("Time Elapsed: " + elapsedTime);
	}
}
