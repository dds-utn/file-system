package fs;

public class FileSystemAdapter {

	private LowLevelFileSystem lowLevelFileSystem;

	public FileSystemAdapter(LowLevelFileSystem lowLevelFileSystem) {
		this.lowLevelFileSystem = lowLevelFileSystem;
	}

	public OpenedFile openFile(String path) {
		int fd = this.lowLevelFileSystem.openFile(path);
		return new OpenedFile(fd, this.lowLevelFileSystem);
	}


}
