package com.runssnail.monolith.socket.mina;




/**
 * 
 * ��������
 * 
 * @author zhengwei
 *
 */
public interface Request {
    
    String getTransCode();

	/**
	 * ���ý��״���
	 * 
	 * @return
	 */
	void setTransCode(String transCode);
}
