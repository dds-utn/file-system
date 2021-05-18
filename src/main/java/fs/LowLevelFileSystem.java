package fs;

import java.util.function.Consumer;

/**
 * Interfaz de bajo nivel de nuestro sistema de archivos, que nos permite abrir
 * y cerrar archivos binarios, y leer y escribir en ellos en bloques.
 * <p>
 * La escritura es siempre sincrónica. La lectura puede ser sincrónica o
 * asincrónica, según que método se utilice
 */
public interface LowLevelFileSystem {

  /**
   * Dice si un path es un directorio
   *
   * @param path
   * @return si el path dado existe y es un directorio
   */
  boolean isDirectory(String path);

  /**
   * Dice si un path es un archivo regular, es decir, si
   * no detona un directorio, un link ni un archivo especial
   *
   * @param path
   * @return si el path dado existe y es un archivo regular
   */
  boolean isRegularFile(String path);

  /**
   * Dice si un path existe, independientemetne de aquello a lo que apunte
   *
   * @param path
   * @return si el path existe
   */
  boolean exists(String path);

  /**
   * Abre un archivo, dejándolo listo para ser leido o escrito.
   * <p>
   * Debe ser cerrado despues de usado
   *
   * @param path
   * @return el descriptor (id) del archivo, o -1 si no se puedo abrir
   */
  int openFile(String path);

  /**
   * Cierra un archivo.
   *
   * @param fd el descriptor del archivo
   */
  void closeFile(int fd);

  /**
   * Lee tantos bytes como pueda, esto es, bufferEnd - bufferStart + 1 bytes
   * devuelve la cantidad efectiva de bytes leidos y modifica al buffer de forma
   * acorde
   * <p>
   * Por ejemplo, si bufferStart = 0 y bufferEnd = 0 significa que hay que leer
   * 1 byte, y colocarlo en la posicion 0 del buffer
   * <p>
   * Advertencia: este método asume que el descriptor corresponde a un archivo
   * abierto, que bufferEnd >= bufferStart, y que <code>bufferStart >= 0</code>
   * y <code>bufferEnd <= bufferBytes.length</code>
   *
   * @param fd          el descriptor (id) del archivo
   * @param bufferBytes
   * @param bufferStart la posicion inicial a partir de la cual escribir los bytes leidos
   * @param bufferEnd   la posicion final hasta donde escribir los bytes leidos
   *                    (inclusive).
   * @return la cantidad de bytes leidos, que son siempre menores o iguales
   * a bufferEnd - bufferStart + 1, o -1, si no se pudo leer
   */
  int syncReadFile(int fd, byte[] bufferBytes, int bufferStart, int bufferEnd);

  /**
   * Escribe bufferEnd - bufferStart bytes, a partir de bufferStart
   *
   * @param fd
   * @param bufferBytes
   * @param bufferStart
   * @param bufferEnd
   */
  void syncWriteFile(int fd, byte[] bufferBytes, int bufferStart, int bufferEnd);

  /**
   * Similar a {@link #syncReadFile(int, byte[], int, int)}, pero asincrónico.
   * <p>
   * En lugar de devolver la cantidad de bytes leidos, se lo pasa a un callback
   *
   * @param fd
   * @param bufferBytes
   * @param bufferStart
   * @param bufferEnd
   * @param callback    un Callback (tipado como {@link Consumer}) que se
   *                    ejecutará cuando la operación de lectura asincrónica se haya
   *                    concretado. Le llega por parámetro la cantidad de bytes leidos
   *                    efectivamente. Para cuando el {@link LowLevelFileSystem} ejecute
   *                    este callabck, este {@link LowLevelFileSystem} ya habrá
   *                    actualizado el buffer. Si el archivo no se pudo leer,
   *                    los bytes leídos serán -1.
   */
  void asyncReadFile(int fd, byte[] bufferBytes, int bufferStart, int bufferEnd,
                     Consumer<Integer> callback);

  /**
   * Similar a {@link #syncReadFile(int, byte[], int, int)}, pero asincrónico.
   * <p>
   * Cuando la escritura terminó, el callback es ejecutado
   *
   * @param fd
   * @param bufferBytes
   * @param bufferStart
   * @param bufferEnd
   * @param callback    un Callback (tipado como {@link Runnable}) que se
   *                    ejecutará cuando la operación de escritura asincrónica se haya
   *                    concretado.
   */
  void asyncWriteFile(int fd, byte[] bufferBytes, int bufferStart, int bufferEnd,
                      Runnable callback);
}
