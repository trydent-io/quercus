package io.artoo.lance.query.many.oftwo;

import io.artoo.lance.func.Func;
import io.artoo.lance.literator.Cursor;
import io.artoo.lance.literator.cursor.routine.sort.Sort;
import io.artoo.lance.query.Many;
import io.artoo.lance.query.Queryable;
import io.artoo.lance.query.many.Ordering.Arrange;
import io.artoo.lance.tuple.Pair;

import java.util.Arrays;

import static io.artoo.lance.literator.cursor.routine.sort.Sort.By;
import static io.artoo.lance.literator.cursor.routine.sort.Sort.byDefault;
import static io.artoo.lance.query.many.Ordering.Arrange.asc;

public sealed interface Ordering<A, B> extends Many.OfTwo<A, B> {
  default <R> Ordering<A, B> by(Func.Bi<? super A, ? super B, ? extends R> field) {
    return by(field, asc);
  }

  default <R> Ordering<A, B> by(Func.Bi<? super A, ? super B, ? extends R> field, Arrange arrange) {
    return new OrderBy<>(this, By.with(pair -> field.tryApply(pair.first(), pair.second()), arrange));
  }
}

final class Order<A, B> implements Ordering<A, B> {
  private final Queryable.OfTwo<A, B> queryable;

  Order(final Queryable.OfTwo<A, B> queryable) {this.queryable = queryable;}

  @Override
  public Cursor<Pair<A, B>> cursor() {
    return queryable.cursor().to(byDefault());
  }

  @Override
  public <R> Ordering<A, B> by(final Func.Bi<? super A, ? super B, ? extends R> field, final Arrange arrange) {
    return new OrderBy<>(queryable, By.with(pair -> field.tryApply(pair.first(), pair.second()), arrange));
  }
}

final class OrderBy<A, B> implements Ordering<A, B> {
  private final Queryable.OfTwo<A, B> queryable;
  private final By<Pair<A, B>, Object>[] bys;

  @SafeVarargs
  OrderBy(final Queryable.OfTwo<A, B> queryable, final By<Pair<A, B>, Object>... bys) {
    this.queryable = queryable;
    this.bys = bys;
  }

  @Override
  public Cursor<Pair<A, B>> cursor() {
    return queryable.cursor().to(Sort.arranged(bys));
  }

  @Override
  public <R> Ordering<A, B> by(final Func.Bi<? super A, ? super B, ? extends R> field, final Arrange arrange) {
    final var copied = Arrays.copyOf(bys, bys.length + 1);
    copied[bys.length] = By.with(pair -> field.tryApply(pair.first(), pair.second()), arrange);
    return new OrderBy<>(queryable, copied);
  }
}

