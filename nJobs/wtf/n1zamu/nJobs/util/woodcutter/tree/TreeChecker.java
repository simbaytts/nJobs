package wtf.n1zamu.nJobs.util.woodcutter.tree;

import java.util.LinkedList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.Block;
import wtf.n1zamu.nJobs.util.woodcutter.CheckUtil;

public class TreeChecker {
   private List<Block> blocks = new LinkedList();
   private boolean isTree = false;
   private CheckUtil checkUtil = new CheckUtil();

   public boolean checkTree(Location loc) {
      this.rec(loc.getBlock());
      return this.isTree;
   }

   public void rec(Block block) {
      if (!this.isTree && this.checkUtil.isTree(block.getType()) && !this.getBlocks().contains(block)) {
         this.blocks.add(block);
         if (this.checkUtil.isLeaves(block.getType())) {
            this.isTree = true;
         }

         Location loc = block.getLocation();
         this.rec((new Location(loc.getWorld(), (double)(loc.getBlockX() + 1), (double)loc.getBlockY(), (double)loc.getBlockZ())).getBlock());
         this.rec((new Location(loc.getWorld(), (double)(loc.getBlockX() - 1), (double)loc.getBlockY(), (double)loc.getBlockZ())).getBlock());
         this.rec((new Location(loc.getWorld(), (double)loc.getBlockX(), (double)(loc.getBlockY() + 1), (double)loc.getBlockZ())).getBlock());
         this.rec((new Location(loc.getWorld(), (double)loc.getBlockX(), (double)(loc.getBlockY() - 1), (double)loc.getBlockZ())).getBlock());
         this.rec((new Location(loc.getWorld(), (double)loc.getBlockX(), (double)loc.getBlockY(), (double)(loc.getBlockZ() + 1))).getBlock());
         this.rec((new Location(loc.getWorld(), (double)loc.getBlockX(), (double)loc.getBlockY(), (double)(loc.getBlockZ() - 1))).getBlock());
      }

   }

   public List<Block> getBlocks() {
      return this.blocks;
   }
}
