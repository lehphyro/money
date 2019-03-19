package com.github.lehphyro.money;

import java.util.stream.Stream;

public enum CommonCurrencies implements Currency {
  BRL("Brazilian Real", "BRL", "R$"),
  CAD("Canadian Dollar", "CAD", "C$"),
  CHF("Swiss Franc", "CHF", "CHf"),
  DKK("Danish Krone", "DKK", "Kr."),
  EUR("Euro", "EUR", "€"),
  GBP("British Pound", "GBP", "£"),
  NOK("Norwegian Kroner", "NOK", "kr"),
  SEK("Swedish Krona", "SEK", "kr"),
  USD("US Dollar", "USD", "$");

  private final String name;
  private final String code;
  private final String symbol;

  CommonCurrencies(final String name, final String code, final String symbol) {
    this.name = name;
    this.code = code;
    this.symbol = symbol;
  }

  public static CommonCurrencies valueOfCode(String code) {
    return Stream.of(values())
        .filter(v -> v.code.equals(code))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown currency code: " + code));
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getSymbol() {
    return symbol;
  }
}
