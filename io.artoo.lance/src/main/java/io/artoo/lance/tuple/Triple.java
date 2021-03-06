package io.artoo.lance.tuple;

import io.artoo.lance.func.Cons;
import io.artoo.lance.func.Func;
import io.artoo.lance.func.Pred;
import org.jetbrains.annotations.NotNull;

import static io.artoo.lance.tuple.Type.firstOf;
import static io.artoo.lance.tuple.Type.has;
import static io.artoo.lance.tuple.Type.secondOf;
import static io.artoo.lance.tuple.Type.thirdOf;

public interface Triple<A, B, C> extends Tuple {
  default A first() { return firstOf(this); }
  default B second() { return secondOf(this); }
  default C third() { return thirdOf(this); }

  default <T extends Record> T to(final @NotNull Func.Tri<? super A, ? super B, ? super C, ? extends T> to) {
    return to.apply(first(), second(), third());
  }

  default <T> T as(final @NotNull Func.Tri<? super A, ? super B, ? super C, ? extends T> as) {
    return as.apply(first(), second(), third());
  }

  default boolean is(final A value1, final B value2, final C value3) {
    return has(first(), value1) && has(second(), value2) && has(third(), value3);
  }

  default <R extends Record & Triple<A, B, C>> R map(Func.Tri<? super A, ? super B, ? super C, ? extends R> map) {
    return map.apply(first(), second(), third());
  }

  default <R extends Record & Triple<A, B, C>, F extends Record & Single<R>> R flatMap(Func.Tri<? super A, ? super B, ? super C, ? extends F> func) {
    return func.apply(first(), second(), third()).first();
  }

  default Triple<A, B, C> peek(Cons.Tri<? super A, ? super B, ? super C> cons) {
    cons.apply(first(), second(), third());
    return this;
  }

}
