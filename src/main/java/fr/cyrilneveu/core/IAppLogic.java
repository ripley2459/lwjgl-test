package fr.cyrilneveu.core;

public interface IAppLogic {
    void start(Window window, Level level, Render render);

    void input(Window window, Level level, long delta);

    void tick(Window window, Level level, long delta);

    void end();
}
