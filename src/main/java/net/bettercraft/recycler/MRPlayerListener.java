package net.bettercraft.recycler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MRPlayerListener implements Listener
{
  protected static final double SUCCESS_CHANCE = 1.0D;
  protected MRRecipes recipes = new MRRecipes();
  protected Random random = new Random();

  public void onPlayerInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    Action action = event.getAction();
    Block block = event.getClickedBlock();
    World world = player.getWorld();

    if (action == Action.RIGHT_CLICK_BLOCK)
    {
      if (block.getTypeId() == 41)
      {
        ItemStack isInHand = player.getItemInHand();
        int typeId = isInHand.getTypeId();
        short damageValue = isInHand.getDurability();

        ItemStack isToRecycle = this.recipes.getItemStackToRecycle(typeId, damageValue);

        if (isToRecycle == null) {
          player.sendMessage("You can't recycle that!");
          return;
        }

        if (isInHand.getAmount() < isToRecycle.getAmount()) {
          player.sendMessage("You don't have enough of those to recycle!");
          return;
        }

        player.setItemInHand(new ItemStack(typeId, isInHand.getAmount() - isToRecycle.getAmount(), damageValue));

        double currentSuccessChance = 1.0D;
        short maxDamage = this.recipes.getMaxDamage(typeId);
        if (maxDamage > 0) currentSuccessChance *= (maxDamage - damageValue) / maxDamage;

        if (this.random.nextDouble() <= currentSuccessChance)
        {
          ArrayList<ItemStack> giveToPlayer = this.recipes.getItemStacksToGivePlayer(typeId, damageValue);

          for (ItemStack isi : giveToPlayer) {
            HashMap dropOnPlayer = player.getInventory().addItem(new ItemStack[] { isi });

            Location playerLocation = player.getLocation();
            ItemStack isj;
            for (Iterator localIterator2 = dropOnPlayer.values().iterator(); localIterator2.hasNext(); world.dropItem(playerLocation, isj)) isj = (ItemStack)localIterator2.next(); 
          }
        }
        else
        {
          player.sendMessage("You botched the recycling due to item damage!");
        }

        player.updateInventory();
      }
    }
  }
}