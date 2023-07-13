import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class RegularExpressionTest {

    String logValue = "13:05:10.957 [http-nio-8500-exec-7] INFO  c.s.s.controller.SampleController - Employee(firstname=Urmila, lastname=Arora, emailId=fnhy03@gmail.com, addresses=[Address(addressLine1=217 Gandhi Stream, addressLine2=null, city=Mumbai, zip=613182, country=IN)])";
    private static List<String> maskPatterns = new ArrayList<>();
    private static Pattern multilinePattern;

    String regex = "(Geeks)";
    String stringToBeMatched
            = " GeeksForGeeks Geeks for For Geeks Geek";
    @BeforeAll
    static void setup() {
        System.out.println("@BeforeAll - executes once before all test methods in this class");
        maskPatterns.add("lastname\\s*=\\s*\\w*");
        multilinePattern = Pattern.compile(maskPatterns.stream().collect(Collectors.joining("|")), Pattern.MULTILINE);
    }

    @BeforeEach
    void init() {
        System.out.println("@BeforeEach - executes before each test method in this class");
    }

    @DisplayName("Single test successful")
    @Test
    void StringReplaceRegExTest() {
        StringBuilder sb = new StringBuilder(logValue);
        Matcher matcher = multilinePattern.matcher(sb);
        while (matcher.find()) {
            System.out.println("***");
            IntStream.rangeClosed(1, matcher.groupCount()).forEach(group -> {
                if (matcher.group(group) != null) {
                    IntStream.range(matcher.start(group), matcher.end(group)).forEach(i -> {
                        System.out.println(sb.toString());
                        sb.setCharAt(i, '*');
                    });
                }
            });
        }
        System.out.println(sb.toString());
    }


    @Test
    void testGroupCount() {
        Pattern pattern = Pattern.compile(regex);
        MatchResult matcher = pattern.matcher(stringToBeMatched);
        System.out.println(matcher.groupCount());
    }

    @Test
    void groupTest(){
        String regex = "(.*)(\\d+)(.*)";
        String input = "This is a sample Text, 1234, with numbers in between.";
        //Creating a pattern object
        Pattern pattern = Pattern.compile(regex);
        //Matching the compiled pattern in the String
        Matcher matcher = pattern.matcher(input);
        if(matcher.find()) {
            System.out.println("First group match: "+matcher.group(1));
            System.out.println("Second group match: "+matcher.group(2));
            System.out.println("Third group match: "+matcher.group(3));
            System.out.println("Number of groups capturing: "+matcher.groupCount());
        }
    }


}
