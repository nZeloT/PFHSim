package sim.bank;

import sim.Event;
import sim.ExceptionCategorie;

public class BankAccount {
	
	private static final double DEBT_INTEREST_RATE = 0.05d; // 5%
	private static final int    DEBT_LIMIT = - 300000;
	
	private int cash;
	
	private int oldSaldo;
	private int saldo;
	
	private boolean onLimit;
	
	private Event<Integer> cashChanged;
	
	public BankAccount(int startCash) {
		cash = startCash;
		saldo = 0;
		oldSaldo = 0;
		onLimit = false;
	}
	
	public boolean canBeCharged(int amount){
		return amount > 0 && cash - amount >= DEBT_LIMIT;
	}
	
	public void charge(int amount) throws BankException{
		if(amount <= 0)
			throw new BankException(this, "Amount needs so be greater than 0!", ExceptionCategorie.PROGRAMMING_ERROR);
		
		if(cash - amount < DEBT_LIMIT)
			throw new BankException(this, "Cannot handle Transaction because debt limit is reached!", ExceptionCategorie.ERROR);
		
		int old = cash;
		cash -= amount;
		
		saldo -= amount;
		
		if(cashChanged != null)
			cashChanged.changed(old, cash);
	}
	
	public void deposit(int amount) throws BankException{
		if(amount <= 0)
			throw new BankException(this, "Amount needs so be greater than 0!", ExceptionCategorie.PROGRAMMING_ERROR);
		
		int old = cash;
		cash += amount;
		
		saldo += amount;
		
		if(cashChanged != null)
			cashChanged.changed(old, cash);
	}
	
	public void simStep() throws BankException {
		if(cash < 0){
			try{
				charge((int) (cash * DEBT_INTEREST_RATE));
			}catch(BankException e){
				onLimit = true;
				throw new BankException(this, "Can no pay interests!!", ExceptionCategorie.ERROR);
			}
		}
		
		oldSaldo = saldo;
		saldo = 0;
	}
	
	public int getSaldo() {
		return oldSaldo;
	}
	
	public int getCash() {
		return cash;
	}
	
	public boolean isOnLimit() {
		return onLimit;
	}
	
	public void setCashChanged(Event<Integer> cashChanged) {
		this.cashChanged = cashChanged;
	}
	
}
