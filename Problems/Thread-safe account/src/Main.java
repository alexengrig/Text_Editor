class Account {

    private long balance = 0;

    public synchronized boolean withdraw(long amount) {
        long result = balance - amount;
        if (result >= 0) {
            balance = result;
            return true;
        }
        return false;
    }

    public synchronized void deposit(long amount) {
        balance += amount;
    }

    public long getBalance() {
        return balance;
    }
}