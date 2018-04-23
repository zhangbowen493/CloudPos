package cn.wonders.pos_qdg.tool8583;


/**
 * 添加每个域的信息到table中
 */
public class Packet8583Config {
	public static Packet8583ConvertTable table;
	static {
		table = new Packet8583ConvertTable();

		table.addField(new Packet8583Field(FieldType.LL,
				FieldCodeType.BCD_COMPRESS, FieldCodeType.H_BIGGER_ENDIAN,
				"mainAccount", 19, 2));// 主账号
		table.addField(new Packet8583Field(FieldType.N_PADDING_ZERO_LEFT,
				FieldCodeType.BCD_COMPRESS, null, "tradeNum", 6, 3));// 交易处理码
		table.addField(new Packet8583Field(FieldType.N_PADDING_ZERO_LEFT,
				FieldCodeType.BCD_COMPRESS, null, "tradeAmount", 12, 4));// 交易金额

		table.addField(new Packet8583Field(FieldType.N_PADDING_ZERO_LEFT,
				FieldCodeType.BCD_COMPRESS, null, "posSerial", 6, 11));// 受卡方系统跟踪号
		table.addField(new Packet8583Field(FieldType.N_PADDING_ZERO_LEFT,
				FieldCodeType.BCD_COMPRESS, null, "recCardTime", 6, 12));// 受卡方所在地时间
		table.addField(new Packet8583Field(FieldType.N_PADDING_ZERO_LEFT,
				FieldCodeType.BCD_COMPRESS, null, "recCardDate", 4, 13));// 受卡方所在日期
		table.addField(new Packet8583Field(FieldType.N_PADDING_ZERO_LEFT,
				FieldCodeType.BCD_COMPRESS, null, "cardValidityPeriod", 4, 14));// 卡有效期
		table.addField(new Packet8583Field(FieldType.N_PADDING_ZERO_LEFT,
				FieldCodeType.BCD_COMPRESS, null, "dateOfSettlement", 4, 15));// 清算日期

		table.addField(new Packet8583Field(FieldType.N_PADDING_ZERO_LEFT,
				FieldCodeType.BCD_COMPRESS, null, "pointOfServiceEntryMode",
				3, 22));// 服务点输入方式码
		table.addField(new Packet8583Field(FieldType.N_PADDING_ZERO_LEFT,
				FieldCodeType.BCD_COMPRESS_RIGHT, null,"cardSequenceNumber", 3, 23));// 卡序列号

		table.addField(new Packet8583Field(FieldType.N_PADDING_ZERO_LEFT,
				FieldCodeType.BCD_COMPRESS, null,
				"pointOfServiceConditionMode", 2, 25));// 服务点条件码
		table.addField(new Packet8583Field(FieldType.N_PADDING_ZERO_LEFT,
				FieldCodeType.BCD_COMPRESS, null, "pointOfServicePinNumber",
				2, 26));// 服务点PIN获取码

		table.addField(new Packet8583Field(FieldType.LL,
				FieldCodeType.BCD_COMPRESS, FieldCodeType.H_BIGGER_ENDIAN,
				"acquiringCode", 11, 32));// 受理方标识码

		table.addField(new Packet8583Field(FieldType.LL,
				FieldCodeType.BCD_COMPRESS, FieldCodeType.H_BIGGER_ENDIAN,
				"track2Data", 37, 35));// 2磁道数据
		table.addField(new Packet8583Field(FieldType.LLL,
				FieldCodeType.BCD_COMPRESS, FieldCodeType.H_BIGGER_ENDIAN,
				"track3Data", 104, 36));// 3磁道数据
		table.addField(new Packet8583Field(FieldType.AN_PADDING_SPACE_RIGHT,
				FieldCodeType.ASCll, null, "retrievalReferenceNumber", 12, 37));// 检索参考号
		table.addField(new Packet8583Field(FieldType.AN_PADDING_SPACE_RIGHT,
				FieldCodeType.ASCll, null, "respCode", 2, 39));// 应答码

		table.addField(new Packet8583Field(FieldType.ANS_PADDING_SPACE_RIGHT,
				FieldCodeType.ASCll, null, "cardAcceptorTerminalCode", 8, 41));// 受卡机终端标识码
		table.addField(new Packet8583Field(FieldType.ANS_PADDING_SPACE_RIGHT,
				FieldCodeType.ASCll, null, "cardAcceptorCode", 15, 42));// 受卡方标识码

		table.addField(new Packet8583Field(FieldType.LL, FieldCodeType.ASCll,
				FieldCodeType.H_BIGGER_ENDIAN, "additionalRespData", 25, 44));// 附加响应数据
		table.addField(new Packet8583Field(FieldType.LLL, FieldCodeType.BCD_COMPRESS,
				FieldCodeType.H_BIGGER_ENDIAN, "privateData", 322, 48));// 附加数据-私有

		table.addField(new Packet8583Field(FieldType.AN_PADDING_SPACE_RIGHT,
				FieldCodeType.ASCll, null, "currencyCodeOfTransation", 3, 49));// 交易货币代码

		table.addField(new Packet8583Field(FieldType.B, FieldCodeType.B, null,
				"transationCiphertext", 8, 52));// 个人标识码数据
		table.addField(new Packet8583Field(FieldType.N_PADDING_ZERO_LEFT,
				FieldCodeType.BCD_COMPRESS, null,
				"securityRelateControlInfo", 16, 53));// 安全控制信息

		table.addField(new Packet8583Field(FieldType.LLL,
				FieldCodeType.SUBFIELD, FieldCodeType.H_BIGGER_ENDIAN,
				"ICCardInfo", 255, 55));// IC卡数据域

		table.addField(new Packet8583Field(FieldType.LLL,
				FieldCodeType.BCD_COMPRESS, FieldCodeType.H_BIGGER_ENDIAN,
				"reservedPrivate", 17, 60));// 自定义域
		table.addField(new Packet8583Field(FieldType.LLL,
				FieldCodeType.ASCll, FieldCodeType.H_BIGGER_ENDIAN,
				"reservedPrivate2", 512, 62));// 自定义域2
		table.addField(new Packet8583Field(FieldType.LLL, FieldCodeType.ASCll,
				FieldCodeType.H_BIGGER_ENDIAN, "reservedPrivate3", 63, 63));// 自定义域
		table.addField(new Packet8583Field(FieldType.B, FieldCodeType.B, null,
				"mac", 64, 64));// MAC验证码

	}
}
