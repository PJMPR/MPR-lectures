package pl.edu.pjatk.mpr.solid.domain;

import java.util.Objects;

public final class Customer {
  private final String email;

  public Customer(String email) {
    this.email = Objects.requireNonNull(email);
  }

  public String getEmail() {
    return email;
  }
}
