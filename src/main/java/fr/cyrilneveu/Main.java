package fr.cyrilneveu;

import fr.cyrilneveu.core.AppSettings;
import fr.cyrilneveu.core.IAppLogic;
import fr.cyrilneveu.core.Level;
import fr.cyrilneveu.core.Window;
import fr.cyrilneveu.core.render.Render;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;

public class Main implements IAppLogic {
    public final Window window;
    public final long windowL;
    private final IAppLogic coreLogic;
    private Level level;
    private Render render;

    public Main() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW"); // Init GLFW

        this.window = new Window("Test");
        this.windowL = window.getWindowL();

        this.coreLogic = this;
        this.render = new Render();
        this.level = new Level();

        this.coreLogic.start(this.window, this.level, this.render);

        tick();
        end();
    }

    public static void main(String[] args) {
        new Main();
    }

    private void tick() {
        GL.createCapabilities();
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        long initialTime = System.currentTimeMillis();
        float timeU = 1000.0f / AppSettings.tps;
        float timeR = AppSettings.fps > 0 ? 1000.0f / AppSettings.fps : 0;
        float deltaUpdate = 0;
        float deltaFps = 0;

        long updateTime = initialTime;
        while (!window.shouldClose()) {
            glfwPollEvents(); // poll for window events

            long now = System.currentTimeMillis();
            deltaUpdate += (now - initialTime) / timeU;
            deltaFps += (now - initialTime) / timeR;

            if (AppSettings.fps <= 0 || deltaFps >= 1) coreLogic.input(window, level, now - initialTime);

            if (deltaUpdate >= 1) {
                long diffTimeMillis = now - updateTime;
                coreLogic.tick(window, level, diffTimeMillis);
                updateTime = now;
                deltaUpdate--;
            }

            if (AppSettings.fps <= 0 || deltaFps >= 1) {
                render.render(window, level);
                deltaFps--;
                glfwSwapBuffers(windowL);
            }

            initialTime = now;
        }
    }

    @Override
    public void start(Window window, Level level, Render render) {

    }

    @Override
    public void input(Window window, Level level, long delta) {

    }

    @Override
    public void tick(Window window, Level level, long delta) {

    }

    @Override
    public void end() {
        render.end();
        level.end();

        glfwFreeCallbacks(windowL); // Free the window callbacks and destroy the window
        glfwDestroyWindow(windowL);
        glfwTerminate(); // Terminate GLFW
        GLFWErrorCallback callback = glfwSetErrorCallback(null);
        if (callback != null) callback.free();
    }
}