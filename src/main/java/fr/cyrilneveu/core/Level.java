package fr.cyrilneveu.core;

import fr.cyrilneveu.core.render.Mesh;

import java.util.HashMap;
import java.util.Map;

public class Level {
    private Map<String, Mesh> meshMap;

    public Level() {
        meshMap = new HashMap<>();
    }

    public void addMesh(String meshId, Mesh mesh) {
        meshMap.put(meshId, mesh);
    }

    public void end() {
        meshMap.values().forEach(Mesh::end);
    }

    public Map<String, Mesh> getMeshMap() {
        return meshMap;
    }
}
