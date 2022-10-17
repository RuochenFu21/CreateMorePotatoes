package net.forsteri.createmorepotatoes.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;

public class ExplosivePotionPotatoItem extends PotionPotatoItem {
    public ExplosivePotionPotatoItem(){
        super();
    }

    public static void makeAreaOfEffectCloud(ItemStack p_37538_, Potion p_37539_, Level level, double x, double y, double z, Entity owner) {
        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(level, x, y, z);
        if (owner instanceof LivingEntity) {
            areaeffectcloud.setOwner((LivingEntity) owner);
        }

        areaeffectcloud.setRadius(3.0F);
        areaeffectcloud.setRadiusOnUse(-0.5F);
        areaeffectcloud.setWaitTime(10);
        areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());
        areaeffectcloud.setPotion(p_37539_);

        for(MobEffectInstance mobeffectinstance : PotionUtils.getCustomEffects(p_37538_)) {
            areaeffectcloud.addEffect(new MobEffectInstance(mobeffectinstance));
        }

        CompoundTag compoundtag = p_37538_.getTag();
        if (compoundtag != null && compoundtag.contains("CustomPotionColor", 99)) {
            areaeffectcloud.setFixedColor(compoundtag.getInt("CustomPotionColor"));
        }

        level.addFreshEntity(areaeffectcloud);
    }
}
