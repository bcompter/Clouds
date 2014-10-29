package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import com.badlogic.gdx.math.MathUtils;

public class Clouds extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
        ArrayList <Cloud> theClouds;
        ArrayList <Cloud> removeList;
        static final float CLOUD_INTERVAL = 11.0f;
        float timeSinceLastCloud = CLOUD_INTERVAL;
	
	@Override
	public void create () 
        {
            batch = new SpriteBatch();
            img = new Texture("cloud.png");
            theClouds = new ArrayList();
            removeList = new ArrayList();
	}

	@Override
	public void render () 
        {
            // How long has it been since we last rendered
            float deltaTime = Gdx.graphics.getDeltaTime();
            timeSinceLastCloud += deltaTime;
                    
            // Move all clouds
            for (Cloud c: theClouds)
            {
                c.Move(deltaTime);
                
                // Remove any clouds at the end of the screen
                if (c.position.x < (-1) * c.image.getWidth())
                    removeList.add(c);
            }
            
            // Remove all finished clouds
            for (Cloud c: removeList)
            {
                theClouds.remove(c);
            }
            removeList.clear();
            
            // If it has been a while, add a new cloud to the screen
            if (timeSinceLastCloud > CLOUD_INTERVAL)
            {
                float r = MathUtils.random();
                theClouds.add( new Cloud(600.0f, r*400.0f,img));
                timeSinceLastCloud = 0.0f;
            }
            
            Gdx.gl.glClearColor(0.789f, 0.859f, 0.976f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            
            // Draw all of our clouds
            for (Cloud c: theClouds)
            {
                batch.draw(c.image, c.position.x, c.position.y);
            }
            batch.end();
	}
        
        static class Cloud 
        {
            Vector2 position = new Vector2();
            Texture image;
            float velocity;
            
            public Cloud(float x, float y, Texture img)
            {
                this.position.x = x;
                this.position.y = y;
                this.image = img;
                
                // Give each cloud a little velocity
                float r = MathUtils.random();
                velocity = 7 + (5*r);
            }
            
            public void Move(float deltaT)
            {
                this.position.x -= (velocity * deltaT);
            }
            
        }  // end Cloud
        
}  // end ApplicationAdaptor
