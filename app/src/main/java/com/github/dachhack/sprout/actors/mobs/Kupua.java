/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.github.dachhack.sprout.actors.mobs;

import java.util.HashSet;

import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.blobs.Blob;
import com.github.dachhack.sprout.actors.blobs.CorruptGas;
import com.github.dachhack.sprout.actors.blobs.ToxicGas;
import com.github.dachhack.sprout.items.Item;
import com.github.dachhack.sprout.items.StoneOre;
import com.github.dachhack.sprout.items.weapon.enchantments.Death;
import com.github.dachhack.sprout.items.weapon.melee.relic.RelicMeleeWeapon;
import com.github.dachhack.sprout.items.weapon.missiles.JupitersWrath;
import com.github.dachhack.sprout.scenes.GameScene;
import com.github.dachhack.sprout.sprites.KupuaSprite;
import com.watabou.utils.Random;

public class Kupua extends Mob {
  //Gullin
	
	{
		name = "kupua";
		spriteClass = KupuaSprite.class;

		HP = HT = 4000;
		defenseSkill = 15+ adjustForDepth(1);
		baseSpeed = 2f;

		EXP = 10;
		maxLvl = 999;

		loot = new StoneOre();
		lootChance = 0.9f;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(70+ adjustForDepth(0), 160+ adjustForDepth(0));
	}
		
	@Override
	public int attackSkill(Char target) {
		return 120+ adjustForDepth(0);
	}

	@Override
	public int dr() {
		return 50+ adjustForDepth(0);
	}

	@Override
	public void damage(int dmg, Object src) {
		int actualdmg = dmg;
		if(!(src instanceof RelicMeleeWeapon || src instanceof JupitersWrath)){
			int max = Math.round(dmg*.5f);
			actualdmg = Random.Int(1,max);
		}
		if (src instanceof Item) {
			if (((Item)src).reinforced) {
				actualdmg = dmg;
			}
		}
		
		if (actualdmg > HT/8){
		GameScene.add(Blob.seed(pos, 30, CorruptGas.class));
		}
		
		super.damage(actualdmg, src);
	}
	
	@Override
	public String defenseVerb() {
		return "blocked";
	}

	@Override
	public String description() {
		return "Kupua are minor trickster demons who like to appear enmasse. ";
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Death.class);
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(CorruptGas.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
