package com.gestaoensino.gestao_ensino.utils.converter;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@WritingConverter
public class GenericEnumConverter implements ConditionalGenericConverter {

	@SuppressWarnings("rawtypes")
	private static Map<Class<? extends Enum>, Field> cacheFields = new HashMap<>(); 
	
	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		return SCANNED_ENUMS;
	}

	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		try {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			Class<? extends Enum> clazz = (Class) source.getClass();
			Field field = cacheFields.get(clazz);

			Object codigo = field.get(source);
			return codigo.toString().getBytes();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
		return sourceType.isAssignableTo(TypeDescriptor.valueOf(Enum.class))
				&& targetType.isAssignableTo(TypeDescriptor.valueOf(byte[].class));
	}

	private static final Set<ConvertiblePair> SCANNED_ENUMS = scanEnums();
	
	private static Set<ConvertiblePair> scanEnums() {
		ClassPathScanningCandidateComponentProvider scan = new ClassPathScanningCandidateComponentProvider(false);
		Set<BeanDefinition> components = scan.findCandidateComponents("br.com.brasilseg.simulador");
		return components.stream().map(beanDef -> {
			try {
				return new ConvertiblePair(Class.forName(beanDef.getBeanClassName()), byte[].class);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toSet());
	}

}
