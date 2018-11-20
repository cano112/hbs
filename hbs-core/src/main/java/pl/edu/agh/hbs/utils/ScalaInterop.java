package pl.edu.agh.hbs.utils;

import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.util.Collection;

public class ScalaInterop {

    public static <T> Seq<T> toSeq(Collection<T> collection) {
        return JavaConverters.collectionAsScalaIterableConverter(collection).asScala().toSeq();
    }

    public static <T> Collection<T> toCollection(Seq<T> seq) {
        return JavaConverters.asJavaCollection(seq);
    }
}
