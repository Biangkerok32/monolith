package com.kongur.monolith.socket.mina;




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
