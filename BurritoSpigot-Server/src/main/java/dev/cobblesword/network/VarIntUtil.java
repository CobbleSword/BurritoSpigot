package dev.cobblesword.network;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.CorruptedFrameException;

// Borrowed from Velocity (https://github.com/VelocityPowered/Velocity) via PandaSpigot
public class VarIntUtil {
    private static final int[] VARINT_EXACT_BYTE_LENGTHS = new int[33];

    static {
        for (int i = 0; i <= 32; ++i) {
            VARINT_EXACT_BYTE_LENGTHS[i] = (int) Math.ceil((31d - (i - 1)) / 7d);
        }
        VARINT_EXACT_BYTE_LENGTHS[32] = 1; // Special case for the number 0.
    }

    /**
     * Reads a Minecraft-style VarInt from the specified {@code buf}.
     *
     * @param buf the buffer to read from
     * @return the decoded VarInt
     */
    public static int readVarInt(ByteBuf buf) {
        int read = readVarIntSafely(buf);
        if (read == Integer.MIN_VALUE) {
            throw new CorruptedFrameException("Bad VarInt decoded");
        }
        return read;
    }

    /**
     * Reads a Minecraft-style VarInt from the specified {@code buf}. The difference between this
     * method and {@link #readVarInt(ByteBuf)} is that this function returns a sentinel value if the
     * varint is invalid.
     *
     * @param buf the buffer to read from
     * @return the decoded VarInt, or {@code Integer.MIN_VALUE} if the varint is invalid
     */
    public static int readVarIntSafely(ByteBuf buf) {
        int i = 0;
        int maxRead = Math.min(5, buf.readableBytes());
        for (int j = 0; j < maxRead; j++) {
            int k = buf.readByte();
            i |= (k & 0x7F) << j * 7;
            if ((k & 0x80) != 128) {
                return i;
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * Returns the exact byte size of {@code value} if it were encoded as a VarInt.
     *
     * @param value the value to encode
     * @return the byte size of {@code value} if encoded as a VarInt
     */
    public static int varIntBytes(int value) {
        return VARINT_EXACT_BYTE_LENGTHS[Integer.numberOfLeadingZeros(value)];
    }

    /**
     * Writes a Minecraft-style VarInt to the specified {@code buf}.
     *
     * @param buf   the buffer to read from
     * @param value the integer to write
     */
    public static void writeVarInt(ByteBuf buf, int value) {
        // Peel the one and two byte count cases explicitly as they are the most common VarInt sizes
        // that the proxy will write, to improve inlining.
        if ((value & (0xFFFFFFFF << 7)) == 0) {
            buf.writeByte(value);
        } else if ((value & (0xFFFFFFFF << 14)) == 0) {
            int w = (value & 0x7F | 0x80) << 8 | (value >>> 7);
            buf.writeShort(w);
        } else {
            writeVarIntFull(buf, value);
        }
    }

    private static void writeVarIntFull(ByteBuf buf, int value) {
        // See https://steinborn.me/posts/performance/how-fast-can-you-write-a-varint/
        if ((value & (0xFFFFFFFF << 7)) == 0) {
            buf.writeByte(value);
        } else if ((value & (0xFFFFFFFF << 14)) == 0) {
            int w = (value & 0x7F | 0x80) << 8 | (value >>> 7);
            buf.writeShort(w);
        } else if ((value & (0xFFFFFFFF << 21)) == 0) {
            int w = (value & 0x7F | 0x80) << 16 | ((value >>> 7) & 0x7F | 0x80) << 8 | (value >>> 14);
            buf.writeMedium(w);
        } else if ((value & (0xFFFFFFFF << 28)) == 0) {
            int w = (value & 0x7F | 0x80) << 24 | (((value >>> 7) & 0x7F | 0x80) << 16)
                | ((value >>> 14) & 0x7F | 0x80) << 8 | (value >>> 21);
            buf.writeInt(w);
        } else {
            int w = (value & 0x7F | 0x80) << 24 | ((value >>> 7) & 0x7F | 0x80) << 16
                | ((value >>> 14) & 0x7F | 0x80) << 8 | ((value >>> 21) & 0x7F | 0x80);
            buf.writeInt(w);
            buf.writeByte(value >>> 28);
        }
    }
}
