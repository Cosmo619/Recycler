package net.bettercraft.recycler;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.bukkit.inventory.ItemStack;

public class MRRecipes
{
  protected HashMap<String, ItemStack> itemStacksToRecycle = new HashMap();
  protected HashMap<String, ArrayList<ItemStack>> itemStacksToGivePlayer = new HashMap();
  protected HashMap<Integer, Integer> maxDamages = new HashMap();

  MRRecipes()
  {
    populateMaxDurabilities();
    populateItemStacks();
  }

  public ItemStack getItemStackToRecycle(int typeId, short damageValue)
  {
    ItemStack isToRecycle = (ItemStack)this.itemStacksToRecycle.get(Integer.toString(typeId));
    if (isToRecycle == null) isToRecycle = (ItemStack)this.itemStacksToRecycle.get(Integer.toString(typeId) + ":" + Short.toString(damageValue));
    return isToRecycle;
  }

  public short getMaxDamage(int typeId) {
		return this.maxDamages.get(typeId).shortValue();
  }

  public ArrayList<ItemStack> getItemStacksToGivePlayer(int typeId, short damageValue)
  {
    ArrayList isToGivePlayer = (ArrayList)this.itemStacksToGivePlayer.get(Integer.toString(typeId));
    if (isToGivePlayer == null) isToGivePlayer = (ArrayList)this.itemStacksToGivePlayer.get(Integer.toString(typeId) + ":" + Short.toString(damageValue));
    return isToGivePlayer;
  }

  private void populateMaxDurabilities()
  {
    this.maxDamages.put(259,65);
    this.maxDamages.put(346,33);
    this.maxDamages.put(359,239);
    this.maxDamages.put(283,33);
    this.maxDamages.put(285,33);
    this.maxDamages.put(284,33);
    this.maxDamages.put(286,33);
    this.maxDamages.put(294,33);
    this.maxDamages.put(268,60);
    this.maxDamages.put(270,60);
    this.maxDamages.put(269,60);
    this.maxDamages.put(271,60);
    this.maxDamages.put(290,60);
    this.maxDamages.put(272,132);
    this.maxDamages.put(274,132);
    this.maxDamages.put(273,132);
    this.maxDamages.put(275,132);
    this.maxDamages.put(291,132);
    this.maxDamages.put(267,251);
    this.maxDamages.put(257,251);
    this.maxDamages.put(256,251);
    this.maxDamages.put(258,251);
    this.maxDamages.put(292,251);
    this.maxDamages.put(276,1562);
    this.maxDamages.put(278,1562);
    this.maxDamages.put(277,1562);
    this.maxDamages.put(279,1562);
    this.maxDamages.put(293,1562);
    this.maxDamages.put(298,34);
    this.maxDamages.put(299,49);
    this.maxDamages.put(300,46);
    this.maxDamages.put(301,40);
    this.maxDamages.put(314,68);
    this.maxDamages.put(315,96);
    this.maxDamages.put(316,92);
    this.maxDamages.put(317,80);
    this.maxDamages.put(306,136);
    this.maxDamages.put(307,192);
    this.maxDamages.put(308,184);
    this.maxDamages.put(309,160);
    this.maxDamages.put(310,272);
    this.maxDamages.put(311,384);
    this.maxDamages.put(312,368);
    this.maxDamages.put(313,320);
  }

  private void populateItemStacks()
  {
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream("mrconfig.txt"))));

      Pattern ep = Pattern.compile("=");
      Pattern xp = Pattern.compile("x");
      Pattern cp = Pattern.compile(":");
      Pattern pp = Pattern.compile("\\|");
      String line;
      while ((line = br.readLine()) != null)
      {
        String[] wholeLine = ep.split(line);
        String[] itemInfo = xp.split(wholeLine[0]);
        String[] itemTypeDamage = cp.split(itemInfo[1]);
        String key;
        if (itemTypeDamage.length > 1) {
          ItemStack addAsRecycleInput = new ItemStack(Integer.parseInt(itemTypeDamage[0]), Integer.parseInt(itemInfo[0]), Short.parseShort(itemTypeDamage[1]));
          key = itemTypeDamage[0] + ":" + itemTypeDamage[1];
          this.itemStacksToRecycle.put(key, addAsRecycleInput);
        }
        else
        {
          ItemStack addAsRecycleInput = new ItemStack(Integer.parseInt(itemTypeDamage[0]), Integer.parseInt(itemInfo[0]));
          key = itemTypeDamage[0];
          this.itemStacksToRecycle.put(key, addAsRecycleInput);
        }

        ArrayList addAsRecycleOutput = new ArrayList();
        String[] recipeInfo = pp.split(wholeLine[1]);
        for (int i = 0; i < recipeInfo.length; i++) {
          String[] recipeIndividualInfo = xp.split(recipeInfo[i]);
          String[] recipeTypeDamage = cp.split(recipeIndividualInfo[1]);
          ItemStack currentAddAsRecycleOutput;
          if (recipeTypeDamage.length > 1) {
            currentAddAsRecycleOutput = new ItemStack(Integer.parseInt(recipeTypeDamage[0]), Integer.parseInt(recipeIndividualInfo[0]), Short.parseShort(recipeTypeDamage[1]));
          }
          else
          {
            currentAddAsRecycleOutput = new ItemStack(Integer.parseInt(recipeTypeDamage[0]), Integer.parseInt(recipeIndividualInfo[0]));
          }
          addAsRecycleOutput.add(currentAddAsRecycleOutput);
        }

        this.itemStacksToGivePlayer.put(key, addAsRecycleOutput);
      }

      br.close();
    } catch (FileNotFoundException e) {
      System.err.println("[MosekonRecycler][ERROR] Could not open mrconfig.txt. Is mrconfig.txt in the root of the MosekonRecycler .jar file?");
      System.err.println("[MosekonRecycler][ERROR] Not all recycling recipes have been loaded.");
    } catch (IOException e) {
      System.err.println("[MosekonRecycler][ERROR] Failure reading from mrconfig.txt. Has mrconfig.txt in the MosekonRecycler .jar file been corrupted?");
      System.err.println("[MosekonRecycler][ERROR] Not all recycling recipes have been loaded.");
    }
  }

  public void printHashMaps() {
    for (Map.Entry currentPair : this.itemStacksToRecycle.entrySet()) {
      System.out.println((String)currentPair.getKey() + " = " + currentPair.getValue());
    }

    for (Map.Entry currentPair : this.itemStacksToGivePlayer.entrySet())
      System.out.println((String)currentPair.getKey() + " = " + currentPair.getValue());
  }
}