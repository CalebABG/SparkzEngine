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
}
