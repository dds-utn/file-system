package fs;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.function.Consumer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestDriver {

	private LowLevelFileSystem lowLevelFileSystem;
	private FileSystemAdapter highLevelFileSystem;
	private OpenedFile aFile;

	@Before
	public void setup() {
		lowLevelFileSystem = mock(LowLevelFileSystem.class);
		highLevelFileSystem = new FileSystemAdapter(lowLevelFileSystem);
	}

	@After
	public void tearDown() {
	}

	// TODO tests para abrir un archivo
	@Test
	public void sePuedeAbrirUnArchivo() {
		highLevelFileSystem.openFile("unaRuta");
		verify(lowLevelFileSystem).openFile("unaRuta");
	}

	@Test
	public void alAbrirUnArchivoMeDevuelveUnArchivoConElFD() {
		when(lowLevelFileSystem.openFile("unaRuta")).thenReturn(100);
		OpenedFile file = highLevelFileSystem.openFile("unaRuta");
		Assert.assertEquals(file.getFileDescriptor(), 100);
	}

	@Test
	public void sePuedeCerrarUnArchivo() {
		when(lowLevelFileSystem.openFile("unaRuta")).thenReturn(100);
		aFile = highLevelFileSystem.openFile("unaRuta");
		aFile.close();
		verify(lowLevelFileSystem).closeFile(anyInt());
	}

	
	@Ignore
	public void sePuedeLeerUnArchivoSincronicamente() {
		when(lowLevelFileSystem.openFile("unaRuta")).thenReturn(100);

		aFile = highLevelFileSystem.openFile("unaRuta");
		Buffer buffer = new Buffer(10);
		aFile.readSync(buffer);
		verify(lowLevelFileSystem)
			.syncReadFile(eq(aFile.getFileDescriptor()),
				same(buffer.getBytes()), eq(buffer.getStart()),
				eq(buffer.getEnd()));
	}

	@Test
	public void cambiarInicioDeBuffer() {
		Buffer buffer = new Buffer(10);
		buffer.limit(5);
		Assert.assertEquals(buffer.getEnd(), 5);
	}

	@Test
	public void sePuedeLeerAsincronicamente() {
		when(lowLevelFileSystem.openFile("unaRuta")).thenReturn(100);
		aFile = highLevelFileSystem.openFile("unaRuta");
		aFile.readAsync((buffer) -> {
			
		});
		verify(lowLevelFileSystem).
			asyncReadFile(anyInt(), 
					any(byte[].class),
					anyInt(), 
					anyInt(), 
					any(Consumer.class));
	}

}
