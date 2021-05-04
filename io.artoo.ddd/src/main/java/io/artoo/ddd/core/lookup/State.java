package io.artoo.ddd.core.lookup;

import io.artoo.ddd.core.Domain;
import io.artoo.ddd.core.Id;
import io.artoo.lance.func.Pred;
import io.artoo.lance.literator.Cursor;
import io.artoo.lance.query.Many;

public interface State extends Many<Ledger.Change> {
  State ensure(final Pred.Uni<? super Many<Ledger.Change>> validation);

  void commit(Domain.Fact... newFacts);
}

final class Dump implements State {
  private final Ledger ledger;
  private final Id id;
  private final Pred.Uni<? super Many<Ledger.Change>> validation;

  Dump(final Ledger ledger, final Id id) {
    this(
      ledger,
      id,
      ignored -> true
    );
  }

  private Dump(final Ledger ledger, final Id id, final Pred.Uni<? super Many<Ledger.Change>> validation) {
    this.ledger = ledger;
    this.id = id;
    this.validation = validation;
  }

  @Override
  public State ensure(final Pred.Uni<? super Many<Ledger.Change>> validation) {
    return new Dump(ledger, id, validation);
  }

  @Override
  public void commit(final Domain.Fact... newFacts) {
    for (final var newFact : newFacts)
      ledger.attach(id, newFact);
  }

  @Override
  public Cursor<Ledger.Change> cursor() {
    return ledger
      .where(past -> past.stateId().is(id) && validation.tryApply(Many.from(past.changes())))
      .selection(past -> Many.from(past.changes()))
      .cursor();
  }
}