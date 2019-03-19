package com.github.lehphyro.money;

public enum CommonCurrencies implements Currency {
  EURO("Euro", "EUR", "€");

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
