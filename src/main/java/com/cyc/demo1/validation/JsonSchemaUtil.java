package com.cyc.demo1.validation;

import java.io.IOException;
import java.util.Iterator;

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
}
