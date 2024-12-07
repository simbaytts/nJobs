package wtf.n1zamu.nJobs.util.woodcutter.tree;

import java.util.LinkedList;
import java.util.List;
import org.bukkit.block.Block;

public class Tree {
   private List<Block> blocks = new LinkedList();
   private int strength;

   public Tree(List<Block> blocks, int strength) {
      this.setBlocks(blocks);
      this.strength = strength;
   }

   public List<Block> getBlocks() {
      return this.blocks;
   }

   public void setBlocks(List<Block> blocks) {
      this.blocks = blocks;
   }

   public int getStrength() {
      return this.strength;
   }

   public void setStrength(int strength) {
      this.strength = strength;
   }
}
