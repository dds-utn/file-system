package fs;

public class Buffer {
	private byte[] bytes;
    private int start; 
    private int end;

	public Buffer(int size) {
		this.setBytes(new byte[size]);
		this.setStart(0);
		this.setEnd(size - 1);
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void limit(int bytesLeidos) {
		this.end = this.start + bytesLeidos;
	}

}
