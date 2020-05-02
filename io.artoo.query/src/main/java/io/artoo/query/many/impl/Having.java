package io.artoo.query.many.impl;


import io.artoo.query.Many;
import io.artoo.query.Queryable;
import io.artoo.query.many.Grouping;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiPredicate;

public final class Having<K, T> implements Many<Grouping.Bag<K, T>> {
  private final Queryable<Grouping.Bag<K, T>> queryable;
  private final BiPredicate<? super K, ? super Many<T>> having;

  @Contract(pure = true)
  public Having(final Queryable<Grouping.Bag<K, T>> queryable, final BiPredicate<? super K, ? super Many<T>> having) {
    this.queryable = queryable;
    this.having = having;
  }

  @NotNull
  @Override
  public Iterator<Grouping.Bag<K, T>> iterator() {
    final var result = new ArrayList<Grouping.Bag<K, T>>();
    for (final var bag : queryable) {
      if (bag.as(having::test)) {
        result.add(bag);
      }
    }
    return result.iterator();
  }
}