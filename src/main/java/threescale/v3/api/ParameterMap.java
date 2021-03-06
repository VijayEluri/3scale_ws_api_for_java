package threescale.v3.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Hold a set of parameter and metrics for an AuthRep, Authorize, OAuth Authorize or Report.
 * <p/>
 * Each item consists of a name/value pair, where the value can be a String, An Array of ParameterMaps or another Parameter Map.
 * <p/>
 * <p>
 * E.g.  For an AuthRep:
 * </p>
 * <code>
 * ParameterMap params = new ParameterMap();<br/>
 * params.add("app_id", "app_1234");<br/>
 * ParameterMap usage = new ParameterMap();<br/>
 * usage.add("hits", "3");<br/>
 * params.add("usage", usage);<br/>
 * AuthorizeResponse response = serviceApi.authrep(params);<br/>
 * </code>
 * <p>
 * An example for a report might be:
 * <p/>
 * <code>
 * ParameterMap params = new ParameterMap();<br/>
 * params.add("app_id", "foo");<br/>
 * params.add("timestamp", fmt.print(new DateTime(2010, 4, 27, 15, 0)));<br/>
 * ParameterMap usage = new ParameterMap();<br/>
 * usage.add("hits", "1");<br/>
 * params.add("usage", usage);<br/>
 * ReportResponse response = serviceApi.report(params);<br/>
 * </code>
 */
public class ParameterMap {

    private HashMap<String, Object> data;

    /**
     * Construct and empty ParameterMap
     */
    public ParameterMap() {
        // Note: use a linked hash map for more predictable serialization of the parameters (mostly for testing)
        data = new LinkedHashMap<String, Object>();
    }

    /**
     * Add a string value
     *
     * @param key
     * @param value
     */
    public void add(String key, String value) {
        data.put(key, value);
    }

    /**
     * Add another ParameterMap
     *
     * @param key
     * @param map
     */
    public void add(String key, ParameterMap map) {
        data.put(key, map);
    }

    /**
     * Add an array of parameter maps
     *
     * @param key
     * @param array
     */
    public void add(String key, ParameterMap[] array) {
        data.put(key, array);
    }

    /**
     * Return the keys in a ParameterMap
     *
     * @return
     */
    public Set<String> getKeys() {
        return data.keySet();
    }

    /**
     * Get the type of data item associated with the key
     *
     * @param key
     * @return STRING, MAP, ARRAY
     */
    public ParameterMapType getType(String key) {
        Class<?> clazz = data.get(key).getClass();
        if (clazz == String.class) {
            return ParameterMapType.STRING;
        }
        if (clazz == ParameterMap[].class) {
            return ParameterMapType.ARRAY;
        }
        if (clazz == ParameterMap.class) {
            return ParameterMapType.MAP;
        }
        if (clazz == Long.class) {
            return ParameterMapType.LONG;
        }
        throw new RuntimeException("Unknown object in parameters");
    }

    /**
     * Get the String associated with a key
     *
     * @param key
     * @return
     */
    public String getStringValue(String key) {
        switch (getType(key)) {
        case ARRAY:
            return Arrays.toString((ParameterMap[]) data.get(key));
        case LONG:
            return Long.toString((Long) data.get(key));
        case MAP:
            return ((ParameterMap) data.get(key)).toString(); //
        case STRING:
        return (String) data.get(key);
        }
        return null;
    }

    /**
     * Get the map associated with a key
     *
     * @param key
     * @return
     */
    public ParameterMap getMapValue(String key) {
        return (ParameterMap) data.get(key);
    }

    /**
     * Get the array associated with a key.
     *
     * @param key
     * @return
     */
    public ParameterMap[] getArrayValue(String key) {
        return (ParameterMap[]) data.get(key);
    }
    public long getLongValue(String key) {
        return (Long) data.get(key);
    }
    public void setLongValue(String key, long value) {
        data.put(key, value);
    }

    /**
     * Return the number of elements in the map.
     *
     * @return
     */
    public int size() {
        return data.size();
    }
}
