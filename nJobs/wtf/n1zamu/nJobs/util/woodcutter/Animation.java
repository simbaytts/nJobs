package wtf.n1zamu.nJobs.util.woodcutter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;
import wtf.n1zamu.nJobs.NJobs;
import wtf.n1zamu.nJobs.util.RegionHelper;
import wtf.n1zamu.nJobs.util.woodcutter.tree.Tree;
import wtf.n1zamu.nJobs.util.woodcutter.tree.TreeBackup;
import wtf.n1zamu.nJobs.util.woodcutter.tree.TreeBlock;

public class Animation {
   public static void fall(Tree tree) {
      TreeBackup treeBackup = new TreeBackup(tree);
      Iterator var2 = tree.getBlocks().iterator();

      while(var2.hasNext()) {
         Block block = (Block)var2.next();
         if (treeBackup.getRegion() == null && RegionHelper.isLocInRegion(NJobs.getConfiguration().getString("woodcutterRegion"), block.getLocation())) {
            treeBackup.setRegion(NJobs.getConfiguration().getString("woodcutterRegion"));
         }

         Vector vector = getAnimation1();
         Material material = block.getType();
         byte data = block.getData();
         block.setType(Material.AIR);
         Location loc = block.getLocation();
         FallingBlock fb = block.getWorld().spawnFallingBlock(new Location(loc.getWorld(), loc.getX() + 0.5D, loc.getY(), loc.getZ() + 0.5D), material, data);
         fb.setDropItem(false);
         fb.setHurtEntities(false);
         fb.setInvulnerable(true);
         fb.setVelocity(vector);
         fb.setCustomNameVisible(false);
         fb.setCustomName("#tree");
      }

      NJobs.getInstance().trees.put(treeBackup, System.currentTimeMillis() / 1000L);
   }

   public static void back(TreeBackup tree) {
      List<FallingBlock> blocks = new LinkedList();
      Iterator var2 = tree.getBlocks().iterator();

      while(var2.hasNext()) {
         TreeBlock treeBlock = (TreeBlock)var2.next();
         Location loc = treeBlock.getLocation();
         FallingBlock fb = loc.getWorld().spawnFallingBlock(new Location(loc.getWorld(), loc.getX() + 0.5D, loc.getY() + 3.1D, loc.getZ() + 0.5D), treeBlock.getMaterial().createBlockData());
         fb.setDropItem(false);
         fb.setHurtEntities(false);
         fb.setInvulnerable(true);
         fb.setCustomNameVisible(false);
         fb.setCustomName("#tree2");
         blocks.add(fb);
      }

      Bukkit.getScheduler().scheduleSyncDelayedTask(NJobs.getInstance(), () -> {
         Iterator var2 = blocks.iterator();

         while(var2.hasNext()) {
            FallingBlock fb = (FallingBlock)var2.next();
            if (fb != null) {
               fb.remove();
            }
         }

         var2 = tree.getBlocks().iterator();

         while(var2.hasNext()) {
            TreeBlock block = (TreeBlock)var2.next();
            Block bukkitBlock = block.getLocation().getBlock();
            Material material = block.getMaterial();
            BlockData blockData = block.getBlockData();
            bukkitBlock.setType(material);
            bukkitBlock.setBlockData(blockData);
         }

      });
   }

   public static void hardBack(TreeBackup tree) {
      Iterator var1 = tree.getBlocks().iterator();

      while(var1.hasNext()) {
         TreeBlock treeBlock = (TreeBlock)var1.next();
         Block bl = treeBlock.getLocation().getBlock();
         bl.setType(treeBlock.getMaterial());
      }

   }

   public static Vector getAnimation1() {
      Vector vector = new Vector(0, 0, 0);
      vector.setX(ThreadLocalRandom.current().nextDouble(-0.1D, 0.1D));
      vector.setZ(ThreadLocalRandom.current().nextDouble(-0.1D, 0.1D));
      vector.setY(ThreadLocalRandom.current().nextDouble(0.35D, 0.75D));
      return vector;
   }
}
