package br.com.visaocr.condicoes;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CondicaoBancoDeDados implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata arg1) {
		String property = context.getEnvironment().getProperty("app.fonte");
		return property.equalsIgnoreCase("bd");
	}

}
