package oak.query.project;

import oak.func.con.Consumer1;
import oak.query.Maybe;

import java.util.Iterator;

final class LookMaybe<S> implements Maybe<S> {
  private final Maybe<S> maybe;
  private final Consumer1<S> peek;

  LookMaybe(final Maybe<S> maybe, final Consumer1<S> peek) {
    this.maybe = maybe;
    this.peek = peek;
  }

  @Override
  public final Iterator<S> iterator() {
    if (maybe.iterator().hasNext()) peek.accept(maybe.iterator().next());
    return maybe.iterator();
  }
}