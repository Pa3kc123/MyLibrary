package sk.pa3kc.mylibrary.json;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class JsonDecoder {
    private static final char[] JSON_NULL = { 'n', 'u', 'l', 'l' };
    private static final char[] JSON_TRUE = { 't', 'r', 'u', 'e' };
    private static final char[] JSON_FALSE = { 'f', 'a', 'l', 's', 'e' };

    private JsonDecoder() {}

    @NotNull
    static Map<String, Object> decodeJsonObject(
        @NotNull final JsonTokenizer tokenizer,
        @NotNull HashMap<String, Object> output
    ) throws JsonException {
        if (tokenizer.getCurrChar() == 0) {
            tokenizer.nextChar();
        }

        if (tokenizer.getCurrChar() != '{') {
            throw new JsonException("Invalid token at " + tokenizer.getLineIndex() + "x" + tokenizer.getCharIndex());
        }

        boolean useLast = false;
        while (!tokenizer.isEofReached()) {
            final String key = string(tokenizer);

            if (tokenizer.nextClearChar() != ':') {
                throw new JsonException("Missing ':' at " + tokenizer.getLineIndex() + "x" + tokenizer.getCharIndex());
            }

            switch (tokenizer.nextClearChar()) {
                case '"':
                    output.put(key, string(tokenizer));
                break;

                case 't':
                case 'f':
                case 'n':
                    final int length = tokenizer.getCurrChar() == 'f' ? 5 : 4;
                    final char[] value = new char[length];
                    value[0] = tokenizer.getCurrChar();

                    for (int i = 1; i < length; i++) {
                        value[i] = tokenizer.nextChar();
                    }

                    if (Arrays.equals(value, JSON_TRUE)) {
                        output.put(key, true);
                    } else if (Arrays.equals(value, JSON_FALSE)) {
                        output.put(key, false);
                    } else if (Arrays.equals(value, JSON_NULL)) {
                        output.put(key, null);
                    } else {
                        throw new JsonException("Invalid value at " + tokenizer.getLineIndex() + "x" + tokenizer.getCharIndex());
                    }
                break;

                case '{':
                    output.put(key, decodeJsonObject(tokenizer, new HashMap<String, Object>()));
                break;

                case '[':
                    output.put(key, decodeJsonArray(tokenizer, new ArrayList<Object>()));
                break;

                case '-':
                    output.put(key, number(tokenizer, true));
                    useLast = true;
                break;

                default:
                    if (tokenizer.getCurrChar() >= '0' && tokenizer.getCurrChar() <= '9') {
                        output.put(key, number(tokenizer, false));
                        useLast = true;
                    } else {
                        throw new JsonException("Invalid value at " + tokenizer.getLineIndex() + "x" + tokenizer.getCharIndex());
                    }
                break;
            }

            final char cmpChar = useLast ? tokenizer.getCurrChar() : tokenizer.nextClearChar();
            if (",}".indexOf(cmpChar) == -1) {
                throw new JsonException("Invalid token at " + tokenizer.getLineIndex() + "x" + tokenizer.getCharIndex());
            }

            if (cmpChar == '}') {
                break;
            }

            useLast = false;
        }

        return output;
    }
    static List<Object> decodeJsonArray(
        @NotNull final JsonTokenizer tokenizer,
        @NotNull ArrayList<Object> output
    ) throws JsonException {
        if (tokenizer.getCurrChar() == 0) {
            tokenizer.nextChar();
        }

        if (tokenizer.getCurrChar() != '[') {
            throw new JsonException("Invalid token at " + tokenizer.getLineIndex() + "x" + tokenizer.getCharIndex());
        }

        boolean useLast = false;
        while (!tokenizer.isEofReached()) {
            switch (tokenizer.nextClearChar()) {
                case '"':
                    output.add(string(tokenizer));
                break;

                case 't':
                case 'f':
                case 'n':
                    final int length = tokenizer.getCurrChar() == 'f' ? 5 : 4;
                    final char[] value = new char[length];
                    value[0] = tokenizer.getCurrChar();

                    for (int i = 1; i < length; i++) {
                        value[i] = tokenizer.nextChar();
                    }

                    if (Arrays.equals(value, JSON_TRUE)) {
                        output.add(true);
                    } else if (Arrays.equals(value, JSON_FALSE)) {
                        output.add(false);
                    } else if (Arrays.equals(value, JSON_NULL)) {
                        output.add(null);
                    } else {
                        throw new JsonException("Invalid value at " + tokenizer.getLineIndex() + "x" + tokenizer.getCharIndex());
                    }
                break;

                case '{':
                    output.add(decodeJsonObject(tokenizer, new HashMap<String, Object>()));
                break;

                case '[':
                    output.add(decodeJsonArray(tokenizer, new ArrayList<Object>()));
                break;

                case '-':
                    output.add(number(tokenizer, true));
                    useLast = true;
                break;

                default:
                    if (tokenizer.getCurrChar() >= '0' && tokenizer.getCurrChar() <= '9') {
                        output.add(number(tokenizer, false));
                        useLast = true;
                    } else {
                        throw new JsonException("Invalid value at " + tokenizer.getLineIndex() + "x" + tokenizer.getCharIndex());
                    }
                break;
            }

            final char cmpChar = useLast ? tokenizer.getCurrChar() : tokenizer.nextClearChar();
            if (",]".indexOf(cmpChar) == -1) {
                throw new JsonException("Invalid token at " + tokenizer.getLineIndex() + "x" + tokenizer.getCharIndex());
            }

            if (cmpChar == ']') {
                break;
            }

            useLast = false;
        }

        return output;
    }

    private static String string(JsonTokenizer tokenizer) throws JsonException {
        final StringBuilder builder = new StringBuilder();
        if (tokenizer.getCurrChar() != '"' && tokenizer.nextClearChar() != '"') {
            throw new JsonException("Missing '\"' at " + tokenizer.getLineIndex() + "x" + tokenizer.getCharIndex());
        }

        char c;
        while (true) {
            switch (c = tokenizer.nextChar()) {
                case 0:
                case '\r':
                case '\n':
                    throw new JsonException("Unterminated string");

                case '\\':
                    switch (c = tokenizer.nextChar()) {
                        case 'b': builder.append('\b'); break;
                        case 'f': builder.append('\f'); break;
                        case 'n': builder.append('\n'); break;
                        case 'r': builder.append('\r'); break;
                        case 't': builder.append('\t'); break;

                        case 'u':
                            try {
                                builder.append((char)Integer.parseInt(new String(tokenizer.nextCharRange(4)), 16));
                            } catch (NumberFormatException e) {
                                throw new JsonException("Illegal escape. ", e);
                            }
                        break;

                        case '"':
                        case '\\':
                        case '/':
                            builder.append(c);
                        break;

                        default:
                            throw new JsonException("Illegal escape.");
                    }
                break;

                case '"':
                    final String result = builder.toString();
                return result;

                default:
                    builder.append(c);
                break;
            }
        }
    }
    private static Object number(JsonTokenizer stream, boolean isNegative) throws JsonException {
        final StringBuilder builder = new StringBuilder();
        char c = stream.getCurrChar();

        if (isNegative) {
            builder.append('-');
            c = stream.nextChar();
        }

        while (c >= '0' && c <= '9') {
            builder.append(c);
            c = stream.nextChar();
        }

        if (".eE".indexOf(c) == -1) {
            return parseNumber(builder.toString(), false);
        }

        final boolean isDecimal = c == '.';

        if (isDecimal) {
            builder.append(c);
            c = stream.nextChar();

            while (c >= '0' && c <= '9') {
                builder.append(c);
                c = stream.nextChar();
            }
        }

        if (c == 'e' || c == 'E') {
            builder.append(c);
            c = stream.nextChar();

            if (c == '+' || c == '-') {
                if (c == '-') {
                    builder.append(c);
                }
                c = stream.nextChar();
            } else {
                throw new JsonException("Invalid value at " + stream.getLineIndex() + "x" + stream.getCharIndex());
            }

            while (c >= '0' && c <= '9') {
                builder.append(c);
                c = stream.nextChar();
            }
        }

        return parseNumber(builder.toString(), isDecimal);
    }

    private static Object parseNumber(String numAsString, boolean isDecimal) {
        return isDecimal ? parseFloatingNumber(numAsString) : parseRealNumber(numAsString);
    }
    private static Object parseRealNumber(String numAsString) {
        try {
            return Integer.parseInt(numAsString);
        } catch (NumberFormatException ignored) {}

        try {
            return Long.parseLong(numAsString);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Invalid json", ex);
        }
    }
    private static Object parseFloatingNumber(String numAsString) {
        try {
            return Float.parseFloat(numAsString);
        } catch (NumberFormatException ignored) {}

        try {
            return Double.parseDouble(numAsString);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Invalid json", ex);
        }
    }
}
