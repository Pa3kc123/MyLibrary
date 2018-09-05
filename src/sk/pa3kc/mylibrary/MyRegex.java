package sk.pa3kc.mylibrary;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings ({ "WeakerAccess", "unused", "PointlessBooleanExpression" })
public class MyRegex
{
    /**
     * Checks if is match in input String by its pattern
     * @param input
     * String to check
     * @param pattern
     * Pattern used to check
     * @return True if match is found, else false
     */
    public static boolean isMatch(String input, String pattern)
    {
        return Pattern.compile(pattern).matcher(input).matches();
    }
    /**
     * Checks if is match in input String by its pattern
     * @param input
     * String to check
     * @param pattern
     * Pattern used to check
     * @param options
     * Additional regex options (Multiple options need to be divided by '|')
     * @return True if match is found, else false
     */
    public static boolean isMatch(String input, String pattern, int options)
    {
        return Pattern.compile(pattern, options).matcher(input).matches();
    }
    /**
     * Checks if is match in input String by its pattern
     * @param input
     * String to check
     * @param pattern
     * Pattern used to check
     * @return True if match is found, else false
     */
    public static boolean isMatch(String input, Pattern pattern)
    {
        return isMatch(input, pattern.pattern());
    }
    /**
     * Checks if is match in input String by its pattern
     * @param input
     * String to check
     * @param pattern
     * Pattern used to check
     * @param options
     * Additional regex options (Multiple options need to be divided by '|')
     * @return True if match is found, else false
     */
    public static boolean isMatch(String input, Pattern pattern, int options)
    {
        return isMatch(input, pattern.pattern(), options);
    }
    /**
     * If in input is found match by pattern, that part of input will be replaced by replacement
     * @param input
     * String to check
     * @param pattern
     * Pattern used to check
     * @param replacement
     * String chosen as replacement
     */
    public static void Replace(String input, Pattern pattern, String replacement)
    {
        String newline = "\r\n";
        System.out.print("Feature not implemented yet..." + newline);
    }
    /**
     * If in input is found match by pattern, that part of input will be replaced by replacement
     * @param input
     * String to check
     * @param pattern
     * Pattern used to check
     * @param replacement
     * String chosen as replacement
     */
    public static void Replace(String input, String pattern, String replacement)
    {
        Replace(input, Pattern.compile(pattern), replacement);
    }
    /**
     * If in input are found matches by pattern, that parts will be returned as String[] starting from 0
     * @param input
     * String to check
     * @param pattern
     * Patter used to check
     * @return 
     * String array with matches
     */
    public static String[] Matches(String input, Pattern pattern)
    {
        return Matches(input, pattern.pattern());
    }
    /**
     * If in input are found matches by pattern, that parts will be returned as String[] starting from 0
     * @param input
     * String to check
     * @param pattern
     * Patter used to check
     * @return 
     * String array with matches
     */
    public static String[] Matches(String input, String pattern)
    {
        Matcher matcher = Pattern.compile(pattern).matcher(input);
        String[] resultArray = new String[matcher.groupCount()];
        short j = 0;
        
        while (matcher.find() == true)
        {
            for (short i = 1; i <= matcher.groupCount(); i++)
            {
                resultArray[j++] = matcher.group(i);
            }
        }
        
        return resultArray;
    }
    /**
     * If in input are found matches by pattern, that parts will be returned as String[] starting from 0
     * @param input
     * String to check
     * @param pattern
     * Patter used to check
     * @param options
     * Additional regex options (Multiple options need to be divided by '|')
     * @return 
     * String array with matches
     */
    public static String[] Matches(String input, Pattern pattern, int options)
    {
        return Matches(input, pattern.pattern(), options);
    }
    /**
     * If in input are found matches by pattern, that parts will be returned as String[] starting from 0
     * @param input
     * String to check
     * @param pattern
     * Patter used to check
     * @param options
     * Additional regex options (Multiple options need to be divided by '|')
     * @return 
     * String array with matches
     */
    public static String[] Matches(String input, String pattern, int options)
    {
        Matcher matcher = Pattern.compile(pattern, options).matcher(input);
        String[] resultArray = new String[matcher.groupCount()];
        short j = 0;

        while (matcher.find() == true)
        {
            for (short i = 1; i <= matcher.groupCount(); i++)
            {
                resultArray[j++] = matcher.group(i);
            }
        }
        
        return resultArray;
    }
}
