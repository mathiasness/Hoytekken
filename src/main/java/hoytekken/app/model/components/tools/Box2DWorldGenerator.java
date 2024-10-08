package hoytekken.app.model.components.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import hoytekken.app.Hoytekken;

/**
 * Generates game layers from the map
 */
public class Box2DWorldGenerator {
    private final World world;
    private final TiledMap map;

    /**
     * Constructor for the box2d world generator
     * 
     * @param world world to generate the bodies in
     * @param map   map to generate the bodies from
     */
    public Box2DWorldGenerator(World world, TiledMap map) {
        this.world = world;
        this.map = map;
        generate();
    }

    /**
     * Generate box2d bodies from the map
     */
    private void generate() {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body = null;

        // generate main platform
        generateLayer(0, bdef, shape, fdef, body);

        // generate other platforms
        generateLayer(1, bdef, shape, fdef, body);
    }

    /**
     * Generate a layer of box2d bodies
     * 
     * @param layerIndex Which layer to generate
     * @param bdef       body definition
     * @param shape      shape of the body
     * @param fdef       fixture definition
     * @param body       body to generate
     */
    private void generateLayer(int layerIndex, BodyDef bdef, PolygonShape shape, FixtureDef fdef, Body body) {

        for (MapObject object : map.getLayers().get(layerIndex).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(
                    (rect.getX() + rect.getWidth() / 2) / Hoytekken.PPM,
                    (rect.getY() + rect.getHeight() / 2) / Hoytekken.PPM);

            body = world.createBody(bdef);
            body.setUserData(this);

            shape.setAsBox((rect.getWidth() / 2) / Hoytekken.PPM, (rect.getHeight() / 2) / Hoytekken.PPM);
            fdef.shape = shape;
            body.createFixture(fdef).setUserData(layerIndex == 0 ? "mainplatform" : "otherplatform");
        }
    }
}
