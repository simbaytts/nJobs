package wtf.n1zamu.nJobs.jobs.dto.impl;

import org.bukkit.Material;
import org.bukkit.block.Block;
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

public class MineJob extends Job implements Listener {
   private static final Material[] MINE_MATERIALS;
   private static final int[] WEIGHTS;

   public MineJob() {
      super(Jobs.MINE);
   }

   @EventHandler
   public void onBlockBreak(BlockBreakEvent event) {
      if (event.getBlock().getWorld() == this.getJob().getWorld()) {
         Player player = event.getPlayer();
         final Block block = event.getBlock();
         if (RegionHelper.isPlayerInRegion(this.getJob().getRegionName(), player)) {
            if (ItemsHelper.isWhiteListed(block, MINE_MATERIALS)) {
               this.enter(player);
               event.setCancelled(true);
               block.getDrops().forEach((itemStack) -> {
                  block.getLocation().clone().getWorld().dropItemNaturally(block.getLocation().clone(), itemStack);
               });
               JobManager.addBlockBreak(this.getBreaksMap(), player.getUniqueId(), block.getType().toString());
               block.setType(Material.AIR);
               (new BukkitRunnable() {
                  public void run() {
                     block.setType(MineJob.this.getRandomMaterial(MineJob.MINE_MATERIALS, MineJob.WEIGHTS));
                  }
               }).runTaskLater(NJobs.getInstance(), (long)NJobs.getConfiguration().getInt("mineReload") * 20L);
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

   static {
      MINE_MATERIALS = new Material[]{Material.STONE, Material.COAL_ORE, Material.LAPIS_ORE, Material.REDSTONE_ORE, Material.IRON_ORE, Material.GOLD_ORE, Material.EMERALD_ORE, Material.DIAMOND_ORE};
      WEIGHTS = new int[]{65, 5, 5, 5, 5, 5, 5, 5};
   }
}
