package net.bettercraft.recycler;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class MRBlockListener implements Listener
{
  protected static final int SIGN_POST_ID = 63;
  protected static final int SIGN_WALL_ID = 68;
  protected static final int SIGN_ITEM_ID = 323;

  public void onBlockPlace(BlockPlaceEvent event)
  {
    if (event.getBlock().getTypeId() == 41)
      event.getPlayer().sendMessage("You have placed a golden anvil! Hold an item and right click the golden anvil to recycle the item.");
  }

  public void onBlockCanBuild(BlockCanBuildEvent event)
  {
    int materialId = event.getMaterialId();
    if ((materialId == 323) || (materialId == 68) || (materialId == 63)) return;

    if (event.getBlock().getTypeId() == 41) event.setBuildable(false);
  }
}