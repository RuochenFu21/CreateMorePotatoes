package net.forsteri.createmorepotatoes.entry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

@SuppressWarnings("deprecation")
public class ModFoods {
	public static final FoodProperties EXPLOSIVE_POTATO = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.3F).effect(new MobEffectInstance(MobEffects.CONFUSION, 300, 1), 1.0F).effect(new MobEffectInstance(MobEffects.POISON, 100, 0), 0.3F).alwaysEat().build();

	public static final FoodProperties GOLDEN_POTATO = (new FoodProperties.Builder()).nutrition(4).saturationMod(1.2F).effect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F).effect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F).alwaysEat().build();

	public static final FoodProperties DIAMOND_POTATO = (new FoodProperties.Builder()).nutrition(8).saturationMod(2.4F).effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1), 1.0F).effect(new MobEffectInstance(MobEffects.ABSORPTION, 4800, 0), 1.0F).alwaysEat().build();
}
