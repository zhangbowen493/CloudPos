package cn.wonders.pos_qdg.tool8583;
/**
 * 表示每个域放置的数据类型
 */
public enum FieldType {
	/**
	 * ascii码 右补空格
	 */
	A_PADDING_SPACE_RIGHT,
	/**
	 * 数字 左补零
	 */
	N_PADDING_ZERO_LEFT,
	/**
	 * 特殊字符  右补空格
	 */
	S_PADDING_SPACE_RIGHT,
	/**
	 * 字母或数字 右补空格
	 */
	AN_PADDING_SPACE_RIGHT,
	
	/**
	 * 字母数字或特殊符号  右补空格
	 */
	ANS_PADDING_SPACE_RIGHT,
	/**
	 * 字母或特殊符号 右补空格
	 */
	AS_PADDING_SPACE_RIGHT,

	/**
	 * 可变长域，长度标示两位
	 */
	LL,
	
	/**
	 * 可变长域，长度标示三位
	 */
	LLL,
	/**
	 * 二进制
	 */
	B
}
