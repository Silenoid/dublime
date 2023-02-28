package view.panels;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;

public class OpenGL2Panel extends GLJPanel {

    public OpenGL2Panel(int width, int height, GLEventListener glEventListener) {
        super(new GLCapabilities(GLProfile.get(GLProfile.GL2)));
        this.setSize(width, height);
        addGLEventListener(glEventListener);
    }

    public static OpenGL2Panel produceBasicPanel() {
        return new OpenGL2Panel(100, 100, new GLEventListener() {
            float time = 0;

            @Override
            public void init(GLAutoDrawable glAutoDrawable) {
            }

            @Override
            public void dispose(GLAutoDrawable glAutoDrawable) {
            }

            @Override
            public void display(GLAutoDrawable glad) {
                final GL2 gl = glad.getGL().getGL2();
                gl.glBegin(GL2.GL_TRIANGLES);
                gl.glColor3f((float) (1.0f * Math.sin(time / 10)), 0.0f, 0.0f);
                gl.glVertex3f(0.5f, 0.7f, 0.0f);
                gl.glColor3f(0.0f, 1.0f, 0.0f);
                gl.glVertex3f(-0.2f, -0.50f, 0.0f);
                gl.glColor3f(0.0f, 0.0f, 1.0f);
                gl.glVertex3f(0.5f, -0.5f, 0.0f);
                gl.glEnd();
                time += 1.0;
            }

            @Override
            public void reshape(GLAutoDrawable glAutoDrawable, int viewportX, int viewportY, int viewportWidth, int viewportHeight) {
            }
        });
    }
}
