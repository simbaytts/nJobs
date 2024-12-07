package wtf.n1zamu.nJobs.util.woodcutter.tree;

import java.util.LinkedList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.Block;
import wtf.n1zamu.nJobs.util.woodcutter.CheckUtil;

public class TreeUtil {
   private List<Block> blocks = new LinkedList();
   private int count = 0;
   private CheckUtil checkUtil = new CheckUtil();

   public Tree getTree(Location loc) {
      this.rec(loc.getBlock());
      return new Tree(this.getBlocks(), this.count);
   }

   public void rec(Block block) {
      if (!this.getBlocks().contains(block) && this.checkUtil.isTree(block.getType())) {
         this.blocks.add(block);
         if (this.checkUtil.isWood(block.getType())) {
            ++this.count;
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

   public void setBlocks(List<Block> blocks) {
      this.blocks = blocks;
   }
}
