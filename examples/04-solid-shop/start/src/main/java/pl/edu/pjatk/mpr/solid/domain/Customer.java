package pl.edu.pjatk.mpr.solid.domain;

import java.util.Objects;

public final class Customer {
  public enum Type {
    REGULAR,
    VIP,
    EMPLOYEE
  }

  private final String email;
  private final Type type;

  public Customer(String email, Type type) {
    this.email = Objects.requireNonNull(email);
    this.type = Objects.requireNonNull(type);
  }

  public String getEmail() {
    return email;
  }

  public Type getType() {
    return type;
  }
}
