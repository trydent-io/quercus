package io.artoo.lance.query.many;

import io.artoo.lance.query.TestData.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.artoo.lance.query.Many.from;
import static org.assertj.core.api.Assertions.assertThat;

class AggregatableTest {
  @Test
  @DisplayName("should reduce to the longest name")
  void shouldReduceLongestName() {
    final var aggregated = from("apple", "mango", "orange", "passionfruit", "grape")
      .aggregate("", String::toUpperCase, (longest, next) -> longest.length() > next.length() ? longest : next)
      .yield();

    assertThat(aggregated).isEqualTo("PASSIONFRUIT");
  }

  @Test
  @DisplayName("should reduce to the total for even numbers")
  void shouldReduceTotalForEvens() {
    final var aggregated = from(4, 8, 8, 3, 9, 0, 7, 8, 2)
      .aggregate(0, (total, next) -> next % 2 == 0 ? ++total : total)
      .yield();

    assertThat(aggregated).isEqualTo(6);
  }

  @Test
  @DisplayName("should reduce to reversed phrase")
  void shouldReduceReversePhrase() {
    final var aggregated = from("the quick brown fox jumps over the lazy dog".split(" "))
      .aggregate((reversed, next) -> next + " " + reversed)
      .yield();

    assertThat(aggregated).isEqualTo("dog lazy the over jumps fox brown quick the");
  }

  @Test
  @DisplayName("should find the max and min by selector")
  void shouldFindMaxAndMin() {
    final var pets = from(
      new Pet("Barley", 8),
      new Pet("Boots", 4),
      new Pet("Whiskers", 1)
    );

    final var max = pets.max(pet -> pet.name().length() + pet.age()).yield();

    final var min = pets.min(pet -> pet.name().length()).yield();

    assertThat(max).isEqualTo(14);
    assertThat(min).isEqualTo(5);
  }
}

