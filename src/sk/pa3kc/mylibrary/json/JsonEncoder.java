package sk.pa3kc.mylibrary.json;

import java.util.List;
import java.util.Map;

abstract class JsonEncoder {
    private JsonEncoder() {}

    static StringBuilder encodeJsonObject(Map<String, Object> json, StringBuilder builder) {
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

    static StringBuilder encodeJsonArray(List<Object> list, StringBuilder builder) {
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
}
