package com.cyc.demo1.validation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * json schema校验json工具类
 * 
 * @author atchen
 */
@Slf4j
public class JsonSchemaUtil {

    private JsonSchemaUtil() {

    }

    /**
     * 验证json串是否满足jsonSchema的要求
     * 
     * @param json
     *            json字符串
     * @param jsonSchema
     *            jsonSchema字符串
     */
    public static void validateJsonBySchema(String json, String jsonSchema) {
        try {
            // json的node节点
            JsonNode jsonNode = JsonLoader.fromString(json);

            // jsonSchema的node节点
            JsonNode jsonSchemaNode = JsonLoader.fromString(jsonSchema);

            ProcessingReport validate = JsonSchemaFactory.byDefault().getValidator().validate(jsonSchemaNode, jsonNode);

            if (validate.isSuccess()) {
                log.debug("校验成功");
            } else {
                Iterator<ProcessingMessage> iterator = validate.iterator();

                StringBuilder errorMessgae = new StringBuilder(32);
                while (iterator.hasNext()) {
                    ProcessingMessage next = iterator.next();

                    if (!LogLevel.WARNING.equals(next.getLogLevel())) {
                        errorMessgae.append(next.getMessage()).append("|");
                    }
                }

                throw new RuntimeException(errorMessgae.toString());
            }

        } catch (IOException | ProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 获得jsonSchema的所有定义的属性
     * 
     * @param jsonSchema
     */
    public static List<String> getProperties(String jsonSchema) {
        List<String> propertiesList = null;
        try {
            JsonNode jsonNode = JsonLoader.fromString(jsonSchema);

            JsonNode properties = jsonNode.get("properties");

            if (properties != null) {
                int size = properties.size();
                propertiesList = new ArrayList<>(size);

                // 获得属性
                Iterator<String> stringIterator = properties.fieldNames();
                while (stringIterator.hasNext()) {
                    String next = stringIterator.next();
                    propertiesList.add(next);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return propertiesList;
    }
}
