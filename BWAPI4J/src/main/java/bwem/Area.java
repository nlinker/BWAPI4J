package bwem;

import java.util.List;
import java.util.Objects;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public class Area {

    private Id areaId;
    private GroupId groupId;
    private TilePosition topLeft;
    private TilePosition bottomRight;
    private WalkPosition top;
    private List<Chokepoint> chokepoints;
    private Graph graph;
    private int tiles;
    private int miniTiles;
    private int buildableTiles;
    private int highGroundTiles;
    private int veryHighGroundTiles;
    private Altitude maxAltitude;

    private Area() {
        throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
    }

    public Area(Graph graph, Area.Id areaId, WalkPosition top, int miniTiles) {
        this.graph = graph;
        this.areaId = new Area.Id(areaId);
        this.top = new WalkPosition(top.getX(), top.getY());
        this.miniTiles = miniTiles;

        this.tiles = 0;
        this.buildableTiles = 0;
        this.highGroundTiles = 0;
        this.veryHighGroundTiles = 0;

        this.topLeft = new TilePosition(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.bottomRight = new TilePosition(Integer.MIN_VALUE, Integer.MIN_VALUE);

//        bwem_assert(areaId > 0);
        if (!(this.areaId.intValue() > 0)) {
            throw new IllegalArgumentException("invalid Areae.Id");
        }

        MiniTile topMiniTile = this.graph.getMap().getMiniTile(top);
//        bwem_assert(topMiniTile.AreaId() == areaId);
        if (!(topMiniTile.getAreaId().equals(areaId))) {
            throw new IllegalArgumentException();
        }

        this.maxAltitude = new Altitude(topMiniTile.getAltitude());
    }

    /**
     * Unique id > 0 of this Area. Range = 1 .. Map::Areas().size()
     * this == Map::GetArea(Id())
     * Id() == Map::GetMiniTile(w).AreaId() for each walkable MiniTile w in this Area.
     * Area::ids are guaranteed to remain unchanged.
     */
    public Id getAreaId() {
        return new Id(this.areaId);
    }

    /**
     * Unique id > 0 of the group of Areas which are accessible from this Area.
     * For each pair (a, b) of Areas: a->GroupId() == b->GroupId()  <==>  a->AccessibleFrom(b)
     * A groupId uniquely identifies a maximum set of mutually accessible Areas, that is, in the absence of blocking ChokePoints, a continent.
     */
    public GroupId getGroupId() {
        return new GroupId(this.groupId);
    }

    public void setGroupId(GroupId groupId) {
//        { bwem_assert(gid >= 1); m_groupId = gid; }
        if (!(groupId.intValue() >= 1)) {
            throw new IllegalArgumentException("invalid Area.GroupId");
        }
        this.groupId = new GroupId(groupId);
    }

    public TilePosition getTopLeft() {
        return new TilePosition(this.topLeft.getX(), this.topLeft.getY());
    }

    public TilePosition getBottomRight() {
        return new TilePosition(this.bottomRight.getX(), this.bottomRight.getY());
    }

    public TilePosition getBoundingBoxSize() {
        return this.bottomRight.add(this.topLeft).subtract(new TilePosition(1, 1));
    }

    /**
     * Position of the MiniTile with the highest Altitude() value.
     */
    public WalkPosition getTop() {
        return this.top;
    }

    /**
     * Returns Map::GetMiniTile(Top()).Altitude().
     */
    //TODO: It does? ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    public Altitude getMaxAltitude() {
        return new Altitude(this.maxAltitude);
    }

    /**
     * Returns the number of MiniTiles in this Area.
     * This most accurately defines the size of this Area.
     */
    //TODO: Rename this function to `size()'?
    public int getMiniTileCount() {
        return this.miniTiles;
    }

    public boolean isAccessibleFrom(Area that) {
        return (this.groupId.val == that.groupId.val);
    }

    public List<Chokepoint> getChokepoints() {
        return this.chokepoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Area)) {
            throw new IllegalArgumentException("object is not an instance of Area");
        } else {
            Area that = (Area) o;
            return (this.areaId.intValue() == that.areaId.intValue());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.areaId.intValue());
    }

    /**
     * Immutable wrapper of the integer primitive to satisfy
     * the original C++ definition:
     * area.h:54:typedef int16_t id;
     */
    public static class Id implements IWrappedInteger<Id>, Comparable<Id> {

        private int val;

        private Id() {
            throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
        }

        public Id(int val) {
            this.val = val;
        }

        public Id(Id id) {
            this.val = id.val;
        }

        @Override
        public Id add(Id that) {
            return new Id(this.val + that.val);
        }

        @Override
        public Id subtract(Id that) {
            return new Id(this.val + that.val);
        }

        @Override
        public int intValue() {
            return this.val;
        }

        @Override
        public int compareTo(Id that) {
            int lhs = this.val;
            int rhs = that.val;
            return (lhs < rhs) ? -1 : (lhs > rhs) ? 1 : 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof Area.Id)) {
                throw new IllegalArgumentException("object is not an instance of Area.Id");
            } else {
                Area.Id that = (Area.Id) o;
                return this.val == that.val;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.val);
        }

    }

    /**
     * Immutable wrapper of the integer primitive to satisfy
     * the original C++ definition:
     * area.h:56:typedef int16_t groupId;
     */
    public static class GroupId implements IWrappedInteger<GroupId>, Comparable<GroupId> {

        private int val;

        private GroupId() {}

        public GroupId(int val) {
            this.val = val;
        }

        public GroupId(GroupId groupId) {
            this.val = groupId.val;
        }

        @Override
        public GroupId add(GroupId that) {
            return new GroupId(this.val + that.val);
        }

        @Override
        public GroupId subtract(GroupId that) {
            return new GroupId(this.val - that.val);
        }

        @Override
        public int intValue() {
            return this.val;
        }

        @Override
        public int compareTo(GroupId that) {
            int lhs = this.val;
            int rhs = that.val;
            return (lhs < rhs) ? -1 : (lhs > rhs) ? 1 : 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof Area.GroupId)) {
                throw new IllegalArgumentException("object is not an instance of Area.GroupId");
            } else {
                Area.GroupId that = (Area.GroupId) o;
                return this.val == that.val;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.val);
        }

    }


    /**
     * Helper class for void Map::ComputeAreas()
     * Maintains some information about an area being computed.
     * A TempAreaInfo is not Valid() in two cases:
     * - a default-constructed TempAreaInfo instance is never Valid (used as a dummy value to simplify the algorithm).
     * - any other instance becomes invalid when absorbed (see Merge)
     */
    public static class TempInfo {

        private boolean isValid;
        private Area.Id areaId;
        private WalkPosition top;
        private Altitude highestAltitude;
        private int size;

        public TempInfo() {
            this.isValid = false;
            this.areaId = new Area.Id(0);
            this.top = new WalkPosition(0, 0);

//            bwem_assert(!Valid());
        }

//        TempAreaInfo(Area::id id, MiniTile * pMiniTile, WalkPosition pos)
//                : m_valid(true), m_id(id), m_top(pos), m_size(0), m_highestAltitude(pMiniTile->Altitude())
//                                            { Add(pMiniTile); bwem_assert(Valid()); }
        public TempInfo(Area.Id areaId, MiniTile miniTile, WalkPosition w) {
            this.isValid = true;
            this.areaId = areaId;
            this.top = w;
            this.size = 0;
            this.highestAltitude = miniTile.getAltitude();

            add(miniTile);

            /*
                TODO: Further investigation required. I don't think this
                will ever fail here but it's included just in case and to stay
                true to the original code.
            */
            if (!this.isValid) {
                throw new IllegalStateException();
            }
        }

        public boolean isValid() {
            return this.isValid;
        }

        public Area.Id getId() {
//            { bwem_assert(Valid()); return m_id; }
            if (!isValid()) {
                throw new IllegalStateException();
            }
            return this.areaId;
        }

        public WalkPosition getTop()  {
//            { bwem_assert(Valid()); return m_top; }
            if (!isValid()) {
                throw new IllegalStateException();
            }
            return this.top;
        }

        public int getSize() {
//            { bwem_assert(Valid()); return m_size; }
            if (!isValid()) {
                throw new IllegalStateException();
            }
            return this.size;
        }

        public Altitude getHighestAltitude() {
//            { bwem_assert(Valid()); return m_highestAltitude; }
            if (!isValid()) {
                throw new IllegalStateException();
            }
            return this.highestAltitude;
        }

        public void add(MiniTile miniTile) {
//            { bwem_assert(Valid()); ++m_size; pMiniTile->SetAreaId(m_id); }
            if (!isValid()) {
                throw new IllegalStateException();
            }
            this.size++;
            miniTile.setAreaId(this.areaId);
        }

        public void merge(TempInfo absorbed) {
//            bwem_assert(Valid() && Absorbed.Valid());
//            bwem_assert(m_size >= Absorbed.m_size);
            if (!(this.isValid && absorbed.isValid)) {
                throw new IllegalStateException("failed: isValid");
            } else if (!(this.size >= absorbed.size)) {
                throw new IllegalArgumentException("invalid TempInfo size");
            } else {
                this.size += absorbed.size;
                absorbed.isValid = false;
            }
        }

//        TempAreaInfo &		operator=(const TempAreaInfo &) = delete;
        @Override
        public boolean equals(Object o) {
            throw new UnsupportedOperationException("use of this method is forbidden");
        }

    }

}
