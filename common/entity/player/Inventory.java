package holo.common.entity.player;

import holo.common.item.*;

public class Inventory 
{
	public ITEMS[] items;
	public int selectedSlot = 0;
	
	public Inventory(int slots)
	{
		items = new ITEMS[slots];
	}
	
	public void selectSlot(int slot)
	{
		selectedSlot = slot;
	}
	
	public int getSelectedSlot()
	{
		return selectedSlot;
	}
	
	public ITEMS getSelectedItem()
	{
		return items[selectedSlot];
	}
	
	public void putItem(ITEMS item)
	{
		items[item.getSlot()] = item;
	}
}
