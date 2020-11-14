package sk.pa3kc.mylibrary.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgsParser {
    private static final Pattern PATTERN = Pattern.compile("--(.*?)=(.*)");

    String[] args = new String[0];
    final HashMap<String, String> options = new HashMap<String, String>();

    public ArgsParser() {
        this(null);
    }
    public ArgsParser(@Nullable String[] args) {
        if (args != null) {
            parse(args);
        }
    }

    @NotNull
    public String[] getAllArgs() {
        return this.args;
    }
    @NotNull
    public Map<String, String> getAllOptions() {
        return this.options;
    }
    @NotNull
    public String getArg(final int index) {
        return this.args[index];
    }
    @Nullable
    public String getOption(final String key) {
        return this.options.get(key);
    }
    @NotNull
    public String getOption(final String key, final String def) {
        final String result = this.options.get(key);
        return result != null ? result : def;
    }

    @NotNull
    public ArgsParser parse(@NotNull final String... args) {
        if (args.length == 0) {
            if (this.args == null) {
                this.args = new String[0];
            }

            return this;
        }

        this.options.clear();

        final ArrayList<String> argsList = new ArrayList<String>();

        int index = 0;
        for (; index < args.length; index++) {
            final String arg = args[index];

            if ("--".equals(arg)) {
                break;
            }

            if (arg.startsWith("--")) {
                if (arg.contains("=")) {
                    final Matcher matcher = PATTERN.matcher(arg);

                    if (matcher.find()) {
                        final String key = matcher.group(1);
                        final String value = matcher.group(2);

                        if (!"".equals(key) && !"".equals(value)) {
                            this.options.put(key, value);
                        }
                    }
                } else {
                    final String value = arg.substring(2);
                    this.options.put(value, "true");
                }

                continue;
            }

            if (arg.startsWith("-") && !"-".equals(arg)) {
                this.options.put(arg.substring(1), "true");
                continue;
            }

            argsList.add(arg);
        }

        for (; index < args.length; index++) {
            argsList.add(args[index]);
        }

        this.args = argsList.toArray(new String[0]);
        return this;
    }
}
