package cn.wonders.pos_qdg.tool8583;
/**
 * 添加每个子域的信息到table中
 */
public class Packet8583SubfieldConfig {
	public static Packet8583ConvertTable subfieldtable;
	static {
		subfieldtable = new Packet8583ConvertTable();

		subfieldtable.addField(new Packet8583Field(FieldType.B,
				FieldCodeType.B, null, "applyCode", 64,1, "9F26"));// 应用密文
		subfieldtable.addField(new Packet8583Field(FieldType.B,
				FieldCodeType.B, null, "applyData", 8,2, "9F27"));// 应用信息数据
		subfieldtable.addField(new Packet8583Field(FieldType.LLL,
				FieldCodeType.B, FieldCodeType.H_BIGGER_ENDIAN,
				"issuingBandData", 256, 3,"9F10"));// 发卡行应用数据

		subfieldtable.addField(new Packet8583Field(FieldType.B,
				FieldCodeType.B, null, "unpredictableCode", 32, 4,"9F37"));// 不可预知数
		subfieldtable.addField(new Packet8583Field(FieldType.B,
				FieldCodeType.B, null, "dealCounter", 16, 5,"9F36"));// 受卡方所在地时间
		subfieldtable.addField(new Packet8583Field(FieldType.B,
				FieldCodeType.B, null, "verifiedResults", 40,6, "95"));// 终端验证结果
		subfieldtable.addField(new Packet8583Field(
				FieldType.N_PADDING_ZERO_LEFT, FieldCodeType.BCD_COMPRESS,
				null, "tradeData", 6,7, "9A"));// 交易日期
		subfieldtable.addField(new Packet8583Field(
				FieldType.N_PADDING_ZERO_LEFT, FieldCodeType.BCD_COMPRESS,
				null, "tradeType", 2,8, "9C"));// 交易类型

		subfieldtable.addField(new Packet8583Field(
				FieldType.N_PADDING_ZERO_LEFT, FieldCodeType.BCD_COMPRESS,
				null, "tradeMoney", 12, 9,"9F02"));// 交易金额
		subfieldtable.addField(new Packet8583Field(
				FieldType.N_PADDING_ZERO_LEFT, FieldCodeType.BCD_COMPRESS,
				null, "currencyCode", 3,10, "5F2A"));// 交易货币代码

		subfieldtable.addField(new Packet8583Field(FieldType.B,
				FieldCodeType.B, null, "interchangeProfile", 16, 11,"82"));// 应用交互特征
		subfieldtable.addField(new Packet8583Field(
				FieldType.N_PADDING_ZERO_LEFT, FieldCodeType.BCD_COMPRESS,
				null, "countryCode", 3,12, "9F1A"));// 终端国家代码

		subfieldtable.addField(new Packet8583Field(
				FieldType.N_PADDING_ZERO_LEFT, FieldCodeType.BCD_COMPRESS,
				null, "elseMoney", 12,13, "9F03"));// 其他金额

		subfieldtable.addField(new Packet8583Field(FieldType.B,
				FieldCodeType.B, null, "uePerformance", 24,14, "9F33"));// 终端性能
		subfieldtable.addField(new Packet8583Field(
				FieldType.AN_PADDING_SPACE_RIGHT, FieldCodeType.ASCll, null,
				"deviceNumber", 8, 15,"9F1E"));// 接口设备序列号
		subfieldtable.addField(new Packet8583Field(FieldType.LLL,
				FieldCodeType.B, FieldCodeType.H_BIGGER_ENDIAN, "fileName",
				128,16, "84"));// 专用文件名称
		subfieldtable.addField(new Packet8583Field(FieldType.B,
				FieldCodeType.B, null, "versionNumber", 16,17, "9F09"));// 应用版本号

		subfieldtable.addField(new Packet8583Field(FieldType.LL,
				FieldCodeType.BCD_COMPRESS, FieldCodeType.H_BIGGER_ENDIAN,
				"orderCounter", 4, 18,"9F41"));// 受卡机终端标识码
		subfieldtable.addField(new Packet8583Field(FieldType.B,
				FieldCodeType.B, null, "userVerifiedResults", 24, 19,"9F34"));// 持卡人验证结果

		subfieldtable.addField(new Packet8583Field(
				FieldType.N_PADDING_ZERO_LEFT, FieldCodeType.BCD_COMPRESS,
				null, "termTYP", 2,20, "9F35"));// 终端类型

		subfieldtable.addField(new Packet8583Field(FieldType.B,
				FieldCodeType.B, null, "productMarking", 128, 21,"9F63"));// 卡产品标识

		subfieldtable.addField(new Packet8583Field(
				FieldType.A_PADDING_SPACE_RIGHT, FieldCodeType.ASCll, null,
				"licenseKey", 6,22, "9F74"));// 个人标识码数据
		subfieldtable.addField(new Packet8583Field(
				FieldType.AN_PADDING_SPACE_RIGHT, FieldCodeType.ASCll, null,
				"authorizationResponseCode", 2, 23,"8A"));// 授权响应码
	}
}
