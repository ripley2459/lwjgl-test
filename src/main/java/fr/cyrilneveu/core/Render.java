package fr.cyrilneveu.core;

import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;

public class Render {
    public Render() {
        GL.createCapabilities();
    }

    public void end() {
    }

    public void render(Window window, Level level) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
