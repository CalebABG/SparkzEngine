package com.engine.Verlet;

public interface VModes {
    enum EditorModes {
        Add      ,
        Delete   ,
        Select   ,
        Drag     ,
        Position ,
        Attach
    }

    enum CreationModes {
        Point       ,
        Stick       ,
        IKChain     ,
        Box         ,
        SolidMesh   ,
        ElasticMesh ,
        Cloth
    }

    static CreationModes getMode(CreationModes mode, int sign) {
        if (sign > 0) return CreationModes.values()[(mode.ordinal() + 1) % CreationModes.values().length];
        else return CreationModes.values()[(CreationModes.values().length + (mode.ordinal() - 1)) % CreationModes.values().length];
    }
}
