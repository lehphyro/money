package com.github.lehphyro.money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.ImmutableSet.toImmutableSet;

public final class Money implements Serializable, Comparable<Money> {

  private static final long serialVersionUID = 3720810196241002650L;

  private final long amount;
  private final Currency currency;

  private Money(long amount, final Currency currency) {
    this.amount = amount;
    this.currency = currency;
  }

  public static Money of(long major, int minor, Currency currency) {
    checkArgument(minor < 100, "Minor must be less than 100, received: %s", minor);
    checkArgument(minor >= 0, "Minor must be greater than or equal to 0, received: %s", minor);
    checkNotNull(currency, "Currency may not be null");
    if (major != 0) {
      minor = (int) Math.signum(major) * minor;
    }
    return new Money(major * 100 + minor, currency);
  }

  public static Money of(BigDecimal v, Currency currency) {
    checkArgument(v.scale() <= 2, "Value scale must be less than or equal to 2, received: %s", v);
    if (v.scale() == 1) {
      return new Money(v.unscaledValue().longValue() * 10, currency);
    }
    return new Money(v.unscaledValue().longValue(), currency);
  }

  public static Money sum(Iterable<Money> monies) {
    final ImmutableSet<Currency> currencies = Streams.stream(monies).map(Money::getCurrency).collect(toImmutableSet());
    checkArgument(currencies.size() == 1, "All monies must have the same currency: %s", monies);
    long total = 0L;
    for (Money money : monies) {
      total += money.amount;
    }
    return new Money(total, currencies.iterator().next());
  }

  private void checkSameCurrency(Currency other) {
    checkArgument(currency.equals(other), "Currencies must be the same, received: %s", other);
  }

  public Money plus(Money arg) {
    checkNotNull(arg, "Cannot sum with null value");
    checkSameCurrency(arg.currency);
    return new Money(amount + arg.amount, currency);
  }

  public Money plus(long major, int minor) {
    return new Money(amount + major * 100 + minor, currency);
  }

  public Money minus(Money arg) {
    checkNotNull(arg, "Cannot subtract with null value");
    checkSameCurrency(arg.currency);
    return new Money(amount - arg.amount, currency);
  }

  public Money minus(long major, int minor) {
    return new Money(amount - major * 100 - minor, currency);
  }

  public Money negate() {
    return new Money(-amount, currency);
  }

  public Money multiply(long arg) {
    return new Money(amount * arg, currency);
  }

  public List<Money> divide(int denominator) {
    List<Money> result = new ArrayList<>(denominator);
    long simpleResult = amount / denominator;
    int remainder = (int) (amount - simpleResult * denominator);
    for (int i = 0; i < denominator; i++) {
      if (i < remainder) {
        result.add(new Money(simpleResult + 1, currency));
      } else {
        result.add(new Money(simpleResult, currency));
      }
    }
    return result;
  }

  public boolean isGreaterThan(Money arg) {
    checkNotNull(arg, "Cannot compare against null value");
    return compareTo(arg) > 0;
  }

  public boolean isLessThan(Money arg) {
    checkNotNull(arg, "Cannot compare against null value");
    return compareTo(arg) < 0;
  }

  public Money abs() {
    return isPositive() ? this : negate();
  }

  public long getMajorAmount() {
    return amount / 100;
  }

  public int getMinorAmount() {
    return (int) Math.abs(amount % 100);
  }

  public Currency getCurrency() {
    return currency;
  }

  public boolean isZero() {
    return amount == 0;
  }

  public boolean isPositive() {
    return amount > 0;
  }

  public boolean isNegative() {
    return amount < 0;
  }

  public double percentageOf(Money arg) {
    checkSameCurrency(arg.currency);
    return amount * 100.0 / arg.amount / 100;
  }

  @Override
  public int compareTo(Money arg) {
    checkSameCurrency(arg.currency);
    return Long.compare(amount, arg.amount);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Money money = (Money) o;
    return amount == money.amount && currency.equals(money.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, currency);
  }

  @Override
  public String toString() {
    return String.format("%s %d,%02d", getCurrency().getCode(), getMajorAmount(), getMinorAmount());
  }
}
