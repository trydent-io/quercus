package oak.query.many;

import oak.cursor.Cursor;
import oak.func.$2.Func;
import oak.func.Cons;
import oak.func.Pred;
import oak.query.Queryable;
import oak.query.one.One;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

final class Aggregate<T, A, R> implements One<A> {
  private final Queryable<T> queryable;
  private final Cons<? super T> peek;
  private final A seed;
  private final Pred<? super T> where;
  private final oak.func.Func<? super T, ? extends R> select;
  private final Func<? super A, ? super R, ? extends A> aggregate;

  Aggregate(
    final Queryable<T> queryable,
    final Cons<? super T> peek,
    final A seed,
    final Pred<? super T> where,
    final oak.func.Func<? super T, ? extends R> select,
    final Func<? super A, ? super R, ? extends A> aggregate
  ) {
    this.queryable = queryable;
    this.peek = peek;
    this.seed = seed;
    this.where = where;
    this.select = select;
    this.aggregate = aggregate;
  }

  @NotNull
  @Override
  public final Iterator<A> iterator() {
    var aggregated = seed;
    for (final var it : queryable) {
      peek.accept(it);
      if (where.test(it)) {
        final var apply = select.apply(it);
        aggregated = aggregate.apply(aggregated, apply);
      }
    }
    return Cursor.once(aggregated);
  }
}
