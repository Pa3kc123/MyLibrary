package sk.pa3kc.mylibrary.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {
    private JsonParser() {}

    //#region Decode Json
    private static HashMap<String, Object> decodeJsonObject(String jsonString, HashMap<String, Object> json) {
        int startIndex = jsonString.indexOf('"');
        int endIndex = jsonString.indexOf('"', startIndex + 1);

        if (startIndex == -1 || endIndex == -1) return json;

        startIndex = jsonString.indexOf('"');
        endIndex = jsonString.indexOf('"', startIndex + 1);

        for (; startIndex != -1 || endIndex != -1; startIndex = jsonString.indexOf('"', startIndex), endIndex = jsonString.indexOf('"', startIndex)) {

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
}
