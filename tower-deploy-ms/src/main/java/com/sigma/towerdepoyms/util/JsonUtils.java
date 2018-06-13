package com.sigma.towerdepoyms.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Summary:
 *
 * @author zhenpeng
 */
@Slf4j
public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    //父类的静态变量->父类的静态初始化块->子类的静态变量->子类的静态初始化块
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));

        objectMapper.registerModule(javaTimeModule);
    }

    /**
     * 将对象序列化为JSON字符串
     *
     * @param object
     * @return JSON字符串
     */
    public static String serialize(Object object) {
        return serialize(object, false);
    }

    public static String serialize(Object object, boolean withPretty) {
        if (object == null) {
            return "";
        }
        Writer write = new StringWriter();
        try {
            if (withPretty) {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(write, object);
            } else {
                objectMapper.writeValue(write, object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return write.toString();
    }

    /**
     * 将JSON字符串反序列化为对象
     *
     * @param json
     * @return JSON字符串
     */
    public static <T> T deserialize(String json, Class<T> clazz) {
        Object object = null;
        try {
            object = objectMapper.readValue(json, TypeFactory.rawClass(clazz));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) object;
    }

    /**
     * 将JSON字符串反序列化为对象
     *
     * @param json
     * @return JSON字符串
     */
    public static <T> T deserialize(String json, TypeReference<T> typeRef) {
        Object object = null;
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
