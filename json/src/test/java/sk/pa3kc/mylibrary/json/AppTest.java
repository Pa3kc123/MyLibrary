package sk.pa3kc.mylibrary.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class AppTest {
    private InputStreamReader isr = null;

    @Before
    public void init() {
        try {
            final InputStream is = AppTest.class.getClassLoader().getResourceAsStream("file.json");

            assertNotNull(is);

            this.isr = new InputStreamReader(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(this.isr);
    }

    @Test
    public void contentTest() {
        final Map<String, Object> json = JsonParser.decodeJsonObject(this.isr);
        assertEquals(1, json.size());
        final Map<String, Object> webApp = (Map)json.get("web-app");
        assertEquals(3, webApp.size());
        final Map<String, Object> servletMapping = (Map)webApp.get("servlet-mapping");
        assertEquals(5, servletMapping.size());
    }

    @After
    public void cleanUp() {
        if (this.isr != null) {
            try {
                this.isr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
