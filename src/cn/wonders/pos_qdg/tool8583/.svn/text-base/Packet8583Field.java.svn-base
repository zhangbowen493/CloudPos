package cn.wonders.pos_qdg.tool8583;
/**
 * 表示每个域的详细信息
 */
public class Packet8583Field {
	 /**
     * 每个域放置数据类型
     */
	private FieldType fieldType;
	/**
	 * 每个域放置的数据的编码方式
	 */
	private FieldCodeType fieldCodeType;
	
	/**
	 * 用于表示不定长域的长度表示方式
	 */
	private FieldCodeType fieldLengthType;
	
	/**
	 * 每个域对应的key
	 */
	private String fieldKey;
	/**
	 * 每个域对应的最大字节长度 如果LL LLL 不起作用
	 */
	private int fieldMaxSize;
	/**
	 * 每个域对应的序列
	 */
	private int fieldIndex;
	/**
	 * 每个子域对应的tag
	 */
	private String fieldTag;
	public FieldType getFieldType() {
		return fieldType;
	}
	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}
	public FieldCodeType getFieldCodeType() {
		return fieldCodeType;
	}
	public void setFieldCodeType(FieldCodeType fieldCodeType) {
		this.fieldCodeType = fieldCodeType;
	}
	public FieldCodeType getFieldLengthType() {
		return fieldLengthType;
	}
	public void setFieldLengthType(FieldCodeType fieldLengthType) {
		this.fieldLengthType = fieldLengthType;
	}
	public String getFieldKey() {
		return fieldKey;
	}
	public void setFieldKey(String fieldKey) {
		this.fieldKey = fieldKey;
	}
	public int getFieldMaxSize() {
		return fieldMaxSize;
	}
	public void setFieldMaxSize(int fieldMaxSize) {
		this.fieldMaxSize = fieldMaxSize;
	}
	public int getFieldIndex() {
		return fieldIndex;
	}
	public void setFieldIndex(int fieldIndex) {
		this.fieldIndex = fieldIndex;
	}
	public Packet8583Field() {
		super();
	}
	public Packet8583Field(FieldType fieldType, FieldCodeType fieldCodeType,
			FieldCodeType fieldLengthType, String fieldKey, int fieldMaxSize,
			int fieldIndex) {
		super();
		this.fieldType = fieldType;
		this.fieldCodeType = fieldCodeType;
		this.fieldLengthType = fieldLengthType;
		this.fieldKey = fieldKey;
		this.fieldMaxSize = fieldMaxSize;
		this.fieldIndex = fieldIndex;
	}
	public Packet8583Field(FieldType fieldType, FieldCodeType fieldCodeType,
			FieldCodeType fieldLengthType,String fieldTag , int fieldMaxSize,
			int fieldIndex,String fieldKey) {
		super();
		this.fieldType = fieldType;
		this.fieldCodeType = fieldCodeType;
		this.fieldLengthType = fieldLengthType;
		this.fieldKey = fieldKey;
		this.fieldMaxSize = fieldMaxSize;
		this.fieldIndex = fieldIndex;
		this.fieldTag=fieldTag;
	}
	public String getFieldTag() {
		return fieldTag;
	}
	public void setFieldTag(String fieldTag) {
		this.fieldTag = fieldTag;
	}
}
