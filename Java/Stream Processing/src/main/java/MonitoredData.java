import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class MonitoredData {

	private Date startTime;
	private Date endTime;
	private String activityLabel;	

	 public static void main(String [] args)
	 {
		 	List<MonitoredData> activity=new ArrayList<MonitoredData>();
		 	
	        String fileName = "Activities.txt";
	        String line = null;

	        try 
	        {
	        	//reading from the file given
	            FileReader fileReader = new FileReader(fileName);
	            BufferedReader bufferedReader = new BufferedReader(fileReader);
	            while((line = bufferedReader.readLine()) != null) 
	            {
	            	String format="yyyy-MM-dd HH:mm:ss";
	            	DateFormat formatter = new SimpleDateFormat(format);
	            	String date1=line.substring(0, 19);
	            	String date2=line.substring(21, 40);
	            	
	            	String act=line.substring(42, line.length());
	            	act=act.trim();
	            	
	            	Date startdate = (Date)formatter.parse(date1);
	            	Date enddate = (Date)formatter.parse(date2);
	            	//System.out.println(startdate+"       "+enddate);
	            	
	            	activity.add(new MonitoredData(startdate,enddate,act));
	            }  
	            
	            for(MonitoredData a: activity)
	            {
	            	//System.out.println(a.getStartTime()+" "+a.getEndTime()+" "+a.getActivityLabel());
        	    	//System.out.println(a.getStartTime().getTime()+" - "+a.getStartTime().getTime()+" = " +diff+ " = "+hours+"/"+hour);
	            }
    	    	
	            
	            //Ex1: counting distinct days
	            System.out.println("\nEx1:");
	            List<Integer> nrOfDays= activity
            			.stream()
                        .map(q->{
    	            	    Calendar cal=Calendar.getInstance(); cal.setTime(q.startTime); return cal.get(Calendar.DAY_OF_MONTH); })
                        .distinct()
                        .collect(Collectors.toList());
	            
	            System.out.println("The number of distinct days is: " + nrOfDays.size());
	            
	            //Ex2: to each distinct action type the number of occurrences in the log. each String(activity) -  nr of occurences in log
	            System.out.println("\nEx2:");
	            Map<String, Long> activityNumber = activity
	            	    .stream()
	            	    .collect(Collectors.groupingBy(s -> s.activityLabel, Collectors.counting()));
	            
	            String fileEx2="Ex2"+".txt";
	            BufferedWriter bwEx2=new BufferedWriter(new FileWriter(fileEx2));		
	            activityNumber
	            	    .forEach((activityLabel, numberOfApparitions) ->
	            	    {
	            	    	System.out.format("%s: %s\n", activityLabel, numberOfApparitions);
	            	    	try 
	            	    	{
								bwEx2.write(activityLabel+": "+numberOfApparitions);
								bwEx2.newLine();
							} catch (IOException e) 
	            	    	{
								System.out.println( "Error writing in "+fileEx2);  
							}
	            	    });
	            	   
	            bwEx2.close();

	            //Ex3:activity count for each day of the log (task number 2 applied for each day of the log) and writes the result in a text file
	            System.out.println("\nEx3:");
				Map<Object, Map<Object, Long>> activityDay = activity
	            	    .stream()
	            	    .collect(Collectors.groupingBy(q->{
	            	    Calendar cal=Calendar.getInstance(); cal.setTime(q.startTime); return cal.get(Calendar.DAY_OF_MONTH); }, Collectors.groupingBy(s -> s.activityLabel, Collectors.counting())));

	            String fileEx3="Ex3"+".txt";
	            BufferedWriter bwEx3=new BufferedWriter(new FileWriter(fileEx3));		
	            
	            activityDay
        	    	.forEach((day, activitiesOnDay) ->
        	    	{
        	    		System.out.format("%s: %s\n", day, activitiesOnDay);
            	    	try 
            	    	{
							bwEx3.write(day+": "+activitiesOnDay);
							bwEx3.newLine();
						} catch (IOException e) 
            	    	{
							System.out.println( "Error writing in "+fileEx3);  
						}
            	    });
	            bwEx3.close();

	            //Ex4: for each activity the total duration computed over the monitoring period. Filter the activities with total duration larger than 10 hours
	            System.out.println("\nEx4:");
	            Map<String, Long> activityDuration = activity
	            	    .stream()
	            	    .collect(Collectors.groupingBy(s -> s.activityLabel, Collectors.summingLong(q->{
	            	    	long diff = q.endTime.getTime()- q.startTime.getTime();
	            	    	return diff;
	            	    	})));
	            	
	            
	            String fileEx4="Ex4"+".txt";
	            BufferedWriter bwEx4=new BufferedWriter(new FileWriter(fileEx4));		
  
	            activityDuration
	            	    .forEach((activityLabel,timeInMilli) ->
	            	    {
	            	    	long timeInHours=TimeUnit.MILLISECONDS.toHours(timeInMilli);
	            	    	if(timeInHours>10)
	            	    		System.out.format("%s: %s\n", activityLabel, timeInHours);
		            	    try 
		            	    {
		            	    	if(timeInHours>10)
		            	    	{
		            	    		bwEx4.write(activityLabel+": "+ timeInHours);
		            	    		bwEx4.newLine();
		            	    	}
							} catch (IOException e) 
		            	    {
								System.out.println( "Error writing in "+fileEx4);  
							}
	            	    });

	            bwEx4.close();
	            
	            
	            //Ex5: Filter the activities that have 90% of the monitoring samples with duration less than 5 minutes, collect the results in a List<String> containing only the distinct activity names
	            System.out.println("\nEx5:");
	            Map<String, Map<Object, Long>> activityDurationPercent = activity
	            	    .stream()
	            	    .collect(Collectors.groupingBy(s -> s.activityLabel, Collectors.groupingBy(q->{
	            	    	long diff = q.endTime.getTime()- q.startTime.getTime();
	            	    	long minutes = TimeUnit.MILLISECONDS.toMinutes(diff); 
	            	    	return minutes;
	            	    	},Collectors.counting())));
	            	
	            
	            String fileEx5="Ex5"+".txt";
	            BufferedWriter bwEx5=new BufferedWriter(new FileWriter(fileEx5));		
  
	            List<String> activityUnder5Minutes=new ArrayList<>();
	            
	            activityDurationPercent
	            	    .forEach((activityLabel, time) ->
	            	    {
	            	    	//System.out.format("%s: %s\n", activityLabel,q);
		            	    Set<Object> minutesVector= time.keySet();
							long underCounter=0, overCounter=0;
							for(Object min: minutesVector)
							{
								Long nr = time.get(min);
								Long vali=(Long) min;
								if(vali>5)
									overCounter=overCounter+nr;
								else
									underCounter=underCounter+nr;
							}
							if(underCounter>=(overCounter+underCounter)*90/100)
							{
								System.out.format("%s: %s\n", activityLabel,time);
								activityUnder5Minutes.add(activityLabel);
							}
	            	    });
	            for(String a: activityUnder5Minutes)
	            {
	            	bwEx5.write(a+"");
	            	bwEx5.newLine();
	            }
	            bwEx5.close();
	            
	            bufferedReader.close();         
	        }
	        catch(FileNotFoundException ex) 
	        {
	            System.out.println( "Unable to open file '" +  fileName + "'");                
	        }
	        catch(IOException ex)
	        {
	            System.out.println( "Error reading file '" + fileName + "'");                  
	        } 
	        catch (ParseException e) 
	        {
	        	System.out.println( "Error parsing" ); 
			}
	 }


	public MonitoredData(Date startTime, Date endTime, String activityLabel) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.activityLabel = activityLabel;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startdate) {
		this.startTime = startdate;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public String getActivityLabel() {
		return activityLabel;
	}


	public void setActivityLabel(String activityLabel) {
		this.activityLabel = activityLabel;
	}
}
