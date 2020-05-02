package io.artoo.query.many;



import io.artoo.query.Queryable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static io.artoo.type.Nullability.nonNullable;

public interface Convertable<T> extends Queryable<T> {
  default @NotNull <K, E> Map<? extends K, ? extends E> asMap(final Function<? super T, ? extends K> key, final Function<? super T, ? extends E> element) {
    nonNullable(key, "key");
    nonNullable(element, "element");
    final var map = new ConcurrentHashMap<K, E>();
    for (final var value : this)
      map.put(key.apply(value), element.apply(value));
    return map;
  }

  default Collection<T> asCollection() {
    return asList();
  }

  default List<T> asList() {
    final List<T> list = new ArrayList<>();
    for (final var value : this)
      list.add(value);
    return list;
  }

  default T[] asArray(final Function<? super Integer, T[]> initializer) {
    nonNullable(initializer, "initializer");
    final var ts = ((Countable<T>) this::iterator)
      .count()
      .select(initializer::apply)
      .asIs();

    var i = 0;
    for (final var value : this) ts[i++] = value;

    return ts;
  }
}
