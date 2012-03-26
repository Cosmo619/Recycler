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
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.bukkit.inventory.ItemStack;

public class MRRecipes
{
  protected HashMap<String, ItemStack> itemStacksToRecycle = new HashMap();
  protected HashMap<String, ArrayList<ItemStack>> itemStacksToGivePlayer = new HashMap();
  protected HashMap<Integer, Short> maxDamages = new HashMap();

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
    return ((Short)this.maxDamages.get(Integer.valueOf(typeId))).shortValue();
  }

  public ArrayList<ItemStack> getItemStacksToGivePlayer(int typeId, short damageValue)
  {
    ArrayList isToGivePlayer = (ArrayList)this.itemStacksToGivePlayer.get(Integer.toString(typeId));
    if (isToGivePlayer == null) isToGivePlayer = (ArrayList)this.itemStacksToGivePlayer.get(Integer.toString(typeId) + ":" + Short.toString(damageValue));
    return isToGivePlayer;
  }

  private void populateMaxDurabilities()
  {
    this.maxDamages.put(Integer.valueOf(259), Short.valueOf(65));
    this.maxDamages.put(Integer.valueOf(346), Short.valueOf(33));
    this.maxDamages.put(Integer.valueOf(359), Short.valueOf(239));
    this.maxDamages.put(Integer.valueOf(283), Short.valueOf(33));
    this.maxDamages.put(Integer.valueOf(285), Short.valueOf(33));
    this.maxDamages.put(Integer.valueOf(284), Short.valueOf(33));
    this.maxDamages.put(Integer.valueOf(286), Short.valueOf(33));
    this.maxDamages.put(Integer.valueOf(294), Short.valueOf(33));
    this.maxDamages.put(Integer.valueOf(268), Short.valueOf(60));
    this.maxDamages.put(Integer.valueOf(270), Short.valueOf(60));
    this.maxDamages.put(Integer.valueOf(269), Short.valueOf(60));
    this.maxDamages.put(Integer.valueOf(271), Short.valueOf(60));
    this.maxDamages.put(Integer.valueOf(290), Short.valueOf(60));
    this.maxDamages.put(Integer.valueOf(272), Short.valueOf(132));
    this.maxDamages.put(Integer.valueOf(274), Short.valueOf(132));
    this.maxDamages.put(Integer.valueOf(273), Short.valueOf(132));
    this.maxDamages.put(Integer.valueOf(275), Short.valueOf(132));
    this.maxDamages.put(Integer.valueOf(291), Short.valueOf(132));
    this.maxDamages.put(Integer.valueOf(267), Short.valueOf(251));
    this.maxDamages.put(Integer.valueOf(257), Short.valueOf(251));
    this.maxDamages.put(Integer.valueOf(256), Short.valueOf(251));
    this.maxDamages.put(Integer.valueOf(258), Short.valueOf(251));
    this.maxDamages.put(Integer.valueOf(292), Short.valueOf(251));
    this.maxDamages.put(Integer.valueOf(276), Short.valueOf(1562));
    this.maxDamages.put(Integer.valueOf(278), Short.valueOf(1562));
    this.maxDamages.put(Integer.valueOf(277), Short.valueOf(1562));
    this.maxDamages.put(Integer.valueOf(279), Short.valueOf(1562));
    this.maxDamages.put(Integer.valueOf(293), Short.valueOf(1562));
    this.maxDamages.put(Integer.valueOf(298), Short.valueOf(34));
    this.maxDamages.put(Integer.valueOf(299), Short.valueOf(49));
    this.maxDamages.put(Integer.valueOf(300), Short.valueOf(46));
    this.maxDamages.put(Integer.valueOf(301), Short.valueOf(40));
    this.maxDamages.put(Integer.valueOf(314), Short.valueOf(68));
    this.maxDamages.put(Integer.valueOf(315), Short.valueOf(96));
    this.maxDamages.put(Integer.valueOf(316), Short.valueOf(92));
    this.maxDamages.put(Integer.valueOf(317), Short.valueOf(80));
    this.maxDamages.put(Integer.valueOf(306), Short.valueOf(136));
    this.maxDamages.put(Integer.valueOf(307), Short.valueOf(192));
    this.maxDamages.put(Integer.valueOf(308), Short.valueOf(184));
    this.maxDamages.put(Integer.valueOf(309), Short.valueOf(160));
    this.maxDamages.put(Integer.valueOf(310), Short.valueOf(272));
    this.maxDamages.put(Integer.valueOf(311), Short.valueOf(384));
    this.maxDamages.put(Integer.valueOf(312), Short.valueOf(368));
    this.maxDamages.put(Integer.valueOf(313), Short.valueOf(320));
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
        String line;
        String[] wholeLine = ep.split(line);
        String[] itemInfo = xp.split(wholeLine[0]);
        String[] itemTypeDamage = cp.split(itemInfo[1]);
        String key;
        if (itemTypeDamage.length > 1) {
          ItemStack addAsRecycleInput = new ItemStack(Integer.parseInt(itemTypeDamage[0]), Integer.parseInt(itemInfo[0]), Short.parseShort(itemTypeDamage[1]));
          String key = itemTypeDamage[0] + ":" + itemTypeDamage[1];
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