/**
 * Write a description of CountTester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class CountTester
{
    public void testCounts() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("short-test_log");
        HashMap<String,Integer> counts = la.countVisitsPerIP();
        System.out.println(counts);
    }
    
    public void testMostNumverVisitsByIP() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        int count = la.mostNumberVisitsByIP(la.countVisitsPerIP());
        System.out.println(count);
    }
    
    public void testIpsMostVisits() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        System.out.println(la.iPsMostVisits(la.countVisitsPerIP()));
    }
    
    public void testDays() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        System.out.println(la.iPsForDays());
    }
    
    public void quizQ1() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        System.out.println(la.mostNumberVisitsByIP(la.countVisitsPerIP()));
    }
    
    public void quizQ2() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        System.out.println(la.iPsMostVisits(la.countVisitsPerIP()));
    }
    
    public void quizQ3() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        System.out.println(la.dayWithMostIPVisits(la.iPsForDays()));
    }
    
    public void quizQ4() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        System.out.println(la.iPsWithMostVisitsOnDay(la.iPsForDays(), "Mar 17"));
    }
    
    public void testDayWithMostIpVisits() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        System.out.println(la.dayWithMostIPVisits(la.iPsForDays()));
    }
    
    public void testIpsWithMostVisitsOnDay() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        System.out.println(la.iPsWithMostVisitsOnDay(la.iPsForDays(), "Sep 30"));
    }
}