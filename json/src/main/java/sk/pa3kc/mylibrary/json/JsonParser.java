package sk.pa3kc.mylibrary.json;

import org.jetbrains.annotations.NotNull;

import java.io.Reader;
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

    public static Map<String, Object> decodeJsonObject(Reader reader) throws JsonException {
        return JsonDecoder.decodeJsonObject(new JsonTokenizer(reader), new HashMap<String, Object>());
    }
    public static Map<String, Object> decodeJsonObject(String jsonString) throws JsonException {
        jsonString = jsonString.trim();
        validateJson(jsonString);
        return JsonDecoder.decodeJsonObject(new JsonTokenizer(jsonString), new HashMap<String, Object>());
    }

    public static List<Object> decodeJsonArray(Reader reader) {
        return JsonDecoder.decodeJsonArray(new JsonTokenizer(reader), new ArrayList<Object>());
    }
    public static List<Object> decodeJsonArray(String jsonString) {
        jsonString = jsonString.trim();
        validateJson(jsonString);
        return JsonDecoder.decodeJsonArray(new JsonTokenizer(jsonString), new ArrayList<Object>());
    }

    private static void validateJson(@NotNull String jsonString) throws JsonException {
        if (jsonString.length() == 0) {
            throw new JsonException("Invalid json");
        }

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

            if (curlyBrackets == 0 && squareBrackets == 0) {
                if (++i != jsonString.length() && jsonString.charAt(i) > ' ') {
                    throw new JsonException("Invalid json");
                }
            }
        }

        if (curlyBrackets != 0 || squareBrackets != 0 || isWithinString) {
            throw new JsonException("Invalid json");
        }
    }
}
