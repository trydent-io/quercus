package io.artoo.lance.query.many;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.artoo.lance.query.Many.fromAny;
import static org.assertj.core.api.Assertions.assertThat;

public class MatchableTest {
  @Test
  @DisplayName("should match when string")
  public void shouldMatchWhenStringOrCharacter() {
    enum Value { Default; int val = 0; }

    fromAny("returning", 1, 12.3, true, 'a')
      .when(String.class, it -> { Value.Default.val = it.length(); })
      .when(Character.class, it -> { Value.Default.val++; })
      .eventually();

    assertThat(Value.Default.val).isEqualTo(6);
  }

  @Test
  @DisplayName("should match when string")
  public void shouldMatchWhenString() {
    enum Value { Default; int val = 0; }

    fromAny("returning", 1, 12.3, true, 'a')
      .when(String.class, it -> { Value.Default.val = it.length(); })
      .when(Character.class, it -> { Value.Default.val++; })
      .eventually();

    assertThat(Value.Default.val).isEqualTo(6);
  }
}
