package com.runssnail.monolith.socket.message.header;

/**
 * ����ͷ
 * 
 * @author zhengwei
 * 
 * @date 2014-1-26
 *
 */
public interface Header {
    
    /**
     * ���״���
     * 
     * @return
     */
    public String getTransCode();

    public void setTransCode(String transCode);

    /**
     * �Ƿ�ɹ�
     * 
     * @return
     */
    public boolean isSuccess();

    /**
     * ����ͷ�̶�����(�ֽ�)
     * 
     * @return
     */
    public int getBytesLen();

}
