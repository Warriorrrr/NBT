package net.querz.nbt;

import java.io.IOException;
import java.util.Arrays;

public class ByteArrayTag extends Tag {
	private byte[] value;
	
	protected ByteArrayTag() {
		this(new byte[0]);
	}
	
	public ByteArrayTag(byte[] value) {
		this("", value);
	}
	
	public ByteArrayTag(String name, byte[] value) {
		super(TagType.BYTE_ARRAY, name);
		setValue(value);
	}
	
	public void setValue(byte[] value) {
		this.value = value;
	}
	
	public int length() {
		return value.length;
	}
	
	@Override
	public byte[] getValue() {
		return value;
	}

	@Override
	protected void serialize(NBTOutputStream nbtOut) throws IOException {
		nbtOut.dos.writeInt(value.length);
		nbtOut.dos.write(value);
	}
	
	@Override
	protected ByteArrayTag deserialize(NBTInputStream nbtIn) throws IOException {
		int length = nbtIn.dis.readInt();
		value = new byte[length];
		nbtIn.dis.readFully(value);
		return this;
	}

	@Override
	public String toTagString() {
		return NBTUtil.createNamePrefix(this) + "[" + NBTUtil.joinBytes(",", value) + "]";
	}
	
	@Override
	public String toString() {
		return "<byte[]:" + getName() + ":[" + NBTUtil.joinBytes(",", value) + "]>";
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ByteArrayTag)) {
			return false;
		}
		ByteArrayTag tag = (ByteArrayTag) other;
		return getName() != null && getName().equals(tag.getName()) && Arrays.equals(value, tag.getValue());
	}
	
	@Override
	public ByteArrayTag clone() {
		return new ByteArrayTag(getName(), value);
	}
}
