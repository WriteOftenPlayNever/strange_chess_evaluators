package dev.wopn.realchess;

import dev.wopn.realchess.components.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {

    private int id;
    private List<EvaluatorComponent> components = new ArrayList<>();

    public Player() {
        this.id = new Random().nextInt();
        this.populate();
    }

    public Player(List<EvaluatorComponent> components) {
        this.components = components;
    }

    public List<EvaluatorComponent> getComponents() {
        return components;
    }

    public int getId() {
        return id;
    }

    public void populate() {
        components.add(BasicComponent.generate());
        for (int i = 0; i < new Random().nextInt(25) + 1; i++) {
            components.add(randomEC());
        }
    }

    private EvaluatorComponent randomEC() {
        EvaluatorComponent retVal = null;
        int selector = new Random().nextInt(17);

        switch (selector) {
            case 0 -> retVal = CentreDistanceComponent.generate();
            case 1 -> retVal = FileCountComponent.generate();
            case 2 -> retVal = RankCountComponent.generate();
            case 3 -> retVal = DiagonalCountComponent.generate();
            case 4 -> retVal = AllyAdjCountComponent.generate();
            case 5 -> retVal = AllyAdjValuesComponent.generate();
            case 6 -> retVal = EnemyAdjCountComponent.generate();
            case 7 -> retVal = EnemyAdjValuesComponent.generate();
            case 8 -> retVal = FavTilesCountComponent.generate();
            case 9 -> retVal = FavTilesValuesComponent.generate();
            case 10 -> retVal = HistoryCountComponent.generate();
            case 11 -> retVal = CaptureCountComponent.generate();
            case 12 -> retVal = PlyCountComponent.generate();
            case 13 -> retVal = PiecePopularityComponent.generate();
            case 14 -> retVal = TilePopularityComponent.generate();
            case 15 -> retVal = AvoidanceComponent.generate();
            case 16 -> retVal = CohesionComponent.generate();
        }

        return retVal;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", components=" + components +
                '}';
    }
}
