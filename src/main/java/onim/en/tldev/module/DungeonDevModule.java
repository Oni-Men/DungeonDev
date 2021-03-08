package onim.en.tldev.module;

import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface DungeonDevModule {
  
  String id();

  String name();

  String category() default "その他";
}
