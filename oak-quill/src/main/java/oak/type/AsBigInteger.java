package oak.type;

import oak.collect.cursor.Cursor;
import oak.func.Functional;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Iterator;

@FunctionalInterface
public interface AsBigInteger extends Iterable<BigInteger>, Functional.Sup {
  BigInteger get();

  @NotNull
  @Override
  default Iterator<BigInteger> iterator() { return Cursor.of(get()); }
}