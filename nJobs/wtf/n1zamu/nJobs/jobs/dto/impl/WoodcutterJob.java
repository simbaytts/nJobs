package wtf.n1zamu.nJobs.jobs.dto.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.scheduler.BukkitRunnable;
import wtf.n1zamu.nJobs.NJobs;
import wtf.n1zamu.nJobs.event.events.RegionLeaveEvent;
import wtf.n1zamu.nJobs.jobs.Jobs;
import wtf.n1zamu.nJobs.jobs.dto.Job;
import wtf.n1zamu.nJobs.util.JobManager;
import wtf.n1zamu.nJobs.util.RegionHelper;
import wtf.n1zamu.nJobs.util.woodcutter.Animation;
import wtf.n1zamu.nJobs.util.woodcutter.CheckUtil;
import wtf.n1zamu.nJobs.util.woodcutter.tree.Tree;
import wtf.n1zamu.nJobs.util.woodcutter.tree.TreeBackup;
import wtf.n1zamu.nJobs.util.woodcutter.tree.TreeChecker;
import wtf.n1zamu.nJobs.util.woodcutter.tree.TreeUtil;

public class WoodcutterJob extends Job implements Listener {
   public static List<TreeBackup> trees = new ArrayList();
   public static HashMap<Player, Integer> breaks = new HashMap();

   public WoodcutterJob() {
      super(Jobs.WOODCUTTER);
   }

   @EventHandler
   public void onBlockBreak(BlockBreakEvent event) {
      if (event.getBlock().getWorld() == this.getJob().getWorld()) {
         Block block = event.getBlock();
         Player player = event.getPlayer();
         if (RegionHelper.isPlayerInRegion(this.getJob().getRegionName(), player)) {
            Material blockType = block.getType();
            Location blockLocation = block.getLocation();
            if ((new CheckUtil()).isWood(blockType)) {
               if ((new TreeChecker()).checkTree(blockLocation)) {
                  event.setCancelled(true);
                  this.enter(player);
                  Tree tree = (new TreeUtil()).getTree(block.getLocation());
                  long code = (long)blockLocation.getBlockX() * 100000L + (long)blockLocation.getBlockZ();
                  if (!NJobs.getInstance().strength.containsKey(code)) {
                     NJobs.getInstance().strength.put(code, (new TreeUtil()).getTree(blockLocation).getStrength() - 1);
                  } else {
                     NJobs.getInstance().strength.put(code, (Integer)NJobs.getInstance().strength.get(code) - 1);
                  }

                  if ((Integer)NJobs.getInstance().strength.get(code) != 0) {
                     player.sendMessage(ChatColor.translateAlternateColorCodes('&', NJobs.getConfiguration().getString("woodMessage")).replace("%left%", Integer.toString((Integer)NJobs.getInstance().strength.get(code))));
                     breaks.put(player, (Integer)breaks.getOrDefault(player, 0) + 4);
                  } else {
                     if (tree == null) {
                        tree = (new TreeUtil()).getTree(blockLocation);
                     }

                     NJobs.getInstance().strength.remove(code);
                     final TreeBackup treeBackup = new TreeBackup(tree);
                     Animation.fall(tree);
                     JobManager.addBlockBreak(this.getBreaksMap(), player.getUniqueId(), "WOOD");
                     trees.add(treeBackup);
                     (new BukkitRunnable() {
                        public void run() {
                           Animation.back(treeBackup);
                        }
                     }).runTaskLater(NJobs.getInstance(), 100L);
                  }
               }
            }
         }
      }
   }

   @EventHandler
   public void onFallingBlockLand(EntityChangeBlockEvent e) {
      Entity entity = e.getEntity();
      Block block = e.getBlock();
      if (entity.getCustomName() != null) {
         if (entity.getCustomName().equals("#tree2") || entity.getCustomName().equals("#tree")) {
            entity.remove();
            block.setType(Material.AIR);
            e.setCancelled(true);
         }

      }
   }

   @EventHandler
   public void onLeavesDecay(LeavesDecayEvent event) {
      if (event.getBlock().getWorld() == this.getJob().getWorld()) {
         if (RegionHelper.isLocInRegion(this.getJob().getRegionName(), event.getBlock().getLocation())) {
            event.setCancelled(true);
         }
      }
   }

   @EventHandler
   public void onRegionLeave(RegionLeaveEvent event) {
      if (event.getRegion().getId().equalsIgnoreCase(this.getJob().getRegionName())) {
         breaks.remove(event.getPlayer());
         this.quit(event.getPlayer());
      }
   }
}
