package JUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.logging.Logger;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager;
import engine.FileManager;
import engine.InputManager;

public class CoreTest {

	@Test
	public void testGetLogger() {
		assertSame(Logger.class, Core.getLogger().getClass());
	}

	@Test
	public void testGetDrawManager() {
		assertSame(DrawManager.class, Core.getDrawManager().getClass());
	}

	@Test
	public void testGetInputManager() {
		assertSame(InputManager.class, Core.getInputManager().getClass());
	}

	@Test
	public void testGetFileManager() {
		assertSame(FileManager.class, Core.getFileManager().getClass());
	}

	@Test
	public void testGetCooldown() {
		assertSame(Cooldown.class, Core.getCooldown(1000).getClass());
	}

	@Test
	public void testGetVariableCooldown() {
		assertSame(Cooldown.class, Core.getVariableCooldown(1000, 100).getClass());
	}

}
