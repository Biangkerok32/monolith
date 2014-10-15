package com.runssnail.monolith.socket.mina;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.runssnail.monolith.socket.message.codec.DefaultMessageCodecFactory;
import com.runssnail.monolith.socket.mina.DefaultProtocolCodecFactory;



public class CodecServerTest {

    private static final Log  logger   = LogFactory.getLog(CodecServerTest.class);
    public static final int   port     = 10000;

    private NioSocketAcceptor acceptor = null;

    public static void main(String[] args) throws IOException {

        // testCloseService();

        // testBankTransToExchange6200(); // ���
        
        
        CodecServerTest server = new CodecServerTest();
        server.start("6200");

        System.out.println("server started...");

    }

    /**
     * ����µĲ��Թ���ʱ��������Ҫ�����Ӧ��response
     * 
     * @param transCode
     * @return
     */
    private Response createResponse(String transCode) {

        Response res = null;

        if ("6200".equals(transCode)) {
            res = createTransferResponseRes6200();
        }

        return res;
    }
    
    
    /**
     * 
     * ���ñ������
     * 
     * @return
     */
    private static DefaultMessageCodecFactory createMessageCodecFactory() {

        DefaultMessageCodecFactory messageCodecFactory = new DefaultMessageCodecFactory();
        messageCodecFactory.putMessageDecoder("6200", new EBankToExchangeMessageDecoder());
        
        messageCodecFactory.putMessageEncoder("6200", new EBankToExchangeMessageEncoder());

        return messageCodecFactory;
    }

    public void start(final String transCode) throws IOException {
        acceptor = new NioSocketAcceptor();
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
        chain.addLast("logger", new LoggingFilter());

        DefaultMessageCodecFactory messageCodecFactory = createMessageCodecFactory();

        DefaultProtocolCodecFactory protocolCodecFactory = new DefaultProtocolCodecFactory();
        DefaultProtocolDecoder protocolDecoder = new DefaultProtocolDecoder();
        protocolDecoder.setMessageCodecFactory(messageCodecFactory);
        protocolCodecFactory.setProtocolDecoder(protocolDecoder);

        DefaultProtocolEncoder encoder = new DefaultProtocolEncoder();
        encoder.setMessageCodecFactory(messageCodecFactory);

        protocolCodecFactory.setProtocolEncoder(encoder);

        chain.addLast("protocol", new ProtocolCodecFilter(protocolCodecFactory));
        acceptor.setHandler(new IoHandlerAdapter() {

            @Override
            public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
                logger.error("error.", cause);
                session.close(false);
            }

            @Override
            public void messageReceived(IoSession session, Object message) throws Exception {
                logger.info("handler get message:" + message);

                System.out.println("handler get message:" + message);
                
                session.write(createResponse(transCode));
                session.close(false);
            }

        });
        // new ProtocolIoHandler()
        // acceptor.setHandler((ProtocolIoHandler)beanFactory.getBean("protocolIoHandler"));
        acceptor.bind(new InetSocketAddress(port));
    }

    

    /**
     * ��������������ת������(���)
     * 
     * @return
     */
    protected static EBankToExchangeResponse createTransferResponseRes6200() {
        // /**
        // * �����ʺ�
        // */
        // private String bankAccount;
        //
        // /**
        // * �������ʽ��ʺ�
        // */
        // private String fundAccount;
        //
        // /**
        // * ���Ҵ���
        // */
        // private String moneyType;
        //
        // /**
        // * ת�˽��
        // */
        // private Long transAmount;
        //
        EBankToExchangeResponse res = new EBankToExchangeResponse();
        res.setBankAccount("33333333");
        res.setFundAccount("02222222");
        res.setMoneyType("cny");
        res.setTransAmount(10000L);
        return res;
    }

    
    public void endServer() {
        if (acceptor != null) {
            acceptor.dispose();
        }
    }
}
