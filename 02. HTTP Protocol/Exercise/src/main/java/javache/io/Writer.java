package javache.io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class Writer {

    private Writer() {
    }

    public static void writeBytes(final byte[] data, final OutputStream outputStream) throws IOException {
        final DataOutputStream buffer = new DataOutputStream(outputStream);

        buffer.write(data);
    }
}
