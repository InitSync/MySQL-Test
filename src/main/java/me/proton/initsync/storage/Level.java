package me.proton.initsync.storage;

public class Level
{
	// Declaring a variable called `amount` that is of type `int` and is `private`.
	private int amount;
	
	// This is a constructor. It is called when the class is instantiated.
	public Level()
	{
		this.amount = 1;
	}
	
	/**
	 * Increase the amount by the given amount.
	 *
	 * @param amount The amount of the resource to increase.
	 */
	public void increase(int amount)
	{
		this.amount = this.amount + amount;
	}
	
	/**
	 * Decrease the amount by the given amount.
	 *
	 * @param amount The amount of the item in the inventory
	 */
	public void decrease(int amount)
	{
		this.amount = this.amount - amount;
	}
	
	/**
	 * This function sets the amount of money in the wallet.
	 *
	 * @param amount The amount of the item to be added.
	 */
	public void set(int amount)
	{
		this.amount = amount;
	}
	
	/**
	 * This function returns the amount of money in the wallet.
	 *
	 * @return The amount of the item.
	 */
	public int amount()
	{
		return this.amount;
	}
}
