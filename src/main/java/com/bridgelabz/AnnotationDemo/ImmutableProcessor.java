package com.bridgelabz.AnnotationDemo;

import java.lang.reflect.Modifier;
import java.util.Set;
import javax.tools.*;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

@SupportedAnnotationTypes("com.bridgelabz.AnnotationDemo.Immutable")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ImmutableProcessor extends AbstractProcessor {

	/*
	 * @Override public synchronized void init(ProcessingEnvironment processingEnv)
	 * { System.out.println("Init"); super.init(processingEnv); }
	 */

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		System.out.println("Inside immutable");
		for (Element element : roundEnv.getElementsAnnotatedWith(ImmutableAnno.class)) {
			if (element instanceof TypeElement) {
				TypeElement typeElement = (TypeElement) element;

				for (Element enclosedElement : typeElement.getEnclosedElements()) {
					if (enclosedElement instanceof VariableElement) {
						VariableElement variableElement = (VariableElement) enclosedElement;
						if (!variableElement.getModifiers().contains(Modifier.FINAL)) {
							processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format(
									"Class %s is annotated as @Immutable, but field %s is not declared as final",
									typeElement.getSimpleName(), variableElement.getSimpleName()));
						}
					}
				}

			}
		}

		return true;
	}

}
