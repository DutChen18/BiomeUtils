package kaptainwutax.biomeutils.layer.land;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.BiomeSource;
import kaptainwutax.biomeutils.layer.BiomeLayer;
import kaptainwutax.biomeutils.layer.composite.MergingLayer;

public class HillsLayer extends MergingLayer {

	public HillsLayer(long worldSeed, long salt, BiomeLayer... parents) {
		super(worldSeed, salt, parents);
	}

	public HillsLayer(long worldSeed, long salt) {
		this(worldSeed, salt, (BiomeLayer[])null);
	}

	@Override
	public int sample(int x, int z) {
		this.setSeed(x, z);
		int i = this.parents[0].sample(x, z);
		int j = this.parents[1].sample(x, z);

		int k = (j - 2) % 29;
		Biome biome3;

		if (!BiomeSource.isShallowOcean(i) && j >= 2 && k == 1) {
			Biome biome = Biome.REGISTRY.get(i);

			if(biome == null || !biome.hasParent()) {
				biome3 = biome == null ? null : biome.getChild();
				return biome3 == null ? i : biome3.getId();
			}
		}

		if(this.nextInt(3) == 0 || k == 0) {
			int l = i;
			if (i == Biome.DESERT.getId()) {
				l = Biome.DESERT_HILLS.getId();
			} else if(i == Biome.FOREST.getId()) {
				l = Biome.WOODED_HILLS.getId();
			} else if(i == Biome.BIRCH_FOREST.getId()) {
				l = Biome.BIRCH_FOREST_HILLS.getId();
			} else if(i == Biome.DARK_FOREST.getId()) {
				l = Biome.PLAINS.getId();
			} else if(i == Biome.TAIGA.getId()) {
				l = Biome.TAIGA_HILLS.getId();
			} else if(i == Biome.GIANT_TREE_TAIGA.getId()) {
				l = Biome.GIANT_TREE_TAIGA_HILLS.getId();
			} else if(i == Biome.SNOWY_TAIGA.getId()) {
				l = Biome.SNOWY_TAIGA_HILLS.getId();
			} else if(i == Biome.PLAINS.getId()) {
				l = this.nextInt(3) == 0 ? Biome.WOODED_HILLS.getId() : Biome.FOREST.getId();
			} else if (i == Biome.SNOWY_TUNDRA.getId()) {
				l = Biome.SNOWY_MOUNTAINS.getId();
			} else if (i == Biome.JUNGLE.getId()) {
				l = Biome.JUNGLE_HILLS.getId();
			} else if (i == Biome.BAMBOO_JUNGLE.getId()) {
				l = Biome.BAMBOO_JUNGLE_HILLS.getId();
			} else if (i == Biome.OCEAN.getId()) {
				l = Biome.DEEP_OCEAN.getId();
			} else if (i == Biome.LUKEWARM_OCEAN.getId()) {
				l = Biome.DEEP_LUKEWARM_OCEAN.getId();
			} else if (i == Biome.COLD_OCEAN.getId()) {
				l = Biome.DEEP_COLD_OCEAN.getId();
			} else if (i == Biome.FROZEN_OCEAN.getId()) {
				l = Biome.DEEP_FROZEN_OCEAN.getId();
			} else if (i == Biome.MOUNTAINS.getId()) {
				l = Biome.WOODED_MOUNTAINS.getId();
			} else if (i == Biome.SAVANNA.getId()) {
				l = Biome.SAVANNA_PLATEAU.getId();
			} else if (BiomeSource.areSimilar(i, Biome.WOODED_BADLANDS_PLATEAU)) {
				l = Biome.BADLANDS.getId();
			} else if ((i == Biome.DEEP_OCEAN.getId() || i == Biome.DEEP_LUKEWARM_OCEAN.getId()
					|| i == Biome.DEEP_COLD_OCEAN.getId() || i == Biome.DEEP_FROZEN_OCEAN.getId())
					&& this.nextInt(3) == 0) {
				l = this.nextInt(2) == 0 ? Biome.PLAINS.getId() : Biome.FOREST.getId();
			}

			if(k == 0 && l != i) {
				biome3 = Biome.REGISTRY.get(l).getChild();
				l = biome3 == null ? i : biome3.getId();
			}

			if(l != i) {
				int m = 0;
				Biome b = Biome.REGISTRY.get(i);
				if(BiomeSource.areSimilar(this.parents[0].sample(x, z - 1), b))m++;
				if(BiomeSource.areSimilar(this.parents[0].sample(x + 1, z), b))m++;
				if(BiomeSource.areSimilar(this.parents[0].sample(x - 1, z), b))m++;
				if(BiomeSource.areSimilar(this.parents[0].sample(x, z + 1), b))m++;
				if(m >= 3)return l;
			}
		}

		return i;
	}
}