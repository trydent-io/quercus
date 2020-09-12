package io.artoo.lance.func;

import io.artoo.lance.func.Func.Nothing;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static io.artoo.lance.func.Func.Nothing.Nil;

public interface Cons {
  interface Uni<A> extends Consumer<A>, Func.Uni<A, Nothing> {
    static <T> Uni<T> nothing() {
      return it -> {};
    }

    void tryAccept(A a) throws Throwable;

    @Override
    default Nothing tryApply(A a) throws Throwable {
      tryAccept(a);
      return Nil;
    }

    @Override
    default void accept(A a) {
      try { tryAccept(a); } catch (Throwable ignored) {}
    }

    @Override
    default Nothing apply(A a) {
      accept(a);
      return Nil;
    }
  }

  interface Bi<A, B> extends BiConsumer<A, B>, Func.Bi<A, B, Nothing> {
    void tryAccept(A a, B b) throws Throwable;

    @Override
    default Nothing tryApply(A a, B b) throws Throwable {
      tryAccept(a, b);
      return Nil;
    }

    @Override
    default void accept(A a, B b) {
      try { tryAccept(a, b); } catch (Throwable ignored) {
        ignored.printStackTrace();
      }
    }

    @Override
    default Nothing apply(A a, B b) {
      accept(a, b);
      return Nil;
    }
  }

  interface Tri<A, B, C> extends Func.Tri<A, B, C, Nothing> {
    void tryAccept(A a, B b, C c) throws Throwable;

    @Override
    default Nothing tryApply(A a, B b, C c) throws Throwable {
      tryAccept(a, b, c);
      return Nil;
    }

    default void accept(A a, B b, C c) {
      try { tryAccept(a, b, c); } catch (Throwable ignored) {}
    }

    @Override
    default Nothing apply(A a, B b, C c) {
      accept(a, b, c);
      return Nil;
    }
  }

  interface Quad<A, B, C, D> extends Func.Quad<A, B, C, D, Nothing> {
    void tryAccept(A a, B b, C c, D d) throws Throwable;

    @Override
    default Nothing tryApply(A a, B b, C c, D d) throws Throwable {
      tryAccept(a, b, c, d);
      return Nil;
    }

    default void accept(A a, B b, C c, D d) {
      try { tryAccept(a, b, c, d); } catch (Throwable ignored) {}
    }

    @Override
    default Nothing apply(A a, B b, C c, D d) {
      accept(a, b, c, d);
      return Nil;
    }
  }
}
