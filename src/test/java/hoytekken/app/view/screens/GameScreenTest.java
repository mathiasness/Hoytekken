// package hoytekken.app.view.screens;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.mockingDetails;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import com.badlogic.gdx.ApplicationAdapter;
// import com.badlogic.gdx.ApplicationListener;
// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.backends.headless.HeadlessApplication;
// import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
// import com.badlogic.gdx.graphics.GL20;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// import hoytekken.app.Hoytekken;
// import hoytekken.app.model.HTekkenModel;
// import hoytekken.app.model.components.GameState;
// import hoytekken.app.view.ViewableModel;

// public class GameScreenTest {

// private static HeadlessApplication application;
// private static Hoytekken gameMock;
// private static ViewableModel modelMock;

// private GameScreen gameScreen;

// @BeforeAll
// static void setUpBeforeAll() {
// HeadlessApplicationConfiguration config = new
// HeadlessApplicationConfiguration();
// application = new HeadlessApplication(new Hoytekken(), config);

// // Mock dependencies
// Gdx.gl = mock(GL20.class);
// gameMock = mock(Hoytekken.class);
// modelMock = mock(ViewableModel.class);
// gameMock.batch = mock(SpriteBatch.class);
// }

// @BeforeEach
// void setUpBeforeEach() {
// gameScreen = new GameScreen(gameMock, modelMock);
// }

// @AfterAll
// static void cleanUp() {
// application.exit();
// application = null;
// }

// @Test
// void sanityTest() {
// assertNotNull(gameScreen);
// assertNotNull(gameScreen.gameCam);
// assertNotNull(gameScreen.gamePort);
// }
// }
