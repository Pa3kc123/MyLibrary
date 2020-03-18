package sk.pa3kc.mylibrary.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class StringStream implements Iterator<Character> {
    private final char[] arr;
    private int index = 0;

    private Character nextChar;

    public final int length;

    StringStream(String string) {
        this(string.toCharArray());
    }
    StringStream(char[] arr) {
        this.arr = arr;
        this.length = arr.length;

        this.nextChar = arr[0];
    }

    public int index() {
        return this.index - 1;
    }
    public char get(int index) {
        if (index < 0 || index > this.length) {
            throw new IndexOutOfBoundsException();
        }

        return this.arr[index];
    }
    public char[] getRange(int startIndex) {
        if (startIndex < 0 || startIndex > this.length) throw new IndexOutOfBoundsException();

        return this.getRange(startIndex, this.length - startIndex);
    }
    public char[] getRange(int startIndex, int length) {
        if (startIndex < 0 || startIndex > this.length || startIndex + length < 0 || startIndex + length > this.length) {
            throw new IndexOutOfBoundsException();
        }

        final char[] arr = new char[length];

        for (int i = 0; i < length; i++) {
            arr[i] = this.arr[startIndex++];
        }

        return arr;
    }

    @Override
    public boolean hasNext() {
        return this.index < arr.length;
    }

    @Override
    public Character next() {
        while (this.hasNext()) {
            final char ch = this.arr[this.index++];

            //debug Hello Worlds
            this.nextChar = this.index < this.length ? this.arr[this.index] : null;

            if (ch != ' ' && ch != '\n' && ch != '\r' && ch != '\t') {
                return ch;
            }
        }

        return null;
    }

    public int findNext(char ch) {
        while (this.hasNext()) {
            if (this.arr[this.index++] == ch) return this.index - 1;
        }

        return -1;
    }
}

abstract class JsonDecoder {
    private static final String JSON_NULL = "null";
    private static final String JSON_TRUE = "true";
    private static final String JSON_FALSE = "false";

    private static final RuntimeException INVALID_JSON_EX = new RuntimeException("Invalid json");

    private JsonDecoder() {}

    // json is not null
    // json is not empty
    // json is trimmed
    // json is bracket and quotation mark checked
    static HashMap<String, Object> decodeJsonObject(String jsonString, HashMap<String, Object> output) {
        final StringStream stream = new StringStream(jsonString);

        if (stream.next() != '{') {
            throw INVALID_JSON_EX;
        }

        while (stream.hasNext()) {
            if (stream.next() != '"') throw INVALID_JSON_EX;
            final String key = string(stream);

            if (stream.next() != ':') throw INVALID_JSON_EX;

            if ("useJSP".equals(key)) {
                System.out.flush();
            }

            valueLoop:
            while (stream.hasNext()) {
                final char ch = stream.next();

                switch (ch) {
                    case '"': output.put(key, string(stream)); break valueLoop;

                    case 't':
                    case 'f':
                    case 'n':
                        final int length = ch == 'f' ? 5 : 4;
                        final String value = new String(stream.getRange(stream.index(), length));

                        for (int i = 0; i < length - 1; i++) {
                            stream.next();
                        }

                        if (JSON_TRUE.equals(value)) {
                            output.put(key, true);
                        } else if (JSON_FALSE.equals(value)) {
                            output.put(key, false);
                        } else if (JSON_NULL.equals(value)) {
                            output.put(key, null);
                        } else {
                            throw INVALID_JSON_EX;
                        }
                    break valueLoop;

                    case '{':
                        output.put(key, decodeJsonObject(object(stream), new HashMap<String, Object>()));
                    break valueLoop;

                    case '[':
                        output.put(key, decodeJsonArray(array(stream), new ArrayList<Object>()));
                    break valueLoop;

                    default:
                        if (ch == '-' || (ch >= '0' && ch <= '9')) {
                            output.put(key, number(stream));
                        } else {
                            throw INVALID_JSON_EX;
                        }
                    break valueLoop;
                }
            }

            final char ch = stream.next();
            if (ch != ',' && ch != '}') {
                throw INVALID_JSON_EX;
            }
        }

        return output;
    }

    static ArrayList<Object> decodeJsonArray(String jsonString, ArrayList<Object> list) {
        final StringStream stream = new StringStream(jsonString);

        if (stream.next() != '[') throw INVALID_JSON_EX;

        while (stream.hasNext()) {
            valueLoop:
            while (stream.hasNext()) {
                final char ch = stream.next();

                switch (ch) {
                    case '"': list.add(string(stream)); break valueLoop;

                    case 't':
                    case 'f':
                    case 'n': {
                        final String value = new String(stream.getRange(stream.index(), ch == 'f' ? 5 : 4));

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

            final char ch = stream.next();
            if (ch != ',' && ch != ']') {
                throw INVALID_JSON_EX;
            }
        }

        return list;
    }


    private static String object(StringStream stream) {
        int index = stream.index();
        int bracketCount = 1;
        boolean isWithinString = false;

        while (stream.hasNext()) {
            final char ch = stream.next();

            if (ch == '"') {
                isWithinString = !isWithinString;
                continue;
            }

            if (!isWithinString) {
                if (ch == '{') bracketCount++;
                if (ch == '}') bracketCount--;

                if (bracketCount == 0) {
                    return new String(stream.getRange(index, stream.index() - index + 1));
                }
            }
        }

        throw INVALID_JSON_EX;
    }
    private static String array(StringStream stream) {
        int index = stream.index();
        int bracketCount = 1;
        boolean isWithinString = false;

        while (stream.hasNext()) {
            final char ch = stream.next();

            if (ch == '"') isWithinString = !isWithinString;

            if (!isWithinString) {
                if (ch == '[') bracketCount++;
                if (ch == ']') bracketCount--;

                if (bracketCount == 0) {
                    return new String(stream.getRange(index, stream.index() - index + 1));
                }
            }

        }

        throw INVALID_JSON_EX;
    }
    private static String string(StringStream stream) {
        final int index = stream.index() + 1;

        while (stream.hasNext()) {
            if (stream.next() == '"' && stream.get(stream.index() - 1) != '\\') {
                return new String(stream.getRange(index, stream.index() - index));
            }
        }

        throw INVALID_JSON_EX;
    }
    private static Object number(StringStream stream) {
        final StringBuilder builder = new StringBuilder();

        char ch = stream.get(stream.index());
        if (ch == '-') {
            builder.append(ch);
            ch = stream.next();
        }

        if (ch < '0' && ch > '9') throw INVALID_JSON_EX;

        while (stream.hasNext()) {
            builder.append(ch);

            ch = stream.get(stream.index() + 1);
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

                ch = stream.get(stream.index() + 1);
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
            } else throw INVALID_JSON_EX;

            while (stream.hasNext()) {
                builder.append(ch);

                ch = stream.get(stream.index() + 1);
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
        } catch (NumberFormatException ex) {}

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
