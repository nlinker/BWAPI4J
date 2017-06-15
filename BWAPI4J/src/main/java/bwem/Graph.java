package bwem;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.mutable.MutableInt;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.WalkPosition;

public class Graph {

    private Map map = null;
    private List<Area> areas = null;
    private List<List<Integer>> chokepointDistanceMatrix = null;
    private List<List<CPPath>> pathsBetweenChokepoints = null;

    private Graph() {
        throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
    }

    public Graph(Map map) {
        this.map = map;
        this.areas = new ArrayList<>();
    }

    public Map getMap() {
        return this.map;
    }

    //TODO
    public CPPath getPath(Position a, Position b, MutableInt length) {

        Area areaA = getNearestArea(a.toWalkPosition());
        Area areaB = getNearestArea(b.toWalkPosition());

        if (areaA.equals(areaB)) {
            if (length != null) {
                length.setValue((int) a.getDistance(b));
            }
            return new CPPath(); //TODO: Return empty list or null?
        }

        if (!areaA.isAccessibleFrom(areaB)) {
            if (length != null) {
                length.setValue(-1);
            }
            return new CPPath(); //TODO: Return empty list or null?
        }

        int minDist_A_B = Integer.MAX_VALUE;

        Chokepoint bestCpA = null;
        Chokepoint bestCpB = null;

        //TODO
        for (Chokepoint cpA : areaA.getChokepoints()) {
            if (cpA.isBlocked()) {
                continue;
            }
            int dist_A_cpA = (int) a.getDistance(cpA.getCenter().toPosition());
            for (Chokepoint cpB : areaB.getChokepoints()) {
                if (cpB.isBlocked()) {
                    continue;
                }
                int dist_B_cpB = (int) b.getDistance(cpB.getCenter().toPosition());
                int dist_A_B = dist_A_cpA + dist_B_cpB + getDistance(cpA, cpB);
                if (dist_A_B < minDist_A_B)
                {
                    minDist_A_B = dist_A_B;
                    bestCpA = cpA;
                    bestCpB = cpB;
                }
            }
        }

//        bwem_assert(minDist_A_B != numeric_limits<int>::max());
        if (!(minDist_A_B != Integer.MAX_VALUE)) {
            throw new IllegalStateException("failed assert: minDist_A_B != Integer.MAX_VALUE");
        }

        CPPath path = getPath(bestCpA, bestCpB);

        if (length != null) {
//            bwem_assert(Path.size() >= 1);
            if (!(path.size() >= 1)) {
                throw new IllegalStateException("failed assert: path.size() >= 1");
            }

            length.setValue(minDist_A_B);

            if (path.size() == 1) {
                if (!(bestCpA.equals(bestCpB))) {
                    throw new IllegalStateException("failed assert: bestCpA.equals(bestCpB)");
                }
                Chokepoint cp = bestCpA;
                //TODO: Here
//                Position cpEnd1 =
            }
        }

        throw new UnsupportedOperationException("not supported yet");
    }

    public CPPath getPath(Position a, Position b) {
        return getPath(a, b, null);
    }

    public CPPath getPath(Chokepoint a, Chokepoint b) {
        return this.pathsBetweenChokepoints.get(a.getIndex().toInt()).get(b.getIndex().toInt());
    }

    public Area getArea(Area.Id areaId) {
//        bwem_assert(Valid(id));
        if (!isValid(areaId)) {
            throw new IllegalArgumentException("id is not valid");
        }
        return this.areas.get(areaId.toInt() - 1);
    }

    public Area getArea(WalkPosition w) {
        Area.Id areaId = this.map.getMiniTile(w).getAreaId();
        return (areaId.toInt() > 0) ? getArea(areaId) : null;
    }

    public Area getNearestArea(WalkPosition w) {
        Area area = getArea(w);
        if (area != null) {
            return area;
        }

        w = this.map.breadFirstSearch(
            w,
            new Pred() {
                @Override
                public boolean is(Object... args) {
                    Object ttile = args[0];
                    if (ttile instanceof MiniTile) {
                        MiniTile tile = (MiniTile) ttile;
                        return (tile.getAreaId().toInt() > 0);
//                    } else if (ttile instanceof Tile) {
//                        //TODO
                    } else {
                        throw new IllegalArgumentException("tile type not supported");
                    }
                }
            },
            new Pred() {
                @Override
                public boolean is(Object... args) {
                    return true;
                }
            }
        );

        return getArea(w);
    }

    public boolean isValid(Area.Id id) {
        return (id.toInt() >= 1 && this.areas.size() >= id.toInt());
    }

    int getDistance(Chokepoint cpA, Chokepoint cpB) {
        return this.chokepointDistanceMatrix.get(cpA.getIndex().toInt()).get(cpB.getIndex().toInt());
    }

}
