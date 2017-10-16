package bwem;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Tile
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// Corresponds to BWAPI/Starcraft's concept of tile (32x32 pixels).
// Tiles are accessed using TilePositions (Cf. Map::GetTile).
// A Map holds Map::Size().x * Map::Size().y Tiles as its "Tile map".
//
// It should be noted that a Tile exactly overlaps 4 x 4 MiniTiles.
// As there are 16 times as many MiniTiles as Tiles, we allow a Tiles to contain more data than MiniTiles.
// As a consequence, Tiles should be preferred over MiniTiles, for efficiency.
// The use of Tiles is further facilitated by some functions like Tile::AreaId or Tile::MinAltitude
// which somewhat aggregate the MiniTile's corresponding information
//
// Tiles inherit utils::Markable, which provides marking ability
// Tiles inherit utils::UserData, which provides free-to-use data.
public class Tile extends Markable<Tile> {

    private Altitude minAltitude = null;
    private Area.Id areaId = null;
    private UserData internalData = null;
    private Bits bits = null;

    public Tile() {
        this.areaId = new Area.Id(0);
        this.internalData = new UserData();
        this.bits = new Bits();
    }

	// Tile::MinAltitude() somewhat aggregates the MiniTile::Altitude() values of the 4 x 4 sub-MiniTiles.
	// Returns the minimum value.
    public Altitude getMinAltitude() {
        return new Altitude(this.minAltitude);
    }

    public void setMinAltitude(Altitude altitude) {
//        { bwem_assert(a >= 0); m_minAltitude = a; }
        if (!(altitude.intValue() >= 0)) {
            throw new IllegalArgumentException("invalid Altitude");
        } else {
            this.minAltitude = new Altitude(altitude);
        }
    }

	// Tile::AreaId() somewhat aggregates the MiniTile::AreaId() values of the 4 x 4 sub-MiniTiles.
	// Let S be the set of MiniTile::AreaId() values for each walkable MiniTile in this Tile.
	// If empty(S), returns 0. Note: in this case, no contained MiniTile is walkable, so all of them have their AreaId() == 0.
	// If S = {a}, returns a (whether positive or negative).
	// If size(S) > 1 returns -1 (note that -1 is never returned by MiniTile::AreaId()).
    public Area.Id getAreaId() {
        return new Area.Id(this.areaId);
    }

    public void setAreaId(Area.Id areaId) {
//        { bwem_assert((id == -1) || !m_areaId && id); m_areaId = id; }
        if (!(areaId.intValue() == -1 || (this.areaId.intValue() == 0 && areaId.intValue() != 0))) {
            throw new IllegalStateException();
        } else {
            this.areaId = new Area.Id(areaId);
        }
    }

	// Corresponds to BWAPI::isBuildable
	// Note: BWEM enforces the relation buildable ==> walkable (Cf. MiniTile::Walkable)
    public boolean isBuildable() {
        return (this.bits.buildable != 0x0);
    }

    public void setBuildable() {
        this.bits.buildable = 0x1;
    }

    // Tells if at least one of the sub-MiniTiles is Walkable.
    public boolean isWalkable() {
        return (this.areaId.intValue() != 0);
    }

    // Tells if at least one of the sub-MiniTiles is a Terrain-MiniTile.
    public boolean isTerrain() {
        return isWalkable();
    }

	// Tells if this Tile is part of a doodad.
	// Corresponds to BWAPI::getGroundHeight % 2
    public boolean isDoodad() {
        return (this.bits.doodad != 0x0);
    }

    public void setDoodad() {
        this.bits.doodad = 0x1;
    }

    //TODO: Double-check that this and related code is right. Also consider enums.
	// 0: lower ground    1: high ground    2: very high ground
	// Corresponds to BWAPI::getGroundHeight / 2
    public int getGroundHeight() {
        return ((int) this.bits.groundHeight);
    }

    //TODO: Double-check this lines up with getGroundHeight().
    public void setGroundHeight(int height) {
//        { bwem_assert((0 <= h) && (h <= 2)); m_bits.groundHeight = h; }
        if (!((height >= 0) && (height <= 2))) {
            throw new IllegalArgumentException("invalid height: h=" + height);
        } else {
            this.bits.groundHeight = (byte) height;
        }
    }

    public UserData getInternalData() {
        return this.internalData;
    }

    public void resetAreaId() {
        this.areaId = new Area.Id(0);
    }

}
