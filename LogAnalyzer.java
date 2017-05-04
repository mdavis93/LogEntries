
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
    private ArrayList<LogEntry> records;
    
    public LogAnalyzer() {
        records = new ArrayList<LogEntry>();
    }
    
    public void readFile(String filename) {
        FileResource resource = new FileResource(filename);
        for (String s : resource.lines()) {
            LogEntry entry = WebLogParser.parseEntry(s);
            records.add(entry);
        }
    }
    
    private String getDayPart(String s) {
        return s.split(" ")[1] + " " + s.split(" ")[2];
    }
    
    private String getDayPart(String s, int i) {
        // 0: Weekday
        // 1: Month
        // 2: Day
        // 3: Time
        // 4: Timezone
        // 5: Year
        return s.split(" ")[i];
    }
   
    public HashMap<String, Integer> countVisitsPerIP() {
        // Make an empty HashMap<String, Integer> counts
        HashMap<String,Integer> counts = new HashMap<String,Integer>();
        // For each le in records
        for (LogEntry le : records) {
            // ip is le's ipAddress
            String ip = le.getIpAddress();
            // check if ip is in counts
            if (!counts.containsKey(ip)) {
                // If not: put ip in with a value of 1
                counts.put(ip, 1);
            } else {
                // If so: update the value to be +1
                counts.put(ip, counts.get(ip) + 1);
            }
        }
        // counts is the answer
        return counts;
    }
    
    public int countUniqueIPs() {
        // uniqueIPs start as an empty list
        ArrayList<String> uniqueIPs = new ArrayList<String>();
        //   for each element le in records
        for (LogEntry le : records) {
            // ipAddr s le's ipAddress
            String ipAddr = le.getIpAddress();
            // if ipAddr is not in uniqueIPs
            if(!uniqueIPs.contains(ipAddr)) {
                // add ipAddr to uniqueIPs
                uniqueIPs.add(ipAddr);
            }
        }
        
        // return the size of uniqueIPs
        return uniqueIPs.size();
    }
    
    public ArrayList<String> uniqueIPVisitsOnDay(String someday) {
        // someday format: MMM DD
        // MMM = First three characters of the month name, with the first char capitalized
        // DD  = Two digit day, such as 04 and 23
        // returns an ArrayList<String> containing the uniqueIPs for that day.
        
        String month = someday.split(" ")[0];
        String day = someday.split(" ")[1];
        ArrayList<String> uniqueIPs = new ArrayList<String>();
        
        for (LogEntry le : records) {
            String ip = le.getIpAddress();
            
            String date = le.getAccessTime().toString();
            String leMonth = getDayPart(date, 1);
            String leDay = getDayPart(date, 2);
                
            if ( leMonth.equals(month) ) {
                if ( leDay.equals(day) ) {
                    if (!uniqueIPs.contains(ip)) {
                        uniqueIPs.add(ip);
                    }
                }
            }
        }
        
        return uniqueIPs;
    }
    
    public int countUniqueIPsInRange(int low, int high) {
        ArrayList<String> uniqueIPs = new ArrayList<String>();
        
        for (LogEntry le : records) {
            int code = le.getStatusCode();
            String ip = le.getIpAddress();
            
            if ( code >= low && code <= high ) {
                if ( !uniqueIPs.contains(ip) ) {
                    uniqueIPs.add(ip);
                }
            }
        }
        
        return uniqueIPs.size();
    }
    
    public int mostNumberVisitsByIP(HashMap<String,Integer> map) {
        // This method returns the maximum number of visits to this website by a single IP address.
        int count = 0;
        
        for ( String ip : map.keySet() ) {
            if (map.get(ip) > count) {
                count = map.get(ip);
            }
        }
        return count;
    }
    
    public ArrayList<String> iPsMostVisits(HashMap<String,Integer> map) {
        // This method returns an ArrayList of Strings of IP addresses that all have
        // the maximum number of visits to this website. The call iPsMostVisits
        // on a HashMap formed using the file weblog3-short_log returns the ArrayList with
        // these two IP addresses: 61.15.121.171 and 84.133.195.161. Both of them visited the
        // site three times, which is the maximum number of times any IP address visited the site.
        ArrayList<String> ipList = new ArrayList<String>();
        int maxVisits = mostNumberVisitsByIP(map);
        
        for (String ip : map.keySet()) {
            if (map.get(ip) == maxVisits) {
                ipList.add(ip);
            }
        }
        return ipList;
    }
    
    public HashMap<String,ArrayList<String>> iPsForDays() {
        // This method returns a HashMap that maps days from web logs to an ArrayList
        // of IP addresses that occurred on that day (including repeated IP addresses).
        HashMap<String,ArrayList<String>> ipHash = new HashMap<String,ArrayList<String>>();
        
        for (LogEntry le : records) {
            // getDayPart with one argument returns the MMM DD day
            String day = getDayPart(le.getAccessTime().toString());
            
            if (ipHash.containsKey(day)) {
                ArrayList<String> list = ipHash.get(day);
                list.add(le.getIpAddress());
                ipHash.put(day, list);
            } else {
                ArrayList<String> list = new ArrayList<String>();
                list.add(le.getIpAddress());
                ipHash.put(day, list);
            }
        }
        return ipHash;
    }
    
    public String dayWithMostIPVisits(HashMap<String,ArrayList<String>> map) {
        // This method returns the day that has the most IP address visits.
        String day = "None";
        int count = 0;
        
        for (String key : map.keySet()) {
            if (map.get(key).size() > count) {
                day = key;
                count = map.get(key).size();
            }
        }
        
        return day;
    }
    
    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String,ArrayList<String>> map, String day) {
        // This method returns an ArrayList<String> of IP addresses
        // that had the most accesses on the given day.
        
        ArrayList<String> res = new ArrayList<String>();
        HashMap<String,Integer> count = new HashMap<String,Integer>();
        
        for (String k : map.keySet()) {
            if ( k.equals(day) ) {
                ArrayList<String> ipAddresses = map.get(k);
                
                for (String s : ipAddresses) {
                    if (count.containsKey(s)) {
                        count.put(s, count.get(s) + 1);
                    } else {
                        count.put(s, 1);
                    }
                }
            }
        }
        
        return iPsMostVisits(count);
    }
    
    public void printAll() {
        for (LogEntry le : records) {
            System.out.println(le);
        }
    }
    
    public void printAllHigherThanNum(int num) {
        for (LogEntry le : records) {
            if( le.getStatusCode() > num ) {
                System.out.println(le);
            }
        }
    }
}
