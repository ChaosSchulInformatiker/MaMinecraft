package org.q11mk.maminecraft.gl

import imgui.ImGui
import org.lwjgl.glfw.GLFW.*
import imgui.flag.ImGuiWindowFlags
import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11C.*

const val VSYNC = true

var window = 0L

lateinit var implGlfw: ImGuiImplGlfw
lateinit var implGl3: ImGuiImplGl3

var deltaTime = 0.0
var fps = 0.0

fun setup() {
    check(glfwInit()) { "Could not initialize GLFW" }
    window = glfwCreateWindow(1280, 720, "Malculator", 0L, 0L)
    //glfwMaximizeWindow(window); //Don't need that
    glfwShowWindow(window)
    glfwMakeContextCurrent(window)
    //glfwSetCharCallback(window, UI::charCallback)
    GL.createCapabilities()
    glClearColor(1f, 0f, 1f, 1f)
    ImGui.createContext()
    implGlfw = ImGuiImplGlfw()
    implGl3 = ImGuiImplGl3()
    val io = ImGui.getIO()
    //io.addConfigFlags(ImGuiConfigFlags.DockingEnable | ImGuiConfigFlags.ViewportsEnable);
    //val cambria: Unit = ImGuiUtils.loadFromResources("fonts/cambria.ttf")
    //fonts.add(io.getFonts().addFontFromMemoryTTF(cambria, 25f))
    //fonts.add(io.getFonts().addFontFromMemoryTTF(cambria, 15f))
    //io.setFontDefault(fonts.get(0))
    implGlfw.init(window, true)
    implGl3.init()
    setStyle()
    glfwSwapInterval(if (VSYNC) 1 else 0)
}

fun setStyle() {
    ImGui.getStyle().windowRounding = 0f
}

fun loop() {
    var lastTime = 0.0
    while (!glfwWindowShouldClose(window)) {
        deltaTime = glfwGetTime() - lastTime
        lastTime = glfwGetTime()
        fps = 1.0 / deltaTime

        glfwSwapBuffers(window)
        glfwPollEvents()
        glClear(GL_COLOR_BUFFER_BIT)
        implGlfw.newFrame()
        ImGui.newFrame()
        render()
        ImGui.render()
        implGl3.renderDrawData(ImGui.getDrawData())
    }
}

private var savedFps = 0.0

fun render() {
    ImGui.begin("TestImGui")
    ImGui.text("Hello World!")
    ImGui.text("Your fps: " + (savedFps + 0.5).toInt())
    ImGui.end()
}

fun shutdown() {
    implGlfw.dispose()
    implGl3.dispose()
    glfwDestroyWindow(window)
    glfwTerminate()
}