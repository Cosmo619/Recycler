package net.bettercraft.recycler;

import java.io.PrintStream;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MosekonRecycler extends JavaPlugin
{
  public static final int RECYCLER_BLOCK_ID = 41;

  public void onDisable()
  {
    PluginDescriptionFile pdf = getDescription();
    System.out.println(pdf.getName() + " version " + pdf.getVersion() + " is disabled.");
  }

  public void onEnable() {
    MRPlayerListener playerListener = new MRPlayerListener();
    MRBlockListener blockListener = new MRBlockListener();

    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(playerListener,this);
    pm.registerEvents(blockListener,this);

    PluginDescriptionFile pdf = getDescription();
    System.out.println(pdf.getName() + " version " + pdf.getVersion() + " is enabled.");
  }
}