package bwem.tile;

import bwem.area.typedef.AreaId;
import bwem.typedef.Altitude;
import org.openbw.bwapi4j.WalkPosition;

/**
 * Corresponds to BWAPI/Starcraft's concept of walk tile (8x8 pixels).
 * - MiniTiles are accessed using WalkPositions {@link bwem.map.AdvancedData#getMiniTile(WalkPosition)}
 * - A Map holds Map::WalkSize().x * Map::WalkSize().y MiniTiles as its "MiniTile map".
 * - A MiniTile contains essentialy 3 pieces of information:
 *   i) its Walkability
 *   ii) its altitude (distance from the nearest non walkable MiniTile, except those which are part of small enough zones (lakes))
 *   iii) the id of the Area it is part of, if ever.
 * - The whole process of analysis of a Map relies on the walkability information
 * from which are derived successively: altitudes, Areas, ChokePoints.
 */
public interface MiniTile {

    /**
     * Corresponds approximatively to BWAPI::isWalkable
     * The differences are:
     * - For each BWAPI's unwalkable MiniTile, we also mark its 8 neighbors as not walkable.
     * According to some tests, this prevents from wrongly pretending one small unit can go by some thin path.
     * - The relation buildable ==&gt; walkable is enforced, by marking as walkable any MiniTile part of a buildable Tile (Cf. {@link Tile#isBuildable()})
     * Among the miniTiles having Altitude() &gt; 0, the walkable ones are considered Terrain-miniTiles, and the other ones Lake-miniTiles.
     */
    boolean isWalkable();

    /**
     * Distance in pixels between the center of this MiniTile and the center of the nearest Sea-MiniTile
     * - Sea-miniTiles all have their Altitude() equal to 0.
     * - miniTiles having Altitude() &gt; 0 are not Sea-miniTiles. They can be either Terrain-miniTiles or Lake-miniTiles.
     */
    Altitude getAltitude();

    /**
     * Sea-miniTiles are unwalkable miniTiles that have their altitude equal to 0.
     */
    boolean isSea();

    /**
     * Lake-miniTiles are unwalkable miniTiles that have their Altitude() &gt; 0.
     * - They form small zones (inside Terrain-zones) that can be eaysily walked around (e.g. Starcraft's doodads)
     * - The intent is to preserve the continuity of altitudes inside areas.
     */
    boolean isLake();

    /**
     * Terrain miniTiles are just walkable miniTiles
     */
    boolean isTerrain();

    /**
     * For Sea and Lake miniTiles, returns 0
     * For Terrain miniTiles, returns a non zero id:
     * - if (id &gt; 0), id uniquely identifies the Area A that contains this MiniTile.
     * Moreover we have: A.id() == id and Map::getArea(id) == A
     * - For more information about positive Area::ids, see Area::id()
     * - if (id &lt; 0), then this MiniTile is part of a Terrain-zone that was considered too small to create an Area for it.
     * - Note: negative Area::ids start from -2
     * - Note: because of the lakes, Map::getNearestArea should be prefered over Map::getArea.
     */
    AreaId getAreaId();

}
