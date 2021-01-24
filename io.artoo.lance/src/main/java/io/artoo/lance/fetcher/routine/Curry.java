package io.artoo.lance.fetcher.routine;

import io.artoo.lance.fetcher.Cursor;
import io.artoo.lance.fetcher.Fetcher;
import io.artoo.lance.func.Func;
import io.artoo.lance.query.Many;

import java.util.Iterator;

final class Curry<T, R> implements Routine<T, R> {
  private final Func.Uni<? super T, ? extends R> map;

  Curry(final Func.Uni<? super T, ? extends R> map) {this.map = map;}

  @SuppressWarnings("unchecked")
  @Override
  public Func.Uni<T[], Cursor<R>> onArray() {
    return prevs -> {
      final var rs = (R[]) new Object[prevs.length];
      for (var i = 0; i < prevs.length; i++) {
        rs[i] = map.tryApply(prevs[i]);
      }
      return Cursor.open(rs);
    };
  }

  @SuppressWarnings("unchecked")
  @Override
  public Func.Uni<Fetcher<T>, Cursor<R>> onFetcher() {
    return fetcher -> {
      var many = Many.<R>empty();
      while (fetcher.hasNext()) {
        many = many.concat(map.tryApply(fetcher.fetch()));
      }
      return many.cursor();
    };
  }

  @SuppressWarnings("unchecked")
  @Override
  public Func.Uni<Iterator<T>, Cursor<R>> onIterator() {
    return iterator -> {
      var many = Many.<R>empty();
      while (iterator.hasNext()) {
        many = many.concat(map.tryApply(iterator.next()));
      }
      return many.cursor();
    };
  }

  @Override
  public Func.Uni<T, Cursor<R>> onPlain() {
    return it -> Cursor.maybe(map.tryApply(it));
  }
}
