package de.derjonk.home_sweet_home;

public abstract class AccountingEntryBuilder<T extends AccountingEntry> {
    private final T entry;

    protected AccountingEntryBuilder(T entry) {
        this.entry = entry;
    }

    public AccountingEntryBuilder<T> forAccount(final Account account) {
        entry.setAccount(account);
        return this;
    }

    public AccountingEntryBuilder<T> withAmount(final Integer amount) {
        entry.setAmount(amount);
        return this;
    }

    public AccountingEntryBuilder<T> withTitle(final String title) {
        entry.setTitle(title);
        return this;
    }

    public T end() {
        return entry;
    }
}
