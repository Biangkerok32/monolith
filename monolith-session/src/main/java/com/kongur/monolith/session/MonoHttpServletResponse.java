package com.kongur.monolith.session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.log4j.Logger;

/**
 * mono http response
 * 
 * @author zhengwei
 * @date��2011-6-15 �����ύ�����أ� getWriter()��getOutputStream()��flushBuffer()��sendError()��sendRedirect()��
 */

public class MonoHttpServletResponse extends HttpServletResponseWrapper {

    private static final Logger         log                 = Logger.getLogger(MonoHttpServletResponse.class);

    private static final TimeZone       GMT_TIME_ZONE       = TimeZone.getTimeZone("GMT");

    private static final String         COOKIE_DATE_PATTERN = "EEE, dd-MMM-yyyy HH:mm:ss 'GMT'";

    private static final FastDateFormat DATE_FORMAT         = FastDateFormat.getInstance(COOKIE_DATE_PATTERN,
                                                                                         GMT_TIME_ZONE, Locale.US);
    private static final String         EXPIRES             = "Expires";

    private static final String         PATH                = "Path";

    private static final String         DOMAIN              = "Domain";

    private static final String         HTTP_ONLY           = "HttpOnly";

    private static final String         SET_COOKIE          = "Set-Cookie";

    private static final String         COOKIE_SEPARATOR    = ";";

    private static final String         KEY_VALUE_SEPARATOR = "=";

    private boolean                     flushed             = false;
    private int                         status;
    private String                      sendRedirect;
    private SendError                   sendError;
    private MonoHttpSession             session;
    
    /**
     * �Ƿ񻺴��ڴ浽���д��
     */
    private boolean                     isWriterBuffered    = true;

    private SimpleServletOutputStream   outputStream;

    private BufferedServletWriter       writer;

    private PrintWriter                 streamAdapter;
    private ServletOutputStream         writerAdapter;

    public MonoHttpServletResponse(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {

        if (isWriterBuffered) {
            if (outputStream != null) {
                return outputStream;
            }

            if (writer != null) {
                if (writerAdapter != null) {
                    return writerAdapter;
                } else {
                    log.warn("Attampt to getOutputStream after calling getWriter.  This may cause unnecessary system cost.");
                    writerAdapter = new WriterOutputStream(writer, getCharacterEncoding());
                    return writerAdapter;
                }
            }

            outputStream = new SimpleServletOutputStream(new ByteArrayOutputStream());
            if (log.isDebugEnabled()) {
                log.debug("Created new byte buffer");
            }

            return outputStream;
        }

        this.getSession().commit();
        return super.getOutputStream();

    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (isWriterBuffered) {
            if (writer != null) {
                return writer;
            }
            if (outputStream != null) {
                // ���getOutputStream�����Ѿ������ã���streamת����PrintWriter��
                // ����������������������ڴ濪��������׼��servlet engine���������������Σ�
                // ֻ������servlet engine��Ҫ����������resin����
                if (streamAdapter != null) {
                    return streamAdapter;
                } else {
                    log.warn("Attampt to getWriter after calling getOutputStream.  This may cause unnecessary system cost.");
                    streamAdapter = new PrintWriter(new OutputStreamWriter(outputStream, getCharacterEncoding()), true);
                    return streamAdapter;
                }
            }
            writer = new BufferedServletWriter(new StringWriter());
            return writer;
        }

        this.getSession().commit();
        return super.getWriter();
    }

    /**
     * ����content���ȡ���Ч��
     * 
     * @param length content����
     */
    @Override
    public void setContentLength(int length) {
        if (!isWriterBuffered) {
            super.setContentLength(length);
        }
    }

    @Override
    public void flushBuffer() throws IOException {
        flushBufferAdapter();
        if (writer != null) {
            writer.flush();
        } else if (outputStream != null) {
            outputStream.flush();
        }

        this.flushed = true;
    }

    @Override
    public void sendError(int status) throws IOException {
        sendError(status, null);
    }

    @Override
    public void sendError(int status, String message) throws IOException {
        if ((sendError == null) && (sendRedirect == null)) {
            sendError = new SendError(status, message);
        }
    }

    @Override
    public void resetBuffer() {
        flushBufferAdapter();
        if (outputStream != null) {
            outputStream.updateOutputStream(new ByteArrayOutputStream());
        }

        if (writer != null) {
            writer.updateWriter(new StringWriter());
        }

        super.resetBuffer();
    }

    /**
     * ��buffer�е������ύ��������servlet������С�
     * <p>
     * �������û��ִ�й�<code>getOutputStream</code>��<code>getWriter</code> ��������÷��������κ����顣
     * </p>
     * 
     * @throws IOException ����������ʧ��
     * @throws IllegalStateException ���������bufferģʽ����bufferջ�в�ֹһ��buffer
     */
    public void commitBuffer() throws IOException {
        if (status > 0) {

            if (log.isDebugEnabled()) {
                log.debug("Set HTTP status to " + status);
            }
            super.setStatus(status);
        }

        if (sendError != null) {
            if (sendError.message == null) {
                if (log.isDebugEnabled()) {
                    log.debug("Set error page: " + sendError.status);
                }
                super.sendError(sendError.status);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Set error page: " + sendError.status + " " + sendError.message);
                }
                super.sendError(sendError.status, sendError.message);
            }
        } else if (sendRedirect != null) {
            if (log.isDebugEnabled()) {
                log.debug("Set redirect location to " + sendRedirect);
            }

            // ��location���������ת��һ�£���������ȷ��������US_ASCII�ַ���URL��ȷ���
            // String charset = getCharacterEncoding();
            //
            // if (charset != null) {
            // sendRedirect = new String(sendRedirect.getBytes(charset), "8859_1");
            // }

            super.sendRedirect(sendRedirect);
        } else if (outputStream != null) {
            flushBufferAdapter();
            OutputStream ostream = super.getOutputStream();
            byte[] bytes = this.outputStream.getBytes().toByteArray();

            ostream.write(bytes);

        } else if (writer != null) {
            flushBufferAdapter();
            PrintWriter pw = super.getWriter();

            try {
                String chars = this.writer.getChars().toString();
                pw.write(chars);
            } catch (NullPointerException e) {
                log.error("write has been closed", e);
            }

            if (log.isDebugEnabled()) {
                log.debug("Committed buffered characters to the Servlet writer");
            }
        }

        if (this.flushed) {
            super.flushBuffer();
        }
    }

    /**
     * ��ϴbuffer adapter��ȷ��adapter�е���Ϣ��д��buffer�С�
     */
    private void flushBufferAdapter() {
        if (streamAdapter != null) {
            streamAdapter.flush();
        }

        if (writerAdapter != null) {
            try {
                writerAdapter.flush();
            } catch (IOException e) {
                log.error(e);
            }
        }
    }

    public void addCookie(MonoCookie cookie) {
        if (!cookie.isHttpOnly()) {
            super.addCookie(cookie);
        } else {
            // ��Servlet 3.0��Ͳ���Ҫ��������δ����ˣ�����ֱ��cookie.setHttpOnly(true)
            // Ȼ��response.addCookie(cookie);
            String cookieString = buildHttpOnlyCookieString(cookie);
            addHeader(SET_COOKIE, cookieString);
        }
    }

    private String buildHttpOnlyCookieString(MonoCookie cookie) {
        StringBuilder cookieBuilder = new StringBuilder();

        cookieBuilder.append(cookie.getName()).append(KEY_VALUE_SEPARATOR).append(cookie.getValue());
        cookieBuilder.append(COOKIE_SEPARATOR);

        if (cookie.getDomain() != null) {
            cookieBuilder.append(DOMAIN).append(KEY_VALUE_SEPARATOR).append(cookie.getDomain());
            cookieBuilder.append(COOKIE_SEPARATOR);
        }

        if (cookie.getPath() != null) {
            cookieBuilder.append(PATH).append(KEY_VALUE_SEPARATOR).append(cookie.getPath());
            cookieBuilder.append(COOKIE_SEPARATOR);
        }

        if (cookie.getMaxAge() >= 0) {
            cookieBuilder.append(EXPIRES).append(KEY_VALUE_SEPARATOR).append(getCookieExpires(cookie));
            cookieBuilder.append(COOKIE_SEPARATOR);
        }

        cookieBuilder.append(HTTP_ONLY);

        return cookieBuilder.toString();
    }

    private String getCookieExpires(MonoCookie cookie) {
        String result = null;

        int maxAge = cookie.getMaxAge();
        if (maxAge > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, maxAge);
            result = DATE_FORMAT.format(calendar);
        } else { // maxAge == 0
            result = DATE_FORMAT.format(0); // maxAgeΪ0ʱ��ʾ��Ҫɾ����cookie����˽�ʱ����Ϊ��Сʱ�䣬��1970��1��1��
        }

        return result;
    }

    public boolean isFlushed() {
        return flushed;
    }

    public void setFlushed(boolean flushed) {
        this.flushed = flushed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSendRedirect() {
        return sendRedirect;
    }

    public void setSendRedirect(String sendRedirect) {
        this.sendRedirect = sendRedirect;
    }

    public MonoHttpSession getSession() {
        return session;
    }

    public void setSession(MonoHttpSession session) {
        this.session = session;
    }

    public boolean isWriterBuffered() {
        return isWriterBuffered;
    }

    public void setWriterBuffered(boolean isWriterBuffered) {
        this.isWriterBuffered = isWriterBuffered;
    }

    private class SendError {

        public final int    status;
        public final String message;

        public SendError(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }

    /**
     * ����һ�������ݱ������ڴ��е�<code>PrintWriter</code>��
     */
    private static class BufferedServletWriter extends PrintWriter {

        public BufferedServletWriter(StringWriter chars) {
            super(chars);
        }

        public Writer getChars() {
            return this.out;
        }

        public void updateWriter(StringWriter chars) {
            this.out = chars;
        }

        public void close() {
            try {
                this.out.close();
            } catch (IOException e) {
            }

        }
    }

    /**
     * ��<code>Writer</code>���䵽<code>ServletOutputStream</code>��
     */
    private static class WriterOutputStream extends ServletOutputStream {

        private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        private Writer                writer;
        private String                charset;

        public WriterOutputStream(Writer writer, String charset) {
            this.writer = writer;
            this.charset = (null == charset ? "ISO-8859-1" : charset);
        }

        @Override
        public void write(int b) throws IOException {
            buffer.write((byte) b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            buffer.write(b, 0, b.length);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            buffer.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            byte[] bytes = buffer.toByteArray();

            if (bytes.length > 0) {
                ByteArrayInputStream inputBytes = new ByteArrayInputStream(bytes);
                InputStreamReader reader = new InputStreamReader(inputBytes, charset);

                io(reader, writer);
                writer.flush();

                buffer.reset();
            }
        }

        @Override
        public void close() throws IOException {
            this.flush();
        }

        private void io(Reader in, Writer out) throws IOException {
            char[] buffer = new char[8192];
            int amount;

            while ((amount = in.read(buffer)) >= 0) {
                out.write(buffer, 0, amount);
            }
        }
    }

    private class SimpleServletOutputStream extends ServletOutputStream {

        private ByteArrayOutputStream bytes;

        public SimpleServletOutputStream(ByteArrayOutputStream bytes) {
            this.bytes = bytes;
        }

        public void updateOutputStream(ByteArrayOutputStream bytes) {
            this.bytes = bytes;
        }

        public ByteArrayOutputStream getBytes() {
            return this.bytes;
        }

        @Override
        public void write(int b) throws IOException {
            bytes.write((byte) b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            write(b, 0, b.length);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            bytes.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            bytes.flush();
        }

        @Override
        public void close() throws IOException {
            bytes.flush();
            bytes.close();
        }

    }

    public static void main(String[] args) throws IOException {
        // SimpleServletWriter writer = new SimpleServletWriter(new SimpleServletOutputStream(new
        // ByteArrayOutputStream()));
        //
        // writer.write("fafafa");
        // writer.flush();
        // System.out.println(writer.getChars().toString());

        // SimpleServletOutputStream outputStream = new SimpleServletOutputStream(new ByteArrayOutputStream());
        // outputStream.write("fafhakfakf".getBytes());
        // outputStream.flush();
        // byte[] bytes = outputStream.getBytes().toByteArray();
        // System.out.println(new String(bytes));
    }

}
