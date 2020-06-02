package sk.pa3kc.mylibrary.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class JsonParser {
    private JsonParser() {}

    public static String encodeJsonObject(Map<String, Object> json) {
        return JsonEncoder.encodeJsonObject(json, new StringBuilder()).toString();
    }

    public static String encodeJsonArray(List<Object> list) {
        return JsonEncoder.encodeJsonArray(list, new StringBuilder()).toString();
    }

    public static HashMap<String, Object> decodeJsonObject(String jsonString) {
        if (jsonString == null || (jsonString = jsonString.trim()).length() == 0) {
            throw new RuntimeException("Invalid json");
        }

        checkBrackets(jsonString);

        return JsonDecoder.decodeJsonObject(jsonString, new HashMap<String, Object>());
    }

    public static ArrayList<Object> decodeJsonArray(String jsonString) {
        if (jsonString == null || (jsonString = jsonString.trim()).length() == 0) {
            throw new RuntimeException("Invalid json");
        }

        checkBrackets(jsonString);

        return JsonDecoder.decodeJsonArray(jsonString, new ArrayList<Object>());
    }

    private static void checkBrackets(String jsonString) {
        int curlyBrackets = 0;
        int squareBrackets = 0;
        boolean isWithinString = false;
        for (int i = 0; i < jsonString.length(); i++) {
            final char character = jsonString.charAt(i);

            if (character == '\"') {
                isWithinString = !isWithinString;
                continue;
            }

            if (character == '\\' && isWithinString) {
                i++;
                continue;
            }

            if (!isWithinString) {
                switch (character) {
                    case '{': curlyBrackets++; break;
                    case '}': curlyBrackets--; break;

                    case '[': squareBrackets++; break;
                    case ']': squareBrackets--; break;
                }
            }
        }

        if (curlyBrackets != 0 || squareBrackets != 0 || isWithinString) {
            throw new RuntimeException("Invalid json");
        }
    }
}
