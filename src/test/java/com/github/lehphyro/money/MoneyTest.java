package com.github.lehphyro.money;

import java.math.BigDecimal;

import com.google.common.collect.ImmutableList;

import org.junit.Test;

import static com.github.lehphyro.money.CommonCurrencies.EUR;
import static org.junit.Assert.assertEquals;

public class MoneyTest {

  @Test
  public void testOf() {
    assertEquals("EUR 1,23", Money.of(1, 23, EUR).toString());
    assertEquals("EUR 1,23", Money.of(BigDecimal.valueOf(123, 2), EUR).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOfSmallMinor() {
    Money.of(0, -1, EUR);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOfTooBigMinor() {
    Money.of(0, 100, EUR);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOfOverScaledValue() {
    Money.of(BigDecimal.valueOf(10.9282), EUR);
  }

  @Test
  public void testPlus() {
    Money m1 = Money.of(1, 53, EUR);
    Money m2 = Money.of(2, 76, EUR);
    assertEquals(Money.of(4, 29, EUR), m1.plus(m2));
    assertEquals(Money.of(3, 21, EUR), m1.plus(1, 68));
  }

  @Test
  public void testMinus() {
    Money m1 = Money.of(0, 54, EUR);
    Money m2 = Money.of(1, 89, EUR);
    assertEquals(Money.of(-1, 35, EUR), m1.minus(m2));
    assertEquals(Money.of(0, 10, EUR), m1.minus(0, 44));
  }

  @Test
  public void testMultiply() {
    Money m1 = Money.of(1, 20, EUR);
    assertEquals(Money.of(2, 40, EUR), m1.multiply(2));
    m1 = Money.of(-1, 20, EUR);
    assertEquals(Money.of(2, 40, EUR).negate(), m1.multiply(2));
  }

  @Test
  public void testDivide() {
    Money m1 = Money.of(1, 0, EUR);
    assertEquals(ImmutableList.of(Money.of(0, 50, EUR), Money.of(0, 50, EUR)), m1.divide(2));

    m1 = Money.of(100, 0, EUR);
    assertEquals(ImmutableList.of(Money.of(33, 34, EUR), Money.of(33, 33, EUR), Money.of(33, 33, EUR)), m1.divide(3));
  }

  @Test
  public void testSum() {
    ImmutableList<Money> monies = ImmutableList.of(Money.of(1, 2, EUR), Money.of(2, 34, EUR), Money.of(0, 76, EUR));
    assertEquals(Money.of(4, 12, EUR), Money.sum(monies));
  }

  @Test
  public void testEquals() {
    assertEquals(Money.of(2, 50, EUR), Money.of(BigDecimal.valueOf(2.5), EUR));
    assertEquals(Money.of(2, 56, EUR), Money.of(BigDecimal.valueOf(2.56), EUR));
  }

  @Test
  public void testPercentageOf() {
    Money money = Money.of(1, 0, EUR);
    assertEquals(0.1, money.percentageOf(Money.of(10, 0, EUR)), 0.0001);
    money = Money.of(774, 27, EUR);
    assertEquals(0.0042, money.percentageOf(Money.of(182737, 9, EUR)), 0.0001);
  }
}
