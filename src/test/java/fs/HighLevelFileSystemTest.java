package fs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class HighLevelFileSystemTest {

  private LowLevelFileSystem lowLevelFileSystem;
  private HighLevelFileSystem fileSystem;

  @BeforeEach
  void initFileSystem() {
    lowLevelFileSystem = mock(LowLevelFileSystem.class);
    fileSystem = new HighLevelFileSystem(lowLevelFileSystem);
  }

  @Test
  void sePuedeAbrirUnArchivo() {
    when(lowLevelFileSystem.openFile("unArchivo.txt")).thenReturn(42);
    File file = fileSystem.open("unArchivo.txt");
    Assertions.assertEquals(file.getDescriptor(), 42);
  }

  @Test
  void siLaAperturaFallaUnaExcepcionEsLanzada() {
    when(lowLevelFileSystem.openFile("otroArchivo.txt")).thenReturn(-1);
    Assertions.assertThrows(CanNotOpenFileException.class, () -> fileSystem.open("otroArchivo.txt"));
  }

  @Test
  void sePuedeLeerSincronicamenteUnArchivoCuandoNoHayNadaParaLeer() {
    Buffer buffer = new Buffer(100);

    when(lowLevelFileSystem.openFile("ejemplo.txt")).thenReturn(42);
    when(lowLevelFileSystem.syncReadFile(42, buffer.getBytes(), 0, 100)).thenReturn(0);

    File file = fileSystem.open("ejemplo.txt");
    file.read(buffer);

    Assertions.assertEquals(0, buffer.getStart());
    Assertions.assertEquals(-1, buffer.getEnd());
    Assertions.assertEquals(0, buffer.getCurrentSize());
  }

  @Test
  void sePuedeLeerSincronicamenteUnArchivoCuandoHayAlgoParaLeer() {
    Buffer buffer = new Buffer(10);

    when(lowLevelFileSystem.openFile("ejemplo.txt")).thenReturn(42);
    when(lowLevelFileSystem.syncReadFile(42, buffer.getBytes(), 0, 9)).thenAnswer(invocation -> {
      Arrays.fill(buffer.getBytes(), 0, 4, (byte) 3);
      return 4;
    });

    File file = fileSystem.open("ejemplo.txt");
    file.read(buffer);

    Assertions.assertEquals(0, buffer.getStart());
    Assertions.assertEquals(3, buffer.getEnd());
    Assertions.assertEquals(4, buffer.getCurrentSize());
    Assertions.assertArrayEquals(buffer.getBytes(), new byte[] {3, 3, 3, 3, 0, 0, 0, 0, 0, 0});
  }

  @Test
  void siLaLecturaSincronicaFallaUnaExcepciónEsLanzada() {
    Buffer buffer = new Buffer(10);

    when(lowLevelFileSystem.openFile("archivoMalito.txt")).thenReturn(13);
    when(lowLevelFileSystem.syncReadFile(anyInt(), any(), anyInt(), anyInt())).thenReturn(-1);

    File file = fileSystem.open("archivoMalito.txt");

    Assertions.assertThrows(CanNotReadFileException.class, () -> file.read(buffer));
  }

  @Test
  void sePuedeEscribirSincronicamenteUnArchivoCuandoHayNoHayNadaParaEscribir() {
    Assertions.fail("Completar");
  }

  @Test
  void sePuedeEscribirSincronicamenteUnArchivoCuandoHayAlgoParaEscribir() {
    Assertions.fail("Completar");
  }

  @Test
  void sePuedeLeerAsincronicamenteUnArchivo() {
    Assertions.fail("Completar");
  }

  @Test
  void sePuedeEscribirAsincronicamenteUnArchivo() {
    Assertions.fail("Completar");
  }

  @Test
  void sePuedeCerrarUnArchivo() {
    Assertions.fail("Completar");
  }

  @Test
  @Disabled("Punto Bonus")
  void sePuedeSaberSiUnPathEsUnArchivoRegular() {
    Assertions.fail("Completar: te va a convenir extraer una nueva abstracción para diferenciar a los paths de los archivos");
  }

  @Test
  @Disabled("Punto Bonus")
  void sePuedeSaberSiUnPathEsUnDirectorio() {
    Assertions.fail("Completar: te va a convenir extraer una nueva abstracción para diferenciar a los paths de los archivos");
  }

  @Test
  @Disabled("Punto Bonus")
  void sePuedeSaberSiUnPathExiste() {
    Assertions.fail("Completar: te va a convenir extraer una nueva abstracción para diferenciar a los paths de los archivos");
  }
}
