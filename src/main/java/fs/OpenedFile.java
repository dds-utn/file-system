package fs;

import java.util.function.Consumer;

public class OpenedFile {

	private int fileDescriptor;
	private LowLevelFileSystem lowLevelFileSystem;

	public OpenedFile(int fd, LowLevelFileSystem lowLevelFileSystem) {
		this.setFileDescriptor(fd);
		this.lowLevelFileSystem = lowLevelFileSystem;
	}

	public int getFileDescriptor() {
		return fileDescriptor;
	}

	public void setFileDescriptor(int fileDescriptor) {
		this.fileDescriptor = fileDescriptor;
	}

	public void close() {
		this.lowLevelFileSystem.closeFile(this.getFileDescriptor());
	}

	public void readSync(Buffer buffer) {
		int readBytes = this.lowLevelFileSystem.syncReadFile(this.getFileDescriptor(),
				buffer.getBytes(), buffer.getStart(), buffer.getEnd());
		buffer.limit(readBytes);
	}

	public void readAsync(Consumer<Buffer> callback) {
		Buffer buffer = new Buffer(100);
		this.lowLevelFileSystem.asyncReadFile(
				this.getFileDescriptor(),
				buffer.getBytes(), 
				buffer.getStart(), buffer.getEnd(), 
				readBytes -> {
					buffer.limit(readBytes);
					callback.accept(buffer);
				});
	}

}
