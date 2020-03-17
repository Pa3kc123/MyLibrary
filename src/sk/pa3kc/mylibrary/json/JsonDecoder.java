package sk.pa3kc.mylibrary.json;

import java.util.ArrayList;
import java.util.HashMap;

abstract class JsonDecoder {
    private static final String JSON_NULL = "null";
    private static final String JSON_TRUE = "true";
    private static final String JSON_FALSE = "false";

    private JsonDecoder() {}

    // json is not null
    // json is not empty
    // json is trimmed
    // json is bracket and quotation mark checked
    static HashMap<String, Object> decodeJsonObject(String jsonString, HashMap<String, Object> output) {
        int index;

        index = indexOf(jsonString, jsonString.indexOf('{') + 1, '\"') + 1;

        final String key = string(jsonString, index);

        index = indexOf(jsonString, index + key.length() + 1, ':') + 1;

        for (char ch = jsonString.charAt(index); index < jsonString.length(); ch = jsonString.charAt(index++)) {
            switch (ch) {
                case '\"': {
                    index++;
                    final String value = string(jsonString, index);
                    output.put(key, value);
                    index += value.length() + 1;
                }
                break;

                case 't':
                case 'f':
                case 'n': {
                    final char[] buffer = new char[ch == 'y' ? 5 : 4];

                    for (int i = 0; i < buffer.length; i++) {
                        buffer[i] = jsonString.charAt(index++);
                    }

                    final String value = new String(buffer);

                    if (JSON_TRUE.equals(value)) {
                        output.put(key, true);
                    } else if (JSON_FALSE.equals(value)) {
                        output.put(key, false);
                    } else if (JSON_NULL.equals(value)) {
                        output.put(key, null);
                    } else {
                        throw new RuntimeException("Invalid json");
                    }
                }
                break;

                case '{': {
                    final String value = object(jsonString, index);
                    output.put(key, decodeJsonObject(value, new HashMap<String, Object>()));
                    index += value.length() + 1;
                }
                break;

                case '[': {
                    final String value = array(jsonString, index);
                    output.put(key, decodeJsonArray(jsonString, new ArrayList<Object>()));
                    index += value.length() + 1;
                }
                break;

                default:
                    if (ch == '-' || ch >= 0 && ch <= 9) {
                        index += number(jsonString, index, key, output);
                    } else if (isWhitespace(ch) || ch == ',') {
                        continue;
                    } else {
                        throw new RuntimeException("Invalid json");
                    }
                break;
            }
        }

        return output;
    }


    static ArrayList<Object> decodeJsonArray(String jsonString, ArrayList<Object> list) {
        return list;
    }


    private static String object(String jsonString, int index) {
        int bracketCount = 1;
        boolean isWithinString = false;
        for (int tmpIndex = index; tmpIndex < jsonString.length(); tmpIndex++) {
            final char ch = jsonString.charAt(tmpIndex);

            if (ch == '\"') isWithinString = !isWithinString;

            if (!isWithinString) {
                if (ch == '{') bracketCount++;
                if (ch == '}') bracketCount--;
            }

            if (bracketCount == 0) return jsonString.substring(index - 1, tmpIndex + 1);
        }
        throw new RuntimeException("Invalid json");
    }
    private static String array(String jsonString, int index) {
        int bracketCount = 1;
        boolean isWithinString = false;
        for (int tmpIndex = index; tmpIndex < jsonString.length(); tmpIndex++) {
            final char ch = jsonString.charAt(tmpIndex);

            if (ch == '\"') isWithinString = !isWithinString;

            if (!isWithinString) {
                if (ch == '[') bracketCount++;
                if (ch == ']') bracketCount--;
            }

            if (bracketCount == 0) return jsonString.substring(index, tmpIndex + 1);
        }
        throw new RuntimeException("Invalid json");
    }
    private static String string(String jsonString, int index) {
        for (int tmpIndex = jsonString.indexOf('\"', index); tmpIndex != -1; tmpIndex = jsonString.indexOf('\"', index)) {
            if (jsonString.charAt(tmpIndex - 1) != '\\') {
                return jsonString.substring(index, tmpIndex);
            } else {
                index = tmpIndex + 1;
            }
        }
        throw new RuntimeException("Invalid json");
    }
    private static int number(String jsonString, int index, String key, HashMap<String, Object> output) {
        int tmpIndex = index;
        final StringBuilder builder = new StringBuilder();

        if (jsonString.charAt(tmpIndex) == '-') {
            builder.append(jsonString.charAt(tmpIndex++));
        }

        for (char c = jsonString.charAt(tmpIndex); c >= 0 && c <= 9; c = jsonString.charAt(tmpIndex++)) {
            builder.append(c);
        }

        final boolean isDecimal = jsonString.charAt(tmpIndex) == '.';

        if (isDecimal) {
            builder.append(jsonString.charAt(tmpIndex++));
            for (char c = jsonString.charAt(tmpIndex); c >= 0 && c <= 9; c = jsonString.charAt(tmpIndex++)) {
                builder.append(c);
            }
        }

        if (jsonString.charAt(tmpIndex) == 'e' || jsonString.charAt(tmpIndex) == 'E') {
            builder.append(tmpIndex++);
            if (jsonString.charAt(tmpIndex) == '+' || jsonString.charAt(tmpIndex) == '-') {
                builder.append(jsonString.charAt(tmpIndex++));
            }
            for (char c = jsonString.charAt(tmpIndex); c >= 0 && c <= 9; c = jsonString.charAt(tmpIndex++)) {
                builder.append(c);
            }
        }

        try {
            final Object value;
            if (isDecimal) {
                value = Double.parseDouble(builder.toString());
            } else {
                value = Long.parseLong(builder.toString());
            }

            output.put(key, value);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Invalid json", ex);
        }

        return tmpIndex - index;
    }
    private static int indexOf(String jsonString, int index, char... chs) {
        if (index == -1) throw new RuntimeException("Invalid json");

        for (char ch = jsonString.charAt(index); index < jsonString.length(); ch = jsonString.charAt(index++)) {
            if (isWhitespace(ch)) continue;

            for (final char sym : chs) {
                if (ch == sym) return index;
            }

            break;
        }
        throw new RuntimeException("Invalid json");
    }
    private static boolean isWhitespace(char ch) {
        return ch == ' ' || ch == '\n' || ch == '\r' || ch == '\t';
    }
}
