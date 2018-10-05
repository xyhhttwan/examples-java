package utils;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.util.List;
import java.util.Map;


public class MapUtils {
    private static final Splitter.MapSplitter MAP_SPLITTER =
            Splitter.on('&').omitEmptyStrings().trimResults().withKeyValueSeparator('=');

    private static final Splitter SPLITTER =
            Splitter.on('|').omitEmptyStrings();

    public static List<String> url2List(String param) {
        return SPLITTER.splitToList(param);
    }

    public static Map<String, String> url2Map(String param) {
        return MAP_SPLITTER.split(param);
    }

    public static Map<String, String> base64EncodeUrl2Map(String param) {
        return MAP_SPLITTER.split(new String(Base64.decode(param), Charsets.UTF_8));
    }
}
