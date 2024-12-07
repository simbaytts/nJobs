package wtf.n1zamu.nJobs.util.woodcutter.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.block.Block;

public class TreeBackup {
   private List<TreeBlock> blocks = new LinkedList();
   private String region = null;

   public TreeBackup(Tree tree) {
      Iterator var2 = tree.getBlocks().iterator();

      while(var2.hasNext()) {
         Block block = (Block)var2.next();
         this.addBlock(new TreeBlock(block));
      }

   }

   public void addBlock(TreeBlock block) {
      this.blocks.add(block);
   }

   public List<TreeBlock> getBlocks() {
      return this.blocks;
   }

   public void setBlocks(List<TreeBlock> blocks) {
      this.blocks = blocks;
   }

   public String getRegion() {
      return this.region;
   }

   public void setRegion(String region) {
      this.region = region;
   }
}
