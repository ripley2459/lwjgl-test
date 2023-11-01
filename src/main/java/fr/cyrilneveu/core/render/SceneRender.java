package fr.cyrilneveu.core.render;

import fr.cyrilneveu.core.Level;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class SceneRender {
    private OGLProgram shaderProgram;

    public SceneRender() {
        List<OGLProgram.ShaderModuleData> shaderModuleDataList = new ArrayList<>();
        shaderModuleDataList.add(new OGLProgram.ShaderModuleData("shaders/vertex.vs", GL_VERTEX_SHADER));
        shaderModuleDataList.add(new OGLProgram.ShaderModuleData("shaders/fragment.fs", GL_FRAGMENT_SHADER));
        shaderProgram = new OGLProgram(shaderModuleDataList);
    }

    public void cleanup() {
        shaderProgram.cleanup();
    }

    public void render(Level level) {
        shaderProgram.bind();

        level.getMeshMap().values().forEach(mesh -> {
                    glBindVertexArray(mesh.getVaoId());
                    glDrawArrays(GL_TRIANGLES, 0, mesh.getNumVertices());
                }
        );

        glBindVertexArray(0);

        shaderProgram.unbind();
    }
}
