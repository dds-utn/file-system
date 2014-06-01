package fs;

import java.util.function.Consumer;

/**
 * Interfaz de bajo nivel de nuestro sistema de archivos, que nos permite abrir
 * y cerrar archivos binarios, y leer y escribir en ellos en bloques.
 * 
 * La escritura es siempre sincrónica. La lectura puede ser sincrónica o
 * asincrónica, según que método se utilize
 */
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
   * Por ejemplo, si bufferStart = 0 y bufferEnd = 0 significa que hay que leer
   * 1 byte, y colocarlo en la posicion 0 del buffer
   * 
   * Advertencia: este método asume que el descriptor corresponde a un archivo
   * abierto, que bufferEnd >= bufferStart, y que <code>bufferStart >= 0</code>
   * y <code>bufferEnd <= bufferBytes.length - 1</code>
   * 
   * @param fd
   *          el descriptor (id) del archivo
   * @param bufferBytes
   * @param bufferStart
   *          la posicion inicial a partir de la cual escribir los bytes leidos
   * @param bufferEnd
   *          la posicion final hasta donde escribir los butes leidos
   *          (inclusive).
   * @return la cantidad de bytes leidos, que son siempre menores o iguales a
   *         bufferStart - bufferEnd + 1
   */
  int syncReadFile(int fd, byte[] bufferBytes, int bufferStart, int bufferEnd);

  /**
   * Escribe bufferStart - bufferEnd + 1 bytes, a partir de bufferStart
   * 
   * @param fd
   * @param bufferBytes
   * @param bufferStart
   * @param bufferEnd
   */
  void syncWriteFile(int fd, byte[] bufferBytes, int bufferStart, int bufferEnd);

  /**
   * Similar a {@link #syncReadFile(int, byte[], int, int)}, pero asincrónico.
   * 
   * En lugar de devolver la cantidad de bytes leidos, se lo pasa a un callback
   * 
   * @param fd
   * @param bufferBytes
   * @param bufferStart
   * @param bufferEnd
   * @param callback
   *          un Callback (tipado arbitrariamente como {@link Consumer}) que se
   *          ejecutará cuando la operación de lectura asincrónica se haya
   *          concretado. Le llega por parámetro la cantidad de bytes leidos
   *          efectivamente. Para cuando el {@link LowLevelFileSystem} ejecute
   *          este callabck, este {@link LowLevelFileSystem} ya habrá
   *          actualizado el buffer
   */
  void asyncReadFile(int fd, byte[] bufferBytes, int bufferStart, int bufferEnd,
      Consumer<Integer> callback);
}