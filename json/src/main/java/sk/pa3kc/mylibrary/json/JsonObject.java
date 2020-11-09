package sk.pa3kc.mylibrary.json;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Set;

public class JsonObject extends AbstractMap<String, Object> {
    @NotNull
    @Override
    public Set<Entry<String, Object>> entrySet() {

        return new HashSet<Entry<String, Object>>();
    }
}
