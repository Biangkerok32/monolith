package com.runssnail.monolith.socket.mina;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;

import com.runssnail.monolith.lang.StringUtil;
import com.runssnail.monolith.socket.message.codec.CodecUtils;



/**
 * ģ�⽻������������
 * 
 * @author zhengwei
 */
public class CodecClientTest {

    private static final int port = CodecServerTest.port;

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        test("6200");
    }

    /**
     * ����D�ι̶����Ȳ��ֵ��������
     * 
     * @param transCode
     * @return
     */
    private static String buildFixedParams(String transCode) {
        String body = "";
        if ("6200".equals(transCode)) {
            body = build6200Params();
        }

        return body;
    }

    /**
     * E�β���
     * 
     * @param transCode
     * @return
     */
    private static String buildCBlockParams(String transCode) {

        String body = "";
        if ("6200".equals(transCode)) {
            body = build6200Params();
        }

        return body;
    }

    private static void test(String transCode) throws Exception {
        CodecServerTest server = new CodecServerTest();
        server.start(transCode);
        send(transCode);

        server.endServer();
    }

    private static void send(String transCode) throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", port));
        OutputStream out = socket.getOutputStream();
        writeBytes(transCode, out);
        InputStream in = socket.getInputStream();
        byte[] buffer = new byte[2048];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            System.out.println("len=" + len + ", get==" + new String(buffer, com.runssnail.monolith.socket.Constants.DEFAULT_CHARSET));
        }

        System.out.println("received, " + new String(buffer));
        in.close();
        out.close();
        socket.close();
    }

    /**
     * a + b + c
     * 
     * @param transCode
     * @param out
     * @throws Exception
     */
    private static void writeBytes(String transCode, OutputStream out) throws Exception {

        String headerStr = buildHead(transCode);

//        String fixedParams = buildFixedParams(transCode);

        String cBlockParams = buildCBlockParams(transCode);

        IoBuffer bb = IoBuffer.allocate(256);
        bb.setAutoExpand(true);
        
       

        CharsetEncoder encoder = com.runssnail.monolith.socket.Constants.DEFAULT_CHARSET.newEncoder();

        int headerLen = headerStr.getBytes(com.runssnail.monolith.socket.Constants.DEFAULT_CHARSET).length;
        
        

        //int fixedLen = fixedParams.getBytes(com.runssnail.monolith.socket.Constants.DEFAULT_CHARSET).length;

        int cLen = cBlockParams.getBytes(com.runssnail.monolith.socket.Constants.DEFAULT_CHARSET).length;
        
        int len = 4 + headerLen + cLen; // a + b + c = �������ĳ���

//        int dLen = headerLen + fixedLen;
//        int bLen = headerLen ;

//        bb.putString(StringUtil.alignRight(String.valueOf(bLen), 8, "0"), encoder); // a
        
        bb.putInt(len);

//        bb.putString(StringUtil.alignRight(String.valueOf(cLen), 8, "0"), encoder); // b

//        bb.putString(StringUtil.alignRight("0", 16, "0"), encoder); // c

        bb.putString(headerStr, encoder);
//        bb.putString(fixedParams, encoder);

        if (StringUtil.isNotBlank(cBlockParams)) {
            bb.putString(cBlockParams, encoder);
        }

        // bb.order(ProtocolDTO.byteOrder);
        // bb.putInt(body.length());

        byte[] b = new byte[bb.position()];

        System.out.println("send bytes=" + b.length);

        bb.flip();
        bb.get(b);

        out.write(b);
    }

    private static String buildHead(String transCode) {
        String head = null;
        // if ("3100".equals(transCode)) {
        // head = build3100Head();
        // } else if ("6200".equals(transCode)) {
        // head = build6200Head();
        // }

        head = buildDefaultHead(transCode);

        return head;
    }

    private static String buildDefaultHead(String transCode) {
        // �汾�� 2 ĿǰĬ��01
        // ���״��� 4 0001
        // ��������� 6
        // ���д��� 6
        // ������ˮ�� 20 ���㲹��
        // �������� 8 YYYYMMDD
        // ����ʱ�� 6 HH24MiSS
        // ��¼���� 8 ǰ��0

        StringBuilder sb = new StringBuilder();
        sb.append("01");
        sb.append(transCode);// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ���״���
        sb.append("000001");// ���������

        sb.append("001001");// ���д���

        sb.append("00100100100100002222");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ǰ̨��ˮ��12

        sb.append("20130822");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ��������

        sb.append("153022");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ����ʱ��

        sb.append("00000001");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ��¼����

        return sb.toString();
    }

    private static String build6200Head() {
        StringBuilder sb = new StringBuilder();

        // bankAcc �����˺� C(30) ���пͻ������˺� M
        // fundAcc �����˺� C(30) �������Ľ����˺� M
        // moneyType ���ִ��� C(3) CNY������� HKD���۱� USD����Ԫ M
        // transAmount ת�˽�� N(15) ��λ��ȷ���� M
        sb.append("");

        return sb.toString();
    }

    private static String build6200Params() {

        // bankAcc �����˺� C(30) ���пͻ������˺� M
        // fundAcc �����˺� C(30) �������Ľ����˺� M
        // moneyType ���ִ��� C(3) CNY������� HKD���۱� USD����Ԫ M
        // transAmount ת�˽�� N(15) ��λ��ȷ���� M
        StringBuilder sb = new StringBuilder();
        sb.append(CodecUtils.alignLeft("22222222222222", "", 30, " "));

        sb.append(CodecUtils.alignLeft("33333333333333", "", 30, " "));

        sb.append("CNY");
        sb.append("000000000010000");
        return sb.toString();
    }

    private static String build3101Body() {
        // BankAcc �����˺� 22 ���пͻ��Ĵ��ۺ� M
        // AccType �˺����� 1 0�����п���1������ M
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.alignLeft("333333332222222222222", 22, " "));

        sb.append("0");

        return sb.toString();
    }

    public static String build3101Head() {

        // ���״��� 4 0001
        // �������루���л������룩 6 811801 ȡ�Խ�����ά����
        // ������� 6 811800 ȡ�Խ�����ά����
        // ���׹�Ա�� 6 980066ȡ�Խ�����ά����
        // ǰ̨��ˮ�� 12
        // �������� 8 YYYYMMDD
        // ����ʱ�� 6 HH24MiSS
        // ������Դ 2 Y������Ԥ��ö��
        // �ļ��� 12 �󲹿գ������һ������
        // �ļ����� 8 ��¼������ǰ��0

        StringBuilder sb = new StringBuilder();

        sb.append("3101");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ���״���
        sb.append("000001");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ��������
        sb.append("000301");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // �������

        sb.append("001001");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ��Ա��

        sb.append("001001001001");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ǰ̨��ˮ��12

        sb.append("20130822");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ��������

        sb.append("153022");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ����ʱ��
        sb.append("Y ");

        sb.append("filename    ");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // �ļ���

        sb.append("00000001");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ��¼����

        return sb.toString();

    }

    private static String build3500Body() {

        // CompanyNo ���������
        // BankAcc �����˺�
        // AccType �˺�����
        // FundAcc �����˺�
        // PinBlock �����˺�����
        // AccType �˺���� ??????
        // IDType ֤�����
        // IDNo ֤������
        // CustName �ͻ�����
        // Sex �ͻ��Ա�

        StringBuilder sb = new StringBuilder();

        sb.append("323232").append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ���������
        sb.append("100000033333"); // �����ʺ�
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        sb.append("1").append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // �ʺ�����

        sb.append("00000101"); // �����ʺ�
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        sb.append("123456"); // �����˺�����
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        // sb.append("1"); // 0:����1�����п���ĿǰĬ��Ϊ0
        // sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        sb.append("2");// ֤�����
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        sb.append("238383833338299292289"); // ֤������
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        sb.append("zhengwei");// �ͻ�����

        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);
        // �Ա�
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        // // �Ƽ��˱��
        // sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);
        //
        // // �绰����
        // sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);
        //
        // // �ֻ�����
        // sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);
        //
        // // �������
        // sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);
        //
        // // �����ʼ�
        // sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);
        //
        // // ��������
        // sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);
        //
        // // ͨѶ��ַ
        // sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        return sb.toString();
    }

    private static String build3500Header() {

        // ���״���
        // ���׻���
        // �������
        // ��Ա��
        // ǰ̨��ˮ��
        // ��������
        // ����ʱ��
        // �ļ���
        // �ļ�����

        StringBuilder sb = new StringBuilder(); // �汾��

        sb.append("3500");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ���״���
        sb.append("000001");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ���������
        sb.append("000301");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // �������

        sb.append("001001");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ��Ա��

        sb.append("001001");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ǰ̨��ˮ��

        sb.append("20130822");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ��������

        sb.append("153022");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ����ʱ��

        sb.append("filename    ");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // �ļ���

        sb.append("00000001");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ��¼����

        return sb.toString();
    }

    /**
     * ��������������ת������(�˽�)
     * 
     * @return
     */
    private static String buildBankToExchange() {

        // �����˺� C(30) ���пͻ������˺�
        // �����˺� C(30) �������Ľ����˺�
        // ���ִ��� C(4) CNY������� HKD���۱� USD����Ԫ
        // ת�˽�� N(15) ��λ��ȷ����
        // �����˺����� C(16) �����˺������������Ĵ���

        StringBuilder sb = new StringBuilder();
        sb.append("4444444333").append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);
        sb.append("00000101"); // �����ʺ�
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);
        sb.append("CNY"); // �����
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        sb.append("10000").append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ���

        sb.append("123456"); // �����˺�����

        return sb.toString();
    }

    private static String buildBody() {

        StringBuilder sb = new StringBuilder();
        sb.append("100000033333"); // �����ʺ�
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        sb.append("00000101"); // �����ʺ�
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        sb.append("CNY"); // �����
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        sb.append("123456"); // �����˺�����
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        sb.append("1"); // 0:����1�����п���ĿǰĬ��Ϊ0
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        sb.append("2");// ֤�����
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        sb.append("238383833338299292289"); // ֤������
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        sb.append("zhengwei");// �ͻ�����

        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);
        // �Ա�
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        // �Ƽ��˱��
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        // �绰����
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        // �ֻ�����
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        // �������
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        // �����ʼ�
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        // ��������
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        // ͨѶ��ַ
        sb.append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);

        return sb.toString();
    }

    private static String buildHeader() {

        // �汾�� 2 ĿǰĬ��01
        // ���״��� 4 0001
        // ��������� 6
        // ���д��� 6
        // ������ˮ�� 20 ���㲹��
        // �������� 8 YYYYMMDD
        // ����ʱ�� 6 HH24MiSS
        // ��¼���� 8 ǰ��0

        StringBuilder sb = new StringBuilder(); // �汾��
        sb.append("01");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR);
        sb.append("6200");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ���״���
        sb.append("000001");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ���������

        sb.append("001001");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ���д���

        sb.append("00100193939393939443");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ������ˮ��

        sb.append("20130822");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ��������

        sb.append("153022");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ����ʱ��

        sb.append("00000001");// .append(com.runssnail.monolith.socket.Constants.DEFAULT_SPLIT_CHAR); // ��¼����

        return sb.toString();
    }

}
