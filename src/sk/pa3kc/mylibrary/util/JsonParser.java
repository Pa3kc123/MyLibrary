package sk.pa3kc.mylibrary.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {
    private JsonParser() {}

    //#region Decode Json
    private static HashMap<String, Object> decodeJsonObject(String jsonString, HashMap<String, Object> json) {
        int startIndex;
        int endIndex;

        startIndex = findSymbol(jsonString, jsonString.indexOf('{'), '\"');
        endIndex = findSymbol(jsonString, startIndex, '\"');

        final String key = jsonString.substring(startIndex + 1, endIndex);

        startIndex = findSymbol(jsonString, findSymbol(jsonString, endIndex, ':'), '\"', 't', 'f', 'n', '{', '[', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

        switch (jsonString.charAt(startIndex)) {
            case '\"':
                endIndex = findSymbol(jsonString, startIndex, '\"');
                json.put(key, jsonString.substring(startIndex + 1, endIndex));
            break;

            case 't':
            case 'f':
                endIndex = jsonString.indexOf(',');
                if (endIndex == -1) {
                    endIndex = findSymbol(jsonString, startIndex, '}');
                }
                json.put(key, Boolean.valueOf(jsonString.substring(startIndex, endIndex).trim()));
            break;

            case 'n':

            break;

            case '{':

            break;

            case '[':

            break;

            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':

            break;
        }

        return json;
    }

    public static HashMap<String, Object> decodeJsonObject(String jsonString) {
        if (jsonString == null) return null;

        jsonString.trim();

        if (jsonString.length() == 0) return new HashMap<String, Object>();
        if (!checkBrackets(jsonString)) throw new RuntimeException("Invalid json");

        return decodeJsonObject(jsonString, new HashMap<String, Object>());
    }

    private static ArrayList<Object> decodeJsonArray(String jsonString, ArrayList<Object> list) {
        return list;
    }

    public static ArrayList<Object> decodeJsonArray(String jsonString) {
        if (jsonString == null) return null;
        if (!checkBrackets(jsonString)) throw new RuntimeException("Invalid json");

        return decodeJsonArray(jsonString, new ArrayList<Object>());
    }

    private static boolean checkBrackets(CharSequence jsonString) {
        if (jsonString == null) return false;

        int roundBrackets = 0;
        int squareBrackets = 0;
        for (int i = 0; i < jsonString.length(); i++) {
            final char character = jsonString.charAt(i);

            if (character == '\\') {
                i++;
                continue;
            }

            switch (jsonString.charAt(i)) {
                case '(': roundBrackets++; break;
                case ')': roundBrackets--; break;

                case '[': squareBrackets++; break;
                case ']': squareBrackets--; break;
            }
        }

        return roundBrackets == 0 && squareBrackets == 0;
    }
    //#endregion

    //#region Encode Json
    private static StringBuilder encodeJson(Object value, StringBuilder builder) {
        if (value instanceof String) {
            builder.append('"');
            builder.append(value);
            builder.append('"');
        } else if (value instanceof Number) {
            builder.append(value);
        } else if (value instanceof Map) {
            Map<String, Object> valueAsMap = null;

            try {
                valueAsMap = (Map<String, Object>) value;
            } catch (ClassCastException ex) {
                ex.printStackTrace();
            }

            encodeJsonObject(valueAsMap, builder);
        } else if (value instanceof List) {
            List<Object> valueAsList = null;

            try {
                valueAsList = (List<Object>) value;
            } catch (ClassCastException ex) {
                ex.printStackTrace();
            }

            encodeJsonArray(valueAsList, builder);
        } else if (value == null) {
            builder.append("null");
        } else {
            builder.append(value);
        }
        return builder;
    }

    private static StringBuilder encodeJsonObject(Map<String, Object> json, StringBuilder builder) {
        if (json == null) return builder.append("null");

        builder.append("{ ");

        for (String key : json.keySet()) {
            builder.append('"');
            builder.append(key);
            builder.append("\": ");

            encodeJson(json.get(key), builder);

            builder.append(", ");
        }

        builder.replace(builder.length() - 2, builder.length(), " }");

        return builder;
    }

    public static String encodeJsonObject(Map<String, Object> json) {
        return encodeJsonObject(json, new StringBuilder()).toString();
    }

    private static StringBuilder encodeJsonArray(List<Object> list, StringBuilder builder) {
        if (list == null) return builder.append("null");
        if (list.size() == 0) return builder.append("[]");

        builder.append("[ ");

        for (Object value : list) {
            encodeJson(value, builder);
            builder.append(", ");
        }

        builder.replace(builder.length() - 2, builder.length(), " ]");

        return builder;
    }

    public static String encodeJsonArray(List<Object> list) {
        return encodeJsonArray(list, new StringBuilder()).toString();
    }
    //#endregion

    //#region Utility functions
    private static int findSymbol(String jsonString, int startIndex, char... syms) {
        if (startIndex == -1) throw new RuntimeException("Invalid json");

        while (startIndex < jsonString.length()) {
            final char stringSym = jsonString.charAt(startIndex++);

            if (stringSym == ' ' || stringSym == '\n' || stringSym == '\r' || stringSym == '\t') continue;

            for (final char sym : syms) {
                if (stringSym == sym) return startIndex;
            }

            break;
        }
        throw new RuntimeException("Invalid json");
    }
    //#endregion
}
