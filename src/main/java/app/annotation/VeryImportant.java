package app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME) // available at runtime
@Target({ElementType.TYPE})          // can annotate classes only
public @interface VeryImportant {
}

