package fr.cyrilneveu.core;

import fr.cyrilneveu.utils.Size;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final long windowL;
    private final Size size;

    public Window(String title) {
        glfwDefaultWindowHints(); // Default window hints
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // Stay visible
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // Resizable

        // Compatibility with OSX
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        if (AppSettings.compatibleProfile)
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);
        else {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        }

        // Size
        if (AppSettings.size != Size.ZERO) this.size = AppSettings.size;
        else {
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            this.size = new Size(vidMode.width(), vidMode.height());
        }

        this.windowL = glfwCreateWindow(this.size.getWidth(), this.size.getHeight(), title, NULL, NULL);
        if (windowL == NULL) throw new RuntimeException("Failed to create the GLFW window!");

        glfwSetKeyCallback(windowL, (window, key, scancode, action, mods) -> closeOnEspace(key, action));// Callback when key is pressed

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            glfwGetWindowSize(windowL, pWidth, pHeight); // Get the window size passed to glfwCreateWindow
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());     // Get the resolution of the primary monitor
            glfwSetWindowPos(windowL, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);  // Center the window
        } // the stack frame is popped automatically

        glfwMakeContextCurrent(windowL); // Make the OpenGL context current
        glfwSwapInterval(AppSettings.vsync ? 1 : 0); // Enable v-sync

        glfwShowWindow(windowL); // Make the window visible
    }

    private void closeOnEspace(int key, int action) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(windowL, true);
    }

    public long getWindowL() {
        return windowL;
    }

    public Size getSize() {
        return size;
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(windowL);
    }
}
