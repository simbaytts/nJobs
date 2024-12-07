package wtf.n1zamu.nJobs.util.woodcutter;

import org.bukkit.Material;

public class CheckUtil {
   public boolean isWood(Material material) {
      return material == Material.BIRCH_LOG || material == Material.OAK_LOG || material == Material.SPRUCE_LOG;
   }

   public boolean isLeaves(Material material) {
      return material == Material.OAK_LEAVES || material == Material.SPRUCE_LEAVES || material == Material.BIRCH_LEAVES;
   }

   public boolean isTree(Material material) {
      return this.isWood(material) || this.isLeaves(material);
   }
}
