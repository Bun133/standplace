package com.bun133.standplace.util

import com.bun133.standplace.Standplace
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.CreatureSpawner
import org.bukkit.block.data.BlockData
import org.bukkit.entity.EntityType
import kotlin.random.Random

class RandomMaterialMaker(var plugin: Standplace) {
    var factories: MutableList<RandomMaterialFactory> = mutableListOf()

    init {
        // NormalBlocks
        factories.addAll(NormalBlocksFactoryFactory().getAll())
        factories.addAll(SpawnersFactoryFactory().getAll(plugin))
    }

    fun setRandom(former: Block) {
        factories[Random.nextInt(factories.size)].set(former)
    }
}

data class BlockSet(var material: Material, var data: BlockData?)

abstract class RandomMaterialFactory {
    abstract fun set(former: Block)
}

class BlockSetAdapter(val block: BlockSet) : RandomMaterialFactory() {
    override fun set(former: Block) {
        former.type = block.material
        if (block.data != null) {
            former.blockData = block.data!!
        }
    }
}

class SimpleMaterialFactory(val material: Material) : RandomMaterialFactory() {
    override fun set(former: Block) {
        BlockSetAdapter(get()).set(former)
    }

    fun get(): BlockSet = BlockSet(material, null)
}

abstract class StateMaterialFactory(open val material: Material) : RandomMaterialFactory() {
    override fun set(former: Block) {
        BlockSetAdapter(get(former)).set(former)
    }

    fun get(former: Block): BlockSet = BlockSet(material, getData(former))

    abstract fun getData(former: Block): BlockData
}

class SimpleStateMaterialFactory(override val material: Material, val data: BlockData) : StateMaterialFactory(material) {
    override fun getData(former: Block): BlockData = data
}

/**
 * Provide NormalBlocks(like dirt,logs) Factory
 */
class NormalBlocksFactoryFactory {
    fun getAll(): MutableList<RandomMaterialFactory> {
        return Material.values().filter { it.isBlock }.map { SimpleMaterialFactory(it) }.toMutableList()
    }
}

class SpawnerFactory(val entity: EntityType, val plugin: Standplace) : RandomMaterialFactory() {
    override fun set(former: Block) {
        former.type = Material.SPAWNER
        plugin.scheduler.setTimer(5L, Runnable {
            val state = former.state
            if (state !is CreatureSpawner) println("Spawner Error!")
            val spawner = state as CreatureSpawner
            spawner.spawnedType = entity
            state.update(true, false)
        })
    }

}

class SpawnersFactoryFactory() {
    fun getAll(plugin: Standplace): MutableList<RandomMaterialFactory> {
        return EntityType.values().filter { it.isSpawnable && it.isAlive }.map { SpawnerFactory(it, plugin) }.toMutableList()
    }
}