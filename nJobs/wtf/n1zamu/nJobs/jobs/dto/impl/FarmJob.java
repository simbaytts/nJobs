package wtf.n1zamu.nJobs.jobs.dto.impl;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import wtf.n1zamu.nJobs.NJobs;
import wtf.n1zamu.nJobs.event.events.RegionLeaveEvent;
import wtf.n1zamu.nJobs.jobs.Jobs;
import wtf.n1zamu.nJobs.jobs.dto.Job;
import wtf.n1zamu.nJobs.util.ItemsHelper;
import wtf.n1zamu.nJobs.util.JobManager;
import wtf.n1zamu.nJobs.util.RegionHelper;

public class FarmJob extends Job implements Listener {
   private final Material[] FARM_MATERIALS;

   public FarmJob() {
      super(Jobs.FARM);
      this.FARM_MATERIALS = new Material[]{Material.CARROTS, Material.POTATOES, Material.WHEAT, Material.MELON, Material.PUMPKIN, Material.BEETROOTS};
   }

   @EventHandler
   public void onBlockBreak(BlockBreakEvent event) {
      if (event.getBlock().getWorld() == this.getJob().getWorld()) {
         Player player = event.getPlayer();
         final Block block = event.getBlock();
         if (RegionHelper.isPlayerInRegion(this.getJob().getRegionName(), player)) {
            if (ItemsHelper.isWhiteListed(block, this.FARM_MATERIALS)) {
               this.enter(player);
               event.setCancelled(true);
               JobManager.addBlockBreak(this.getBreaksMap(), player.getUniqueId(), block.getType().toString());
               block.setType(Material.AIR);
               block.getLocation().add(0.0D, -1.0D, 0.0D).getBlock().setType(Material.FARMLAND);
               player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
               (new BukkitRunnable() {
                  public void run() {
                     block.getLocation().add(0.0D, -1.0D, 0.0D).getBlock().setType(Material.FARMLAND);
                     block.setType(FarmJob.this.FARM_MATERIALS[(new Random()).nextInt(FarmJob.this.FARM_MATERIALS.length)]);
                     BlockData blockData = block.getBlockData();
                     if (blockData instanceof Ageable) {
                        ((Ageable)blockData).setAge(((Ageable)blockData).getMaximumAge());
                        block.setBlockData(blockData);
                     }

                  }
               }).runTaskLater(NJobs.getInstance(), (long)NJobs.getConfiguration().getInt("farmReload") * 20L);
            }
         }
      }
   }

   @EventHandler
   public void onRegionLeave(RegionLeaveEvent event) {
      if (event.getRegion().getId().equalsIgnoreCase(this.getJob().getRegionName())) {
         this.quit(event.getPlayer());
      }
   }
}
