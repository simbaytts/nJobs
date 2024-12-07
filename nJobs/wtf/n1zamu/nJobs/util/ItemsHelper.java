package wtf.n1zamu.nJobs.util;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class ItemsHelper {
   public static boolean isWhiteListed(Block block, Material[] whiteList) {
      return Arrays.asList(whiteList).contains(block.getType());
   }
}
