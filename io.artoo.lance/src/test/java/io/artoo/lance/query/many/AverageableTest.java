package io.artoo.lance.query.many;

import io.artoo.lance.query.Many;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

class AverageableTest {
  @Test
  @DisplayName("should average doubles")
  void shouldAverageDoubles() {
    final var averaged = Many.from(78.0, 92.0, 100.0, 37.0, 81.0).average().yield();

    final var expected = 77.6;
    assertThat(averaged).isEqualTo(expected);
  }

  @Test
  @DisplayName("should average integers")
  void shouldAverageIntegers() {
    final var averaged = Many.from(78, 92, 100, 37, 81).average().yield();

    final var expected = 77.6;
    assertThat(averaged).isEqualTo(expected);
  }

  @Test
  @DisplayName("should reduce to average even with nullables")
  void shouldReduceAverageWithNullables() {
    final var averaged = Many.fromAny(null, 10007L, 37L, 399846234235L).average().yield();

    assertThat(averaged).isEqualTo(133282081426.33333);
  }

  @Test
  @DisplayName("should reduce to average with a selector")
  void shouldReduceAverageWithSelector() {
    final var average = Many.from("apple", "banana", "mango", "orange", "passionfruit", "grape").average(String::length).yield();

    assertThat(average).isEqualTo(6.5);
  }

  @Test
  @DisplayName("should be null average since there's no numbers")
  void shouldBeNullSinceNoNumbers() {
    for (final var ignored : Many.from("apple", "banana", "mango", "orange", "passionfruit", "grape").average()) {
      fail();
    }
  }
}
