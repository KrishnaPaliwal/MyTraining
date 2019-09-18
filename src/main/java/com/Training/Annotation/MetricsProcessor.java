package com.myTraining.Annotation;

import java.lang.reflect.Method;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

//http://travisdazell.blogspot.com/2012/10/writing-annotation-based-processor-in.html

@SupportedAnnotationTypes("com.myTraining.Annotation.Metrics")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class MetricsProcessor extends AbstractProcessor {
    
    @Override
    public boolean process(Set<? extends TypeElement> arg0,
            RoundEnvironment roundEnv) {

        StringBuilder message = new StringBuilder();

        for (Element elem : roundEnv.getElementsAnnotatedWith(Metrics.class)) {
            Metrics implementation = elem.getAnnotation(Metrics.class);

            message.append("Methods found in " + elem.getSimpleName().toString() + ":\n");

            for (Method method : implementation.getClass().getMethods()) {
                message.append(method.getName() + "\n");
            }

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message.toString());
        }

        return false; // allow others to process this annotation type
    }
}
