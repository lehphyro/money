package com.github.lehphyro.money;

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
