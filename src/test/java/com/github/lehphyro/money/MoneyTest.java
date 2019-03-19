package com.github.lehphyro.money;

import java.math.BigDecimal;

import com.google.common.collect.ImmutableList;

import org.junit.Test;

import static com.github.lehphyro.money.CommonCurrencies.EURO;
import static org.junit.Assert.assertEquals;

public class MoneyTest {

  @Test
  public void testOf() {
    assertEquals("EUR 1,23", Money.of(1, 23, EURO).toString());
    assertEquals("EUR 1,23", Money.of(BigDecimal.valueOf(123, 2), EURO).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOfSmallMinor() {
    Money.of(0, -1, EURO);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOfTooBigMinor() {
    Money.of(0, 100, EURO);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOfOverScaledValue() {
    Money.of(BigDecimal.valueOf(10.9282), EURO);
  }

  @Test
  public void testPlus() {
    Money m1 = Money.of(1, 53, EURO);
    Money m2 = Money.of(2, 76, EURO);
    assertEquals(Money.of(4, 29, EURO), m1.plus(m2));
    assertEquals(Money.of(3, 21, EURO), m1.plus(1, 68));
  }

  @Test
  public void testMinus() {
    Money m1 = Money.of(0, 54, EURO);
    Money m2 = Money.of(1, 89, EURO);
    assertEquals(Money.of(-1, 35, EURO), m1.minus(m2));
    assertEquals(Money.of(0, 10, EURO), m1.minus(0, 44));
  }

  @Test
  public void testMultiply() {
    Money m1 = Money.of(1, 20, EURO);
    assertEquals(Money.of(2, 40, EURO), m1.multiply(2));
    m1 = Money.of(-1, 20, EURO);
    assertEquals(Money.of(2, 40, EURO).negate(), m1.multiply(2));
  }

  @Test
  public void testDivide() {
    Money m1 = Money.of(1, 0, EURO);
    assertEquals(2, m1.divide(2).size());
    assertEquals(Money.of(0, 50, EURO), m1.divide(2).get(0));
  }

  @Test
  public void testSum() {
    ImmutableList<Money> monies = ImmutableList.of(Money.of(1, 2, EURO), Money.of(2, 34, EURO), Money.of(0, 76, EURO));
    assertEquals(Money.of(4, 12, EURO), Money.sum(monies));
  }

  @Test
  public void testEquals() {
    assertEquals(Money.of(2, 50, EURO), Money.of(BigDecimal.valueOf(2.5), EURO));
    assertEquals(Money.of(2, 56, EURO), Money.of(BigDecimal.valueOf(2.56), EURO));
  }

  @Test
  public void testPercentageOf() {
    Money money = Money.of(1, 0, EURO);
    assertEquals(0.1, money.percentageOf(Money.of(10, 0, EURO)), 0.0001);
    money = Money.of(774, 27, EURO);
    assertEquals(0.0042, money.percentageOf(Money.of(182737, 9, EURO)), 0.0001);
  }
}
