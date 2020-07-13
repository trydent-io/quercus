package io.artoo.lance.query.many;

import io.artoo.lance.func.Cons;
import io.artoo.lance.query.Many;
import io.artoo.lance.query.Queryable;
import io.artoo.lance.query.operation.Peek;

public interface Peekable<T> extends Queryable<T> {
  default Many<T> peek(Cons.Uni<? super T> peek) {
    return peek((index, it) -> peek.tryAccept(it));
  }

  default Many<T> peek(Cons.Bi<? super Integer, ? super T> peek) {
    return () -> cursor().map(new Peek<T, T>(peek).butNulls());
  }
}
