package sk.pa3kc.mylibrary.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

abstract class JsonDecoder {
    private static final String JSON_NULL = "null";
    private static final String JSON_TRUE = "true";
    private static final String JSON_FALSE = "false";

    private static final RuntimeException INVALID_JSON_EX = new RuntimeException("Invalid json");

    private JsonDecoder() {}

    static Map<String, Object> decodeJsonObject(String jsonString, HashMap<String, Object> output) throws IOException {
        final JsonTokenizer tokenizer = new JsonTokenizer(jsonString);

        if (tokenizer.nextChar() != '{') {
            throw INVALID_JSON_EX;
        }

        char c;
        while (!tokenizer.isEofReached()) {
            if (tokenizer.nextChar() != '"') throw INVALID_JSON_EX;
            final String key = tokenizer.string();

            if (tokenizer.nextChar() != ':') throw INVALID_JSON_EX;

            c = tokenizer.nextChar();
            switch (c) {
                case '"': output.put(key, tokenizer.string()); break;

                case 't':
                case 'f':
                case 'n':
                    final int length = c == 'f' ? 5 : 4;
                    final String value = new String(tokenizer.nextCharRange(length));

                    if (JSON_TRUE.equals(value)) {
                        output.put(key, true);
                    } else if (JSON_FALSE.equals(value)) {
                        output.put(key, false);
                    } else if (JSON_NULL.equals(value)) {
                        output.put(key, null);
                    } else {
                        throw INVALID_JSON_EX;
                    }
                break;

                case '{':
                    output.put(key, decodeJsonObject(tokenizer.object(), new HashMap<String, Object>()));
                break;

                case '[':
                    output.put(key, decodeJsonArray(tokenizer.array(), new ArrayList<Object>()));
                break;

                default:
                    if (c == '-' || (c >= '0' && c <= '9')) {
                        output.put(key, tokenizer.number());
                    } else {
                        throw INVALID_JSON_EX;
                    }
                break;
            }

            c = tokenizer.nextChar();
            if (c != ',' && c != '}') {
                throw INVALID_JSON_EX;
            }
        }

        return output;
    }

    static ArrayList<Object> decodeJsonArray(String jsonString, ArrayList<Object> list) {
        final JsonTokenizer stream = new JsonTokenizer(jsonString);

        if (stream.nextChar() != '[') throw INVALID_JSON_EX;

        while (!stream.isEofReached()) {
            valueLoop:
            while (!stream.isEofReached()) {
                final char ch = stream.nextChar();

                switch (ch) {
                    case '"': list.add(stream.string()); break valueLoop;

                    case 't':
                    case 'f':
                    case 'n': {
                        final String value = new String(stream.nextCharRange(ch == 'f' ? 5 : 4));

                        if (JSON_TRUE.equals(value)) {
                            list.add(true);
                        } else if (JSON_FALSE.equals(value)) {
                            list.add(false);
                        } else if (JSON_NULL.equals(value)) {
                            list.add(null);
                        } else {
                            throw INVALID_JSON_EX;
                        }
                    }
                    break valueLoop;

                    case '{':
                        list.add(decodeJsonObject(object(stream), new HashMap<String, Object>()));
                    break valueLoop;

                    case '[':
                        list.add(decodeJsonArray(array(stream), new ArrayList<Object>()));
                    break valueLoop;

                    default:
                        if (ch == '-' || (ch >= '0' && ch <= '9')) {
                            list.add(number(stream));
                        } else {
                            throw INVALID_JSON_EX;
                        }
                    break valueLoop;
                }
            }

            final int ch = stream.nextChar();
            if (ch != ',' && ch != ']') {
                throw INVALID_JSON_EX;
            }
        }

        return list;
    }

    private static String object(JsonTokenizer stream) {
        int index = stream.getIndex();
        int bracketCount = 1;
        boolean isWithinString = false;

        while (!stream.isEofReached()) {
            final char ch = stream.nextChar();

            if (ch == '"') {
                isWithinString = !isWithinString;
                continue;
            }

            if (!isWithinString) {
                if (ch == '{') bracketCount++;
                if (ch == '}') bracketCount--;

                if (bracketCount == 0) {
                    return new String(stream.nextCharRange(stream.getIndex() - index + 1));
                }
            }
        }

        throw INVALID_JSON_EX;
    }
    private static String array(JsonTokenizer stream) {
        int index = stream.getIndex();
        int bracketCount = 1;
        boolean isWithinString = false;

        while (stream.hasNext()) {
            final char ch = stream.next();

            if (ch == '"') isWithinString = !isWithinString;

            if (!isWithinString) {
                if (ch == '[') bracketCount++;
                if (ch == ']') bracketCount--;

                if (bracketCount == 0) {
                    return new String(stream.getRange(index, stream.getIndex() - index + 1));
                }
            }

        }

        throw INVALID_JSON_EX;
    }
    private static String string(JsonTokenizer stream) throws IOException {
        final int index = stream.getIndex() + 1;

        while (stream.isEofReached()) {
            if (stream.nextChar() == '"' && stream.get(stream.getIndex() - 1) != '\\') {
                return new String(stream.nextCharRange(stream.getIndex() - index));
            }
        }

        throw INVALID_JSON_EX;
    }
    private static Object number(JsonTokenizer stream) {
        final StringBuilder builder = new StringBuilder();

        char ch = stream.get(stream.getIndex());
        if (ch == '-') {
            builder.append(ch);
            ch = stream.next();
        }

        if (ch < '0' && ch > '9') throw INVALID_JSON_EX;

        while (stream.hasNext()) {
            builder.append(ch);

            ch = stream.get(stream.getIndex() + 1);
            if (ch >= '0' && ch <= '9') {
                stream.next();
            } else break;
        }

        if (ch != '.' && ch != 'e' && ch != 'E') {
            return parseNumber(builder.toString(), false);
        }

        final boolean isDecimal = ch == '.';

        if (isDecimal) {
            builder.append(ch);

            while (stream.hasNext()) {
                builder.append(ch);

                ch = stream.get(stream.getIndex() + 1);
                if (ch >= '0' && ch <= '9') {
                    stream.next();
                } else break;
            }
        }

        if (ch == 'e' || ch == 'E') {
            builder.append(ch);

            ch = stream.next();
            if (ch == '+' || ch == '-') {
                builder.append(ch);
                ch = stream.next();
            }

            while (stream.hasNext()) {
                builder.append(ch);

                ch = stream.get(stream.getIndex() + 1);
                if (ch >= '0' && ch <= '9') {
                    stream.next();
                } else break;
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
