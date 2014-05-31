package fs;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import fs.LowLevelFileSystem;

public class TestDriver {

  private FileSystem fileSystem;
  private OpenFile file;
  private LowLevelFileSystem lowLevelFileSystemMock;
  private boolean asyncCalled = false;

  @Before
  public void setup() {
    lowLevelFileSystemMock = mock(LowLevelFileSystem.class);
    fileSystem = new LowLevelFileSystemAdapter(lowLevelFileSystemMock);
    file = fileSystem.openFile("zarlompa");
  }

  @After
  public void tearDown() {
    file.close();
  }

  @Test
  public void puedeDescribirEscriturasSincronicas() {
    Buffer buffer = new Buffer("hola".getBytes());

    file.syncWrite(buffer);

    verify(lowLevelFileSystemMock).syncWriteFile(anyInt(), same(buffer.getBytes()), eq(0),
        eq(buffer.getEnd()));
  }

  //TODO hacer tests unitarios para lecturas sincronicas y asincronicas
  //TODO hacer tests de integracion para los ejemplos propuestos 
}
