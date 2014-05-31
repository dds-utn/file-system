package fs;

import java.util.function.Consumer;

public interface LowLevelFileSystem {
  /**
   * Abre un archivo, dejándolo listo para ser leido o escrito.
   * 
   * Debe ser cerrado despues de usado
   * 
   * @param path
   * @return el descriptor (id) del archivo
   */
  int openFile(String path);

  /**
   * Cierra un archivo.
   * 
   * @param fd
   *          el descriptor del archivo
   */
  void closeFile(int fd);

  /**
   * Lee tantos bytes como pueda, esto es, bufferStart - bufferEnd + 1 bytes
   * devuelve la cantidad efectiva de bytes leidos y modifica al buffer de forma
   * acorde
   * 
   * Advertencia: este método asume que el descriptor corresponde a un archivo
   * abierto, que bufferEnd >= bufferStart, y que bufferStart >= 0 y
   * <code>bufferEnd <= bufferBytes.length - 1</code>
   * 
   * @param fd
   *          el descriptor (id) del archivo
   * @param bufferBytes
   * @param bufferStart
   * @param bufferEnd
   * @return la cantidad de bytes leidos
   */
  int syncReadFile(int fd, byte[] bufferBytes, int bufferStart, int bufferEnd);

  // Escribe bufferStart - bufferEnd + 1 bytes, a partir de bufferStart
  void syncWriteFile(int fd, byte[] bufferBytes, int bufferStart, int bufferEnd);

  // Intenta leer tantos bytes como pueda, esto es, bufferStart - bufferEnd +
  // 1 bytes
  // devuelve la cantidad efectiva de bytes leidos y modifica al buffer de
  // forma acorde
  void asyncReadFile(int fd, byte[] buffer, int bufferStart, int bufferEnd,
      Consumer<Integer> callback);
}