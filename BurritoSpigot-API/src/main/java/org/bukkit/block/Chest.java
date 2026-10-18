package org.bukkit.block;

import org.bukkit.inventory.Inventory;

/**
 * Represents a chest.
 */
public interface Chest extends BlockState, ContainerBlock {

    /**
     * Returns the chest's inventory. If this is a double chest, it returns
     * just the portion of the inventory linked to this half of the chest.
     *
     * @return The inventory.
     */
    Inventory getBlockInventory();

    /**
     * Sets the custom name shown as this chest's inventory title.
     * <p>
     * Unlike most block state changes this applies to the chest straight away, without
     * {@link #update()}, and is saved with the chest. Players who already have the chest open
     * see the new title the next time they open it. For a double chest, the left half's name
     * is used if it has one, otherwise the right half's.
     *
     * @param name the new title, or null (or empty) to restore the default title
     */
    void setName(String name);
}
