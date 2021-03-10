package onim.en.tldev.module;

import com.google.common.base.Strings;

import onim.en.tldev.annotation.Save;

public abstract class Module {

  public enum State {
    ENABLE("enable", "on", "有効"), DISABLE("disable", "off", "無効"), UNKNOWN;

    private final String[] aliases;

    private State() {
      aliases = new String[] {};
    }

    private State(String... aliases) {
      this.aliases = aliases;
    }

    public static State getState(String text) {
      for (State status : State.values()) {
        for (String alias : status.aliases) {
          if (Strings.isNullOrEmpty(alias)) {
            continue;
          }
          if (alias.equalsIgnoreCase(text)) {
            return status;
          }
        }
      }
      return State.UNKNOWN;
    }
  }

  private String id;
  private String name;
  private String category;

  @Save
  private boolean enabled = false;

  public final String getName() {
    return this.name;
  }

  public final String getId() {
    return this.id;
  }

  public final String getCategory() {
    return this.category;
  }

  public final boolean isEnabled() {
    return this.enabled;
  }

  public void onEnable() {
    this.enabled = true;
  }

  public void onDisable() {
    this.enabled = false;
  }
}
