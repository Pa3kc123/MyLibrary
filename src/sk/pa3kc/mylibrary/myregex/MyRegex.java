package sk.pa3kc.mylibrary.myregex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sk.pa3kc.mylibrary.DefaultSystemPropertyStrings;

public class MyRegex
{
    /**
     * Checks if is match in input String by its pattern
     * @param input String to check
     * @param pattern Pattern used to check
     * @return True if match is found, else false
     */
    public static boolean isMatch(String input, String pattern)
    {
        return Pattern.compile(pattern).matcher(input).matches();
    }
    /**
     * Checks if is match in input String by its pattern
     * @param input String to check
     * @param pattern Pattern used to check
     * @param options Additional regex options
     * @return True if match is found, else false
     */
    public static boolean isMatch(String input, String pattern, int... options)
    {
        int optionCode = 0;
        for (int option : options)
            optionCode |= option;

        return Pattern.compile(pattern, optionCode).matcher(input).matches();
    }
    /**
     * Checks if is match in input String by its pattern
     * @param input String to check
     * @param pattern Pattern used to check
     * @return True if match is found, else false
     */
    public static boolean isMatch(String input, Pattern pattern)
    {
        return isMatch(input, pattern.pattern());
    }
    /**
     * Checks if is match in input String by its pattern
     * @param input String to check
     * @param pattern Pattern used to check
     * @param options Additional regex options
     * @return True if match is found, else false
     */
    public static boolean isMatch(String input, Pattern pattern, int... options)
    {
        return isMatch(input, pattern.pattern(), options);
    }
    /**
     * If in input is found match by pattern, that part of input will be replaced by replacement
     * @param input String to check
     * @param pattern Pattern used to check
     * @param replacement String chosen as replacement
     */
    public static void Replace(String input, Pattern pattern, String replacement)
    {
        System.out.print("Feature not implemented yet..." + DefaultSystemPropertyStrings.LINE_SEPARATOR);
    }
    /**
     * If in input is found match by pattern, that part of input will be replaced by replacement
     * @param input String to check
     * @param pattern Pattern used to check
     * @param replacement String chosen as replacement
     */
    public static void Replace(String input, String pattern, String replacement)
    {
        Replace(input, Pattern.compile(pattern), replacement);
    }
    /**
     * If in input are found matches by pattern, that parts will be returned as String[] starting from 0
     * @param input String to check
     * @param pattern Patter used to check
     * @return String array with matches
     */
    public static String[] Matches(String input, Pattern pattern)
    {
        return Matches(input, pattern.pattern());
    }
    /**
     * If in input are found matches by pattern, that parts will be returned as String[] starting from 0
     * @param input String to check
     * @param pattern Patter used to check
     * @return String array with matches
     */
    public static String[] Matches(String input, String pattern)
    {
        Matcher matcher = Pattern.compile(pattern).matcher(input);
        String[] resultArray = new String[matcher.groupCount()];
        short j = 0;

        while (matcher.find() == true)
        for (short i = 1; i <= matcher.groupCount(); i++)
            resultArray[j++] = matcher.group(i);

        return resultArray;
    }
    /**
     * If in input are found matches by pattern, that parts will be returned as String[] starting from 0
     * @param input String to check
     * @param pattern Patter used to check
     * @param options Additional regex options
     * @return String array with matches
     */
    public static String[] Matches(String input, Pattern pattern, int... options)
    {
        return Matches(input, pattern.pattern(), options);
    }
    /**
     * If in input are found matches by pattern, that parts will be returned as String[] starting from 0
     * @param input String to check
     * @param pattern Patter used to check
     * @param options Additional regex options
     * @return String array with matches
     */
    public static String[] Matches(String input, String pattern, int... options)
    {
        int optionsCode = 0;
        for (int option : options) optionsCode |= option;

        Matcher matcher = Pattern.compile(pattern, optionsCode).matcher(input);
        String[] resultArray = new String[matcher.groupCount()];
        short j = 0;

        while (matcher.find() == true)
        for (short i = 1; i <= matcher.groupCount(); i++)
            resultArray[j++] = matcher.group(i);

        return resultArray;
    }
}
