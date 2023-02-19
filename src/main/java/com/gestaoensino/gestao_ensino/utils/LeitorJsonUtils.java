package com.gestaoensino.gestao_ensino.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

public final class LeitorJsonUtils {

    private final static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private final static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static final PageRequest DEFAULT_PAGEREQUEST = PageRequest.of(0, 10);

    private static <T, E extends Collection<?>> T doGetMockObject(String mockFolder, String fileName, Class<T> targetClazz, @Nullable Class<E> collectionClazz) {
        String filePath = mockFolder + "/" + fileName;
        try (InputStream is = LeitorJsonUtils.class.getResourceAsStream(filePath)) {
            if (collectionClazz != null) {
                CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(collectionClazz, targetClazz);
                return mapper.readValue(is, collectionType);
            } else {
                return mapper.readValue(is, targetClazz);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Um erro ocorreu ao carregar o JSON de teste: " + filePath);
        }
    }

    public static <T> T getMockObject(String mockFolder, String fileName, Class<T> targetClazz) {
        return doGetMockObject(mockFolder, fileName, targetClazz, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getMockObjectList(String mockFolder, String fileName, Class<T> targetClazz) {
        return (List<T>) doGetMockObject(mockFolder, fileName, targetClazz, List.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> generify(Class<?> cls) {
        return (Class<T>)cls;
    }
}

