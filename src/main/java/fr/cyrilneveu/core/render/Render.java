package fr.cyrilneveu.core.render;

import fr.cyrilneveu.core.Level;
import fr.cyrilneveu.core.Window;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;

public class Render {
    private SceneRender sceneRender;

    public Render() {
        GL.createCapabilities();
        sceneRender = new SceneRender();
    }

    public void end() {
        sceneRender.cleanup();
    }

    public void render(Window window, Level level) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0, 0, window.getSize().getWidth(), window.getSize().getHeight());

        sceneRender.render(level);
    }
}
