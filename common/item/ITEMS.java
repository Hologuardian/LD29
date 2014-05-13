package holo.common.item;

public enum ITEMS 
{
	DULLBLADE(0, 0, new ItemSword(ItemRenderHelper.dullSword, 5, 1800, 300, new float[]{44.0F, 58.0F}), "Dull Sword"),
	SHARPBLADE(1, 0, new ItemSword(ItemRenderHelper.sharpSword, 7, 2800, 200, new float[]{46.0F, 64.0F}), "Sharp Sword"),
	MOLTENBLADE(2, 0, new ItemSword(ItemRenderHelper.moltenSword, 16, 4500, 450, new float[]{73.0F, 105.0F}), "Molten Sword"),
	CROSSBOW(0, 1, new ItemBow(ItemRenderHelper.crossbow, "res/textures/entity/Bolt.png", 700, 3, 2500, new float[]{26, 7}, new float[]{28, 31}), "Crossbow"),
	MOLTENCROSSBOW(2, 1, new ItemBow(ItemRenderHelper.moltenCrossbow, "res/textures/entity/MoltenBolt.png", 700, 8, 6500, new float[]{32, 19}, new float[]{48, 63}), "Molten Crossbow"),
	DIMLANTERN(0, 2, new ItemLantern(new String[]{"res/textures/item/DimLantern.png"}, 64), "Dim Lantern"),
	LANTERN(1, 2, new ItemLantern(new String[]{"res/textures/item/Lantern.png"}, 96), "Lantern"),
	BRIGHTLANTERN(2, 2, new ItemLantern(new String[]{"res/textures/item/Lantern.png"}, 128), "Bright Lantern");
	private int tier;
	private int slot;
	private Item itemClass;
	private String name;
	
	ITEMS(int tier, int slot, Item itemClass, String name)
	{
		this.setTier(tier);
		this.setSlot(slot);
		this.setItem(itemClass);
		this.setName(name);
	}
	
	public int getTier()
	{
		return this.tier;
	}
	
	public int getSlot()
	{
		return this.slot;
	}
	
	public Item getItem()
	{
		return this.itemClass;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setTier(int tier)
	{
		this.tier = tier;
	}
	
	public void setSlot(int slot)
	{
		this.slot = slot;
	}
	
	public void setItem(Item itemClass)
	{
		this.itemClass = itemClass;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}
