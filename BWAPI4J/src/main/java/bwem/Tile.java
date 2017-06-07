package bwem;

import org.apache.commons.lang3.mutable.MutableInt;

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

//class Tile : public utils::Markable<Tile, int>, public utils::UserData
public class Tile {

//    Neutral *			m_pNeutral = nullptr; // TODO
    private int m_minAltitude;
    private int m_areaId;
    MutableInt m_internalData;
    Bits m_bits;

    public Tile() {
        m_minAltitude = 0;
        m_areaId = 0;
        m_internalData = new MutableInt(0);
        m_bits = new Bits();
    }

	// Corresponds to BWAPI::isBuildable
	// Note: BWEM enforces the relation buildable ==> walkable (Cf. MiniTile::Walkable)
//	bool				Buildable() const				{ return m_bits.buildable; }
    public boolean Buildable() {
        return (m_bits.buildable != 0x0);
    }

    // Tile::AreaId() somewhat aggregates the MiniTile::AreaId() values of the 4 x 4 sub-MiniTiles.
    // Let S be the set of MiniTile::AreaId() values for each walkable MiniTile in this Tile.
    // If empty(S), returns 0. Note: in this case, no contained MiniTile is walkable, so all of them have their AreaId() == 0.
    // If S = {a}, returns a (whether positive or negative).
    // If size(S) > 1 returns -1 (note that -1 is never returned by MiniTile::AreaId()).
	public int AreaId(){
        return m_areaId;
    }

    // Tile::MinAltitude() somewhat aggregates the MiniTile::Altitude() values of the 4 x 4 sub-MiniTiles.
    // Returns the minimum value.
	public int MinAltitude() {
        return m_minAltitude;
    }

    // Tells if at least one of the sub-MiniTiles is Walkable.
	public boolean Walkable() {
        return (m_areaId != 0);
    }

    // Tells if at least one of the sub-MiniTiles is a Terrain-MiniTile.
	public boolean Terrain() {
        return Walkable();
    }

    // 0: lower ground    1: high ground    2: very high ground
    // Corresponds to BWAPI::getGroundHeight / 2
    public int GroundHeight() {
        return ((int) m_bits.groundHeight);
    }

    // Tells if this Tile is part of a doodad.
    // Corresponds to BWAPI::getGroundHeight % 2
    public boolean Doodad() {
        return (m_bits.doodad == 0x1);
    }

    // TODO
    // If any Neutral occupies this Tile, returns it (note that all the Tiles it occupies will then return it).
    // Otherwise, returns nullptr.
    // Neutrals are Minerals, Geysers and StaticBuildings (Cf. Neutral).
    // In some maps (e.g. Benzene.scx), several Neutrals are stacked at the same location.
    // In this case, only the "bottom" one is returned, while the other ones can be accessed using Neutral::NextStacked().
    // Because Neutrals never move on the Map, the returned value is guaranteed to remain the same, unless some Neutral
    // is destroyed and BWEM is informed of that by a call of Map::OnMineralDestroyed(BWAPI::Unit u) for exemple. In such a case,
    // BWEM automatically updates the data by deleting the Neutral instance and clearing any reference to it such as the one
    // returned by Tile::GetNeutral(). In case of stacked Neutrals, the next one is then returned.
//	Neutral *			GetNeutral() const				{ return m_pNeutral; }

    // TODO
	// Returns the number of Neutrals that occupy this Tile (Cf. GetNeutral).
//	int					StackedNeutrals() const;

    ////////////////////////////////////////////////////////////////////////////
    //	Details: The functions below are used by the BWEM's internals

    public void SetBuildable() {
        m_bits.buildable = 0x1;
    }

    public void SetGroundHeight(int h) {
//        assert ((0 <= h) && (h <= 2));
        if (!((h >= 0) && (h <= 2))) {
            throw new IllegalArgumentException(); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        }
        m_bits.groundHeight = (byte) h;
    }

    public void SetDoodad() {
        m_bits.doodad = 0x1;
    }

    // TODO
//    public void AddNeutral(Neutral pNeutral) {
//        bwem_assert(!m_pNeutral && pNeutral);
//        m_pNeutral = pNeutral;
//    }

    public void SetAreaId(int id) {
//        bwem_assert((id == -1) || !m_areaId && id);
//        assert ((id == -1) || (m_areaId == 0 && id != 0)); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        if (!((id == -1) || (m_areaId == 0 && id != 0))) {
            throw new IllegalStateException();
        }
        m_areaId = id;
    }

    public void ResetAreaId() {
        m_areaId = 0;
    }

    public void SetMinAltitude(int a) {
//        assert (a >= 0); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        if (!(a >= 0)) {
            throw new IllegalArgumentException();
        }
        m_minAltitude = a;
    }

    // TODO
//    public void RemoveNeutral(Neutral * pNeutral) {
//        bwem_assert(pNeutral && (m_pNeutral == pNeutral));
//        utils::unused(pNeutral);
//        m_pNeutral = nullptr;
//    }

    public int InternalData() {
        return m_internalData.intValue();
    }

    public void SetInternalData(int data) {
        m_internalData.setValue(data);
    }

}
