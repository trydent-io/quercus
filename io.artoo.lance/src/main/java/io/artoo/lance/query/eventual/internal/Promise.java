package io.artoo.lance.query.eventual.internal;

import io.artoo.lance.func.Func;
import io.artoo.lance.func.Suppl;

import java.util.concurrent.Executors;

import static java.util.stream.IntStream.range;

public interface Promise<T> extends Next<T> {
  static <T> Promise<T> async(Suppl.Uni<T> job) {
    return new Async<>(job, it -> it);
  }

  static void main(String[] args) {
    Promise.async(() -> range(0, Integer.MAX_VALUE)
      .mapToObj(it -> it * 2)
      .map(it -> it * 2)
      .reduce(0, Integer::sum)
    )
      .select(it -> it * 2)
      .select(it -> {
        System.out.println(it);
        return it;
      })
      .await();

    System.out.println("Io dovrei esser stampato subito subitissimo!");
  }
}

final class Async<T, R> implements Promise<R> {
  private final Suppl.Uni<T> job;
  private Func.Uni<? super T, ? extends R> tick;

  Async(final Suppl.Uni<T> job, final Func.Uni<? super T, ? extends R> tick) {
    this.job = job;
    this.tick = tick;
  }

  @Override
  public void await() {
    final var service = Executors.newSingleThreadExecutor();
    service.submit(() -> tick.apply(job.call()));
  }

  public <P> Next<P> select(final Func.Uni<? super R, ? extends P> select) {
    return new Async<>(this.job, tick.then(select));
  }
}
