package sk.pa3kc.mylibrary.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgsParser {
    private static final Pattern PATTERN = Pattern.compile("--(.*?)=(.*)");

    private String[] args;
    private HashMap<String, String> options = new HashMap<String, String>();

    public ArgsParser(String... args) {
        final ArrayList<String> argsList = new ArrayList<String>();

        boolean doneWithOptions = false;

        for (final String arg : args) {
            if (doneWithOptions) {
                argsList.add(arg);
                continue;
            }

            if ("--".equals(arg)) {
                doneWithOptions = true;
                continue;
            }

            if ("--".equals(arg.substring(0, 2))) {
                final Matcher matcher = PATTERN.matcher(arg);

                if (matcher.find() && matcher.groupCount() == 2) {
                    final String key = matcher.group(1);

                    if (key == null) {
                        this.options.put(arg.substring(2), "true");
                    } else {
                        this.options.put(key, matcher.group(2));
                    }
                }
            }

            if ("-".equals(arg.substring(0, 1)) && !"-".equals(arg)) {
                for (int i = 1; i < arg.length(); i++) {
                    this.options.put(arg.substring(i, i), "true");
                }
                continue;
            }

            argsList.add(arg);
        }

        this.args = argsList.toArray(new String[0]);
    }

    public String getArgument(int index) {
        return this.args[index];
    }
    public String[] getAllArguments() {
        return this.args;
    }
    public String getOption(String name) {
        return this.options.get(name);
    }
    public HashMap<String, String> getAllOptions() {
        return this.options;
    }
}
