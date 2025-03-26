package io.renren.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;


/**
 * Json转换工具类
 * @author
 * jsonUtil
 * 2021年10月28日
 */
public class JsonUtil {

    private static final Logger logger = Logger.getLogger(JsonUtil.class);

    /**
     * 获取到mapper对象
     *
     * @return
     */
    private static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        return mapper;
    }

    /**
     * 将对象转换成JSON字符串
     *
     * @param value
     * @return 转换异常时，返回NULL
     */
    public static String toJson(Object value) {
        try {
            return getMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            logger.error("toJson detected error.", e);
        }
        return null;
    }


    /**
     * 将JSON字符串转换成相应的对象
     *
     * @param value
     * @param type
     * @return 转换失败时，返回NULL
     */
    public static <T> T fromJson(String value, Class<T> type) {
        try {
            return getMapper().readValue(value, type);
        } catch (Exception e) {
            logger.error("fromJson detected error,value:" + value, e);
        }
        return null;
    }

    /**
     * JSON字符串转换成map
     *
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(String json) {
        try {
            return getMapper().readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            logger.error("json to map detected error.", e);
        }
        return null;
    }


    public static Map<Integer, String> toMapLess(String json) {
        try {
            return getMapper().readValue(json, new TypeReference<Map<Integer, String>>() {
            });
        } catch (Exception e) {
            logger.error("json to map detected error.", e);
        }
        return null;
    }


    /**
     * 将实体bean转换成map对象
     *
     * @param bean
     * @return
     */
    public static Map<String, Object> beanToMap(Object bean) {
        String value = toJson(bean);
        return toMap(value);
    }

    /**
     * 将json数组转换成List
     *
     * @param value
     * @param type
     * @return
     */
    public static <T> List<T> fromJsonToList(String value, Class<T> type) {

        ObjectMapper mapper = getMapper();
        try {
            JsonParser parser = mapper.getFactory().createParser(value);
            JsonNode nodes = parser.readValueAsTree();
            List<T> list = new ArrayList<T>(nodes.size());
            for (JsonNode node : nodes) {
                list.add(mapper.readValue(node.traverse(), type));
            }
            return list;
        } catch (IOException e) {
            logger.error("fromJsonToList detected error.", e);
        }
        return null;
    }


    /**
     * 重构Map排序  By Key
     *
     * @param newFormerTitle
     * @return
     */
    public static Map<String, Object> sortMapByKey(Map<String, Object> newFormerTitle) {
        if (newFormerTitle == null || newFormerTitle.isEmpty()) {
            return null;
        }
        Map<String, Object> sortedMap = new TreeMap<String, Object>(new Comparator<String>() {
            public int compare(String key1, String key2) {
                int intKey1 = 0, intKey2 = 0;
                try {
                    intKey1 = getInt(key1);
                    intKey2 = getInt(key2);
                } catch (Exception e) {
                    intKey1 = 0;
                    intKey2 = 0;
                }
                return intKey1 - intKey2;
            }
        });
        sortedMap.putAll(newFormerTitle);
        return sortedMap;
    }

    private static int getInt(String str) {
        int i = 0;
        try {
            Pattern p = Pattern.compile("^\\d+");
            Matcher m = p.matcher(str);
            if (m.find()) {
                i = Integer.valueOf(m.group());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }
}

